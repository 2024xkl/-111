package com.loan.system_loan.pojo;


import jakarta.persistence.*;

@Table(name = "tb_administrator")
@Entity
public class Administrator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer adminId;
    @Column(name = "admin_name")
    private String AdminName;
    @Column(name = "password")
    private String password;
    private RoleType role = RoleType.ADMINISTRATOR;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        adminId = adminId;
    }

    public String getAdminName() {
        return AdminName;
    }

    public void setAdminName(String adminName) {
        AdminName = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "AdminId=" + adminId +
                ", AdminName='" + AdminName + '\'' +
                '}';
    }
}
