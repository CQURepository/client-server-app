package com.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Logger;

import com.example.config.AppConfig;
import com.example.utils.Cryptography;

public class Server {
    
    private static final Logger LOGGER = Logger.getLogger(Server.class.getName());

    public static void main(String args[]) throws NoSuchAlgorithmException, IOException {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(AppConfig.SERVER_PORT);
            LOGGER.info("Server started and listening on port "+ AppConfig.SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                KeyPair keyPair = Cryptography.buildKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                PrivateKey privateKey = keyPair.getPrivate();
                ClientServerConnection connection = new ClientServerConnection(clientSocket, publicKey, privateKey);
                connection.start();
            }
        } 

        catch (IOException e) {
            LOGGER.warning("Error: "+ e.getMessage());
        } 

        finally {
            serverSocket.close();
        }

    }
}
