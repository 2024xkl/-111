package com.loan.system_loan.pojo;

import jakarta.persistence.*;

@Table(name = "tb_assessor")
@Entity
public class Assessor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assessor_id")
    private Integer assessorId;

    @Column(name = "assessor_name")
    private String AssessorName;

    @Column(name = "password")
    private String password;

    private RoleType role = RoleType.ASSESSOR;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAssessorId() {
        return assessorId;
    }

    public void setAssessorId(Integer assessorId) {
        assessorId = assessorId;
    }

    public String getAssessorName() {
        return AssessorName;
    }

    public void setAssessorName(String assessorName) {
        AssessorName = assessorName;
    }

    @Override
    public String toString() {
        return "Assessor{" +
                "AssessorId=" + assessorId +
                ", AssessorName='" + AssessorName + '\'' +
                '}';
    }
}
