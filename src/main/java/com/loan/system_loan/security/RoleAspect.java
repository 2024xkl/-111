package com.loan.system_loan.security;

import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RoleAspect {

    @Around("@annotation(roleRequired)") // 目标是所有有 @RoleRequired 注解的方法
    public Object checkRole(ProceedingJoinPoint joinPoint, RoleRequired roleRequired) throws Throwable {
        // 获取请求中的角色信息，假设角色信息保存在请求的 Header 中
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String userRole = request.getHeader("role"); // 这里假设角色信息通过 header 传递

        if (userRole == null || !userRole.equals(roleRequired.value().name())) {
            // 如果没有角色信息或者角色不匹配，返回403错误
            return new ResponseMessage<>(HttpStatus.FORBIDDEN.value(), "权限不足", null);
        }

        // 继续执行方法
        return joinPoint.proceed();
    }
}