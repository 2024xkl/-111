package com.loan.system_loan.pojo.dto;

public class UserFilterDto {
    private String sex;
    private String birthdayFrom;
    private String birthdayTo;
    private String job;
    private String company;
    private Integer yearIncomeMin;
    private Integer yearIncomeMax;
    private Integer workYearsMin;
    private Integer workYearsMax;
    private String ismarry;

    // Getters and Setters
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdayFrom() {
        return birthdayFrom;
    }

    public void setBirthdayFrom(String birthdayFrom) {
        this.birthdayFrom = birthdayFrom;
    }

    public String getBirthdayTo() {
        return birthdayTo;
    }

    public void setBirthdayTo(String birthdayTo) {
        this.birthdayTo = birthdayTo;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getYearIncomeMin() {
        return yearIncomeMin;
    }

    public void setYearIncomeMin(Integer yearIncomeMin) {
        this.yearIncomeMin = yearIncomeMin;
    }

    public Integer getYearIncomeMax() {
        return yearIncomeMax;
    }

    public void setYearIncomeMax(Integer yearIncomeMax) {
        this.yearIncomeMax = yearIncomeMax;
    }

    public Integer getWorkYearsMin() {
        return workYearsMin;
    }

    public void setWorkYearsMin(Integer workYearsMin) {
        this.workYearsMin = workYearsMin;
    }

    public Integer getWorkYearsMax() {
        return workYearsMax;
    }

    public void setWorkYearsMax(Integer workYearsMax) {
        this.workYearsMax = workYearsMax;
    }

    public String getIsmarry() {
        return ismarry;
    }

    public void setIsmarry(String ismarry) {
        this.ismarry = ismarry;
    }
}
