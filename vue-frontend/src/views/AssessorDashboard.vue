<template>
  <div>
    <div class="top-nav">
      <div class="nav-left">
        <span>存贷款系统</span>
      </div>
      <div class="nav-right">
        <span @mouseover="showUserInfo" @mouseleave="handleMouseLeave">{{ assessorName || '加载中...' }}</span>
        <!-- 当鼠标悬浮时显示审核员账号信息 -->
        <div v-if="isUserInfoVisible" class="user-info">
          <p>编号: {{assessorId }}</p>
          <p>姓名: {{assessorName }}</p>
        </div>
        <button @click="logout">退出登录</button>
      </div>
    </div>

    <!-- 首页内容 -->
    <div class="admin-content">
      <div class="button-group">
        <button @click="showLoanApproval" :class="{'active': isLoanApprovalSelected}">
          审核贷款
        </button>
        <button @click="showCreditCheck" :class="{'active': isCreditCheckSelected}">
          查询客户征信
        </button>
      </div>

      <!-- 审核贷款显示区域 -->
      <div v-if="isLoanApprovalSelected">
        <LoanApproval />
      </div>

      <!-- 查询客户征信显示区域 -->
      <div v-if="isCreditCheckSelected">
        <CreditCheck />
      </div>
    </div>
  </div>
</template>

<script>
import LoanApproval from './assessorLoanApproval.vue';
import CreditCheck from './assessorCreditCheck.vue';

export default {
  components: {
    LoanApproval,
    CreditCheck,
  },
  data() {
    return {
      isLoanApprovalSelected: true,  // 默认显示审核贷款
      isCreditCheckSelected: false,
      assessorInfo: null,
      assessorName: "",
      assessorId: "",
      isUserInfoVisible: false,
    };
  },
  mounted() {
    const assessorInfoJson = localStorage.getItem("assessorInfo");
    console.log("审核员的信息：", assessorInfoJson);
    if (assessorInfoJson) {
      try {
        this.assessorInfo = JSON.parse(assessorInfoJson);
        console.log("营业员信息：",this.assessorInfo);
        this.assessorName = this.assessorInfo.data.assessorName;
        console.log("审核员的信息：", this.assessorInfo.data.assessorName);
        this.assessorId = this.assessorInfo.data.assessorId;
      } catch (error) {
        alert("用户信息无效，请重新登录");
        this.$router.push({ name: "Login" });
      }
    } else {
      alert("审核员未登录，请先登录");
      this.$router.push({ name: "Login" });
    }
  },
  methods: {
    showLoanApproval() {
      this.isLoanApprovalSelected = true;
      this.isCreditCheckSelected = false;
    },
    showCreditCheck() {
      this.isLoanApprovalSelected = false;
      this.isCreditCheckSelected = true;
    },
    showUserInfo() {
      this.isUserInfoVisible = true;
    },
    handleMouseLeave() {
      this.isUserInfoVisible = false;
    },
    logout() {
      localStorage.removeItem("assessorInfo");
      alert("已退出登录");
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

.nav-right .user-info {
  position: absolute;
  top: 35px;
  right: 20px;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 250px;
  z-index: 200;
  animation: fadeIn 0.3s ease;}
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
  background-color: #4caf50;
  color: white;
}
</style>
