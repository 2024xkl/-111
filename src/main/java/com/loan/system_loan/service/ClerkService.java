package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Clerk;
import com.loan.system_loan.pojo.dto.ClerkDto;
import com.loan.system_loan.repository.ClerkRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClerkService {
    @Autowired
    private ClerkRepository clerkRepository;

    @Transactional
    public Clerk addClerk(ClerkDto clerkDto) {


        // 创建新的 Clerk 实体
        Clerk clerk = new Clerk();
        clerk.setClerkName(clerkDto.getClerkName());
        clerk.setPassword(clerkDto.getPassword());
        clerk.setRole(clerkDto.getRole()); // 默认为 CLERK

        // 保存并返回
        return clerkRepository.save(clerk);
    }

    public void delete(Integer clerkId) {
        if (!clerkRepository.existsById(clerkId)) {
            throw new RuntimeException("营业员ID不存在，无法删除!");
        }
        clerkRepository.deleteById(clerkId);
    }

    public Clerk getClerkById(Integer clerkId) {
        return clerkRepository.findByClerkId(clerkId);
    }

}
