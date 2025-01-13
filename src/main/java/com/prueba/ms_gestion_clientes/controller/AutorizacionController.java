package com.prueba.ms_gestion_clientes.controller;

import com.prueba.ms_gestion_clientes.config.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutorizacionController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Constructor de AutorizacionController.
     *
     * @param authenticationManager El gestor de autenticación.
     * @param jwtUtil Utilidad para la generación de tokens JWT.
     */
    public AutorizacionController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Endpoint para el inicio de sesión.
     *
     * @param username El nombre de usuario.
     * @param password La contraseña del usuario.
     * @return Un token JWT si la autenticación es exitosa.
     */
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return jwtUtil.generateToken(authentication.getName());
    }
}
