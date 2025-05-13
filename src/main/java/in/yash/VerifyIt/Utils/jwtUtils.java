package in.yash.VerifyIt.Utils;

import in.yash.VerifyIt.service.impl.CustomUserDetailsService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class jwtUtils {

    private final String SECRET_KEY="91211283ff83c0d5ed693fccb20a072966d300628711bf83b65af2e113e93065";


    private final CustomUserDetailsService customUserDetailsService;

    public String generateToken(String email){
        UserDetails user =customUserDetailsService.loadUserByUsername(email);
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .claim(null,null)
                .signWith(SignatureAlgorithm.HS256,SECRET_KEY)
                .compact();


    }
}
