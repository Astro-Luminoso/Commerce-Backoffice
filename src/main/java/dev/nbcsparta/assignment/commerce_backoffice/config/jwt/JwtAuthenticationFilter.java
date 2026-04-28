package dev.nbcsparta.assignment.commerce_backoffice.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. 요청 헤더에서 토큰을 꺼내옵니다.
        String token = resolveToken(request);

        // 2. 토큰이 존재하고, 유효한지 JwtProvider에게 물어봅니다.
        if (token != null && jwtProvider.validateToken(token)) {
            // 3. 유효하다면 신분증(Authentication)을 발급받습니다.
            Authentication authentication = jwtProvider.getAuthentication(token);
            // 4. 보안 요원이 볼 수 있는 수첩(SecurityContextHolder)에 기록해둡니다.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 5. 다음 검문 단계(혹은 실제 컨트롤러)로 요청을 넘깁니다.
        filterChain.doFilter(request, response);
    }

    // 헤더의 "Authorization: Bearer <Token>"에서 토큰만 쏙 빼오는 메서드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}