package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.Administrator;
import com.loan.system_loan.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends CrudRepository<Administrator,Integer> {
    Administrator findByAdminId(Integer administratorId);
}
