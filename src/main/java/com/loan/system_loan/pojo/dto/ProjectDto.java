package com.loan.system_loan.pojo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProjectDto {
    @NotNull(groups = ValidationGroups.Edit.class, message = "用户ID不能为空")
    private Integer ProjectId;

    @NotBlank(groups = ValidationGroups.Create.class,message = "项目名称不能为空")
    private String ProjectName;

    @NotNull(groups = ValidationGroups.Create.class,message = "贷款金额最大值不能为空")
    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "贷款金额最大值不能为负数")
    private BigDecimal loanmoneyMax;

    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "贷款金额最小值不能为负数")
    @NotNull(groups = ValidationGroups.Create.class,message = "贷款金额最小值不能为空")
    private BigDecimal loanmoneyMin;

    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "贷款期限最大值不能为负数")
    @NotNull(groups = ValidationGroups.Create.class,message = "贷款期限最大值不能为空")
    private Integer loanmonthMax;

    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "贷款期限最小值不能为负数")
    @NotNull(groups = ValidationGroups.Create.class,message = "贷款期限最小值不能为空")
    private Integer loanmonthMin;

    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "贷款利率最大值不能为负数")
    @NotNull(groups = ValidationGroups.Create.class,message = "贷款利率最大值不能为空")
    private BigDecimal rateMax;

    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "贷款利率最小值不能为负数")
    @NotNull(groups = ValidationGroups.Create.class,message = "贷款利率最小值不能为空")
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
