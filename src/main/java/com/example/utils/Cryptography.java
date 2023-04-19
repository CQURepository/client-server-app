package com.example.utils;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

import com.example.config.AppConfig;

public class Cryptography {
    
    public static KeyPair buildKeyPair() throws NoSuchAlgorithmException{
        var keyPairGenerator = KeyPairGenerator.getInstance(AppConfig.ENCODING_ALGORITHM);
        keyPairGenerator.initialize(AppConfig.KEY_SIZE);
        return keyPairGenerator.genKeyPair();
    }

    public static byte[] encrypt(PublicKey publicKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance(AppConfig.ENCODING_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance(AppConfig.ENCODING_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey, cipher.getParameters());
        return cipher.doFinal(encrypted);
    }
    
}
