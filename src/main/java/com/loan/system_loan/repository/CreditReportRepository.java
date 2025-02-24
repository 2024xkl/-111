package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.CreditReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface CreditReportRepository extends CrudRepository<CreditReport, Integer> {
    Optional<CreditReport> findByUserId(Integer userId);
    @Query("SELECT c.totalDebt FROM CreditReport c WHERE c.userId = ?1")
    BigDecimal findTotalDebtByUserId(Integer userId);

}
