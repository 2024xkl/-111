package com.loan.system_loan.controller;

import com.loan.system_loan.pojo.Loan;
import com.loan.system_loan.pojo.LoanMapper;
import com.loan.system_loan.pojo.dto.LoanDto;
import com.loan.system_loan.repository.LoanRepository;
import com.loan.system_loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/loan")
public class LoanController {
    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;
//    // 根据 userId 获取所有贷款信息
//    @GetMapping("/findbyuserid/{userId}")
//    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Integer userId) {
//        List<Loan> loans = loanService.getLoansByUserId(userId);
//        if (loans.isEmpty()) {
//            return ResponseEntity.noContent().build();  // 如果没有贷款记录，返回 204 No Content
//        }
//        return ResponseEntity.ok(loans);  // 返回 200 和贷款信息
//    }


    @GetMapping("/findbyuserr/{userId}")
    public ResponseEntity<List<LoanDto>> getLoansByUserId(@PathVariable Integer userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);
        List<LoanDto> loanDtos = loans.stream()
                .map(LoanMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(loanDtos);
    }
}
