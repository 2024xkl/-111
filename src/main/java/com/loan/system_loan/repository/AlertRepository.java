package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.Alert;
import com.loan.system_loan.pojo.BankStatement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepository extends CrudRepository<Alert,Integer> {
}
