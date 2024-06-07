package jh.park.screenback.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jh.park.screenback.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtils {

    private static final long TOKEN_VALIDITY = 86400000L;
    private static final long TOKEN_VALIDITY_REMEMBER = 2592000000L;
    private final SecretKey key;

    public JWTUtils(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String createToken(User user, boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + (rememberMe ? TOKEN_VALIDITY_REMEMBER : TOKEN_VALIDITY)); // 30 days or 1 day

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        return Jwts.builder()
                .claims(claims)
                .subject(String.valueOf(user.getId()))
                .issuedAt(new Date())
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    public Authentication verifyAndGetAuthentication(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(claims.get("role", String.class));
            return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, authorities);
        }  catch (ExpiredJwtException e) {
            return null; // Token is expired
        } catch (JwtException | IllegalArgumentException e) {
            token = null;
            return null;
        }
    }

    public String refreshToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            User user = new User();
            user.setId(Long.valueOf(claims.getSubject()));
            user.setRole(claims.get("role", String.class));
            return createToken(user, false);
        }  catch (ExpiredJwtException e) {
            return null; // Token is expired
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }
    public Long getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return Long.valueOf(claims.getSubject());
        }  catch (ExpiredJwtException e) {
            return null; // Token is expired
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}