package com.loan.system_loan.controller;

import com.loan.system_loan.annotation.RoleRequired;
import com.loan.system_loan.pojo.Project;
import com.loan.system_loan.pojo.ResponseMessage;
import com.loan.system_loan.pojo.RoleType;
import com.loan.system_loan.pojo.dto.ProjectDto;
import com.loan.system_loan.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // 查看所有项目
    @GetMapping("/viewall")
//    @RoleRequired(RoleType.CLERK) // 你可以根据需要修改角色要求
    public ResponseEntity<ResponseMessage> viewAllProjects() {
        try {
            // 获取所有项目
            List<Project> projects = projectService.getAllProjects();
            ResponseMessage responseMessage = new ResponseMessage<>(HttpStatus.OK.value(), "查询成功", projects);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            // 处理异常并返回错误信息
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }

    @PostMapping("/add")
    @RoleRequired(RoleType.ADMINISTRATOR)
    public ResponseEntity<Project> addProject(@Valid @RequestBody ProjectDto projectDto) {
        Project project = projectService.addProject(projectDto);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/delete/{projectId}")
    @RoleRequired(RoleType.ADMINISTRATOR)
    public ResponseEntity<String> deleteProject(@PathVariable Integer projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.ok("Project deleted successfully.");
    }

    // 编辑项目
    @PutMapping("/edit")
    @RoleRequired(RoleType.ADMINISTRATOR)
    public ResponseEntity<ResponseMessage> editProject(@Valid @RequestBody ProjectDto projectDto) {
        try {
            // 调用 service 层更新项目
            Project project = projectService.edit(projectDto);
            // 创建统一的响应消息
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.OK.value(), "项目编辑成功", project);
            return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
        } catch (Exception e) {
            // 处理异常并返回错误信息
            ResponseMessage responseMessage = new ResponseMessage(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseMessage);
        }
    }
}
