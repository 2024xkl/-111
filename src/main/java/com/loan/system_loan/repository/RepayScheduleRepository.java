package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.RepaySchedule;
import com.loan.system_loan.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepayScheduleRepository extends CrudRepository<RepaySchedule,Integer> {
    List<RepaySchedule> findByLoanId(Integer loanId);

    List<RepaySchedule> findByRepayDateAndStatus(String string, int i);
        // 根据 loanId 查找还款计划


    // 根据 userId 查找还款计划（通过关联 loan 表）
    @Query("SELECT r FROM RepaySchedule r WHERE r.loanId IN (SELECT l.loanId FROM Loan l WHERE l.userId = :userId)")
    List<RepaySchedule> findByUserId(Integer userId);


}
