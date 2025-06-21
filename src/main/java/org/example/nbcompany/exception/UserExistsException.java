package org.example.nbcompany.exception;

/**
 * 用户已存在异常
 */
public class UserExistsException extends RuntimeException {
    
    public UserExistsException(String username) {
        super("用户名 '" + username + "' 已被使用，请选择其他用户名");
    }
} 