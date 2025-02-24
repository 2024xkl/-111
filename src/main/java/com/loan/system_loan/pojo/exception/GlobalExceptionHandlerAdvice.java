package com.loan.system_loan.pojo.exception;

import com.loan.system_loan.pojo.ResponseMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.loan.system_loan.pojo.ResponseMessage;

@RestControllerAdvice  //统一处理
public class GlobalExceptionHandlerAdvice {
    Logger log = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);
    @ExceptionHandler({Exception.class})
    public ResponseMessage handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("统一异常",e);
        return new ResponseMessage(500,"error",null);
    }
}
