// store/modules/user.js
export default {
    namespaced: true,
    state: {
        userInfo: null, // 用户详细信息
        isLoggedIn: false, // 用户是否已登录
    },
    mutations: {
        SET_USER_INFO(state, userInfo) {
            state.userInfo = userInfo;
            state.isLoggedIn = true;
        },
        CLEAR_USER_INFO(state) {
            state.userInfo = null;
            state.isLoggedIn = false;
        },
    },
    actions: {
        login({ commit }, userInfo) {
            commit("SET_USER_INFO", userInfo);
            localStorage.setItem("userInfo", JSON.stringify(userInfo)); // 同步到 localStorage
        },
        logout({ commit }) {
            commit("CLEAR_USER_INFO");
            localStorage.removeItem("userInfo");
        },
    },
    getters: {
        getUserInfo: (state) => state.userInfo,
        isLoggedIn: (state) => state.isLoggedIn,
    },
};
