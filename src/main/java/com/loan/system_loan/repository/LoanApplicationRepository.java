package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.LoanApplication;
import com.loan.system_loan.pojo.dto.LoanApplicationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Integer> {
    // 查询所有贷款申请
    List<LoanApplication> findByStatus(Integer status);

//    // 查询所有未审核的贷款申请 (status = 0)
//    List<LoanApplication> findByStatus(0);
//
//    // 查询所有已审核未通过的贷款申请 (status = 1)
//    List<LoanApplication> findByStatus(1);
//
//    // 查询所有已审核已通过的贷款申请 (status = 2)
//    List<LoanApplication> findByStatus(2);

    // 根据用户id查询贷款申请
    List<LoanApplication> findByUserId(Integer userId);
}
