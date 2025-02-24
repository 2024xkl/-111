package com.loan.system_loan.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.loan.system_loan.pojo.dto.AccountDto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tb_creditreport")
@Entity
public class CreditReport {

    @Id
    @Column(name = "user_id", nullable = false, unique = true)  // 设置唯一约束
    private Integer userId;

    @Column(name = "present_total_debt")
    private BigDecimal totalDebt = BigDecimal.ZERO;

//    @Column(name = "loans")
    @OneToMany(mappedBy = "creditReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans = new ArrayList<>();  // 历史贷款记录

//    @Column(name = "accounts")

@JsonIgnore
@OneToMany(mappedBy = "creditReport", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Account> accounts = new ArrayList<>(); ;  // 信用卡记录

    @Column(name = "loans")
    private String loanInfo;  // 存储贷款信息的字符串

    @Column(name = "accounts")
    private String accountInfo;  // 存储账户信息的字符串

    public String getLoanInfo() {
        return loanInfo;
    }

    public void setLoanInfo(String loanInfo) {
        this.loanInfo = loanInfo;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    // 更新总负债金额
    public void updateTotalDebt() {
        BigDecimal totalDebt = loans.stream()
                .map(Loan::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        this.totalDebt = totalDebt;
    }

    public void addLoan(Loan loan) {
        this.loans.add(loan);
        updateTotalDebt();
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(BigDecimal totalDebt) {
        this.totalDebt = totalDebt;
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "CreditReport{" +
                "userId=" + userId +
                ", totalDebt=" + totalDebt +
                ", loans=" + loans +
                ", accounts=" + accounts +
                ", loanInfo='" + loanInfo + '\'' +
                ", accountInfo='" + accountInfo + '\'' +
                '}';
    }
}
