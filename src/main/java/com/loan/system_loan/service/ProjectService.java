package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Project;
import com.loan.system_loan.pojo.dto.ProjectDto;
import com.loan.system_loan.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public Project addProject(ProjectDto projectDto) {
        Project project = new Project();
        project.setProjectName(projectDto.getProjectName());
        project.setLoanmoneyMax(projectDto.getLoanmoneyMax());
        project.setLoanmoneyMin(projectDto.getLoanmoneyMin());
        project.setLoanmonthMax(projectDto.getLoanmonthMax());
        project.setLoanmonthMin(projectDto.getLoanmonthMin());
        project.setRateMax(projectDto.getRateMax());
        project.setRateMin(projectDto.getRateMin());

        return projectRepository.save(project);
    }

    @Transactional
    public void deleteProject(Integer projectId) {
        projectRepository.deleteById(projectId);
    }


    @Transactional
    public Project edit(ProjectDto projectDto) {

        // 确保传入的 projectDto 不为空
        if (projectDto == null) {
            throw new IllegalArgumentException("传入的项目信息不能为空");
        }

        // 查找原有的项目信息
        Project project = projectRepository.findById(projectDto.getProjectId())
                .orElseThrow(() -> new IllegalArgumentException("项目不存在"));

        // 更新非空字段
        if (projectDto.getProjectName() != null) {
            project.setProjectName(projectDto.getProjectName());
        }

        if (projectDto.getLoanmoneyMax() != null) {
            if (projectDto.getLoanmoneyMax().compareTo(projectDto.getLoanmoneyMin()) < 0) {
                throw new IllegalArgumentException("贷款金额最大值不能小于最小值");
            }
            project.setLoanmoneyMax(projectDto.getLoanmoneyMax());
        }

        if (projectDto.getLoanmoneyMin() != null) {
            if (projectDto.getLoanmoneyMax() != null && projectDto.getLoanmoneyMin().compareTo(projectDto.getLoanmoneyMax()) > 0) {
                throw new IllegalArgumentException("贷款金额最小值不能大于最大值");
            }
            project.setLoanmoneyMin(projectDto.getLoanmoneyMin());
        }

        if (projectDto.getLoanmonthMax() != null) {
            if (projectDto.getLoanmonthMax() < 0) {
                throw new IllegalArgumentException("贷款期限最大值不能为负数");
            }
            project.setLoanmonthMax(projectDto.getLoanmonthMax());
        }

        if (projectDto.getLoanmonthMin() != null) {
            if (projectDto.getLoanmonthMin() < 0) {
                throw new IllegalArgumentException("贷款期限最小值不能为负数");
            }
            project.setLoanmonthMin(projectDto.getLoanmonthMin());
        }

        if (projectDto.getRateMax() != null) {
            if (projectDto.getRateMax().compareTo(projectDto.getRateMin()) < 0) {
                throw new IllegalArgumentException("贷款利率最大值不能小于最小值");
            }
            project.setRateMax(projectDto.getRateMax());
        }

        if (projectDto.getRateMin() != null) {
            if (projectDto.getRateMax() != null && projectDto.getRateMin().compareTo(projectDto.getRateMax()) > 0) {
                throw new IllegalArgumentException("贷款利率最小值不能大于最大值");
            }
            project.setRateMin(projectDto.getRateMin());
        }

        // 保存并返回更新后的项目信息
        return projectRepository.save(project);
    }

    // 获取所有项目
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
