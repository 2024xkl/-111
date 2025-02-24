package com.loan.system_loan.pojo.dto;

import com.loan.system_loan.pojo.RoleType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.math.BigDecimal;

public class UserDto {
    @NotNull(groups = ValidationGroups.Edit.class, message = "用户ID不能为空")
    private Integer userId;



    @NotBlank(groups = ValidationGroups.Create.class, message = "用户名不能为空")
    private String userName;

    @NotBlank(groups = ValidationGroups.Create.class, message = "密码不能为空")
    @Length(groups = ValidationGroups.Create.class, min = 6, max = 12, message = "密码长度应在6到12位之间")
    private String password;

    @Email(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, message = "email格式不正确")
    private String email;

    @NotBlank(groups = ValidationGroups.Create.class,message = "性别不能为空")
    @Pattern(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, regexp = "^(男|女)$", message = "性别只能为 男 或 女")
    private String sex; // 对应 User 类中的枚举类型 Sex

//    @NotBlank(groups = ValidationGroups.Create.class,message = "出生日期不能为空")
//    @Pattern(
//            groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class},
//            regexp = "^\\d{4}-\\d{2}-\\d{2}$",
//            message = "出生日期格式不正确，应为YYYY-MM-DD"
//    )
    private String birthday;

//    @Column(name = "age")
    private Integer age;

    @Column(name = "card_id", nullable = false, unique = true)
    @NotBlank(groups = ValidationGroups.Create.class, message = "身份证号不能为空")
    @Pattern(
            groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class},
            regexp = "^[1-9]\\d{5}(18|19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[1-2]\\d|3[0-1])\\d{3}[0-9Xx]$",
            message = "身份证号格式不正确"
    )
    private String cardId;

    @NotBlank(groups = ValidationGroups.Create.class, message = "手机号不能为空")
    @Pattern(
            groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class},
            regexp = "^1[3-9]\\d{9}$",
            message = "手机号格式不正确"
    )
    private String phone;

    private String address;

    private String job;

    private String company;

    @NotNull(groups = ValidationGroups.Create.class,message = "年收入不能为空")
    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "年收入不能为负数")
    private BigDecimal yearIncome;

    @NotNull(groups = ValidationGroups.Create.class,message = "工作年限不能为空")
    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, value = 0, message = "工作年限不能为负数")
    private Integer workYears;

    @NotBlank(groups = ValidationGroups.Create.class,message = "婚否状况不能为空")
    @Pattern(groups = {ValidationGroups.Create.class, ValidationGroups.Edit.class}, regexp = "^(已婚|未婚)$", message = "婚否状况只能为 已婚 或 未婚")
    private String ismarry; // 对应 User 类中的枚举类型 MaritalStatus


    private RoleType role = RoleType.USER;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public  BigDecimal getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(BigDecimal yearIncome) {
        this.yearIncome = yearIncome;
    }

    public Integer getWorkYears() {
        return workYears;
    }

    public void setWorkYears(Integer workYears) {
        this.workYears = workYears;
    }

    public String getIsmarry() {
        return ismarry;
    }

    public void setIsmarry( String ismarry) {
        this.ismarry = ismarry;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", cardId='" + cardId + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", job='" + job + '\'' +
                ", company='" + company + '\'' +
                ", yearIncome=" + yearIncome +
                ", workYears=" + workYears +
                ", ismarry='" + ismarry + '\'' +
                '}';
    }
}
