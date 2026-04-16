package com.example.crudapp.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
    }

    // ✅ Test token generation
    @Test
    void testGenerateToken() {
        String token = jwtUtil.generateToken("admin");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    // ✅ Test username extraction
    @Test
    void testExtractUsername() {
        String token = jwtUtil.generateToken("admin");

        String username = jwtUtil.extractUsername(token);

        assertEquals("admin", username);
    }

    // ✅ Test token validation (valid case)
    @Test
    void testValidateToken_Valid() {
        String token = jwtUtil.generateToken("admin");

        boolean isValid = jwtUtil.validateToken(token, "admin");

        assertTrue(isValid);
    }

    // ✅ Test token validation (invalid username)
    @Test
    void testValidateToken_InvalidUsername() {
        String token = jwtUtil.generateToken("admin");

        boolean isValid = jwtUtil.validateToken(token, "user");

        assertFalse(isValid);
    }

    // ✅ Test token expiration (basic check)
    @Test
    void testTokenNotExpiredImmediately() {
        String token = jwtUtil.generateToken("admin");

        boolean isValid = jwtUtil.validateToken(token, "admin");

        assertTrue(isValid); // should still be valid immediately
    }
}