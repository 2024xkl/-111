package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Account;
import com.loan.system_loan.pojo.BankStatement;
import com.loan.system_loan.repository.AccountRepository;
import com.loan.system_loan.repository.BankStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankStatementService {
    @Autowired
    private AccountRepository accountRepository; // 获取账户信息
    @Autowired
    private BankStatementRepository bankStatementRepository; // 获取银行流水信息
    // 根据账户ID查询银行流水
    public List<BankStatement> getBankStatementsByAccountIds(List<Integer> accountIds) {
        return bankStatementRepository.findByAccountIdIn(accountIds);
    }
}
