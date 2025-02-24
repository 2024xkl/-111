package com.loan.system_loan.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

@Table(name = "tb_bank_statement")
@Entity
public class BankStatement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_statement_id")
    private Integer BankStatementID;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "date")
    private String date;

    @Column(name = "motion_type", nullable = false)
    @Pattern(regexp = "还款|取款|存款", message = "motionType 只能是 '还款', '取款', 或 '存款'")
    private String motionType;
    @Column(name = "ammount")
    private BigDecimal amount;

    public Integer getBankStatementID() {
        return BankStatementID;
    }

    public void setBankStatementID(Integer bankStatementID) {
        BankStatementID = bankStatementID;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMotionType() {
        return motionType;
    }

    public void setMotionType(String motionType) {
        this.motionType = motionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BankStatement{" +
                "BankStatementID=" + BankStatementID +
                ", accountID=" + accountId +
                ", date='" + date + '\'' +
                ", motionType='" + motionType + '\'' +
                ", amount=" + amount +
                '}';
    }
}
