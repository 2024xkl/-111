package com.loan.system_loan.service;

import com.loan.system_loan.pojo.User;
import com.loan.system_loan.pojo.dto.RegisterDto;
import com.loan.system_loan.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserRegisterService implements RegisterService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public void register(RegisterDto request) {
//        try {
//            // 先检查身份证号是否已存在
//            Optional<User> existingUser = userRepository.findByCardId(user.getCardId());
//            if (existingUser.isPresent()) {
//                throw new RuntimeException("身份证号已被注册，请检查输入!");
//            }
//
//            // 检查手机号是否已存在
//            Optional<User> existingUserByPhone = userRepository.findByPhone(user.getPhone());
//            if (existingUserByPhone.isPresent()) {
//                throw new RuntimeException("该手机号已被注册，请检查输入!");
//            }
//
//            // 检查邮箱是否已存在
//            Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
//            if (existingUserByEmail.isPresent()) {
//                throw new RuntimeException("该邮箱号已被注册，请检查输入!");
//            }

            User user = new User();
        user.setUserName(request.getUserName());
        user.setPassword(request.getPassword());
        user.setEmail((String) request.getAdditionalFields().get("email"));
        user.setPhone((String) request.getAdditionalFields().get("phone"));
        user.setSex((String) request.getAdditionalFields().get("sex"));
        user.setBirthday((String) request.getAdditionalFields().get("birthday"));
        user.setCardId((String) request.getAdditionalFields().get("card_id"));
        user.setAddress((String) request.getAdditionalFields().get("address"));
        user.setYearIncome((BigDecimal) request.getAdditionalFields().get("year_income"));
        user.setWorkYears((Integer) request.getAdditionalFields().get("work_years"));
        user.setIsmarry((String) request.getAdditionalFields().get("is_marry"));
        user.setCompany((String) request.getAdditionalFields().get("company"));
        user.setJob((String) request.getAdditionalFields().get("job"));
        userRepository.save(user);
    }


}
