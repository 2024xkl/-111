<template>
  <div class="bank-statements">
    <h2>银行流水</h2>
    <div v-if="loading" class="loading">加载中...</div>
    <div v-if="error" class="error-message">{{ error }}</div>

    <table v-if="bankStatements.length > 0">
      <thead>
      <tr>
        <th>流水号</th>
        <th>账户ID</th>
        <th>交易金额</th>
        <th>交易日期</th>
        <th>交易类型</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="statement in bankStatements" :key="statement.id">
        <td>{{statement.bankStatementID}}</td>
        <td>{{ bankNames[statement.accountId] || '加载中...' }}</td>
        <td>{{ statement.amount }}</td>
        <td>{{ formatDate(statement.date) }}</td>
        <td>{{ statement.motionType}}</td>
      </tr>
      </tbody>
    </table>

    <div v-if="bankStatements.length === 0" class="no-data">
      暂无银行流水记录。
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      bankStatements: [], // 存储银行流水数据
      loading: true, // 控制加载状态
      error: '', // 错误信息
      bankNames: {}, // 用于存储银行名称的缓存
    };
  },
  async mounted() {
    // 获取当前用户ID
    const userInfoJson = localStorage.getItem('userInfo');
    const userInfo = JSON.parse(userInfoJson);
    if (userInfo && userInfo.data && userInfo.data.userId) {
      await this.fetchBankStatements(userInfo.data.userId);
      // 获取所有账户的银行名称
      for (const statement of this.bankStatements) {
        if (!this.bankNames[statement.accountId]) {
          this.bankNames[statement.accountId] = await this.getName(statement.accountId);
        }
      }
    } else {
      this.error = '用户信息无效，请重新登录';
      this.loading = false;
    }
  },
  methods: {
    // 获取银行流水信息
    async fetchBankStatements(userId) {
      const sessionId = userId;
      try {
        const response = await axios.get(`http://localhost:8080/bankstatements/findbyuserid/${userId}`, {
          headers: {
            role: 'USER',
            session: sessionId,
          }
        });
        this.bankStatements = response.data;
        console.log("流水信息：", response.data);
      } catch (error) {
        this.error = '获取银行流水失败，请稍后重试';
        console.error('获取银行流水失败:', error);
      } finally {
        this.loading = false;
      }
    },
    // 格式化日期
    formatDate(dateString) {
      const standardizedDate = dateString.split('.')[0];
      const date = new Date(standardizedDate);
      if (isNaN(date)) {
        return "Invalid Date";
      }
      return `${date.getFullYear()}-${(date.getMonth() + 1).toString().padStart(2, '0')}-${date.getDate().toString().padStart(2, '0')}`;
    },
    // 通过账户ID查找账户名字
    async getName(Id) {
      try {
        const userInfoJson = localStorage.getItem('userInfo');
        const userInfo = JSON.parse(userInfoJson);
        const sessionId = userInfo?.data?.userId;

        const response = await axios.get(`http://localhost:8080/account/getbankname/${Id}`, {
          headers: {
            role: 'USER',
            session: sessionId
          }
        });
        console.log('获取到的银行名称:', response.data);
        return response.data;
      } catch (error) {
        console.error('获取银行名称失败:', error);
        return '未知银行';
      }
    }
  }
};
</script>


<style scoped>
.bank-statements {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h2 {
  margin-bottom: 20px;
  color: #333;
}

.loading {
  text-align: center;
  color: #007bff;
}

.error-message {
  color: red;
  text-align: center;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

table th, table td {
  padding: 10px;
  text-align: center;
  border: 1px solid #ddd;
}

table th {
  background-color: #f2f2f2;
}

.no-data {
  text-align: center;
  color: #888;
}
</style>
