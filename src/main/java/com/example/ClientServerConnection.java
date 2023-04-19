package com.example;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.AuthenticationException;

import com.example.config.AppConfig;
import com.example.models.Task;
import com.example.utils.Cryptography;
import com.example.utils.DatabaseConnection;
import com.example.utils.StringUtils;

public class ClientServerConnection extends Thread {

    private static final Logger LOGGER = Logger.getLogger(ClientServerConnection.class.getName());

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private Socket clientSocket;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private DatabaseConnection dbConnection;

    public ClientServerConnection(Socket clientSocket, PublicKey publicKey, PrivateKey privateKey) throws IOException {
        this.clientSocket = clientSocket;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.inputStream = new ObjectInputStream(clientSocket.getInputStream());
        LOGGER.info("Connection made using iNet address "+ this.clientSocket.getInetAddress());
    }

    @Override
    public void run() {

        try {
            // Write public key to the stream
            byte[] pubKeyBytes = publicKey.getEncoded();
            outputStream.writeObject(pubKeyBytes);

            while(true){

                // Listen
                Object obj = inputStream.readObject();

                //Authorization request
                if(obj instanceof byte[]){
                    var authRequest = new String(Cryptography.decrypt(privateKey, (byte[]) obj));
                    var username = authRequest.split(AppConfig.DELIMITER)[0];
                    if(isAuthenticated(authRequest)){
                        LOGGER.info("User '"+ username +"' authenticated");
                        outputStream.writeObject(
                            StringUtils.composeTransportString(
                                "true", 
                                "Welcome "+ StringUtils.capitalize(username)));
                    }
                }

                // Perform task
                else if (obj instanceof Task){
                    Task task = (Task) obj;
                    task.execute();
                    outputStream.writeObject(task);
                    LOGGER.info("Task completed: " + task.getClass().getSimpleName());
                }
            }
        } 
        catch (EOFException e) {
            LOGGER.info("Connection terminated");
        }
        catch (Exception e) {
            LOGGER.info("Exception: "+ e.getMessage());
        }
        finally {
            try {
                clientSocket.close();
            } 
            catch (IOException e) {
                LOGGER.info("Error closing the socket");
            }
        }
    }

    private boolean isAuthenticated(String authString) throws IOException, SQLException{
        try {
            dbConnection = new DatabaseConnection();
            var credentials = StringUtils.decomposeTransportString(authString);
            var username = credentials[0];
            var password = credentials[1];

            if(dbConnection.isValidUser(username)){
                if(dbConnection.isValidPassword(username, password)){
                    return true;
                }
                else {
                    LOGGER.info("Invalid password for user "+ username);
                    throw new AuthenticationException("Invalid password for user " + username);
                } 
            }
            else {
                LOGGER.info("Username '"+ username +"' is not valid");
                throw new AuthenticationException("Username '" + username + "' is not valid");
            } 
        } 
        catch (SQLException e) {
            LOGGER.info("Database connection failed: " + e.getMessage());
            outputStream.writeObject(
                    StringUtils.composeTransportString(
                            "error",
                            "Database connection failed"));
        } 
        catch (AuthenticationException e){
            LOGGER.info("AuthenticationException "+ e.getMessage());
            outputStream.writeObject(
                    StringUtils.composeTransportString(
                            "error",
                            e.getMessage()));
        }
        finally {
            dbConnection.close();
        }
        return false;
    }
    
}
