package com.example;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.example.config.AppConfig;
import com.example.models.Factorial;
import com.example.models.Fibonacci;
import com.example.models.Gcd;
import com.example.models.Task;
import com.example.utils.Cryptography;
import com.example.utils.StringUtils;

public class Client {

    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());

    private static PublicKey publicKey;

    public static void main(String[] args) throws NoSuchAlgorithmException, Exception {

        try (
                Scanner scan = new Scanner(System.in);
                Socket clientSocket = new Socket(AppConfig.SERVER_NAME, AppConfig.SERVER_PORT);
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());) {

            boolean validUser = false;
            String username = "";
            byte[] publicKeyBytes = (byte[]) in.readObject();
            X509EncodedKeySpec publicKeySpecification = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(AppConfig.ENCODING_ALGORITHM);
            publicKey = keyFactory.generatePublic(publicKeySpecification);

            while (true) {

                if (validUser) {
                    
                    System.out.println(AppConfig.USER_MENU);
                    Task task = null;

                    try {

                        switch (scan.nextInt()) {

                            case 1: // Fibonacci task
                                System.out.println("Enter the range limit");
                                int rangeVal = scan.nextInt();
                                task = new Fibonacci(rangeVal);
                                break;

                            case 2: // Factorial task
                                System.out.println("Enter the range limit");
                                int num = scan.nextInt();
                                task = new Factorial(num);
                                break;

                            case 3: // GCD task
                                System.out.println("Enter Number 1:");
                                int num1 = scan.nextInt();
                                System.out.println("Enter Number 2:");
                                int num2 = scan.nextInt();
                                task = new Gcd(num1, num2);
                                break;

                            case 4: // Exit the system
                                System.out.println("Goodbye "+ StringUtils.capitalize(username));
                                clientSocket.close();
                                LOGGER.info("User '"+ username +"' exiting");
                                System.exit(0);
                        }

                        // Write task to the stream and listen for a response
                        out.writeObject(task);
                        Object response = in.readObject();
                        System.out.printf("\n%s\n\n", ((Task) response).getResults());

                    } 
                    catch (InputMismatchException e) {
                        System.out.println("Invalid menu choice... Please try again");
                        scan.next();
                    }
                } 
                else {
                    // Authenticate the user
                    System.out.print("Please enter your username --> ");
                    username = scan.next();
                    System.out.print("Please enter your password --> ");
                    String password = scan.next();

                    byte[] authorizationRequest = Cryptography.encrypt(
                            publicKey,
                            StringUtils.composeTransportString(username, password));
                    out.writeObject(authorizationRequest);
                    
                    Object obj = in.readObject();

                    if (obj instanceof String) {
                        var auth = StringUtils.decomposeTransportString((String) obj);
                        System.out.printf(AppConfig.USER_GREETING, auth[1]);
                        validUser = Boolean.parseBoolean(auth[0]) ; 
                    }
                }
            }
        } catch (ClassCastException | IOException | ClassNotFoundException e) {
            LOGGER.warning(e.getMessage());
        }
    }

}
