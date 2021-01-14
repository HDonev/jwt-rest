package bg.mvr.the.kiss.rest.configuration.security.jwt;

import bg.mvr.the.kiss.rest.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * User: HDonev.
 * Date: 07.10.2020.
 * Time: 14:16.
 * Organization: DKIS MOIA.
 */
@Component
public class JwtUtils {

    @Value("${jwt.secretKey}")
    private String jwtKey;

    @Value("${jwt.tokenValidity}")
    private Integer tokenValidity;


    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey()).build().parseClaimsJws(token).getBody().get("email", String.class);
    }


    public String generateToken(User user) {
        Set<Object> claims = user.getAuthorities().stream().map(m -> new SimpleGrantedAuthority(m.getAuthority())).collect(Collectors.toSet());
        return Jwts.builder().claim("authorities", claims.toString()).claim("id", user.getId()).claim("email", user.getEmail()).setSubject(user.getUsername()).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + tokenValidity)).signWith(secretKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean validateToken(String authToken, String username) {
        String subject = Jwts.parserBuilder().setSigningKey(secretKey()).build().parseClaimsJws(authToken).getBody().getSubject();
        if (username.equals(subject)) {
            return true;
        }
        return false;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(jwtKey.getBytes());
    }
}
