package dev.nbcsparta.assignment.commerce_backoffice.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;

@Component
public class JwtProvider {

    private final String secret;
    private final long expiration;
    private SecretKey key;

    public JwtProvider(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expire-time}") long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    // 1. 객체 초기화: Secret Key를 암호화 알고리즘에 적합한 형태로 변환
    @PostConstruct
    protected void init() {
        byte[] keyBytes = Decoders.BASE64.decode(secret); // 혹은 secret.getBytes()
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // 2. 토큰 생성: 유저 정보를 받아 JWT 발급
    public String createToken(String username, List<String> roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .header().add("typ", "JWT").and() // 헤더: 타입 설정
                .subject(username)               // 내용: 유저 식별값
                .claim("roles", roles)             // 내용: 권한 정보 (커스텀 클레임)
                .issuedAt(now)                   // 내용: 발행 시간
                .expiration(validity)            // 내용: 만료 시간
                .signWith(key)                   // 서명: 우리 서버의 키로 암호화
                .compact();
    }

    // 3. 권한 조회: 토큰에 저장된 정보를 꺼내서 Spring Security의 Authentication 객체로 변환
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        @SuppressWarnings("unchecked")
        List<String> roles = claims.get("roles", List.class);

        // 토큰에 담긴 권한 정보 추출 (예: "ROLE_SUPER")
        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.startsWith("ROLE_") ? role : "ROLE_" + role))
                .toList();

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // 4. 토큰 검증: 유효한 토큰인지, 변조되진 않았는지 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 토큰이 변조되었거나, 만료되었거나, 형식이 잘못된 경우
            return false;
        }
    }
}