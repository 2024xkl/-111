package com.loan.system_loan.controller;

import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.*;
import com.loan.system_loan.repository.AccountRepository;
import com.loan.system_loan.service.CreditReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/creditreport")
public class CreditReportController {
    @Autowired
    private CreditReportService creditReportService;

    // 用户注册时创建信用报告
    @PostMapping("/create")
    public ResponseEntity<String> createCreditReport(@RequestParam Integer userId) {
        creditReportService.createCreditReport(userId);
        return ResponseEntity.ok("信用报告已创建");
    }

    // 贷款审核通过时更新信用报告
    @PostMapping("/updateAfterLoanApproval")
    public ResponseEntity<String> updateCreditReportAfterLoanApproval(@RequestBody Loan loan) {
        creditReportService.updateCreditReportAfterLoanApproval(loan);
        return ResponseEntity.ok("信用报告已更新，贷款信息已添加");
    }

    // 用户创建账户后更新信用报告
    @PostMapping("/updateAfterAccountCreation")
    public ResponseEntity<String> updateCreditReportAfterAccountCreation(@RequestBody Account account) {
        creditReportService.updateCreditReportAfterAccountCreation(account);
        return ResponseEntity.ok("信用报告已更新，账户信息已添加");
    }

    // 还款后更新信用报告
    @PostMapping("/updateAfterRepayment")
    public ResponseEntity<String> updateCreditReportAfterRepayment(@RequestParam Integer userId, @RequestParam BigDecimal repaymentAmount) {
        creditReportService.updateCreditReportAfterRepayment(userId, repaymentAmount);
        return ResponseEntity.ok("信用报告已更新，还款信息已处理");
    }

    @GetMapping("findbyuserid/{userId}")
    @RoleRequired(RoleType.ASSESSOR)
    public ResponseEntity<CreditReport> getCreditReport(@PathVariable Integer userId) {
        CreditReport creditReportDTO = creditReportService.getCreditReport(userId);
        return ResponseEntity.ok(creditReportDTO);
    }

    @GetMapping("/gettotaldebt/{userId}")
    public ResponseEntity<BigDecimal> getTotalDebtByUserId(@PathVariable Integer userId) {
        BigDecimal totalDebt = creditReportService.getTotalDebtByUserId(userId);
        if (totalDebt == null) {
            return ResponseEntity.notFound().build(); // 如果找不到，返回 404
        }
        return ResponseEntity.ok(totalDebt); // 返回 totalDebt
    }
}
