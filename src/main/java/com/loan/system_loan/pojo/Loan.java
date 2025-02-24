package com.loan.system_loan.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name = "tb_loan")
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    private Integer loanId;

    @Column(name = "user_id") // 外键字段/ 指定外键字段
    private Integer userId;

    @Column(name = "date")
    private String date;

    @Column(name = "money")
    private BigDecimal money;

   //贷款年化利率
    @Column(name = "rate")
    private BigDecimal rate;

    //贷款期限
    @Column(name = "time")
    private Integer time;

    //超期年化利率
    @Column(name = "overtime_rate")
    private BigDecimal overtimeRate;

    //1支持提前还款 0不支持提前还款
    @Column(name = "advance_repay")
    private boolean advanceRepay;

    // 新增与 CreditReport 的关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private CreditReport creditReport;  // 反向关联到 CreditReport

    // Getters and setters
    public CreditReport getCreditReport() {
        return creditReport;
    }

    public void setCreditReport(CreditReport creditReport) {
        this.creditReport = creditReport;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public BigDecimal getOvertimeRate() {
        return overtimeRate;
    }

    public void setOvertimeRate(BigDecimal overtimeRate) {
        this.overtimeRate = overtimeRate;
    }

    public boolean isAdvanceRepay() {
        return advanceRepay;
    }

    public void setAdvanceRepay(boolean advanceRepay) {
        this.advanceRepay = advanceRepay;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "loanId=" + loanId +
                ", userId=" + userId +
                ", date='" + date + '\'' +
                ", money=" + money +
                ", rate=" + rate +
                ", time=" + time +
                ", overtimeRate=" + overtimeRate +
                ", advanceRepay=" + advanceRepay +
                '}';
    }
}
