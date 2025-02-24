package com.loan.system_loan.pojo.dto;

import java.math.BigDecimal;

public class LoanApplicationRequestDTO {
    private Integer userId;
    private Integer accountId;
    private Integer projectId;
    private BigDecimal applicationMoney;
    private Integer applicationTime;

    // Getters 和 Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getApplicationMoney() {
        return applicationMoney;
    }

    public void setApplicationMoney(BigDecimal applicationMoney) {
        this.applicationMoney = applicationMoney;
    }

    public Integer getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(Integer applicationTime) {
        this.applicationTime = applicationTime;
    }
}
