package com.loan.system_loan.controller;

import com.loan.system_loan.pojo.RepaySchedule;
import com.loan.system_loan.repository.RepayScheduleRepository;
import com.loan.system_loan.service.RepayScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/repay")
public class RepayscheduleController {
    @Autowired
    private RepayScheduleService repayScheduleService; // 使用@Autowired注入Service实例

    // 查看所有还款计划
    @GetMapping("/viewall")
    public ResponseEntity<List<RepaySchedule>> viewAll() {
        List<RepaySchedule> schedules = repayScheduleService.viewAll();
        return ResponseEntity.ok(schedules);
    }

    // 根据 loanId 查找还款计划
    @GetMapping("/byloanid/{loanId}")
    public ResponseEntity<List<RepaySchedule>> findByLoanId(@PathVariable Integer loanId) {
        List<RepaySchedule> schedules = repayScheduleService.findByLoanId(loanId);
        return ResponseEntity.ok(schedules);
    }
}
