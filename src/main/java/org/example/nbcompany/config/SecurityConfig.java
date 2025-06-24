package org.example.nbcompany.config;

import org.example.nbcompany.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                // 认证相关端点
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                
                // 企业相关端点
                .requestMatchers("/api/v1/companies").permitAll()
                .requestMatchers("/api/v1/companies/**").permitAll()
                .requestMatchers("/api/companies").permitAll()
                .requestMatchers("/api/companies/**").permitAll()
                
                // 新闻相关端点（可能需要公开访问）
                .requestMatchers("/api/news/**").permitAll()
                .requestMatchers("/api/v1/news/**").permitAll()
                
                // 课程相关端点需要认证
                .requestMatchers("/api/courses/**").hasRole("USER")
                .requestMatchers("/api/v1/courses/**").hasRole("USER")
                
                // Mobile相关端点需要认证
                .requestMatchers("/api/v1/mobile/**").authenticated()
                .requestMatchers("/api/mobile/**").authenticated()
                
                // 测试端点
                .requestMatchers("/api/test/**").permitAll()
                
                // 系统端点
                .requestMatchers("/error").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/").permitAll()
                
                // 用户相关端点需要认证
                .requestMatchers("/api/v1/user/**").hasRole("USER")
                .requestMatchers("/api/user/**").hasRole("USER")
                
                // 其他所有请求需要认证
                .anyRequest().authenticated()
            )
            .addFilterBefore(new JwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
} 