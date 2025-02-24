package com.loan.system_loan.pojo.dto;

import com.loan.system_loan.pojo.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class ClerkDto {


    private Integer ClerkId;

    @NotBlank(message = "营业员姓名不能为空")
    private String ClerkName;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.CLERK;  // 默认角色为 CLERK

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Integer getClerkId() {
        return ClerkId;
    }

    public void setClerkId(Integer clerkId) {
        ClerkId = clerkId;
    }

    public  String getClerkName() {
        return ClerkName;
    }

    public void setClerkName(String clerkName) {
        ClerkName = clerkName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }
}
