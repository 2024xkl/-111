// src/api/index.js
import axios from 'axios';
import { USER, ACCOUNT, LOAN_APPLICATION, CLERK, ADMIN } from './path';

// 创建一个 axios 实例
const instance = axios.create({
    baseURL: 'http://localhost:8080',
    timeout: 5000,
    headers: { 'Content-Type': 'application/json' },
});

// 请求函数封装

// 用户相关接口
export const userApi = {
    login(data) {
        return instance.post(USER.LOGIN, data);
    },
    addUser(data) {
        return instance.post(USER.ADD, data);
    },
    deleteUser(userId) {
        return instance.delete(USER.DELETE(userId));
    },
    editUser(data) {
        return instance.put(USER.EDIT, data);
    },
    findUserById(userId) {
        return instance.get(USER.FIND_BY_ID(userId));
    },
    findUserByUsername(username) {
        return instance.get(USER.FIND_BY_USERNAME(username));
    },
    findUserByCardId(cardId) {
        return instance.get(USER.FIND_BY_CARDID(cardId));
    },
    filterUser(data) {
        return instance.post(USER.FILTER, data);
    }
};

// 账户相关接口
export const accountApi = {
    addAccount(data) {
        return instance.post(ACCOUNT.ADD, data);
    },
    viewAccount(userId) {
        return instance.get(ACCOUNT.VIEW(userId));
    },
    deposit(data) {
        return instance.post(ACCOUNT.DEPOSIT, data);
    },
    withdraw(data) {
        return instance.post(ACCOUNT.WITHDRAW, data);
    },
    bankStatements(userId) {
        return instance.get(ACCOUNT.BANK_STATEMENTS(userId));
    }
};

// 贷款申请相关接口
export const loanApplicationApi = {
    applyLoan(data) {
        return instance.post(LOAN_APPLICATION.APPLY, data);
    },
    findLoanByUserId(userId) {
        return instance.get(LOAN_APPLICATION.FIND_BY_USER_ID(userId));
    },
    assessLoanApplication(data) {
        return instance.post(LOAN_APPLICATION.ASSESS, data);
    }
};

// 营业员相关接口
export const clerkApi = {
    findUserById(userId) {
        return instance.get(CLERK.FIND_USER_BY_ID(userId));
    },
    findUserByUsername(username) {
        return instance.get(CLERK.FIND_USER_BY_USERNAME(username));
    },
    filterUser(data) {
        return instance.post(CLERK.FILTER_USER, data);
    },
    findAllLoanProjects() {
        return instance.get(CLERK.FIND_ALL_LOAN_PROJECTS);
    }
};

// 管理员相关接口
export const adminApi = {
    addLoanProject(data) {
        return instance.post(ADMIN.ADD_LOAN_PROJECT, data);
    },
    deleteLoanProject(projectId) {
        return instance.delete(ADMIN.DELETE_LOAN_PROJECT(projectId));
    },
    editLoanProject(data) {
        return instance.put(ADMIN.EDIT_LOAN_PROJECT, data);
    },
    findAllLoanApplications() {
        return instance.get(ADMIN.FIND_ALL_LOAN_APPLICATIONS);
    },
    findUnassessedLoanApplications() {
        return instance.get(ADMIN.FIND_UNASSESSED_LOAN_APPLICATIONS);
    },
    findNotApprovedLoanApplications() {
        return instance.get(ADMIN.FIND_NOT_APPROVED_LOAN_APPLICATIONS);
    },
    findApprovedLoanApplications() {
        return instance.get(ADMIN.FIND_APPROVED_LOAN_APPLICATIONS);
    }
};
