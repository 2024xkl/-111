package com.loan.system_loan.pojo.dto;

import jakarta.persistence.Column;

public class AssessorLoginDTO {
    private Integer AssessorId;
    private String password;

    public Integer getAssessorId() {
        return AssessorId;
    }

    public void setAssessorId(Integer assessorId) {
        AssessorId = assessorId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
