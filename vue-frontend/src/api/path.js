// 用户相关路径
export const USER = {
    LOGIN: '/user/login',
    ADD: '/user/add',
    DELETE: (userId) => `/user/delete/${userId}`,
    EDIT: '/user/edit',
    FIND_BY_ID: (userId) => `/user/findbyuserid/${userId}`,
    FIND_BY_USERNAME: (username) => `/user/findbyusername/${username}`,
    FIND_BY_CARDID: (cardId) => `/user/findbycardid/${cardId}`,
    FILTER: '/user/filter',
};

// 账户相关路径
export const ACCOUNT = {
    ADD: '/account/add',
    VIEW: (userId) => `/account/view/${userId}`,
    DEPOSIT: '/account/deposit',
    WITHDRAW: '/account/withdraw',
    BANK_STATEMENTS: (userId) => `/bankstatements/findbyuserid/${userId}`,
};

//贷款申请相关路径
export const LOAN_APPLICATION = {
    APPLY: '/loanapplication/apply',
    FIND_BY_USER_ID: (userId) => `/loanapplication/findbyuserid/${userId}`,
    ASSESS: '/loanapplication/assess',
};

// 营业员相关路径
export const CLERK = {
    FIND_USER_BY_ID: (userId) => `/user/findbyuserid/${userId}`,
    FIND_USER_BY_USERNAME: (username) => `/user/findbyusername/${username}`,
    FILTER_USER: '/user/filter',
    FIND_ALL_LOAN_PROJECTS: '/projects/viewall',
};

// 管理员相关路径
export const ADMIN = {
    ADD_LOAN_PROJECT: '/projects/add',
    DELETE_LOAN_PROJECT: (projectId) => `/projects/delete/${projectId}`,
    EDIT_LOAN_PROJECT: '/projects/edit',
    FIND_ALL_LOAN_APPLICATIONS: '/loanapplication/find/all',
    FIND_UNASSESSED_LOAN_APPLICATIONS: '/loanapplication/find/havenotassessed',
    FIND_NOT_APPROVED_LOAN_APPLICATIONS: '/loanapplication/find/notapproved',
    FIND_APPROVED_LOAN_APPLICATIONS: '/loanapplication/find/approved',
};
