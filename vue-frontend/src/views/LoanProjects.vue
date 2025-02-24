<template>
  <div class="loan-projects">
    <!-- 顶部区域：用户贷款申请记录 -->
    <div class="section">
      <h2 class="section-title">我的贷款申请记录</h2>

      <!-- 筛选器：选择显示已审核或未审核 -->
      <div class="loan-filter">
        <label for="statusFilter">筛选贷款申请状态：</label>
        <select v-model="selectedStatus" @change="filterLoans" class="select-filter">
          <option value="">全部</option>
          <option value="0">未审核</option>
          <option value="1">审核通过</option>
          <option value="2">审核未通过</option>
        </select>
      </div>

      <div v-if="filteredLoans.length > 0" class="loan-table">
        <table>
          <thead>
          <tr>
            <th>贷款申请编号</th>
            <th>申请金额</th>
            <th>申请时间（月）</th>
            <th>账户ID</th>
            <th>项目编号</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="loan in filteredLoans" :key="loan.loanApplicationId">
            <td>{{ loan.loanApplicationId }}</td>
            <td>{{ loan.applicationmoney }}</td>
            <td>{{ loan.applicationtime }}</td>
            <td>{{ loan.accountId }}</td>
            <td>{{ loan.projectId }}</td>
            <td>{{ getStatusText(loan.status) }}</td>
            <td>
              <!-- 只有审核通过的贷款，才显示查看还款计划按钮 -->
              <button v-if="loan.status === 1" @click="showRepaymentSchedule(loan.loanApplicationId)">
                查看还款计划
              </button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        <p>暂无贷款申请记录。</p>
      </div>
    </div>

    <!-- 模态框：显示还款计划 -->
    <div v-if="isModalVisible" class="repayment-modal">
      <div class="repayment-modal-content">

        <table class="repayment-table">
          <thead>
          <tr>
            <th>还款编号</th>
            <th>本金</th>
            <th>利息</th>
            <th>逾期利息</th>
            <th>总还款</th>
            <th>还款日期</th>
            <th>状态</th>
          </tr>
          </thead>
          <tbody>
          <tr v-if="repaymentData.length === 0">
            <td colspan="7" class="no-data">暂无还款计划</td>
          </tr>
          <tr v-else v-for="item in repaymentData" :key="item.repayId">
            <td>{{ item.repayId }}</td>
            <td>{{ item.principal }}</td>
            <td>{{ item.interest }}</td>
            <td>{{ item.overdueInterest }}</td>
            <td>{{ item.totalRepay }}</td>
            <td>{{ item.repayDate }}</td>
            <td>
              <span v-if="item.status === 0" class="status-unpaid">未还款</span>
              <span v-else class="status-paid">已还款</span>
            </td>
          </tr>
          </tbody>
        </table>
        <button class="close-button" @click="isModalVisible = false">关闭</button>
      </div>
    </div>



    <!-- 中间区域：所有贷款项目 -->
    <div class="section">
      <h2 class="section-title">所有贷款项目</h2>
      <div v-if="projects.length > 0" class="loan-table">
        <table>
          <thead>
          <tr>
            <th>项目编号</th>
            <th>项目名称</th>
            <th>贷款金额范围</th>
            <th>贷款期限范围</th>
            <th>利率范围</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="project in projects" :key="project.projectId">
            <td>{{ project.projectId }}</td>
            <td>{{ project.projectName }}</td>
            <td>{{ project.loanmoneyMin }} - {{ project.loanmoneyMax }}</td>
            <td>{{ project.loanmonthMin }} - {{ project.loanmonthMax }}</td>
            <td>{{ project.rateMin }}% - {{ project.rateMax }}%</td>
          </tr>
          </tbody>
        </table>
      </div>
      <div v-else>
        <p>暂无贷款项目。</p>
      </div>
    </div>

    <!-- 底部区域：申请贷款 -->
    <div class="section">
      <h3 class="section-title">申请贷款</h3>
      <form @submit.prevent="applyLoan" class="loan-form">
        <div class="form-group">
          <label for="accountId">账户编号:</label>
          <input type="number" id="accountId" v-model="loanForm.accountId" required />
        </div>
        <div class="form-group">
          <label for="projectId">贷款项目编号:</label>
          <input type="number" id="projectId" v-model="loanForm.projectId" required />
        </div>
        <div class="form-group">
          <label for="applicationMoney">申请金额:</label>
          <input type="number" id="applicationMoney" v-model="loanForm.applicationMoney" required />
        </div>
        <div class="form-group">
          <label for="applicationTime">申请时间（月）:</label>
          <input type="number" id="applicationTime" v-model="loanForm.applicationTime" required />
        </div>
        <button type="submit" class="submit-btn">提交申请</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userLoans: [], // 用户贷款申请记录
      filteredLoans: [], // 筛选后的贷款记录
      projects: [], // 所有贷款项目
      loanForm: {
        accountId: "",
        projectId: "",
        applicationMoney: "",
        applicationTime: "",
      },
      selectedStatus: '', // 筛选的状态
      repaymentData: [], // 存储还款计划数据
      isModalVisible: false, // 控制模态框显示
    };
  },
  methods: {
    fetchUserLoans() {
      const userInfo = JSON.parse(localStorage.getItem("userInfo"));
      const userId = userInfo.data.userId;

      axios
          .get(`http://localhost:8080/loanapplication/findbyuserid/${userId}`, {
            headers: {
              role: "USER",
              session: userId,
            },
          })
          .then((response) => {
            this.userLoans = response.data || [];
            this.filterLoans();
          })
          .catch((error) => {
            console.error("获取用户贷款记录失败:", error);
          });
    },
    fetchProjects() {
      axios
          .get("http://localhost:8080/projects/viewall", { headers: { role: "USER" } })
          .then((response) => {
            this.projects = response.data.data || [];
          })
          .catch((error) => {
            console.error("获取贷款项目失败:", error);
          });
    },
    getStatusText(status) {
      switch (status) {
        case 0:
          return "未审核";
        case 1:
          return "审核通过";
        case 2:
          return "审核未通过";
        default:
          return "未知状态";
      }
    },

    //筛选贷款项目
    filterLoans() {
      if (this.selectedStatus === '') {
        this.filteredLoans = this.userLoans;
      } else {
        // 强制将 selectedStatus 转为数字类型
        this.filteredLoans = this.userLoans.filter(loan => loan.status === Number(this.selectedStatus));
      }
    },

    // 获取还款计划
    showRepaymentSchedule(loanId) {
      axios
          .get(`http://localhost:8080/repay/byloanid/${loanId}`)
          .then((response) => {
            this.repaymentData = response.data || [];
            console.log("还款计划：", this.repaymentData);
            this.isModalVisible = true; // 显示模态框
          })
          .catch((error) => {
            console.error("获取还款计划失败:", error);
          });
    },


    // 关闭模态框
    closeModal() {
      this.isModalVisible = false;

    },

    //申请贷款
    applyLoan() {
      const userInfo = JSON.parse(localStorage.getItem("userInfo"));
      const userId = userInfo.data.userId;

      const applicationData = {
        ...this.loanForm,
        userId: Number(userId),
      };

      axios
          .post("http://localhost:8080/loanapplication/apply", applicationData, {
            headers: {
              role: "USER",
              session: userId,
            },
          })
          .then((response) => {
            console.log(response.data);
            alert(response.data);
            this.fetchUserLoans();
          })
          .catch((error) => {
            console.error("贷款申请失败:", error);
            alert("贷款申请失败，请重试！");
          });
    },
  },
  mounted() {
    this.fetchUserLoans();
    this.fetchProjects();
  },
};
</script>

<style scoped>
.loan-projects {
  padding: 20px;
}

.section {
  margin-bottom: 30px;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.section-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 10px;
}

.loan-filter {
  margin-bottom: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.select-filter {
  padding: 8px 12px;
  font-size: 16px;
  border-radius: 4px;
  border: 1px solid #ccc;
}

.loan-table table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.loan-table th,
.loan-table td {
  padding: 10px;
  text-align: left;
  border: 1px solid #ddd;
}

.loan-form .form-group {
  margin-bottom: 15px;
}

.loan-form input {
  padding: 8px;
  width: 100%;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}
.repayment-modal {
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px;
  background-color: rgba(0, 0, 0, 0.5); /* 半透明背景 */
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 1000;
}

.repayment-table {
  width: 80%;
  border-collapse: collapse;
  background-color: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.repayment-table th,
.repayment-table td {
  text-align: center;
  padding: 12px;
  border: 1px solid #ddd;
}

.repayment-table th {
  background-color: #a83434;
  color: white;
  font-weight: bold;
}

.repayment-table tr:nth-child(even) {
  background-color: #f9f9f9;
}

.repayment-table tr:hover {
  background-color: #f1f1f1;
}

.no-data {
  text-align: center;
  color: #999;
  font-style: italic;
}

.status-unpaid {
  color: red;
  font-weight: bold;
}

.status-paid {
  color: green;
  font-weight: bold;
}

.modal {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  max-width: 80%;
  max-height: 80%;
  overflow-y: auto;
  width: 600px;
}

/* 关闭按钮 */
.close-button {
  position: absolute;
  top: 50px;
  right: 50px;
  background-color:transparent;
  border: none;
  font-size: 20px;
  font-weight: bold;
  cursor: pointer;
  background-color: #a83434;
}

.close-button:hover {
  color: #ff4d4f;
}

/* 表格样式 */
.repayment-table {
  width: 100%;
  border-collapse: collapse;
}


.submit-btn {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.submit-btn:hover {
  background-color: #0056b3;
}
</style>
