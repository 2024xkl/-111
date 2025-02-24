package com.loan.system_loan.service;

import com.loan.system_loan.pojo.*;
import com.loan.system_loan.pojo.dto.AccountDto;
import com.loan.system_loan.pojo.dto.LoanApplicationDto;
import com.loan.system_loan.pojo.dto.ProjectDto;
import com.loan.system_loan.pojo.dto.UserDto;
import com.loan.system_loan.repository.*;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class LoanApplicationService {
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private RepayScheduleRepository repayScheduleRepository;

    @Autowired
    CreditReportService creditReportService;


    // 贷款申请
    public String applyForLoan(Integer userId, Integer accountId, Integer projectId, BigDecimal applicationMoney, Integer applicationTime) {
        // 获取账户信息
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account == null) {
            return "账户不存在";
        }
        // 校验账户是否属于当前用户
        if (!account.getUserId().equals(userId)) {
            return "不能为其他用户的账户申请贷款";
        }

        // 获取项目数据
        Project project = projectRepository.findById(projectId).orElse(null);
        if (project == null) {
            return "项目不存在";
        }

        // 校验申请金额
        if (applicationMoney.compareTo(project.getLoanmoneyMin()) < 0 || applicationMoney.compareTo(project.getLoanmoneyMax()) > 0) {
            return "申请金额不符合项目要求";
        }

        // 校验贷款期限
        if (applicationTime < project.getLoanmonthMin() || applicationTime > project.getLoanmonthMax()) {
            return "贷款期限不符合项目要求";
        }

        // 校验账户贷款余额
        if (applicationMoney.compareTo(account.getLoanBalance()) > 0) {
            return "账户贷款余额不足";
        }

        // 创建贷款申请记录
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setUserId(userId);
        loanApplication.setAccountId(accountId);
        loanApplication.setProjectId(projectId);
        loanApplication.setApplicationmoney(applicationMoney);
        loanApplication.setApplicationtime(applicationTime);
        loanApplication.setStatus(0); // 初始为未审核状态

        // 保存贷款申请
        loanApplicationRepository.save(loanApplication);

        return "贷款申请成功,等待审核！";
    }

    // 审核贷款申请
    @Transactional
    public String assessLoanApplication(Integer loanApplicationId, Boolean approve) {
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId).orElse(null);
        if (loanApplication == null) {
            return "贷款申请不存在";
        }

        // 贷款申请已审核
        if (loanApplication.getStatus() != 0) {
            return "贷款申请已经审核过";
        }

        // 审核员批准贷款
        if (approve != null && approve) {
            Account account = accountRepository.findById(loanApplication.getAccountId()).orElse(null);
            if (account != null) {
                // 更新账户贷款余额
                account.setLoanBalance(account.getLoanBalance().subtract(loanApplication.getApplicationmoney()));
                accountRepository.save(account);

                // 更新贷款申请为已审核已通过
                loanApplication.setStatus(2); // 设置为已审核已通过
                loanApplicationRepository.save(loanApplication);

                // 计算年化利率（例如，根据金额和贷款期限计算）
                BigDecimal rate = calculateAnnualRate(loanApplication.getApplicationmoney(), loanApplication.getApplicationtime());

                // 创建并保存贷款记录
                Loan loan = new Loan();
                loan.setUserId(loanApplication.getUserId());
                loan.setDate(LocalDate.now().toString()); // 设置为当前日期
                loan.setMoney(loanApplication.getApplicationmoney());
                loan.setRate(rate); // 自动计算的年化利率
                loan.setTime(loanApplication.getApplicationtime());
                loan.setOvertimeRate(new BigDecimal("0.2")); // 假设超期年化利率为10%
                loan.setAdvanceRepay(true); // 假设支持提前还款
                Loan savedLoan =  loanRepository.save(loan);  // 保存贷款记录

                // 更新用户的信用报告：添加贷款记录
                creditReportService.addLoanToCreditReport(loanApplication.getUserId(), savedLoan);

                // 为贷款生成还款计划
                generateRepaySchedule(savedLoan);

                return "贷款申请审核通过，账户贷款金额已更新";
            }
            return "账户信息错误";
        }

        // 审核不通过
        loanApplication.setStatus(1); // 设置为已审核未通过
        loanApplicationRepository.save(loanApplication);

        return "贷款申请审核未通过";
    }


    private void generateRepaySchedule(Loan loan) {
        try {
            // 确保贷款记录保存后，loan.getLoanId() 获取到正确的ID
            BigDecimal principal = loan.getMoney().divide(new BigDecimal(loan.getTime()), BigDecimal.ROUND_HALF_UP); // 等额本金计算
            BigDecimal interestRate = loan.getRate().divide(new BigDecimal("12"), BigDecimal.ROUND_HALF_UP); // 月利率
            BigDecimal overinterestRate = loan.getOvertimeRate().divide(new BigDecimal("12"), BigDecimal.ROUND_HALF_UP); // 月利率

            List<RepaySchedule> repaySchedules = new ArrayList<>();

            // 使用当前时间作为起始还款日
            LocalDate startDate = LocalDate.now();

            for (int month = 1; month <= loan.getTime(); month++) {
                // 根据月份生成还款日期
                LocalDate repayDate = startDate.plusMonths(month);

                // 计算每期应还本金
                BigDecimal interest = loan.getMoney().multiply(interestRate);
                BigDecimal totalRepay = principal.add(interest); // 总还款金额

                // 创建并设置还款计划
                RepaySchedule repaySchedule = new RepaySchedule();
                repaySchedule.setLoanId(loan.getLoanId()); // 确保正确设置 Loan ID
                repaySchedule.setRepayDate(repayDate.toString()); // 格式化还款日期
                repaySchedule.setPrincipal(principal);
                repaySchedule.setInterest(interest);
                repaySchedule.setOverdueInterest(overinterestRate);
                repaySchedule.setTotalRepay(totalRepay);

                repaySchedules.add(repaySchedule);
            }

            // 保存所有还款计划
            repayScheduleRepository.saveAll(repaySchedules); // 批量保存还款计划

            // 日志，确保还款计划生成
            System.out.println("生成还款计划，贷款ID: " + loan.getLoanId() + "，还款计划数: " + repaySchedules.size());
        } catch (Exception e) {
            System.err.println("保存还款计划时发生错误: " + e.getMessage());
        }
    }

    private BigDecimal calculateAnnualRate(BigDecimal applicationmoney, Integer applicationtime) {
        // 示例：假设年化利率根据贷款金额和期限来决定
        BigDecimal baseRate = new BigDecimal("0.10"); // 基础年化利率为10%
        if (applicationmoney.compareTo(new BigDecimal("10000")) > 0) {
            baseRate = baseRate.add(new BigDecimal("0.05")); // 大于10000的贷款加5%
        }
        if (applicationtime > 12) {
            baseRate = baseRate.add(new BigDecimal("0.08")); // 贷款期限超过12个月，加8%
        }
        return baseRate;
    }


    // 查询所有贷款申请
    public List<LoanApplicationDto> getAllLoanApplications() {
        List<LoanApplication> applications = loanApplicationRepository.findAll();
        return convertToDtoList(applications);
    }

    // 查询所有未审核的贷款申请 (status = 0)
    public List<LoanApplicationDto> getUnapprovedLoanApplications() {
        List<LoanApplication> applications = loanApplicationRepository.findByStatus(0);
        return convertToDtoList(applications);
    }

    // 查询所有已审核未通过的贷款申请 (status = 1)
    public List<LoanApplicationDto> getReviewedNotApprovedLoanApplications() {
        List<LoanApplication> applications = loanApplicationRepository.findByStatus(1);
        return convertToDtoList(applications);
    }

    // 查询所有已审核已通过的贷款申请 (status = 2)
    public List<LoanApplicationDto> getReviewedApprovedLoanApplications() {
        List<LoanApplication> applications = loanApplicationRepository.findByStatus(2);
        return convertToDtoList(applications);
    }

    // 根据用户id查询贷款申请
    public List<LoanApplicationDto> getLoanApplicationsByUserId(Integer userId) {
        List<LoanApplication> applications = loanApplicationRepository.findByUserId(userId);
        return convertToDtoList(applications);
    }

    // DTO转换
    private List<LoanApplicationDto> convertToDtoList(List<LoanApplication> applications) {
        List<LoanApplicationDto> dtoList = new ArrayList<>();
        for (LoanApplication application : applications) {
            LoanApplicationDto dto = new LoanApplicationDto();
            dto.setLoanApplicationId(application.getLoanApplicationId());
            dto.setUserId(application.getUserId());
            dto.setAccountId(application.getAccountId());
            dto.setProjectId(application.getProjectId());
            dto.setApplicationmoney(application.getApplicationmoney());
            dto.setApplicationtime(application.getApplicationtime());
            dto.setStatus(application.getStatus()); // 显示合并状态
            dtoList.add(dto);
        }
        return dtoList;
    }




}
