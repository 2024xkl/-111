<template>
  <div>
    <!-- 顶部导航栏 -->
    <div class="top-nav">
      <div class="nav-left">
        <span>存贷款系统</span>
      </div>
      <div class="nav-right">
        <span @mouseover="showUserInfo" @mouseleave="handleMouseLeave">{{ userName || '加载中...' }}</span>
        <button @click="logout">退出登录</button>
      </div>
    </div>

    <!-- 用户信息弹窗 -->
    <div v-if="showInfo && userInfo" class="user-info-popup" @mouseenter="keepUserInfoVisible" @mouseleave="hideUserInfo">
      <p>姓名: {{ userInfo.data.userName }}</p>
      <p>性别: {{ userInfo.data.sex }}</p>
      <p>身份证号: {{ userInfo.data.cardId }}</p>
      <p>邮箱: {{ userInfo.data.email }}</p>
      <button @click="editUserInfo">编辑信息</button>
    </div>

    <!-- 模态窗口 -->
    <div v-if="showEditForm" class="modal-overlay">
      <div class="edit-form-popup">
        <h3>编辑个人信息</h3>
        <!-- 支持滚动的表单容器 -->
        <div class="scrollable-container">
          <form @submit.prevent="submitEditForm">

            <label for="userName">姓名:</label>
            <input type="text" v-model="userInfo.data.userName" id="userName" />


            <label for="email">邮箱:</label>
            <input type="email" v-model="userInfo.data.email" id="email" />

            <label for="sex">性别:</label>
            <select v-model="userInfo.data.sex" id="sex">
              <option value="男">男</option>
              <option value="女">女</option>
            </select>

            <label for="birthday">出生日期:</label>
            <input type="date" v-model="userInfo.data.birthday" id="birthday" />

            <label for="cardId">身份证号:</label>
            <input type="text" v-model="userInfo.data.cardId" id="cardId" />

            <label for="phone">手机号:</label>
            <input type="tel" v-model="userInfo.data.phone" id="phone" />

            <label for="address">地址:</label>
            <input type="text" v-model="userInfo.data.address" id="address" />

            <label for="job">职业:</label>
            <input type="text" v-model="userInfo.data.job" id="job" />

            <label for="company">公司:</label>
            <input type="text" v-model="userInfo.data.company" id="company" />

            <label for="workYears">工作年限:</label>
            <input type="number" v-model="userInfo.data.workYears" id="workYears" min="0" />

            <label for="yearIncome">年收入(万元):</label>
            <input type="number" v-model="userInfo.data.yearIncome" id="yearIncome" min="0" step="0.01" />

            <label for="ismarry">婚姻状况:</label>
            <select v-model="userInfo.data.ismarry" id="ismarry">
              <option value="未婚">未婚</option>
              <option value="已婚">已婚</option>
            </select>

            <label for="age">年龄:</label>
            <input type="number" v-model="userInfo.data.age" id="age" readonly />

            <label for="role">角色:</label>
            <select v-model="userInfo.data.role" id="role">
              <option value="USER">用户</option>
              <option value="ADMIN">管理员</option>
            </select>

            <div class="buttons">
              <button type="submit">提交</button>
              <button type="button" @click="closeEditForm">关闭</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- 页面内容区域 -->
    <div class="content">
      <div class="left-nav">
        <ul>
          <li @click="changeTab('loanProjects')" :class="{ active: currentTab === 'loanProjects' }">贷款项目</li>
          <li @click="changeTab('accountInfo')" :class="{ active: currentTab === 'accountInfo' }">账户信息</li>
          <li @click="changeTab('bankStatements')" :class="{ active: currentTab === 'bankStatements' }">银行流水</li>

        </ul>
      </div>
      <div class="right-content">
        <component :is="currentComponent"></component>
      </div>
    </div>
  </div>
</template>


<script>
import LoanProjects from './LoanProjects.vue';
import AccountInfo from './AccountInfo.vue';
import BankStatements from './BankStatements.vue';

import axios from 'axios';



export default {
  data() {
    return {
      userInfo: null,  // 用户信息
      userName: '',     // 显示在顶部的用户名
      showInfo: false,  // 控制用户信息弹窗显示
      showEditForm: false, // 控制编辑用户信息表单显示
      currentTab: 'loanProjects', // 当前选中的Tab
      components: {
        loanProjects: LoanProjects,
        accountInfo: AccountInfo,
        bankStatements: BankStatements,

      }
    };
  },
  mounted() {
    const userInfoJson = localStorage.getItem('userInfo'); // 获取存储的用户信息 JSON\
    console.log('从 localStorage 获取到的数据:', userInfoJson);

    if (userInfoJson) {
     try{
       // 解析 JSON 数据
       this.userInfo = JSON.parse(userInfoJson);
       console.log('解析后的用户信息:', this.userInfo);
       this.userName = this.userInfo.data.userName; // 提取用户信息并绑定到 Vue 的 data 属性
       console.log('用户名:', this.userName); // 检查是否正确解构
     }catch (error) {
       console.error('解析用户信息失败:', error);
       alert('用户信息无效，请重新登录');
       this.$router.push({ name: 'Login' }); // 重定向到登录页面
     }
    } else {
      console.error('未找到用户信息');
      alert('用户未登录，请先登录');
      this.$router.push({ name: 'Login' }); // 如果没有找到用户信息，跳转到登录页
    }
  },

  computed: {
    currentComponent() {
      return this.components[this.currentTab];
    }
  },


  methods: {
    // 显示用户信息
    showUserInfo() {
      this.showInfo = true;
    },
    // 隐藏用户信息
    hideUserInfo() {
      this.showInfo = false;
    },
    // 鼠标移动到弹窗上时，保持弹窗可见
    keepUserInfoVisible() {
      this.showInfo = true;
    },
    // 鼠标移开姓名时隐藏弹窗
    handleMouseLeave() {
      this.showInfo = false;
    },
    // 切换Tab
    changeTab(tab) {
      this.currentTab = tab;
    },
    // 退出登录
    logout() {
      localStorage.clear();  // 清除 localStorage 中的数据
      this.$router.push('/'); // 重定向到登录页
    },
    // 编辑用户信息
    editUserInfo() {
      this.showEditForm = true;
    },
    // 提交编辑表单
    submitEditForm() {
      console.log('提交的 userId:', this.userInfo.data.userId); // 打印 userId
      axios.put('http://localhost:8080/user/edit', this.userInfo.data, {
        headers: {
          role: 'USER',
          session: this.userInfo.data.userId,
        }
      })
          .then(response => {
            console.log('用户信息更新成功:', response.data);
            // 更新前端的 userInfo 数据（如果后端返回了最新数据）
            if (response.data) {
              this.userInfo = response.data;
              this.userName = this.userInfo.data.userName;
              localStorage.setItem('userInfo', JSON.stringify(this.userInfo)); // 同步存储更新后的数据
            }
            this.showEditForm = false;

          })
          .catch(error => {
            console.error('更新用户信息失败:', error);
          });
      this.showInfo = false;
    },
    // 关闭编辑表单
    closeEditForm() {
      this.showEditForm = false;
      this.showInfo = false;
    }
  }
};
</script>

<style scoped>
/* 顶部导航栏 */
.top-nav {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #333;
  color: white;
  display: flex;
  justify-content: space-between;
  padding: 10px;
  z-index: 100;
}

.nav-left {
  font-size: 18px;
}

.nav-right {
  display: flex;
  align-items: center;
}

.nav-right span {
  margin-right: 20px;
  cursor: pointer;
}
/* 用户信息弹窗 */
.user-info-popup {
  position: absolute;
  top: 35px;
  right: 20px;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 250px;
  z-index: 200;
  animation: fadeIn 0.3s ease;
}

.nav-right button {
  background-color: #a83434;
  color: white;
  border: none;
  padding: 6px 27px;
  cursor: pointer;
}
/* 背景遮罩 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 999; /* 保证在页面最上层 */
}



.edit-form-popup {
  max-width: 600px;
  margin: 20px auto;
  padding: 20px;

  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}
.edit-form-popup h3 {
  text-align: center;
  margin-bottom: 20px;
  color: #333;
}
.edit-form-popup form {
  display: grid;
  grid-template-columns: 1fr 4fr;
  gap: 10px;
}
.edit-form-popup label {
  text-align: right;
  padding-right: 10px;
  font-weight: bold;
}
.edit-form-popup input,
.edit-form-popup select,
.edit-form-popup button {
  padding: 8px;
  font-size: 14px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.edit-form-popup input:focus,
.edit-form-popup select:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 4px rgba(0, 123, 255, 0.5);
}
.edit-form-popup button {
  width: 100%;
  background-color: #007bff;
  color: #fff;
  border: none;
  cursor: pointer;
}
.edit-form-popup button:hover {
  background-color: #0056b3;
}

.edit-form-popup .buttons {
  grid-column: span 2;
  display: flex;
  justify-content: space-between;
}

.edit-form-popup .buttons button {
  width: 48%;
}
/* 可滚动表单容器 */
.scrollable-container {
  max-height: 400px; /* 限制表单高度 */
  overflow-y: auto; /* 启用垂直滚动 */
  padding-right: 10px;
}
/* 自定义滚动条样式 */
.scrollable-container::-webkit-scrollbar {
  width: 8px;
}
.scrollable-container::-webkit-scrollbar-thumb {
  background-color: #ccc;
  border-radius: 4px;
}
.scrollable-container::-webkit-scrollbar-thumb:hover {
  background-color: #aaa;
}
.content {
  display: flex;
  margin-top: 60px;
}

/* 左侧导航栏 */
.left-nav {
  position: fixed;
  top: 47px;
  left: 0;
  width: 200px;
  background-color: #333;
  padding: 0;
  height: calc(100vh - 47px);
  box-sizing: border-box;
}

.left-nav ul {
  list-style: none;
  padding: 0;
}

.left-nav li {
  padding: 10px;
  cursor: pointer;
  color: white;
  transition: background-color 0.3s;
}

.left-nav li:hover {
  background-color: #333;
}

.left-nav li.active {
  background-color: #a83434;
  font-weight: bold;
}

/* 右侧内容 */
.right-content {
  margin-left: 200px;
  padding: 0;
  width: calc(100% - 200px);
  height: calc(100vh - 60px);
  overflow-y: auto;
  background-color: #f4f4f4;
}
</style>
