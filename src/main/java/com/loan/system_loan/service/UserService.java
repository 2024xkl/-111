package com.loan.system_loan.service;
import com.loan.system_loan.pojo.Account;
import com.loan.system_loan.pojo.IDCardUtils;
import com.loan.system_loan.pojo.User;
import com.loan.system_loan.pojo.UserSpecification;
import com.loan.system_loan.pojo.dto.AccountDto;
import com.loan.system_loan.pojo.dto.UserDto;
import com.loan.system_loan.pojo.dto.UserFilterDto;
import com.loan.system_loan.repository.AccountRepository;
import com.loan.system_loan.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service //spring bean
public class UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    CreditReportService creditReportService;
    @Autowired
    private AccountRepository accountRepository;


    @Transactional
    public User add(UserDto user, BindingResult result) {
        try {
            // 先检查身份证号是否已存在
            Optional<User> existingUser = userRepository.findByCardId(user.getCardId());
            if (existingUser.isPresent()) {
                throw new RuntimeException("身份证号已被注册，请检查输入!");
            }

            // 检查手机号是否已存在
            Optional<User> existingUserByPhone = userRepository.findByPhone(user.getPhone());
            if (existingUserByPhone.isPresent()) {
                throw new RuntimeException("该手机号已被注册，请检查输入!");
            }

            // 检查邮箱是否已存在
            Optional<User> existingUserByEmail = userRepository.findByEmail(user.getEmail());
            if (existingUserByEmail.isPresent()) {
                throw new RuntimeException("该邮箱号已被注册，请检查输入!");
            }

            // 如果不存在，继续进行用户保存
            User userPojo = new User();
            BeanUtils.copyProperties(user, userPojo);
            User savedUser = userRepository.save(userPojo);


            // 创建空的征信报告
            creditReportService.createCreditReport(savedUser.getUserId());

            return savedUser;
        } catch (DataIntegrityViolationException e) {
            // 捕获数据库唯一性约束冲突异常
            if (e.getMessage().contains("card_id")) {
                throw new RuntimeException("身份证号已存在，请检查输入!");
            }
            throw new RuntimeException("保存用户时发生异常!", e);
        } catch (RuntimeException e) {
            // 如果是邮箱冲突等用户自定义异常，抛出原始的异常信息
            throw new RuntimeException(e.getMessage(), e);
        } catch (Exception e) {
            // 捕获其他潜在异常
            throw new RuntimeException("添加用户失败，请稍后再试!", e);
        }

    }


    public User getUser1(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> {
            try {
                throw new IllegalAccessException("该用户ID不存在!");
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

    }


    public List<User> getUser2(String userName) {
        List<User> users = userRepository.findByUserName(userName);
        if (users.isEmpty()) {
            throw new RuntimeException("该用户名不存在!");
        }
        return users;
    }


    public User getUser3(String cardId) {
        return userRepository.findByCardId(cardId).orElseThrow(() ->
                new RuntimeException("该身份证号不存在!")
        );

    }


    public List<AccountDto> getAccountsByUserId(Integer userId) {
        // 查询该用户的所有账户
        List<Account> accounts = accountRepository.findByUserId(userId);
        return accounts.stream().map(account -> {
            AccountDto accountDto = new AccountDto();
            BeanUtils.copyProperties(account, accountDto);
            return accountDto;
        }).collect(Collectors.toList());
    }



    @Transactional
    public User edit(UserDto userDto) {

        // 确保传入的 userDto 不为空
        if (userDto == null) {
            throw new IllegalArgumentException("传入的用户信息不能为空");
        }

        // 查找原有的用户信息
        User user = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("用户不存在"));

        // 更新非空字段
        if (userDto.getUserName() != null) user.setUserName(userDto.getUserName());
        if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());

        // 如果修改了邮箱，检查邮箱是否已被注册
        if (userDto.getEmail() != null && !userDto.getEmail().equals(user.getEmail())) {
            Optional<User> existingEmailUser = userRepository.findByEmail(userDto.getEmail());
            if (existingEmailUser.isPresent()) {
                throw new RuntimeException("该邮箱号已被注册，请检查输入!");
            }
            user.setEmail(userDto.getEmail());
        }

        if (userDto.getSex() != null) {
            try {
                user.setSex(String.valueOf(userDto.getSex()));  // 设置性别
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("性别非法，仅支持：男或女", e);
            }
        }

        // 检查用户是否试图直接修改出生日期或年龄
        if (userDto.getBirthday() != null && !userDto.getBirthday().equals(user.getBirthday())) {
            throw new IllegalArgumentException("请修改身份证号，系统将自动计算出生日期和年龄");
        }


        if (userDto.getCardId() != null && !userDto.getCardId().equals(user.getCardId())) {
            Optional<User> existingCardIdUser = userRepository.findByCardId(userDto.getCardId());
            if (existingCardIdUser.isPresent()) {
                throw new RuntimeException("该身份证号已被注册，请检查输入!");
            }

            // 如果身份证号有变化，则更新身份证号并自动更新出生日期和年龄
            String newCardId = userDto.getCardId();
            user.setCardId(newCardId);
            // 自动更新出生日期和年龄
            String newBirthday = IDCardUtils.extractBirthday(newCardId);
            user.setBirthday(newBirthday);
            user.setAge(IDCardUtils.calculateAge(newBirthday));
        }

// 如果修改了手机号，检查手机号是否已被注册
        if (userDto.getPhone() != null && !userDto.getPhone().equals(user.getPhone())) {
            Optional<User> existingPhoneUser = userRepository.findByPhone(userDto.getPhone());
            if (existingPhoneUser.isPresent()) {
                throw new RuntimeException("该手机号已被注册，请检查输入!");
            }
            user.setPhone(userDto.getPhone());
        }

        if (userDto.getAddress() != null) user.setAddress(userDto.getAddress());
        if (userDto.getJob() != null) user.setJob(userDto.getJob());
        if (userDto.getCompany() != null) user.setCompany(userDto.getCompany());
        // 添加对年收入和工作年限的限制
        if (userDto.getYearIncome() != null) {
            if (userDto.getYearIncome().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("年收入不能为负数");
            }
            user.setYearIncome(userDto.getYearIncome());
        }

        if (userDto.getWorkYears() != null) {
            if (userDto.getWorkYears() < 0) {
                throw new IllegalArgumentException("工作时间不能为负数");
            }
            user.setWorkYears(userDto.getWorkYears());
        }
        if (userDto.getIsmarry() != null) {
            try {
                user.setIsmarry(String.valueOf(userDto.getIsmarry()));  // 设置婚姻状况
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("婚姻状况非法，仅支持：已婚或未婚", e);
            }
        }

        // 保存并返回更新后的用户信息
        return userRepository.save(user);
    }



    public void delete(Integer userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("用户ID不存在，无法删除!");
        }
        userRepository.deleteById(userId);
   }

    public List<User> filterUsers(UserFilterDto filterDto) {
        UserSpecification specification = new UserSpecification(filterDto);
        return userRepository.findAll(specification);
    }

    // 获取所有用户
    public List<User> getalluser() {
        return userRepository.findAll();
    }
}
