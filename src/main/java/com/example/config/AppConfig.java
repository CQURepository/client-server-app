package com.example.config;

public class AppConfig {
    
    //Common
    public static final int SERVER_PORT = 9876;
    public static final String DELIMITER = ":";

    //Encryption
    public static final int KEY_SIZE = 2048;
    public static final String SERVER_NAME = "localhost";
    public static final String ENCODING_ALGORITHM = "RSA";
    
    //Database connection
    public static final int DB_PORT = 3306;
    public static final String DB_HOST = "localhost";
    public static final String DB_NAME = "clients";
    public static final String DB_TABLE = "users";
    public static final String DB_USERNAME = "root";
    public static final String DB_PASSWORD = "P8vYPWhlIorBIrAv";
    public static final String DB_CONNECTION_STRING = String.format("jdbc:mysql://%s:%d/%s", DB_HOST, DB_PORT, DB_NAME);
    
    //UI
    public static final String USER_MENU = """
        PLEASE CHOOSE FROM THE FOLLOWING OPTIONS
        1. Perform a fibonacci sequence
        2. Perform a factorial equation of 2 numbers
        3. Find the greatest common divisor (GCD) of 2 numbers
        4. Exit the system
        Enter your option:""";
    public static final String USER_GREETING = """
        =====================================================
        %s
        =====================================================
        """;
}
