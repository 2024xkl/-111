package com.loan.system_loan.controller;

import com.loan.system_loan.pojo.Administrator;
import com.loan.system_loan.pojo.dto.AdministratorDto;
import com.loan.system_loan.pojo.dto.AdministratorLoginDTO;
import com.loan.system_loan.service.AdministratorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/administrator")
public class AdministratorController {
    @Autowired
    private AdministratorService administratorService;

    // 添加管理员
    @PostMapping("/add")
    public ResponseEntity<Administrator> addAdministrator(@RequestBody AdministratorDto administratorDto) {
        Administrator administrator = administratorService.addAdministrator(administratorDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(administrator);
    }

    // 删除管理员
    @DeleteMapping("/delete/{adminId}")
    public ResponseEntity<String> deleteAdministrator(@PathVariable Integer adminId) {
        administratorService.deleteAdministrator(adminId);
        return ResponseEntity.status(HttpStatus.OK).body("管理员已删除");
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdministratorLoginDTO administratorLoginDTO, HttpServletRequest request) {
        // 从数据库获取管理员信息
        Administrator administrator = administratorService.getAdministratorById(administratorLoginDTO.getAdminId());

        if (administrator != null && administrator.getPassword().equals(administratorLoginDTO.getPassword())) {
            // 登录成功，设置角色
            HttpSession session = request.getSession();
            session.setAttribute("role", administrator.getRole());
            session.setAttribute("adminId", administrator.getAdminId()); // 存储管理员ID

            // 返回完整的管理员信息
            return ResponseEntity.ok(administrator);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }


}
