import axios from 'axios'

// 创建一个 axios 实例
const instance = axios.create({
    baseURL: 'http://localhost:8080', // 后端 API 地址
    timeout: 5000, // 设置超时时间
    headers: {
        'Content-Type': 'application/json', // 默认使用 JSON 格式
    }
})

// 请求拦截器
instance.interceptors.request.use(
    (config) => {
        const userId = localStorage.getItem('userId'); // 从本地存储中获取用户ID（假设使用localStorage存储用户信息）
        if (userId) {
            config.headers['session'] = userId; // 将用户ID放入请求头
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
)

// 响应拦截器
instance.interceptors.response.use(
    (response) => {
        return response.data; // 直接返回响应数据
    },
    (error) => {
        return Promise.reject(error);
    }
)

// 用户相关接口
export const userApi = {
    // 用户登录
    login(data) {
        return instance.post('/user/login', data);
    },

    // 新增用户
    addUser(data) {
        return instance.post('/user/add', data);
    },

    // 删除用户
    deleteUser(userId) {
        return instance.delete(`/user/delete/${userId}`);
    },

    // 编辑个人信息
    editUser(data) {
        return instance.put('/user/edit', data);
    },

    // 查找用户信息
    findUserById(userId) {
        return instance.get(`/user/findbyuserid/${userId}`);
    },

    // 查找用户信息（按用户名）
    findUserByUsername(username) {
        return instance.get(`/user/findbyusername/${username}`);
    },

    // 查找用户信息（按身份证号）
    findUserByCardId(cardId) {
        return instance.get(`/user/findbycardid/${cardId}`);
    },

    // 用户开通账户
    addAccount(data) {
        return instance.post('/account/add', data);
    },

    // 查看账户信息
    viewAccount(userId) {
        return instance.get(`/account/view/${userId}`);
    },

    // 存款
    deposit(data) {
        return instance.post('/account/deposit', data);
    },

    // 取款
    withdraw(data) {
        return instance.post('/account/withdraw', data);
    },

    // 查看银行流水
    findBankStatements(userId) {
        return instance.get(`/bankstatements/findbyuserid/${userId}`);
    },

    // 申请贷款
    applyLoan(data) {
        return instance.post('/loanapplication/apply', data);
    },

    // 查看贷款申请信息
    findLoanApplicationByUserId(userId) {
        return instance.get(`/loanapplication/findbyuserid/${userId}`);
    }
};

// 营业员相关接口
export const clerkApi = {
    // 查找用户
    findUserById(userId) {
        return instance.get(`/user/findbyuserid/${userId}`);
    },
    // 查找用户（按用户名）
    findUserByUsername(username) {
        return instance.get(`/user/findbyusername/${username}`);
    },

    // 筛选用户
    filterUser(data) {
        return instance.post('/user/filter', data);
    },

    // 查找所有贷款项目
    findAllLoanProjects() {
        return instance.get('/projects/viewall');
    }
};

// 审核员相关接口
export const assessorApi = {
    // 查看用户征信记录
    findCreditReportByUserId(userId) {
        return instance.get(`/creditreport/findbyuserid/${userId}`);
    },

    // 审核贷款申请
    assessLoanApplication(data) {
        return instance.post('/loanapplication/assess', data);
    }
};

// 管理员相关接口
export const adminApi = {
    // 新增贷款项目
    addLoanProject(data) {
        return instance.post('/projects/add', data);
    },

    // 删除贷款项目
    deleteLoanProject(projectId) {
        return instance.delete(`/projects/delete/${projectId}`);
    },

    // 编辑贷款项目
    editLoanProject(data) {
        return instance.put('/projects/edit', data);
    },

    // 查看所有贷款申请记录
    findAllLoanApplications() {
        return instance.get('/loanapplication/find/all');
    },

    // 查看所有未审核的贷款申请
    findUnassessedLoanApplications() {
        return instance.get('/loanapplication/find/havenotassessed');
    },

    // 查看所有已审核但未通过的贷款申请
    findNotApprovedLoanApplications() {
        return instance.get('/loanapplication/find/notapproved');
    },

    // 查看所有已审核并且已通过的贷款申请
    findApprovedLoanApplications() {
        return instance.get('/loanapplication/find/approved');
    }
};
