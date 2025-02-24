package com.loan.system_loan.pojo;

import jakarta.persistence.*;

@Table(name = "tb_alert")
@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private Integer alertId;

    @Column(name = "loan_id", nullable = false)
    private Integer loanId;

    @Column(name = "repay_id", nullable = false)
    private Integer repayId;

    @Column(name = "alert_date")
    private String alertDate;

    @Column(name = "alert_message")
    private String alertMessage;

    public Integer getAlertId() {
        return alertId;
    }

    public void setAlertId(Integer alertId) {
        this.alertId = alertId;
    }

    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loanId) {
        this.loanId = loanId;
    }

    public Integer getRepayId() {
        return repayId;
    }

    public void setRepayId(Integer repayId) {
        this.repayId = repayId;
    }

    public String getAlertDate() {
        return alertDate;
    }

    public void setAlertDate(String alertDate) {
        this.alertDate = alertDate;
    }

    public String getAlertMessage() {
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }

    @Override
    public String toString() {
        return "Alert{" +
                "alertId=" + alertId +
                ", loanId=" + loanId +
                ", repayId=" + repayId +
                ", alertDate='" + alertDate + '\'' +
                ", alertMessage='" + alertMessage + '\'' +
                '}';
    }
}
