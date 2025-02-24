package com.loan.system_loan.pojo.dto;

import jakarta.validation.constraints.NotBlank;

public class ClerkLoginDTO {
    private Integer ClerkId;
    private String password;

    public Integer getClerkId() {
        return ClerkId;
    }

    public void setClerkId(Integer clerkId) {
        ClerkId = clerkId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
