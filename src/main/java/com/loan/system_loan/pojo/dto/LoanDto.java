package com.loan.system_loan.pojo.dto;

import java.math.BigDecimal;

public class LoanDto {
    private Integer loanId;
    private Integer userId;
    private String date;
    private BigDecimal money;
    private BigDecimal rate;
    private Integer time;
    private BigDecimal overtimeRate;
    private boolean advanceRepay;

    public LoanDto(Integer loanId, Integer userId, String date, BigDecimal money, BigDecimal rate, Integer time, BigDecimal overtimeRate, boolean advanceRepay) {
        this.loanId = loanId;
        this.userId = userId;
        this.date = date;
        this.money = money;
        this.rate = rate;
        this.time = time;
        this.overtimeRate = overtimeRate;
        this.advanceRepay = advanceRepay;
    }

    public LoanDto() {

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
}
