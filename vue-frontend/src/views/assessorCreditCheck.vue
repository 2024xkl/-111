<template>
  <div class="container">
    <h2>查询客户征信记录</h2>
    <div class="search-container">
      <input
          v-model="userId"
          type="number"
          placeholder="请输入用户ID"
          @keypress.enter="fetchCreditReport"
      />
      <button @click="fetchCreditReport">查询</button>
    </div>

    <!-- 显示负债总额 -->
    <div v-if="totalDebt !== null" class="credit-info">
      <h3>用户负债信息</h3>
      <p><strong>用户ID:</strong> {{ userId }}</p>
      <p><strong>负债总额:</strong> {{ totalDebt }} 元</p>
    </div>

    <!-- 显示还款计划 -->
    <div v-if="repaymentSchedule.length > 0" class="repayment-info">
      <h3>还款计划</h3>
      <table>
        <thead>
        <tr>
          <th>贷款ID</th>
          <th>还款金额</th>
          <th>还款日期</th>
          <th>是否已还款</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="loan in repaymentSchedule" :key="loan.loanId">
          <td>{{ loan.loanId }}</td>
          <td>{{ loan.totalRepay }} 元</td>
          <td>{{ loan.repayDate }}</td>
          <td>
            <span v-if="loan.status === 0" class="status-unpaid">未还款</span>
            <span v-else class="status-paid">已还款</span>
          </td>
        </tr>
        </tbody>
      </table>
    </div>

    <!-- 错误提示 -->
    <div v-if="errorMessage" class="error">
      <p>{{ errorMessage }}</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      userId: '',
      totalDebt: null,
      repaymentSchedule: [],
      errorMessage: ''
    };
  },
  methods: {
    fetchCreditReport() {
      if (!this.userId) {
        this.errorMessage = '请输入有效的用户ID！';
        return;
      }

      this.errorMessage = ''; // 清除错误信息
      this.repaymentSchedule = []; // 清空还款计划

      // 请求总负债
      axios.get(`http://localhost:8080/creditreport/gettotaldebt/${this.userId}`)
          .then(response => {
            this.totalDebt = response.data || 0;
          })
          .catch(error => {
            this.errorMessage = '获取总负债失败！';
            console.error(error);
          });

      // 请求贷款ID
      axios
          .get(`http://localhost:8080/loan/findbyuserr/${this.userId}`,)
          .then(response => {
            const loanApplications = response.data;
            console.log("贷款Id:",response.data );
            console.log("长度:",response.data.length );
            if (loanApplications && loanApplications.length > 0) {
              // 获取每个贷款的还款计划
              loanApplications.forEach(loan => {
                this.fetchRepaymentSchedule(loan.loanId);
              });
            } else {
              this.errorMessage = '没有找到贷款记录！';
            }
          })
          .catch(error => {
            this.errorMessage = '获取贷款记录失败！';
            console.error(error);
          });
    },

    // 根据贷款ID获取还款计划
    fetchRepaymentSchedule(loanId) {
      axios.get(`http://localhost:8080/repay/byloanid/${loanId}`)
          .then(response => {
            console.log("还款计划：", response);
            const repaymentData = response.data || [];
            repaymentData.forEach(repayment => {
              this.repaymentSchedule.push({
                loanId: loanId,
                totalRepay:repayment.totalRepay,
                repayDate:repayment.repayDate,
                status:repayment.status,


              });
            });
          })
          .catch(error => {
            console.error('获取还款计划失败:', error);
          });
    }
  }
};
</script>

<style scoped>
.container {
  font-family: Arial, sans-serif;
  margin: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 24px;
  color: #333;
}

.search-container {
  margin: 20px 0;
  display: flex;
  align-items: center;
  justify-content: center;  /* 水平居中 */
}

input {
  padding: 8px;
  margin-right: 10px;
  width: 200px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 8px 15px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.credit-info, .repayment-info {
  margin-top: 20px;
}

h3 {
  font-size: 20px;
  color: #333;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

table th, table td {
  padding: 10px;
  text-align: left;
  border: 1px solid #ddd;
}

table th {
  background-color: #f2f2f2;
}

.error {
  color: red;
  font-weight: bold;
  margin-top: 20px;
}
</style>
