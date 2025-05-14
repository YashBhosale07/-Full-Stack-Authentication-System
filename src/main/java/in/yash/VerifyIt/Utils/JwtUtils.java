package in.yash.VerifyIt.Utils;

import in.yash.VerifyIt.dto.InvalidAccessTokenException;
import in.yash.VerifyIt.service.impl.CustomUserDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final String SECRET_KEY = "91211283ff83c0d5ed693fccb20a072966d300628711bf83b65af2e113e93065";


    private final CustomUserDetailsService customUserDetailsService;

    public String generateToken(String email) {
        UserDetails user = customUserDetailsService.loadUserByUsername(email);
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (SignatureException e) {
            throw new InvalidAccessTokenException("Invalid JWT Signature");
        } catch (Exception e) {
            // 🔥 Show real issue!
            e.printStackTrace();
            throw new InvalidAccessTokenException("Error parsing the jwt: " + e.getMessage());
        }
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


}
