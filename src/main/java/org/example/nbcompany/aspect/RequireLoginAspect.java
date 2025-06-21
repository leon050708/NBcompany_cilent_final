package org.example.nbcompany.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.nbcompany.annotation.RequireLogin;
import org.example.nbcompany.dto.response.ApiResponse;
import org.example.nbcompany.util.UserContext;
import org.springframework.stereotype.Component;

/**
 * 处理@RequireLogin注解的AOP切面
 * 自动检查用户登录状态，如果未登录则返回错误响应
 */
@Aspect
@Component
public class RequireLoginAspect {

    @Around("@annotation(requireLogin)")
    public Object checkLogin(ProceedingJoinPoint joinPoint, RequireLogin requireLogin) throws Throwable {
        // 检查用户是否已登录
        if (UserContext.getCurrentUserId() == null) {
            return ApiResponse.error(401, "用户未登录");
        }
        
        // 用户已登录，继续执行原方法
        return joinPoint.proceed();
    }
} 