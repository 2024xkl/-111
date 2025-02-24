package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Assessor;
import com.loan.system_loan.pojo.dto.AssessorDto;
import com.loan.system_loan.repository.AssessorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessorService {
    @Autowired
    private AssessorRepository assessorRepository;

    @Transactional
    public Assessor addAssessor(AssessorDto assessorDto) {
        Assessor assessor = new Assessor();
        assessor.setAssessorName(assessorDto.getAssessorName());
        assessor.setPassword(assessorDto.getPassword());
        assessor.setRole(assessorDto.getRole()); // 默认是 ASSESSOR

        return assessorRepository.save(assessor);
    }

    public void deleteAssessor(Integer assessorId) {
        if (!assessorRepository.existsById(assessorId)) {
            throw new RuntimeException("审核员ID不存在，无法删除!");
        }
        assessorRepository.deleteById(assessorId);
    }
    public Assessor getAssessorById(Integer assessorId) {
        return assessorRepository.findByAssessorId(assessorId);
    }

}
