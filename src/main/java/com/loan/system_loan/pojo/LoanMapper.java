package com.loan.system_loan.pojo;

import com.loan.system_loan.pojo.dto.LoanDto;

public class LoanMapper {

    public static LoanDto toDto(Loan loan) {
        LoanDto dto = new LoanDto();
        dto.setLoanId(loan.getLoanId());
        dto.setUserId(loan.getUserId());
        dto.setDate(loan.getDate());
        dto.setMoney(loan.getMoney());
        dto.setRate(loan.getRate());
        dto.setTime(loan.getTime());
        dto.setOvertimeRate(loan.getOvertimeRate());
        dto.setAdvanceRepay(loan.isAdvanceRepay());
        return dto;
    }


}
