package com.loan.system_loan.controller;

import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.LoanApplication;
import com.loan.system_loan.pojo.ResponseMessage;
import com.loan.system_loan.pojo.RoleType;
import com.loan.system_loan.pojo.dto.LoanApplicationAssessRequestDTO;
import com.loan.system_loan.pojo.dto.LoanApplicationDto;
import com.loan.system_loan.pojo.dto.LoanApplicationRequestDTO;
import com.loan.system_loan.repository.LoanApplicationRepository;
import com.loan.system_loan.service.LoanApplicationService;
import com.loan.system_loan.service.LoanService;
import jakarta.servlet.http.HttpServletRequest;
import org.antlr.v4.runtime.misc.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/loanapplication")
public class LoanApplicationController {
    @Autowired
    private LoanApplicationService loanApplicationService;
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    // 贷款申请
    @PostMapping("/apply")
    @RoleRequired(RoleType.USER)
    public ResponseEntity<String> applyForLoan(@RequestBody LoanApplicationRequestDTO loanApplicationRequestDTO) {
        try {
            // 从请求头中获取当前用户 ID
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userIdHeader = request.getHeader("session");
            Integer currentUserId = userIdHeader != null ? Integer.parseInt(userIdHeader) : null;

            // 验证当前用户 ID 是否存在
            if (currentUserId == null) {
                return ResponseEntity.ok("用户未登录，无法更新信息");
            }

            // 验证用户是否只能修改自己的信息
            if (!currentUserId.equals(loanApplicationRequestDTO.getUserId())) {
                return ResponseEntity.ok("用户只能为自己申请贷款");
            }

            String result = loanApplicationService.applyForLoan(
                loanApplicationRequestDTO.getUserId(),
                loanApplicationRequestDTO.getAccountId(),
                loanApplicationRequestDTO.getProjectId(),
                loanApplicationRequestDTO.getApplicationMoney(),
                loanApplicationRequestDTO.getApplicationTime()
        );

        return ResponseEntity.ok(result);
        } catch (Exception e) {
            return  ResponseEntity.ok("更新失败: " + e.getMessage());  // 错误处理
        }
    }

    // 审核贷款申请
    @PostMapping("/assess")
    @RoleRequired(RoleType.ASSESSOR)
    public ResponseEntity<String> assessLoanApplication(@RequestBody LoanApplicationAssessRequestDTO loanApplicationAssessRequestDTO) {
        String result = loanApplicationService.assessLoanApplication(
                loanApplicationAssessRequestDTO.getLoanApplicationId(),
                loanApplicationAssessRequestDTO.getApprove()
        );
        return ResponseEntity.ok(result);
    }



    @GetMapping("/find/all")////    @RoleRequired(RoleType.ADMINISTRATOR)
    public ResponseEntity<List<LoanApplicationDto>> getAllLoanApplications() {
        List<LoanApplicationDto> loanApplications = loanApplicationService.getAllLoanApplications();
        return ResponseEntity.ok(loanApplications);
    }

    // 查询所有未审核的贷款申请 (status = 0)
    @GetMapping("/find/havenotassessed")
    @RoleRequired(RoleType.ADMINISTRATOR)
    public ResponseEntity<List<LoanApplicationDto>> getUnapprovedLoanApplications() {
        List<LoanApplicationDto> loanApplications = loanApplicationService.getUnapprovedLoanApplications();
        return ResponseEntity.ok(loanApplications);
    }

    // 查询所有已审核未通过的贷款申请 (status = 1)
    @GetMapping("/find/notapproved")
    @RoleRequired(RoleType.ADMINISTRATOR)
    public ResponseEntity<List<LoanApplicationDto>> getReviewedNotApprovedLoanApplications() {
        List<LoanApplicationDto> loanApplications = loanApplicationService.getReviewedNotApprovedLoanApplications();
        return ResponseEntity.ok(loanApplications);
    }

    // 查询所有已审核已通过的贷款申请 (status = 2)
    @GetMapping("/find/approved")
    @RoleRequired(RoleType.ADMINISTRATOR)
    public ResponseEntity<List<LoanApplicationDto>> getReviewedApprovedLoanApplications() {
        List<LoanApplicationDto> loanApplications = loanApplicationService.getReviewedApprovedLoanApplications();
        return ResponseEntity.ok(loanApplications);
    }

    // 根据用户id查询贷款申请
    @GetMapping("/findbyuserid/{userId}")
//    @RoleRequired(RoleType.USER)
    public ResponseEntity<List<LoanApplicationDto>> getLoanApplicationsByUserId(@PathVariable Integer userId,HttpServletRequest request) {
        // 1. 获取当前登录用户 ID（假设通过session存储在request header中）
        String currentUserIdHeader = request.getHeader("session");  // 假设session存储了用户的 ID
        Integer currentUserId = currentUserIdHeader != null ? Integer.parseInt(currentUserIdHeader) : null;

        // 2. 验证当前用户 ID 是否与请求的 userId 一致
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  // 返回 403 Forbidden 状态码
        }
        List<LoanApplicationDto> loanApplications = loanApplicationService.getLoanApplicationsByUserId(userId);
        return ResponseEntity.ok(loanApplications);
    }






}
