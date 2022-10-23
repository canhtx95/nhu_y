package com.myapp.canhvm.security;

import com.myapp.canhvm.exception.BaseException;
import com.myapp.canhvm.exception.CustomRuntimeException;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private static final String SECRET_KEY = "abc123!!QQdzWpl";
    private final long JWT_EXPIRATION =60*1000*60;

    public String generateToken(SecurityUserDetails userDetails) {
        Date now = new Date();
        Date expired = new Date(now.getTime() + JWT_EXPIRATION);
        return Jwts.builder().setSubject(userDetails.getUsername())
                .setIssuedAt(now)
                .setExpiration(expired)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
            return token;
        }
        throw new CustomRuntimeException("token is null");
    }

    public String getUserNameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

}
