package com.jnas.books_marketplace_be;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * A simple test class to verify JUnit is working correctly
 */
public class SimpleTest {

    @Test
    void simpleAssertionTest() {
        // Simple test that will always pass
        assertEquals(4, 2 + 2);
        assertTrue(true);
        assertFalse(false);
    }
    
    @Test
    void stringTest() {
        String message = "Hello JUnit";
        assertNotNull(message);
        assertEquals("Hello JUnit", message);
        assertTrue(message.startsWith("Hello"));
        assertTrue(message.endsWith("JUnit"));
        assertEquals(11, message.length());
    }
}