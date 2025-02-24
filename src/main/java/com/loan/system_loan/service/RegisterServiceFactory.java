package com.loan.system_loan.service;

import com.loan.system_loan.pojo.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterServiceFactory {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private ClerkRegisterService clerkRegisterService;
    @Autowired
    private AssessorRegisterService assessorRegisterService;
    @Autowired
    private AdministratorRegisterService administratorRegisterService;

    public RegisterService getService(String roleType) {
        switch (RoleType.valueOf(roleType.toUpperCase())) {
            case USER:
                return userRegisterService;
            case CLERK:
                return clerkRegisterService;
            case ASSESSOR:
                return assessorRegisterService;
            case ADMINISTRATOR:
                return administratorRegisterService;
            // Add other roles
            default:
                throw new IllegalArgumentException("Unsupported role: " + roleType);
        }
    }
}
