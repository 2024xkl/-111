package com.loan.system_loan.repository;

import com.loan.system_loan.pojo.Clerk;
import com.loan.system_loan.pojo.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClerkRepository extends CrudRepository<Clerk,Integer> {
    Clerk findByClerkId(Integer clerkId);
}
