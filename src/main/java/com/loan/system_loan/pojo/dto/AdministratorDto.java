package com.loan.system_loan.pojo.dto;

import com.loan.system_loan.pojo.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AdministratorDto {

    private Integer AdminId;
    @NotBlank(message = "管理员姓名不能为空")
    private String AdminName;
    @NotBlank(message = "密码不能为空")
    private String password;


    private RoleType role = RoleType.ADMINISTRATOR;


    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Integer getAdminId() {
        return AdminId;
    }

    public void setAdminId( Integer adminId) {
        AdminId = adminId;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName( String adminName) {
        AdminName = adminName;
    }

    public  String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }
}
