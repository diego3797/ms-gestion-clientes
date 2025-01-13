package com.prueba.ms_gestion_clientes.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * Genera un token JWT para un nombre de usuario dado.
     *
     * @param username El nombre de usuario para el cual se generará el token.
     * @return El token JWT generado.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000)) // por 1 hora
                .signWith(SECRET_KEY)
                .compact();
    }

    /**
     * Valida un token JWT y extrae los claims.
     *
     * @param token El token JWT a validar.
     * @return Los claims extraídos del token.
     */
    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
