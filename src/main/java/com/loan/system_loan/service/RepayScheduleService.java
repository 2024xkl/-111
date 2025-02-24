package com.loan.system_loan.service;

import com.loan.system_loan.pojo.Account;
import com.loan.system_loan.pojo.Alert;
import com.loan.system_loan.pojo.Loan;
import com.loan.system_loan.pojo.RepaySchedule;
import com.loan.system_loan.repository.AccountRepository;
import com.loan.system_loan.repository.AlertRepository;
import com.loan.system_loan.repository.LoanRepository;
import com.loan.system_loan.repository.RepayScheduleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RepayScheduleService {
    @Autowired
    private RepayScheduleRepository repayScheduleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AlertRepository alertRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private CreditReportService creditReportService;

    // 定时任务每天执行
    @Scheduled(cron = "0 0 0 * * ?") // 每天凌晨0点执行
    @Transactional
    public void processRepayment() {
        LocalDate today = LocalDate.now();

        // 查询所有到期且未还的还款计划
        List<RepaySchedule> dueSchedules = repayScheduleRepository.findByRepayDateAndStatus(today.toString(), 0);

        for (RepaySchedule schedule : dueSchedules) {

            Loan loan = loanRepository.findById(schedule.getLoanId()).orElse(null);

            if (loan == null) continue;

            Account account = accountRepository.findById(loan.getUserId()).orElse(null);
            if (account == null) continue;

            BigDecimal totalRepay = schedule.getTotalRepay();

            // 检查账户余额
            if (account.getBalance().compareTo(totalRepay) >= 0) {
                // 扣款
                account.setBalance(account.getBalance().subtract(totalRepay));
                accountRepository.save(account);

                // 更新还款计划为已还
                schedule.setStatus(1);
                schedule.setOverdueInterest(BigDecimal.ZERO);
                repayScheduleRepository.save(schedule);

                // 更新用户的征信报告


                creditReportService.updateTotalDebtAfterRepayment(loan.getUserId());

                System.out.println("还款成功，计划ID: " + schedule.getRepayId());
            } else {
                // 余额不足，标记为逾期
                schedule.setStatus(2);

                // 计算逾期利息 (年化利率为10%，按天计算)
                BigDecimal overdueInterest = totalRepay
                        .multiply(new BigDecimal("0.1"))
                        .divide(new BigDecimal("365"), BigDecimal.ROUND_HALF_UP);
                schedule.setOverdueInterest(overdueInterest);
                repayScheduleRepository.save(schedule);

                // 创建报警记录
                Alert alert = new Alert();
                alert.setLoanId(schedule.getLoanId());
                alert.setRepayId(schedule.getRepayId());
                alert.setAlertDate(today.toString());
                alert.setAlertMessage("余额不足，逾期还款计划ID: " + schedule.getRepayId());
                alertRepository.save(alert);

                System.out.println("报警记录已生成，计划ID: " + schedule.getRepayId());
            }
        }
    }

    // 查看所有还款计划
    public List<RepaySchedule> viewAll() {
        return (List<RepaySchedule>) repayScheduleRepository.findAll();
    }

    // 根据 loanId 查找还款计划
    public List<RepaySchedule> findByLoanId(Integer loanId) {
        return repayScheduleRepository.findByLoanId(loanId);
    }

}
