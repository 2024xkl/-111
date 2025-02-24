package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Account;
import com.loan.system_loan.pojo.CreditReport;
import com.loan.system_loan.pojo.Loan;
import com.loan.system_loan.pojo.User;
import com.loan.system_loan.repository.AccountRepository;
import com.loan.system_loan.repository.CreditReportRepository;
import com.loan.system_loan.repository.LoanRepository;
import com.loan.system_loan.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CreditReportService {
    @Autowired
    private CreditReportRepository creditReportRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    // 用户注册时创建信用报告
    @Transactional
    public CreditReport createCreditReport(Integer userId) {
        CreditReport creditReport = new CreditReport();
        creditReport.setUserId(userId);
        return creditReportRepository.save(creditReport);
    }

    // 用户申请贷款成功后更新信用报告
    @Transactional
    public void updateCreditReportAfterLoanApproval(Loan loan) {
        CreditReport creditReport = creditReportRepository.findByUserId(loan.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("用户信用报告不存在"));

        // 更新贷款记录
        creditReport.addLoan(loan);

        // 保存更新后的信用报告
        creditReportRepository.save(creditReport);
    }

    // 用户申请账户后更新信用报告
    @Transactional
    public void updateCreditReportAfterAccountCreation(Account account) {
        CreditReport creditReport = creditReportRepository.findByUserId(account.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("用户信用报告不存在"));

        // 更新账户记录
        creditReport.addAccount(account);

        // 保存更新后的信用报告
        creditReportRepository.save(creditReport);
    }

    // 用户完成一期还款计划后更新信用报告
    @Transactional
    public void updateCreditReportAfterRepayment(Integer userId, BigDecimal repaymentAmount) {
        CreditReport creditReport = creditReportRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("用户信用报告不存在"));

        // 还款后更新总负债
        creditReport.updateTotalDebt();

        // 保存更新后的信用报告
        creditReportRepository.save(creditReport);
    }


    // 获取用户的征信报告
    public CreditReport getCreditReport(Integer userId) {
        // 获取 CreditReport
        CreditReport creditReport = creditReportRepository.findById(userId).orElse(null);

        if (creditReport == null) {
            throw new RuntimeException("用户征信报告不存在！");
        }

        // 将贷款信息转换为字符串
        String loanInfo = creditReport.getLoans().stream()
                .map(loan -> "Loan ID: " + loan.getLoanId() + ", Amount: " + loan.getMoney() + ", Rate: " + loan.getRate())
                .collect(Collectors.joining("; "));  // 使用分号分隔每个贷款记录

        // 将账户信息转换为字符串
        String accountInfo = creditReport.getAccounts().stream()
                .map(account -> "Account ID: " + account.getAccountId() + ", Bank: " + account.getBankName() + ", Balance: " + account.getBalance())
                .collect(Collectors.joining("; "));  // 使用分号分隔每个账户记录

        // 创建并返回 CreditReportDTO
        CreditReport creditreport = new CreditReport();
        creditreport.setUserId(creditReport.getUserId());
        creditreport.setTotalDebt(creditReport.getTotalDebt());
        creditreport.setLoanInfo(loanInfo);  // 设置转换后的贷款信息
        creditreport.setAccountInfo(accountInfo);  // 设置转换后的账户信息

        return creditReport;
    }

    @Transactional
    public void addAccountToCreditReport(Integer userId, Account account) {
        // 获取用户的征信报告
        CreditReport creditReport = creditReportRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("用户征信报告不存在！"));

        // 将新账户加入 CreditReport 的账户列表
        creditReport.getAccounts().add(account);

        // 更新 accountInfo 字段
        String accountInfo = creditReport.getAccounts().stream()
                .map(acc -> "Account ID: " + acc.getAccountId() + ", Bank: " + acc.getBankName() + ", Balance: " + acc.getBalance())
                .collect(Collectors.joining("; "));
        creditReport.setAccountInfo(accountInfo);

        // 保存更新后的 CreditReport
        creditReportRepository.save(creditReport);
    }

    @Transactional
    public void addLoanToCreditReport(Integer userId, Loan loan) {
        // 获取用户的征信报告
        CreditReport creditReport = creditReportRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException("用户征信报告不存在！"));

        // 将新贷款加入 CreditReport 的贷款列表
        creditReport.getLoans().add(loan);

        // 更新 totalDebt
        BigDecimal totalDebt = creditReport.getLoans().stream()
                .map(Loan::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        creditReport.setTotalDebt(totalDebt);

        // 更新 loanInfo 字段
        String loanInfo = creditReport.getLoans().stream()
                .map(l -> "Loan ID: " + l.getLoanId() + ", Amount: " + l.getMoney() + ", Rate: " + l.getRate())
                .collect(Collectors.joining("; "));
        creditReport.setLoanInfo(loanInfo);

        // 保存更新后的 CreditReport
        creditReportRepository.save(creditReport);
    }


    // 更新用户的 totalDebt
    public void updateTotalDebtAfterRepayment(Integer userId) {
        // 查找用户的信用报告
        CreditReport creditReport = creditReportRepository.findById(userId).orElse(null);
        if (creditReport == null) return;

        // 重新计算总负债
        BigDecimal totalDebt = loanRepository.findAllByUserId(userId).stream()
                .map(Loan::getMoney)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        creditReport.setTotalDebt(totalDebt);
        creditReportRepository.save(creditReport);

        System.out.println("征信报告已更新，总负债: " + totalDebt);
    }

    public BigDecimal getTotalDebtByUserId(Integer userId) {
        return creditReportRepository.findTotalDebtByUserId(userId);
    }

}


