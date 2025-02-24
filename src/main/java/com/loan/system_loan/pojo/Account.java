package com.loan.system_loan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Table(name = "tb_account")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account-id")
    private Integer accountId;

    @Column(name = "user_id", nullable = false) // 外键字段/ 指定外键字段
    private Integer userId;
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
//    private User user;

    @Column(name = "bank_card_number", unique = true)
    private String bankCardNumber;

    @Column(name = "password")
    private String password;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "loan_balance")
    private BigDecimal loanBalance;

    // 新增与 CreditReport 的关联关系
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private CreditReport creditReport;  // 反向关联到 CreditReport

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getters and setters
    public CreditReport getCreditReport() {
        return creditReport;
    }

    public void setCreditReport(CreditReport creditReport) {
        this.creditReport = creditReport;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getLoanBalance() {
        return loanBalance;
    }

    public void setLoanBalance(BigDecimal loanBalance) {
        this.loanBalance = loanBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", userId=" + userId +
                ", bankCardNumber='" + bankCardNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", balance=" + balance +
                ", loanBalance=" + loanBalance +
                '}';
    }
}
