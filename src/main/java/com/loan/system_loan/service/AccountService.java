package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Account;
import com.loan.system_loan.pojo.BankCardUtils;
import com.loan.system_loan.pojo.BankStatement;
import com.loan.system_loan.pojo.User;
import com.loan.system_loan.pojo.dto.AccountDto;
import com.loan.system_loan.repository.AccountRepository;
import com.loan.system_loan.repository.BankStatementRepository;
import com.loan.system_loan.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankStatementRepository bankStatementRepository;
    @Autowired
    CreditReportService creditReportService;

    /**
     * 添加账户，同时确保用户存在
     */

//    @Transactional
//    public Account add(AccountDto accountDto) {
//        // 获取 userId
//        Integer userId = accountDto.getUserId();
//
//        // 检查 userId 是否存在
//        if (userId == null) {
//            throw new IllegalArgumentException("用户ID不能为空！");
//        }
//
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (!userOptional.isPresent()) {
//            throw new IllegalArgumentException("用户不存在！");
//        }
//
//        // 创建 Account 实体对象
//        Account account = new Account();
//        account.setUserId(userId);  // 设置 userId
//        account.setBankCardNumber(generateUniqueBankCardNumber());
//        account.setBankName(accountDto.getBankName());
//        account.setBalance(accountDto.getBalance());
//        account.setLoanBalance(accountDto.getLoanBalance());
//        Account savedAccount = accountRepository.save(account);
//
//        // 更新征信报告：添加账户记录
//        creditReportService.addAccountToCreditReport(userId, savedAccount);
//
//
//        return savedAccount;
//    }

    @Transactional
    public Account add(Integer userId, String bankName, String password) {
        // 获取用户信息
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        // 生成唯一的银行卡号
        String bankCardNumber = generateUniqueBankCardNumber();

        // 根据年收入计算贷款额度（假设贷款额度为年收入的3倍）
        BigDecimal loanBalance = calculateLoanBalance(user.getYearIncome());

        // 创建 Account 实体对象
        Account account = new Account();
        account.setUserId(userId);  // 设置 userId
        account.setBankCardNumber(bankCardNumber);
        account.setBankName(bankName);
        account.setPassword(password);
        account.setBalance(BigDecimal.ZERO);
        account.setLoanBalance(loanBalance);
        Account savedAccount = accountRepository.save(account);

        // 更新征信报告：添加账户记录
        creditReportService.addAccountToCreditReport(userId, savedAccount);


        return savedAccount;
    }
    private String generateUniqueBankCardNumber() {
        String cardNumber;
        int attempts = 0;
        do {
            if (attempts > 10) {
                throw new RuntimeException("无法生成唯一的银行卡号，请检查数据库或生成逻辑！");
            }
            cardNumber = generateCardNumber();
            attempts++;
        } while (accountRepository.existsByBankCardNumber(cardNumber));
        return cardNumber;
    }

    private String generateCardNumber() {
        return String.format("%016d", new Random().nextLong() & Long.MAX_VALUE);
    }

    private BigDecimal calculateLoanBalance(BigDecimal yearIncome) {
        if (yearIncome == null) {
            return BigDecimal.ZERO;
        }

        return yearIncome.multiply(BigDecimal.valueOf(3));
    }
    /**
     * 根据用户 ID 获取账户列表
     */

    public List<Account> getAccountsByUserId(Integer userId) {

        return accountRepository.findByUserId(userId);
    }


    @Transactional

    public Account deposit(String bankCardNumber, BigDecimal amount, String password) {
        // 验证银行卡号和密码
        Account account = accountRepository.findByBankCardNumber(bankCardNumber)
                .orElseThrow(() -> new IllegalArgumentException("银行卡号不存在"));

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("密码错误");
        }



        // 更新账户余额
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        // 记录银行流水
        BankStatement bankStatement = new BankStatement();
        bankStatement.setAccountId(account.getAccountId());
        bankStatement.setAmount(amount);
        bankStatement.setMotionType("存款");
        bankStatement.setDate(LocalDateTime.now().toString());  // 使用当前时间戳
        bankStatementRepository.save(bankStatement);

        return account;  // 返回更新后的账户信息
    }


    @Transactional

    public Account withdraw(String bankCardNumber, BigDecimal amount, String password) {
        // 使用 orElseThrow 抛出异常
        Account account = accountRepository.findByBankCardNumber(bankCardNumber)
                .orElseThrow(() -> new IllegalArgumentException("银行卡号不存在"));

        // 检查密码是否正确
        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("密码错误");
        }
        // 检查账户余额是否足够
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("账户余额不足");
        }

        // 更新账户余额
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        // 记录银行流水
        BankStatement bankStatement = new BankStatement();
        bankStatement.setAccountId(account.getAccountId());
        bankStatement.setAmount(amount);
        bankStatement.setMotionType("取款");
        bankStatement.setDate(LocalDateTime.now().toString());
        bankStatementRepository.save(bankStatement);

        return account;  // 返回更新后的账户信息
    }

    public String getBankNameByAccountId(Integer accountId) {
        return accountRepository.findBankNameByAccountId(accountId);
    }


}
