package com.loan.system_loan.pojo.dto;

import jakarta.persistence.Column;

public class AdministratorLoginDTO {
    private Integer AdminId;
    private String password;

    public Integer getAdminId() {
        return AdminId;
    }

    public void setAdminId(Integer adminId) {
        AdminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
