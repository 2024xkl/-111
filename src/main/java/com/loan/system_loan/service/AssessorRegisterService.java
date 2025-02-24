package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Assessor;
import com.loan.system_loan.pojo.dto.RegisterDto;
import com.loan.system_loan.repository.AssessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessorRegisterService implements RegisterService {
    @Autowired
    private AssessorRepository assessorRepository;

    @Override
    public void register(RegisterDto request) {
        Assessor assessor = new Assessor();
        assessor.setAssessorName(request.getUserName());
        assessor.setPassword(request.getPassword());
        assessorRepository.save(assessor);
    }
}
