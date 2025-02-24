package com.loan.system_loan.pojo;

import com.loan.system_loan.pojo.dto.UserFilterDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;


public class UserSpecification implements Specification<User> {
    private final UserFilterDto filterDto;

    public UserSpecification(UserFilterDto filterDto) {
        this.filterDto = filterDto;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction(); // 空条件组合

        // 根据性别筛选
        if (filterDto.getSex() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("sex"), filterDto.getSex()));
        }

        // 根据出生日期筛选（范围）
        if (filterDto.getBirthdayFrom() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), filterDto.getBirthdayFrom()));
        }
        if (filterDto.getBirthdayTo() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), filterDto.getBirthdayTo()));
        }

        // 根据职业筛选
        if (filterDto.getJob() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("job"), filterDto.getJob()));
        }

        // 根据公司筛选
        if (filterDto.getCompany() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("company"), filterDto.getCompany()));
        }

        // 根据年收入筛选（范围）
        if (filterDto.getYearIncomeMin() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThanOrEqualTo(root.get("yearIncome"), filterDto.getYearIncomeMin()));
        }
        if (filterDto.getYearIncomeMax() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.lessThanOrEqualTo(root.get("yearIncome"), filterDto.getYearIncomeMax()));
        }

        // 根据工作年限筛选（范围）
        if (filterDto.getWorkYearsMin() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThanOrEqualTo(root.get("workYears"), filterDto.getWorkYearsMin()));
        }
        if (filterDto.getWorkYearsMax() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.lessThanOrEqualTo(root.get("workYears"), filterDto.getWorkYearsMax()));
        }

        // 根据婚姻状况筛选
        if (filterDto.getIsmarry() != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("ismarry"), filterDto.getIsmarry()));
        }

        return predicate;
    }
}
