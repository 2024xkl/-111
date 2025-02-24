package com.loan.system_loan.pojo;

import jakarta.persistence.*;

@Table(name = "tb_clerk")
@Entity
public class Clerk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clerk_id")
    private Integer clerkId;

    @Column(name = "clerk_name")
    private String ClerkName;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)

    private RoleType role;

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public Integer getClerkId() {
        return clerkId;
    }

    public void setClerkId(Integer clerkId) {
        clerkId = clerkId;
    }

    public String getClerkName() {
        return ClerkName;
    }

    public void setClerkName(String clerkName) {
        ClerkName = clerkName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Clerk{" +
                "ClerkId=" + clerkId +
                ", ClerkName='" + ClerkName + '\'' +
                '}';
    }
}
