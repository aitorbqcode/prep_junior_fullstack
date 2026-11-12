package com.app.taskmanagerPractice.service;

import com.app.taskmanagerPractice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

// Esta clase la usamos para crear token cuando un usuario inicia sesión
// Abre, lee y verifica si la firma es auténtica
// Comprueba si el token ya expiro o si no es correcto
// @Service le dice a Spring que esta clase contiene lógica de negocio y debe gestionarla como un Bean.
@Service
public class JwtService {

    //Entiendo que esto lo usamos para pasar un código de caracteres que obtenemos desde el application.propeties
    // que es la llave para encriptar los datos
    @Value("${jwt.secret}")          // en application.properties
    private String secretKey;

    //El tiempo desde que se ha iniciado sesión, hasta que deja de estar disponible que expira la sesión
    //También disponible en application properties
    @Value("${jwt.expiration}")      // en milisegundos, ej: 86400000 (24h)
    private long jwtExpiration;

    //Genera el token, con el correo, el rol, su creación, su expiración
    // signWith(getSigningKey()):Aplica una firma criptográfica al token utilizando tu clave secreta.
    // compact() Convierte toda la estructura de datos que configuraste en una única cadena de texto
    // claim() nos sirve guardar datos. En este caso, estás cogiendo el rol del usuario
    // y lo estás empaquetando y sellando dentro del propio token JWT. De esta forma para los distintos métodos con el token
    // spring pues ver si puedes acceder al método o no según el rol asignado
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getEmail()) //Indica quien es, el email.
                .claim("role", user.getRole().name())  // añade el rol como claim, añade mas datos secundarios importantes
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Añade también un método para extraer el rol del token:
    public String extractRole(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) //Usa la clave secreta para comprobar el token
                .build()//Empaquetas la configuración
                .parseSignedClaims(token)// Coge el String de texto del token JWT que le pasó el cliente, lo abre,
                // comprueba su estructura y verifica criptográficamente la firma usando tu clave secreta
                .getPayload() // Extrae el cuerpo del token el objeto claims
                .get("role", String.class);
    }

    // Extraer el email del token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Comprobar que el token sea válido a partir del email dado y que el token no haya expirado
    public boolean isTokenValid(String token, String email) {
        return extractEmail(token).equals(email) && !isTokenExpired(token);
    }

    //Comprobamos que el token no haya expirado, que la expiration date es después de la fecha actual
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    //Entiendo que es para extraer una campo del token aunque lo de dentro tengo mis dudas
    // Function<Claims, T> claimsResolver indica que parte en concreto tiene que extraer
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey()) //Usa la clave secreta para comprobar el token
                .build()//Empaquetas la configuración
                .parseSignedClaims(token) //Lee el token
                .getPayload();// Extrae el cuerpo del token el objeto claims
        return claimsResolver.apply(claims);
    }

    //Aplica la firma criptografica al token usando la clave secreta
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
