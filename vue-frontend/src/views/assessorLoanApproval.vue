<template>
  <div>
    <h2>审核贷款申请</h2>
    <div v-if="loanApplications.length > 0">
      <table>
        <thead>
        <tr>
          <th>申请ID</th>
          <th>用户ID</th>
          <th>申请金额（元）</th>
          <th>申请状态</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="application in sortedLoanApplications" :key="application.loanApplicationId">
          <td>{{ application.loanApplicationId }}</td>
          <td>{{ application.userId }}</td>
          <td>{{ application.applicationmoney }}</td>
          <td>{{ getStatusText(application.status) }}</td>
          <td>
            <button @click="confirmAction(application.loanApplicationId, true)">
              通过
            </button>
            <button @click="confirmAction(application.loanApplicationId, false)">
              拒绝
            </button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <p>暂无贷款申请需要审核。</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      loanApplications: [],
    };
  },
  computed: {
    sortedLoanApplications() {
      return this.loanApplications.slice().sort((a, b) => a.status - b.status);
    },
  },
  mounted() {
    this.fetchLoanApplications();
  },
  methods: {
    fetchLoanApplications() {
      axios
          .get('http://localhost:8080/loanapplication/find/all', {
            headers: {
              role: "ASSESSOR",
            },
          })
          .then((response) => {
            console.log('获取的贷款申请列表：', response.data);
            this.loanApplications = response.data;
            console.log("信息呢2？！", this.loanApplications);
          })
          .catch((error) => {
            alert("获取贷款申请列表失败：" + error);
          });
    },

    // 确认操作
    confirmAction(loanApplicationId, approve) {
      const action = approve ? "通过" : "拒绝";
      if (confirm(`确定${action}吗？`)) {
        this.approveApplication(loanApplicationId, approve);
      }
    },

    // 进行贷款申请的审核
    approveApplication(loanApplicationId, approve) {
      axios
          .post(
              "http://localhost:8080/loanapplication/assess",
              {
                loanApplicationId,
                approve,
              },
              {
                headers: {
                  role: "ASSESSOR",
                },
              }
          )
          .then((response) => {
            alert("操作成功: " + response.data);
            // 重新加载贷款申请列表
            this.fetchLoanApplications();
          })
          .catch((error) => {
            console.error("审核失败:", error);
            alert("审核失败: " + (error.response?.data || error.message));
          });
    },

    // 获取状态
    getStatusText(status) {
      switch (status) {
        case 0:
          return "未审核";
        case 1:
          return "审核未通过";
        case 2:
          return "审核已通过";
        default:
          return "未知状态";
      }
    },
  },
};
</script>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  border: 1px solid #ddd;
  text-align: left;
  padding: 8px;
}

th {
  background-color: #f4f4f4;
}

button {
  margin-right: 10px;
  padding: 5px 10px;
  border: none;
  color: white;
  background-color: #4caf50;
  cursor: pointer;
}

button:nth-child(2) {
  background-color: #f44336;
}

button:hover {
  opacity: 0.8;
}
</style>
