<template>
  <div class="loan-applications">
    <h2>贷款申请管理</h2>

    <!-- 筛选器部分 -->
    <div class="filter-container">
      <select v-model="statusFilter" @change="fetchLoanApplications">
        <option value="all">查看全部</option>
        <option value="havenotassessed">未审核</option>
        <option value="notapproved">已审核未通过</option>
        <option value="approved">已审核已通过</option>
      </select>
    </div>

    <!-- 贷款申请表格 -->
    <table v-if="loanApplications.length > 0">
      <thead>
      <tr>
        <th>申请人姓名</th>
        <th>贷款金额</th>
        <th>贷款期限</th>
        <th>贷款利率</th>
        <th>状态</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="application in loanApplications" :key="application.id">
        <td>{{ application.applicantName }}</td>
        <td>{{ application.applicationmoney}}</td>
        <td>{{ application.applicationtime }}</td>
        <td>{{ application.loanRate }}</td>
        <td>{{ getStatusText(application.status) }}</td>
      </tr>
      </tbody>
    </table>
    <p v-else>没有符合条件的贷款申请。</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loanApplications: [],
      statusFilter: "all",  // 默认筛选全部
    };
  },
  methods: {
    // 获取贷款申请记录
    fetchLoanApplications() {
      let url = `http://localhost:8080/loanapplication/find/all`;  // 默认查询全部
      if (this.statusFilter === "havenotassessed") {
        url = "http://localhost:8080/loanapplication/find/havenotassessed";
      } else if (this.statusFilter === "notapproved") {
        url = "http://localhost:8080/loanapplication/find/notapproved";
      } else if (this.statusFilter === "approved") {
        url = "http://localhost:8080/loanapplication/find/approved";
      }

      // 请求API
      fetch(url, {
        method: "GET",
        headers: {
          role: "ADMINISTRATOR",
        },
      })
          .then((response) => response.json())
          .then((data) => {
            this.loanApplications = data || [];
            console.log("输出：", this.loanApplications );
            // 使用申请人的userId查询姓名
            this.fetchUserNames();
          })
          .catch((error) => {
            console.error("获取贷款申请失败:", error);
          });
    },
// 获取用户姓名并更新贷款申请数据
    fetchUserNames() {
      this.loanApplications.forEach((application) => {
        const userId = application.userId;
        console.log( userId);

        // 通过userId请求用户姓名
        fetch(`http://localhost:8080/user/findbyuserid/${userId}`, {
          method: "GET",
        })
            .then((response) => response.json())
            .then((userData) => {
              // 更新贷款申请的申请人姓名
              console.log("叫啥!",userData)
              application.applicantName = userData.data.userName;
            })
            .catch((error) => {
              console.error(`获取用户姓名失败 (UserId: ${userId}):`, error);
            });
      });
    },
    // 根据状态码返回状态文本
    getStatusText(status) {
      switch (status) {
        case 0:
          return "未审核";
        case 1:
          return "已审核未通过";
        case 2:
          return "已审核已通过";
        default:
          return "未知";
      }
    },
  },
  // 页面加载时默认加载所有贷款申请
  mounted() {
    this.fetchLoanApplications();
  },
};
</script>

<style scoped>
.loan-applications {
  width: 80%;
  margin: 0 auto;
  padding: 20px;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

h2 {
  text-align: center;
  color: #a83434;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th,
td {
  padding: 12px 15px;
  text-align: left;
  border: 1px solid #ddd;
}

th {
  background-color: #a83434;
  color: white;
  font-weight: bold;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

tr:hover {
  background-color: #f1f1f1;
}

.filter-container {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

select {
  padding: 4px;
  font-size: 16px;
  margin-right: 20px;
}
</style>
