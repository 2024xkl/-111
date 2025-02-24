package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Loan;
import com.loan.system_loan.pojo.LoanApplication;
import com.loan.system_loan.pojo.RepaySchedule;
import com.loan.system_loan.repository.LoanApplicationRepository;
import com.loan.system_loan.repository.LoanRepository;
import com.loan.system_loan.repository.RepayScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    // 根据 userId 获取所有贷款信息
    public List<Loan> getLoansByUserId(Integer userId) {

        return loanRepository.findByUserId(userId);
    }



}
