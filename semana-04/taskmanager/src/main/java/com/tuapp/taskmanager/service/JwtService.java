package com.tuapp.taskmanager.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration:86400000}") // 24 horas por defecto
    private long expiration;

    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) //Coge el email
                .issuedAt(new Date()) //Fecha de la issue
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Cuando expira
                .signWith(getSignKey()) // Aplica la firma digital, usando la clave secreta de application.properties
                .compact(); //Ensamblamos el codigo en partes
    }

    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(getSignKey()) // Le decimos al parser cual es la clave secreta oficial. Si no es correcta la lectura falla por seguridad
                .build() //Constructe el objeto
                .parseSignedClaims(token)   // Desencripta/Valida el token y extrae las Claims
                .getPayload()//Obtiene el cuerpo (Payload) del JWT
                .getSubject(); //Extrae el atributo "sub" (donde guardaste el email)
                //Al crear el token hiciste .subject(email). El estándar JWT guarda ese valor bajo la clave "sub".
                // Este metodo simplemente lee ese campo y devuelve el String con el email.
    }

    //Comprueba si el token es auténtico y no ha caducado.
    public boolean isTokenValid(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private SecretKey getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        //secret que tenemos en application.propeties es una cadena codificada en Base64. Por lo que la descodificamos en su representacion binaria pura
        return Keys.hmacShaKeyFor(keyBytes);
        // Convierte ese array de bytes en un objeto de tipo SecretKey compatible con el algoritmo criptográfico HMAC-SHA (HS256). Ademas verifica que el array tenga la longitud requerida
    }
}