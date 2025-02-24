package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Clerk;
import com.loan.system_loan.pojo.dto.RegisterDto;
import com.loan.system_loan.repository.ClerkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClerkRegisterService implements RegisterService{
    @Autowired
    private ClerkRepository clerkRepository;

    @Override
    public void register(RegisterDto request) {
        Clerk clerk = new Clerk();
        clerk.setClerkName(request.getUserName());
        clerk.setPassword(request.getPassword());
        clerkRepository.save(clerk);
    }
}
