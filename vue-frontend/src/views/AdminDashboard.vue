<template>
  <div>
    <div class="top-nav">
      <div class="nav-left">
        <span>存贷款系统</span>
      </div>
      <div class="nav-right">
        <span @mouseover="showUserInfo" @mouseleave="handleMouseLeave">{{ adminName || '加载中...' }}</span>
        <!-- 当鼠标悬浮时显示营业员账号信息 -->
        <div  v-if="isUserInfoVisible" class="user-info">
          <p>编号: {{ adminId }}</p>
          <p>姓名: {{adminName }}</p>
        </div>
        <button @click="logout">退出登录</button>
      </div>
    </div>

    <!-- 首页内容 -->
    <div class="admin-content">
      <div class="button-group">
        <button @click="showLoanProjects" :class="{'active': isLoanProjectSelected}">
          贷款项目
        </button>
        <button @click="showLoanApplications" :class="{'active': isLoanApplicationSelected}">
          贷款申请
        </button>
      </div>

      <!-- 贷款项目显示区域 -->
      <div v-if="isLoanProjectSelected">
        <LoanProjectList />
      </div>

      <!-- 贷款申请显示区域 -->
      <div v-if="isLoanApplicationSelected">
        <LoanApplicationList />
      </div>
    </div>
  </div>
</template>

<script>
import LoanProjectList from './adminLoanProjectList.vue';
import LoanApplicationList from './adminLoanApplicationList.vue';

export default {
  components: {
    LoanProjectList,
    LoanApplicationList,
  },
  data() {
    return {
      isLoanProjectSelected: true,  // 默认显示贷款项目
      isLoanApplicationSelected: false,
      adminInfo: null,
      adminName: "",
      adminId:"",
      isUserInfoVisible: false,
      projects: [],
    };
  },
  mounted() {
    const adminInfoJson = localStorage.getItem("adminInfo");
    if (adminInfoJson) {
      try {

        this.adminInfo = JSON.parse(adminInfoJson);
        console.log(  "管理员信息：",this.adminInfo);
        this.adminName = this.adminInfo.adminName;
        this.adminId=this.adminInfo.adminId;
      } catch (error) {
        alert("用户信息无效，请重新登录");
        this.$router.push({ name: "Login" });
      }
    } else {
      alert("管理员未登录，请先登录");
      this.$router.push({ name: "Login" });
    }
  },
  methods: {
    showLoanProjects() {
      this.isLoanProjectSelected = true;
      this.isLoanApplicationSelected = false;
    },
    showLoanApplications() {
      this.isLoanProjectSelected = false;
      this.isLoanApplicationSelected = true;
    },
    showUserInfo() {
      console.log("鼠标移入，显示用户信息");
      this.isUserInfoVisible = true;
    },
    handleMouseLeave() {
      console.log("鼠标移出，隐藏用户信息");
      this.isUserInfoVisible = false;
    },
    logout() {
      // 清空本地存储并跳转到登录页面
      localStorage.removeItem("adminInfo");
      this.$router.push({ name: "Login" });
    },
  },
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
  color: #ffffff;
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
  position: relative;
}

.nav-right span {
  margin-right: 20px;
  cursor: pointer;
}

.nav-right .user-info {
  position: absolute;
  background-color: #ffffff;
  border: 1px solid #ccc;
  padding: 10px;
  top: 30px; /* 显示在姓名下方 */
  right: 20px;
  width: 200px;
  z-index: 9999;
}
.nav-right .user-info p{
  color:black;
}

.nav-right button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 6px 27px;
  cursor: pointer;
  margin-right: 20px;
}


.admin-content {
  margin-top: 42px;
  padding: 20px;
}

.button-group button {
  padding: 10px 20px;
  margin: 0 10px;
  background-color: #f0f0f0;
  border: none;
  cursor: pointer;
  font-size: 16px;
}
.button-group button.active {
  background-color: #a83434;
  color: white;
}

</style>
