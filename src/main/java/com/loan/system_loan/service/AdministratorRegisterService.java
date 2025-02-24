package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Administrator;
import com.loan.system_loan.pojo.dto.RegisterDto;
import com.loan.system_loan.repository.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorRegisterService implements RegisterService {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    public void register(RegisterDto request) {
        Administrator administrator = new Administrator();
        administrator.setAdminName(request.getUserName());
        administrator.setPassword(request.getPassword());
        administratorRepository.save(administrator);
    }
}
