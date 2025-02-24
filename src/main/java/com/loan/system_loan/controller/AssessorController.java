package com.loan.system_loan.controller;

import com.loan.system_loan.pojo.Assessor;
import com.loan.system_loan.pojo.dto.AssessorDto;
import com.loan.system_loan.pojo.dto.AssessorLoginDTO;
import com.loan.system_loan.service.AssessorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assessor")
public class AssessorController {
    @Autowired
    private AssessorService assessorService;

    // 添加审核员
    @PostMapping("/add")
    public ResponseEntity<Assessor> addAssessor(@RequestBody AssessorDto assessorDto) {
        Assessor assessor = assessorService.addAssessor(assessorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(assessor);
    }

    // 删除审核员
    @DeleteMapping("/delete/{assessorId}")
    public ResponseEntity<String> deleteAssessor(@PathVariable Integer assessorId) {
        assessorService.deleteAssessor(assessorId);
        return ResponseEntity.status(HttpStatus.OK).body("审核员已删除");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AssessorLoginDTO assessorLoginDTO, HttpServletRequest request) {
        // 从数据库获取审核员信息
        Assessor assessor = assessorService.getAssessorById(assessorLoginDTO.getAssessorId());

        if (assessor != null && assessor.getPassword().equals(assessorLoginDTO.getPassword())) {
            // 登录成功，设置角色和审核员ID到会话中
            HttpSession session = request.getSession();
            session.setAttribute("role", assessor.getRole());
            session.setAttribute("assessorId", assessor.getAssessorId());

            // 返回完整的审核员信息
            return ResponseEntity.ok(assessor);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }


}
