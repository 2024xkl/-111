package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.BankStatement;
import com.loan.system_loan.pojo.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BankStatementRepository extends CrudRepository<BankStatement,Integer> {
    // 根据账户ID查询银行流水
    List<BankStatement> findByAccountIdIn(List<Integer> accountIds);

}
