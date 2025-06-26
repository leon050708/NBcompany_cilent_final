package org.example.nbcompany.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.nbcompany.util.JwtUtil;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        
        // 跳过不需要JWT验证的端点
        if (shouldSkipJwtValidation(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }
        
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                Long userId = JwtUtil.getUserId(token);
                Integer userType = JwtUtil.getUserType(token);
                
                // 根据用户类型设置角色
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                
                if (userType != null) {
                    if (userType == 2) {
                        // 超级管理员
                        authorities.add(new SimpleGrantedAuthority("ROLE_SUPER_ADMIN"));
                    } else if (userType == 1) {
                        // 企业用户
                        authorities.add(new SimpleGrantedAuthority("ROLE_COMPANY_USER"));
                    }
                }
                
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId, null, authorities
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                request.setAttribute("userId", userId);
                request.setAttribute("userType", userType);
            } catch (Exception e) {
                // 记录错误但不阻止请求继续
                logger.warn("JWT token validation failed: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
    
    private boolean shouldSkipJwtValidation(String requestURI) {
        // 公开端点，不需要JWT验证
        return requestURI.startsWith("/api/v1/auth/") ||
               requestURI.startsWith("/api/auth/") ||
               requestURI.equals("/api/v1/companies") ||
               requestURI.equals("/api/companies") ||
               requestURI.startsWith("/api/test/") ||
               requestURI.equals("/error") ||
               requestURI.startsWith("/actuator/") ||
               requestURI.startsWith("/swagger-ui/") ||
               requestURI.startsWith("/v3/api-docs/") ||
               requestURI.equals("/");
    }
} 