package com.loan.system_loan.security;

import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.RoleType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class RoleInterceptor {
    @Autowired
    private HttpSession session;


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 Session 中获取当前登录用户的角色
        RoleType currentUserRole = (RoleType) session.getAttribute("role");

        // 获取方法上是否有 @RoleRequired 注解
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            RoleRequired roleRequired = handlerMethod.getMethodAnnotation(RoleRequired.class);

            if (roleRequired != null) {
                // 检查用户角色是否匹配
                if (currentUserRole != roleRequired.value()) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("权限不足");
                    return false;
                }
            }
        }
        return true;
    }
}
