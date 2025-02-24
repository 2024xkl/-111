package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.Account;
import com.loan.system_loan.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account,Integer> {


    // 按 userId 查询账户
    List<Account> findByUserId(Integer userId);

    //Account findByBankCardNumber(String bankCardNumber);

    Optional<Account> findByBankCardNumber(String bankCardNumber);

    // 根据userId查找账户，返回单个账户
   // Account findByUserId(Integer userId);

    // 或者根据用户的 ID 查找账户
    //List<Account> findByUserId(Integer userId);
    @Query("SELECT a.bankName FROM Account a WHERE a.accountId = :accountId")
    String findBankNameByAccountId(Integer accountId);
    boolean existsByBankCardNumber(String bankCardNumber);

}
