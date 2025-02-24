package com.loan.system_loan.controller;

import com.loan.system_loan.pojo.dto.RegisterDto;
import com.loan.system_loan.service.RegisterService;
import com.loan.system_loan.service.RegisterServiceFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterServiceFactory registerServiceFactory;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDto request) {
        RegisterService service = registerServiceFactory.getService(request.getRoleType());
        service.register(request);
        return ResponseEntity.ok("Registration successful!");
    }
}
