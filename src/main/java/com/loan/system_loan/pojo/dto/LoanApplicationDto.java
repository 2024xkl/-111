package com.loan.system_loan.pojo.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class LoanApplicationDto {

    private Integer loanApplicationId;

    @NotNull(message = "用户id不能为空")
    private Integer userId;

    @NotNull(message = "账户id不能为空")
    private Integer accountId;

    @NotNull(message = "项目id不能为空")
    private Integer projectId;

    @NotNull(message = "贷款金额不能为空")
    private BigDecimal Applicationmoney;

    //以月为单位
    @NotNull(message = "贷款时间不能为空")
    private Integer Applicationtime;


    // 0: 未审核, 1: 已审核未通过, 2: 已审核已通过
    private Integer status = 0; // 新的合并字段


    public Integer getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(Integer loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public BigDecimal getApplicationmoney() {
        return Applicationmoney;
    }

    public void setApplicationmoney(BigDecimal applicationmoney) {
        Applicationmoney = applicationmoney;
    }

    public Integer getApplicationtime() {
        return Applicationtime;
    }

    public void setApplicationtime(Integer applicationtime) {
        Applicationtime = applicationtime;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LoanApplicationDto{" +
                "loanApplicationId=" + loanApplicationId +
                ", userId=" + userId +
                ", accountId=" + accountId +
                ", projectId=" + projectId +
                ", Applicationmoney=" + Applicationmoney +
                ", Applicationtime=" + Applicationtime +
                ", status=" + status +
                '}';
    }

}
