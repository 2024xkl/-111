package com.loan.system_loan.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name = "tb_loanapplication")
@Entity
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loanapplication_id")
    private Integer loanApplicationId;

    @Column(name = "user_id", nullable = false) // 外键字段/ 指定外键字段
    private Integer userId;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "project_id", nullable = false)
    private Integer projectId;

    @Column(name = "application_money")
    private BigDecimal Applicationmoney;

    //以月为单位
    @Column(name = "application_time")
    private Integer Applicationtime;

    // 0: 未审核, 1: 已审核未通过, 2: 已审核已通过
    @Column(name = "status")
    private Integer status = 0;  // 默认值为0，表示未审核

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
        return "LoanApplication{" +
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
