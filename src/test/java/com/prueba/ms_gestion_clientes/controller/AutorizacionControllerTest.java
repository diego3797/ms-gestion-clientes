package com.prueba.ms_gestion_clientes.controller;

import com.prueba.ms_gestion_clientes.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AutorizacionControllerTest {

    @InjectMocks
    private AutorizacionController autorizacionController;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginSuccessfully() {
        String username = "user";
        String password = "password";
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(jwtUtil.generateToken(username)).thenReturn("token");

        String token = autorizacionController.login(username, password);

        assertEquals("token", token);
    }

    @Test
    void loginWithInvalidCredentials() {
        String username = "user";
        String password = "wrongPassword";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> autorizacionController.login(username, password));
    }

    @Test
    void loginWithEmptyUsername() {
        String username = "";
        String password = "password";

        assertThrows(RuntimeException.class, () -> autorizacionController.login(username, password));
    }

    @Test
    void loginWithEmptyPassword() {
        String username = "user";
        String password = "";

        assertThrows(RuntimeException.class, () -> autorizacionController.login(username, password));
    }

    @Test
    void loginWithNullUsername() {
        String username = null;
        String password = "password";

        assertThrows(RuntimeException.class, () -> autorizacionController.login(username, password));
    }

    @Test
    void loginWithNullPassword() {
        String username = "user";
        String password = null;

        assertThrows(RuntimeException.class, () -> autorizacionController.login(username, password));
    }
}