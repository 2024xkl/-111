package com.loan.system_loan.controller;

import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.BankStatement;
import com.loan.system_loan.pojo.ResponseMessage;
import com.loan.system_loan.pojo.RoleType;
import com.loan.system_loan.pojo.dto.AccountDto;
import com.loan.system_loan.service.BankStatementService;
import com.loan.system_loan.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bankstatements")
public class BankStatementController {
    @Autowired
    private BankStatementService bankStatementService;
    @Autowired
    private UserService userService;
    // 根据用户ID获取银行流水
    @GetMapping("/findbyuserid/{userId}")
    @RoleRequired(RoleType.USER)
    public ResponseEntity<List<BankStatement>> getBankStatementsByUserId(@PathVariable Integer userId,HttpServletRequest request) {
        // 1. 获取当前登录用户 ID（假设通过session存储在request header中）
        String currentUserIdHeader = request.getHeader("session");  // 假设session存储了用户的 ID
        Integer currentUserId = currentUserIdHeader != null ? Integer.parseInt(currentUserIdHeader) : null;

        // 2. 验证当前用户 ID 是否与请求的 userId 一致
        if (currentUserId == null || !currentUserId.equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);  // 返回 403 Forbidden 状态码
        }
        // 1. 查询该用户的所有账户
        List<AccountDto> accounts = userService.getAccountsByUserId(userId);

        // 2. 获取账户ID列表
        List<Integer> accountIds = accounts.stream()
                .map(AccountDto::getAccountId)
                .collect(Collectors.toList());

        // 3. 根据账户ID查询银行流水
        List<BankStatement> bankStatements = bankStatementService.getBankStatementsByAccountIds(accountIds);

        // 4. 返回银行流水数据
        return ResponseEntity.ok(bankStatements);

    }


}
