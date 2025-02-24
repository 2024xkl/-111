package com.loan.system_loan.pojo;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Table(name = "tb_project")
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prject_id")
    private Integer ProjectId;

    @Column(name = "prject_name")
    private String ProjectName;

    @Column(name = "loanmoney_max")
    private BigDecimal loanmoneyMax;

    @Column(name = "loanmoney_min")
    private BigDecimal loanmoneyMin;

    @Column(name = "loanmonth_max")
    private Integer loanmonthMax;

    @Column(name = "loanmonth_min")
    private Integer loanmonthMin;

    @Column(name = "rate_max")
    private BigDecimal rateMax;

    @Column(name = "rate_min")
    private BigDecimal rateMin;

    public Integer getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Integer projectId) {
        ProjectId = projectId;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public BigDecimal getLoanmoneyMax() {
        return loanmoneyMax;
    }

    public void setLoanmoneyMax(BigDecimal loanmoneyMax) {
        this.loanmoneyMax = loanmoneyMax;
    }

    public BigDecimal getLoanmoneyMin() {
        return loanmoneyMin;
    }

    public void setLoanmoneyMin(BigDecimal loanmoneyMin) {
        this.loanmoneyMin = loanmoneyMin;
    }

    public Integer getLoanmonthMax() {
        return loanmonthMax;
    }

    public void setLoanmonthMax(Integer loanmonthMax) {
        this.loanmonthMax = loanmonthMax;
    }

    public Integer getLoanmonthMin() {
        return loanmonthMin;
    }

    public void setLoanmonthMin(Integer loanmonthMin) {
        this.loanmonthMin = loanmonthMin;
    }

    public BigDecimal getRateMax() {
        return rateMax;
    }

    public void setRateMax(BigDecimal rateMax) {
        this.rateMax = rateMax;
    }

    public BigDecimal getRateMin() {
        return rateMin;
    }

    public void setRateMin(BigDecimal rateMin) {
        this.rateMin = rateMin;
    }

    @Override
    public String toString() {
        return "Project{" +
                "ProjectId=" + ProjectId +
                ", ProjectName='" + ProjectName + '\'' +
                ", loanmoneyMax=" + loanmoneyMax +
                ", loanmoneyMin=" + loanmoneyMin +
                ", loanmonthMax=" + loanmonthMax +
                ", loanmonthMin=" + loanmonthMin +
                ", rateMax=" + rateMax +
                ", rateMin=" + rateMin +
                '}';
    }
}
