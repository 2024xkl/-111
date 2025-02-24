package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.Assessor;
import com.loan.system_loan.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface AssessorRepository extends CrudRepository<Assessor,Integer> {
    Assessor findByAssessorId(Integer assessorId);

}
