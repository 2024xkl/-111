package com.loan.system_loan.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

public class RegisterDto {
    @NotBlank(message = "角色不能为空")
    private String roleType;

    @NotBlank(message = "用户名不能为空")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 12, message = "密码长度应在6到12位之间")
    private String password;

    private Map<String, Object> additionalFields; // 存放角色特定字段

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType( String roleType) {
        this.roleType = roleType;
    }

    public  String getUserName() {
        return userName;
    }

    public void setUserName( String userName) {
        this.userName = userName;
    }

    public  String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public Map<String, Object> getAdditionalFields() {
        return additionalFields;
    }

    public void setAdditionalFields(Map<String, Object> additionalFields) {
        this.additionalFields = additionalFields;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "roleType='" + roleType + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", additionalFields=" + additionalFields +
                '}';
    }
}
