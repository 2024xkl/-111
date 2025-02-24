package com.loan.system_loan.pojo.dto;

public class LoanApplicationAssessRequestDTO {
    private Integer loanApplicationId;
    private Boolean approve;

    // Getters 和 Setters
    public Integer getLoanApplicationId() {
        return loanApplicationId;
    }

    public void setLoanApplicationId(Integer loanApplicationId) {
        this.loanApplicationId = loanApplicationId;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }
}
