<template>
  <div>
    <!-- 顶部导航栏 -->
    <div class="top-nav">
      <div class="nav-left">
        <span>存贷款系统</span>
      </div>
      <div class="nav-right">
        <span @mouseenter="showUserInfo" @mouseleave="hideUserInfo">{{ userName }}</span>
        <button @click="logout">退出登录</button>
      </div>
    </div>

    <!-- 用户信息弹窗 -->
    <div v-if="showInfo" class="user-info-popup">
      <p>姓名: {{ userInfo.userName }}</p>
      <p>邮箱: {{ userInfo.email }}</p>
      <p>性别: {{ userInfo.sex }}</p>
      <p>地址: {{ userInfo.address }}</p>
      <p>婚姻状况: {{ userInfo.ismarry }}</p>
      <button @click="editUserInfo">编辑信息</button>
    </div>

    <!-- 编辑用户信息表单 -->
    <div v-if="showEditForm" class="edit-form-popup">
      <h3>编辑个人信息</h3>
      <form @submit.prevent="submitEditForm">
        <label for="userName">姓名:</label>
        <input type="text" v-model="userInfo.userName" id="userName" />
        <label for="userEmail">邮箱:</label>
        <input type="email" v-model="userInfo.email" id="userEmail" />
        <label for="userSex">性别:</label>
        <select v-model="userInfo.sex" id="userSex">
          <option value="男">男</option>
          <option value="女">女</option>
        </select>
        <label for="userAddress">地址:</label>
        <input type="text" v-model="userInfo.address" id="userAddress" />
        <label for="userIsmarry">婚姻状况:</label>
        <select v-model="userInfo.ismarry" id="userIsmarry">
          <option value="未婚">未婚</option>
          <option value="已婚">已婚</option>
        </select>
        <button type="submit">提交</button>
        <button @click="closeEditForm">关闭</button>
      </form>
    </div>

    <!-- 页面内容区域 -->
    <div class="content">
      <!-- 左侧导航栏 -->
      <div class="left-nav">
        <ul>
          <li
              @click="changeTab('loanProjects')"
              :class="{ active: currentTab === 'loanProjects' }">
            贷款项目
          </li>
          <li
              @click="changeTab('accountInfo')"
              :class="{ active: currentTab === 'accountInfo' }">
            账户信息
          </li>
          <li
              @click="changeTab('bankStatements')"
              :class="{ active: currentTab === 'bankStatements' }">
            银行流水
          </li>
          <li
              @click="changeTab('applyLoan')"
              :class="{ active: currentTab === 'applyLoan' }">
            申请贷款
          </li>
        </ul>
      </div>

      <!-- 右侧内容区域 -->
      <div class="right-content">
        <!-- 动态加载组件 -->
        <component :is="currentComponent"></component>
      </div>
    </div>
  </div>
</template>

<script>
// 导入子组件
import LoanProjects from './LoanProjects.vue';
import AccountInfo from './AccountInfo.vue';
import BankStatements from './BankStatements.vue';
import ApplyLoan from './ApplyLoan.vue';
import axios from 'axios';

export default {
  data() {
    return {
      userName: '', // 假设当前用户名
      userInfo: {
        userName: '',
        email: '',
        sex: '',
        address: '',
        ismarry: '',
        userId: 0
      },
      currentTab: 'loanProjects', // 当前显示的Tab
      components: {
        loanProjects: LoanProjects,
        accountInfo: AccountInfo,
        bankStatements: BankStatements,
        applyLoan: ApplyLoan,
      },
      showInfo: false, // 是否显示用户信息
      showEditForm: false, // 是否显示编辑用户信息表单
    };
  },
  computed: {
    // 根据 currentTab 动态返回需要展示的组件
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
    // 切换Tab
    changeTab(tab) {
      this.currentTab = tab;
    },
    // 退出登录
    logout() {
      // 清除 session 或做其他退出逻辑
      console.log('退出登录');
    },
    // 获取用户信息
    getUserInfo() {
      axios.get(`http://localhost:8080/user/${this.userInfo.userId}`, {
        headers: {
          role: 'USER',
          session: this.userInfo.userId,
        }
      })
          .then(response => {
            this.userInfo = response.data;
          })
          .catch(error => {
            console.error('获取用户信息失败:', error);
          });
    },
    // 编辑用户信息
    editUserInfo() {
      this.showEditForm = true;
    },
    // 提交编辑表单
    submitEditForm() {
      axios.put('http://localhost:8080/user/edit', {
        userId: this.userInfo.userId,
        userName: this.userInfo.userName,
        email: this.userInfo.email,
        sex: this.userInfo.sex,
        address: this.userInfo.address,
        ismarry: this.userInfo.ismarry
      }, {
        headers: {
          role: 'USER',
          session: this.userInfo.userId,
        }
      })
          .then(response => {
            console.log('用户信息更新成功:', response.data);
            this.showEditForm = false;
            this.getUserInfo(); // 更新用户信息显示
          })
          .catch(error => {
            console.error('更新用户信息失败:', error);
          });
    },
    // 关闭编辑表单
    closeEditForm() {
      this.showEditForm = false;
    }
  },
  created() {
    // 假设用户ID已经存储在 session 或 cookie 中，这里进行获取
    this.userInfo.userId = 1; // 模拟用户ID为1
    this.getUserInfo(); // 初始化加载用户信息
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

.nav-right button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 6px 27px;
  cursor: pointer;
}

/* 用户信息弹窗 */
.user-info-popup {
  position: absolute;
  top: 30px;
  right: 10px;
  background-color: #fff;
  padding: 10px;
  border: 1px solid #ccc;
  z-index: 200;
}

/* 编辑用户信息表单 */
.edit-form-popup {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  border: 1px solid #ccc;
  z-index: 300;
  width: 400px;
}
/* 避免用户信息弹窗消失 */
.user-info-popup:hover {
  display: block;
}
.content {
  display: flex;
  margin-top: 60px;
}

/* 左侧导航栏 */
.left-nav {
  position: fixed;
  top: 47px; /* 与顶部导航栏保持一定间距 */
  left: 0;
  width: 200px;
  background-color: #28a745;
  padding: 0; /* 去掉 padding，紧贴左边 */
  height: calc(100vh - 47px); /* 确保高度充满页面 */
  box-sizing: border-box; /* 包括内边距和边框 */
}

.left-nav ul {
  list-style: none;
  padding: 0;
}

.left-nav li {
  padding: 15px;
  cursor: pointer;
  color: white;
}

.left-nav li.active {
  background-color: #006400; /* 激活项的背景颜色 */
}

/* 右侧内容区域 */
.right-content {
  margin-left: 200px; /* 左侧导航栏宽度 */
  padding: 20px;
  width: 100%;
}
</style>
