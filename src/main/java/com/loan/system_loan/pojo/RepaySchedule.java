package com.loan.system_loan.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tb_repay_schedule")
public class RepaySchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repay_id")
    private Integer repayId;

    @Column(name = "loan_id", nullable = false)
    private Integer loanId;

    @Column(name = "repay_date", nullable = false)
    private String repayDate; // 还款日期

    @Column(name = "principal", nullable = false)
    private BigDecimal principal; // 本金

    @Column(name = "interest", nullable = false)
    private BigDecimal interest; // 利息

    @Column(name = "total_repay", nullable = false)
    private BigDecimal totalRepay; // 当期应还总额（本金+利息）

    @Column(name = "status")
    private Integer status = 0; // 0-未还, 1-已还, 2-逾期

    @Column(name = "overdue_interest")
    private BigDecimal overdueInterest; // 逾期利息

    public Integer getRepayId() {
        return repayId;
    }

    public void setRepayId(Integer repayId) {
        this.repayId = repayId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getTotalRepay() {
        return totalRepay;
    }

    public void setTotalRepay(BigDecimal totalRepay) {
        this.totalRepay = totalRepay;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getOverdueInterest() {
        return overdueInterest;
    }

    public void setOverdueInterest(BigDecimal overdueInterest) {
        this.overdueInterest = overdueInterest;
    }

    @Override
    public String toString() {
        return "RepaySchedule{" +
                "repayId=" + repayId +
                ", loanId=" + loanId +
                ", repayDate='" + repayDate + '\'' +
                ", principal=" + principal +
                ", interest=" + interest +
                ", totalRepay=" + totalRepay +
                ", status=" + status +
                ", overdueInterest=" + overdueInterest +
                '}';
    }
}