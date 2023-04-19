package com.example.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StringUtilsTest {

    @Test
    void testCapitalize() {
        assertEquals("Hello", StringUtils.capitalize("hello"));
    }

    @Test
    void testComposeTransportString() {
        assertEquals("true:This is a test", StringUtils.composeTransportString("true", "This is a test"));
    }

    @Test
    void testDecomposeTransportString() {
        var response = StringUtils.decomposeTransportString("true:This is a test");
        assertEquals("true", response[0]);
        assertEquals("This is a test", response[1]);
    }

}
