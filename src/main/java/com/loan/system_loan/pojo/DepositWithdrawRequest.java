package com.loan.system_loan.pojo;

import java.math.BigDecimal;

public class DepositWithdrawRequest {
    private String bankCardNumber; // 银行卡号
    private BigDecimal amount;     // 金额
    private String password;
    // Getters and Setters
    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
