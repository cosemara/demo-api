package com.project.sample.api.demo.common.util;

import com.project.sample.api.demo.domain.entity.User;
import com.project.sample.api.demo.exception.CommonApiException;
import com.project.sample.api.demo.exception.ExceptionCode;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${jwt.base64-secret}")
    private String secretKey;

    // 토큰 유효시간 30분
    private long tokenValidTime = 30 * 60 * 1000L;

    public String generateJwtToken(User user) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                //.setSubject(user.getMdn())
                .setHeader(createHeader())
                .setIssuedAt(now)
                .setClaims(createClaims(user))
                .setExpiration(createExpireDate(now))
                .signWith(createSigningKey(),SignatureAlgorithm.HS512);

        return builder.compact();
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getClaimsFormToken(token);
            log.info("expireTime :" + claims.getExpiration());
            log.info("email :" + claims.get("email"));
            log.info("role :" + claims.get("role"));
            return true;
        } catch (ExpiredJwtException exception) {
            log.error("Token Expired");
            throw new CommonApiException(ExceptionCode.SESSION_INVALIDATE);
        } catch (JwtException exception) {
            log.error("Token Tampered");
            throw new CommonApiException(ExceptionCode.UNAUTHORIZED);
        } catch (NullPointerException exception) {
            log.error("Token is null");
            throw new CommonApiException(ExceptionCode.UNAUTHORIZED);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            throw new CommonApiException(ExceptionCode.UNAUTHORIZED);
        }
    }

    public String getTokenFromHeader(String header, String prefix) {
        String token = "";
        if (header != null && header.startsWith(prefix)) {
            token = header.split(" ")[1];
        }
        return token;
    }

    private Date createExpireDate(Date now) {
        return new Date(now.getTime() + tokenValidTime);
    }

    private Map<String, Object> createHeader() {
        Map<String, Object> header = new HashMap<>();

        header.put("typ", "JWT");
        header.put("alg", "HS512");
        header.put("regDate", System.currentTimeMillis());

        return header;
    }

    private Map<String, Object> createClaims(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        claims.put("role", user.getRole());
        return claims;
    }

    private Key createSigningKey() {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public Claims getClaimsFormToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                .build()
                .parseClaimsJws(token).getBody();
    }

    public String getClaimsData(String token, String key) {
        Claims claims = getClaimsFormToken(token);
        return (String) claims.get(key);
    }

    public String getUserNameFromToken(String token) {
        Claims claims = getClaimsFormToken(token);
        return claims.getSubject();
    }
}