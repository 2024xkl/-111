package com.loan.system_loan.pojo.dto;

import com.loan.system_loan.pojo.RoleType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AssessorDto {

    private Integer AssessorId;

    @NotBlank(message = "审核员姓名不能为空")
    private String AssessorName;

    @NotBlank(message = "密码不能为空")
    private String password;


    private RoleType role = RoleType.ASSESSOR;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public  Integer getAssessorId() {
        return AssessorId;
    }

    public void setAssessorId(Integer assessorId) {
        AssessorId = assessorId;
    }

    public  String getAssessorName() {
        return AssessorName;
    }

    public void setAssessorName( String assessorName) {
        AssessorName = assessorName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password) {
        this.password = password;
    }
}
