package org.example.nbcompany.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilTest {

    @Test
    void testGenerateToken() {
        // 准备
        Long userId = 1L;
        String username = "testuser";
        Integer userType = 1;

        // 执行
        String token = JwtUtil.generateToken(userId, username, userType);

        // 验证
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains(".")); // JWT token应该包含两个点
    }

    @Test
    void testParseToken() {
        // 准备
        Long userId = 1L;
        String username = "testuser";
        Integer userType = 1;
        String token = JwtUtil.generateToken(userId, username, userType);

        // 执行
        Jws<Claims> claims = JwtUtil.parseToken(token);

        // 验证
        assertNotNull(claims);
        assertNotNull(claims.getBody());
        assertEquals(String.valueOf(userId), claims.getBody().getSubject());
        assertEquals(username, claims.getBody().get("username"));
        assertEquals(userType, claims.getBody().get("userType"));
    }

    @Test
    void testGetUserId() {
        // 准备
        Long userId = 123L;
        String token = JwtUtil.generateToken(userId, "testuser", 1);

        // 执行
        Long result = JwtUtil.getUserId(token);

        // 验证
        assertEquals(userId, result);
    }

    @Test
    void testGetUserType() {
        // 准备
        Integer userType = 2;
        String token = JwtUtil.generateToken(1L, "testuser", userType);

        // 执行
        Integer result = JwtUtil.getUserType(token);

        // 验证
        assertEquals(userType, result);
    }

    @Test
    void testGenerateTokenWithDifferentValues() {
        // 测试不同的用户ID
        String token1 = JwtUtil.generateToken(1L, "user1", 1);
        String token2 = JwtUtil.generateToken(2L, "user2", 2);
        
        assertNotEquals(token1, token2);
        
        // 验证解析结果
        assertEquals(1L, JwtUtil.getUserId(token1));
        assertEquals(2L, JwtUtil.getUserId(token2));
        assertEquals(1, JwtUtil.getUserType(token1));
        assertEquals(2, JwtUtil.getUserType(token2));
    }

    @Test
    void testTokenExpiration() {
        // 准备
        String token = JwtUtil.generateToken(1L, "testuser", 1);
        
        // 验证token可以正常解析
        assertDoesNotThrow(() -> {
            Jws<Claims> claims = JwtUtil.parseToken(token);
            assertNotNull(claims);
        });
    }
} 