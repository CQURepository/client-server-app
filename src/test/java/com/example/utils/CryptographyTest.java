package com.example.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.Test;

public class CryptographyTest {


    @Test
    void testBuildKeyPair() throws NoSuchAlgorithmException {
        var keyPair = Cryptography.buildKeyPair();
        assertNotEquals(keyPair.getPublic(), keyPair.getPrivate());
    }

    @Test
    void testEncryptAndDecrypt() throws Exception {
        var keyPair = Cryptography.buildKeyPair();
        String secret = "Super secret message";
        var encrypted = Cryptography.encrypt(keyPair.getPublic(), secret);
        assertNotEquals(secret, new String(encrypted));

        var decrypted = Cryptography.decrypt(keyPair.getPrivate(), encrypted);
        assertEquals(secret, new String(decrypted));

    }

}
