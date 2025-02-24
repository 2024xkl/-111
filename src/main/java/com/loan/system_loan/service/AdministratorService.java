package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Administrator;
import com.loan.system_loan.pojo.dto.AdministratorDto;
import com.loan.system_loan.repository.AdministratorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Transactional
    public Administrator addAdministrator(AdministratorDto administratorDto) {
        Administrator administrator = new Administrator();
        administrator.setAdminName(administratorDto.getAdminName());
        administrator.setPassword(administratorDto.getPassword());
        administrator.setRole(administratorDto.getRole()); // 默认是 ADMINISTRATOR

        return administratorRepository.save(administrator);
    }

    public void deleteAdministrator(Integer adminId) {
        if (!administratorRepository.existsById(adminId)) {
            throw new RuntimeException("管理员ID不存在，无法删除!");
        }
        administratorRepository.deleteById(adminId);
    }

    public Administrator getAdministratorById(Integer administratorId) {
        return administratorRepository.findByAdminId(administratorId);
    }

}
