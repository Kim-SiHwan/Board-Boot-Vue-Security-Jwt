package com.example.boardbvsj.jwt;

import com.example.boardbvsj.service.MemberService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutionException;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final MemberService memberService;
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-validity-in-seconds}")
    long tokenValidityInSeconds;


    public String createToken(Authentication authentication){

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInSeconds);

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authentication.getAuthorities())
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validity)
                .setIssuedAt(new Date(now))
                .compact();


    }

    public Authentication getAuthentication(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        UserDetails userDetails = memberService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails,"", userDetails.getAuthorities());
    }


    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
        } catch (ExpiredJwtException e) {
            System.out.println("토큰만료!");



        } catch (UnsupportedJwtException e) {
        } catch (IllegalArgumentException e) {
        }
        return false;
    }

}
