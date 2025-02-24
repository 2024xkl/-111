package com.loan.system_loan.pojo.dto;

public class UserInfoDto {
    private Integer userId;
    private String userName;
    private String role;

    public UserInfoDto(Integer userId, String userName, String role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    // Getter 和 Setter 方法
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserInfoDto{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
