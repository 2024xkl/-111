package com.loan.system_loan.controller;

import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.*;
import com.loan.system_loan.pojo.dto.AccountDto;
import com.loan.system_loan.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @PostMapping("/add")
    @RoleRequired(RoleType.USER)
    public ResponseMessage<Account> addAccount(@Validated @RequestBody AccountRequest accountRequest) {
        try {
            // 从请求头中获取当前用户 ID
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userIdHeader = request.getHeader("session");
            Integer currentUserId = userIdHeader != null ? Integer.parseInt(userIdHeader) : null;

            // 验证当前用户 ID 是否存在
            if (currentUserId == null) {
                return ResponseMessage.error1("用户未登录，无法更新信息");
            }

            // 验证用户是否只能修改自己的信息
            if (!currentUserId.equals(accountRequest.getUserId())) {
                return ResponseMessage.error1("用户只能建立自己的账户");
            }
            Account newAccount = accountService.add(accountRequest.getUserId(),accountRequest.getBankName(),accountRequest.getPassword());
            return ResponseMessage.success(newAccount);
          // 返回修改后的用户信息
        } catch (Exception e) {
            return ResponseMessage.error1("更新失败: " + e.getMessage());  // 错误处理
        }

    }

    // 根据用户ID查看账户信息
    @GetMapping("/view/{userId}")
    @RoleRequired(RoleType.USER)
    public ResponseMessage<List<Account>> getByUser(@PathVariable Integer userId) {
        try {
            // 从请求头中获取当前用户 ID
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String userIdHeader = request.getHeader("session");
            Integer currentUserId = userIdHeader != null ? Integer.parseInt(userIdHeader) : null;

            // 验证当前用户 ID 是否存在
            if (currentUserId == null) {
                return ResponseMessage.error1("用户未登录，无法更新信息");
            }

            // 验证用户是否只能修改自己的信息
            if (!currentUserId.equals(userId)) {
                return ResponseMessage.error1("用户只能查看自己的账户情况");
            }
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        return ResponseMessage.success(accounts);

        } catch (Exception e) {
            return ResponseMessage.error1("更新失败: " + e.getMessage());  // 错误处理
        }
    }


    @PostMapping("/deposit")
    @RoleRequired(RoleType.USER)
    public ResponseEntity<Account> deposit(@RequestBody DepositWithdrawRequest request) {

        // 校验请求参数
        if (request == null || request.getBankCardNumber() == null || request.getAmount() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // 执行存款操作
        Account updatedAccount = accountService.deposit(request.getBankCardNumber(), request.getAmount(), request.getPassword());
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/withdraw")
    @RoleRequired(RoleType.USER)
    public ResponseEntity<Account> withdraw(@RequestBody DepositWithdrawRequest request) {
        // 校验请求参数
        if (request == null || request.getBankCardNumber() == null || request.getAmount() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // 执行取款操作
        Account updatedAccount = accountService.withdraw(request.getBankCardNumber(), request.getAmount(), request.getPassword());
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/getbankname/{accountId}")
    public ResponseEntity<String> getBankName(@PathVariable Integer accountId) {
        String bankName = accountService.getBankNameByAccountId(accountId);
        if (bankName != null) {
            return ResponseEntity.ok(bankName);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
