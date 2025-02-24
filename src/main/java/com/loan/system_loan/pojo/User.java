package com.loan.system_loan.pojo;

import com.loan.system_loan.pojo.dto.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Table(name = "tb_user")
@Entity
public class User {
    @Id
    //自动生成userid
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank(message = "用户名不能为空")
    @Column(name = "user_name")
    private String userName;

    @Length( min = 6, max = 12, message = "密码长度应在6到12位之间")
    @NotBlank(message = "密码不能为空")
    @Column(name = "password")
    private String password;

    @Email( message = "email格式不正确")
    @Column(name = "email", unique = true)
    private String email;

    // 性别字段限制为男女
    @NotBlank(message = "性别不能为空")
    @Column(name = "sex")
    @Pattern(regexp = "^(男|女)$", message = "性别只能为 男 或 女")
    private String sex;


    @Column(name = "birthday")
    private String birthday;

    @NotBlank(message = "身份证号不能为空")
    @Column(name = "card_id", unique = true)
    private String cardId;

    @Pattern(
            regexp = "^1[3-9]\\d{9}$",
            message = "手机号格式不正确"
    )
    @NotBlank(message = "电话号码不能为空")
    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "job")
    private String job;

    @Column(name = "company")
    private String company;

    @NotNull(message = "工作时长不能为空")
    @Min(value = 0, message = "工作时间不能为负数")
    @Column(name = "work_years")
    private Integer workYears;

    @NotNull(message = "年收入不能为空")
    @Column(name = "year_income")
    @Min(value = 0, message = "年收入不能为负数")
    private BigDecimal yearIncome;

    // 婚姻状况限制为已婚和未婚
    @NotBlank(message = "婚姻状况不能为空")
    @Column(name = "ismarry")
    @Pattern( regexp = "^(已婚|未婚)$", message = "婚否状况只能为 已婚 或 未婚")
    private String ismarry;



    @Column(name = "age")
    private Integer age;

    @PrePersist
    @PreUpdate
    public void updateBirthdayAndAge() {
        if (this.cardId != null) {
            // 自动设置出生日期
            this.birthday = IDCardUtils.extractBirthday(this.cardId);

            // 自动计算年龄
            this.age = IDCardUtils.calculateAge(this.birthday);
        }
    }

    //@Column(name = "roletype")
    @NotNull(message = "角色类型不能为空")
    private RoleType role = RoleType.USER;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
//    public enum Role {
//
//        USER, CLERK, ADMINISTRATOR, ASSESSOR
//
//    }


    public  String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public  String getIsmarry() {
        return ismarry;
    }

    public void setIsmarry(String ismarry) {
        this.ismarry = ismarry;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public Integer getWorkYears() {
        return workYears;
    }

    public void setWorkYears(Integer workYears) {
        this.workYears = workYears;
    }


    public BigDecimal getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(BigDecimal yearIncome) {
        this.yearIncome = yearIncome;
    }
}

