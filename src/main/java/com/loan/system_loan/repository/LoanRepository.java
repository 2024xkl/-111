package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.Loan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends CrudRepository<Loan, Integer> {
    // 根据 userId 查询所有贷款信息
    List<Loan> findByUserId(Integer userId);
    List<Loan> findAllByUserId(Integer userId);
}
