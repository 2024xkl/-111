package com.loan.system_loan.controller;

import com.loan.system_loan.pojo.Clerk;
import com.loan.system_loan.pojo.ResponseMessage;
import com.loan.system_loan.pojo.User;
import com.loan.system_loan.pojo.dto.ClerkDto;
import com.loan.system_loan.pojo.dto.ClerkLoginDTO;
import com.loan.system_loan.service.ClerkService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController//接口方法返回对象 转换成json文本
@RequestMapping("/clerk") //localhost:8080/user/
public class ClerkControlller {

    @Autowired
    private ClerkService clerkService;

    @PostMapping("/add")
    public ResponseMessage<Clerk> addClerk(@RequestBody @Valid ClerkDto clerkDto) {
        try {
            // 调用服务层的 addClerk 方法
            Clerk clerk = clerkService.addClerk(clerkDto);
            return ResponseMessage.success(clerk);  // 返回成功的响应
        } catch (IllegalArgumentException e) {
            return ResponseMessage.error1(e.getMessage());  // 返回错误信息
        }
    }

    //删除
    @DeleteMapping("/delete/{clerkId}")
    public ResponseMessage<Clerk> delete(@PathVariable Integer clerkId){
        clerkService.delete(clerkId);
        return ResponseMessage.success();
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody ClerkLoginDTO clerkLoginDTO, HttpServletRequest request) {
        // 从数据库获取营业员信息
        Clerk clerk = clerkService.getClerkById(clerkLoginDTO.getClerkId());

        if (clerk != null && clerk.getPassword().equals(clerkLoginDTO.getPassword())) {
            // 登录成功，设置角色
            HttpSession session = request.getSession();
            session.setAttribute("role", clerk.getRole());
            session.setAttribute("clerkId", clerk.getClerkId());

            // 返回完整的营业员信息
            return ResponseEntity.ok(clerk);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("用户名或密码错误");
        }
    }

}
