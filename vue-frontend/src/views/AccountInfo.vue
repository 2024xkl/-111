<template>
  <div class="account-management">
    <h2>账户管理</h2>

    <!-- 显示所有账户 -->
    <div v-if="accounts.length > 0">
      <h3>我的账户</h3>
      <table>
        <thead>
        <tr>
          <th>银行名称</th>
          <th>信用卡号</th>
          <th>余额</th>
          <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="account in accounts" :key="account.id">
          <td>{{ account.bankName }}</td>
          <td>{{ account.bankCardNumber }}</td>
          <td>{{ account.balance }}</td>
          <td>
            <button @click="showDepositModal(account)">存款</button>
            <button @click="showWithdrawModal(account)">取款</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div v-else>
      <p class="no-accounts-message">暂无账户信息。</p>
    </div>

    <!-- 添加账户按钮 -->
    <button @click="showAddAccountModal" class="add-account-btn">添加账户</button>

    <!-- 添加账户弹窗 -->
    <div v-if="showAddModal" class="modal">
      <div class="modal-content">
        <h3>添加账户</h3>
        <form @submit.prevent="addAccount">
          <div class="form-group">
            <label for="bankName">银行名称：</label>
            <input
                id="bankName"
                v-model="newAccount.bankName"
                required
                :disabled="loading"

            />
          </div>

          <div class="form-group">
            <label for="password">账户密码：</label>
            <input
                id="password"
                type="password"
                v-model="newAccount.password"
                required
                :disabled="loading"
                autocomplete="new-password"
            />
          </div>

          <div class="button-group">
            <button type="submit" :disabled="loading">
              {{ loading ? '添加中...' : '提交' }}
            </button>
            <button type="button" @click="closeModals" :disabled="loading">取消</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 存款/取款弹窗 -->
    <div v-if="showTransactionModal" class="modal">
      <div class="modal-content">
        <h3>{{ transactionType === 'deposit' ? '存款' : '取款' }}</h3>
        <form @submit.prevent="handleTransaction">
          <div class="form-group">
            <label for="amount">金额：</label>
            <input id="amount" type="number" v-model="transaction.amount" required />
          </div>

          <div class="form-group">
            <label for="transactionPassword">密码：</label>
            <input id="transactionPassword" type="password" v-model="transaction.password" required  autocomplete="new-password" />
          </div>

          <div class="button-group">
            <button type="submit">提交</button>
            <button type="button" @click="closeModals">取消</button>
          </div>
        </form>

        <!-- 错误提示：余额不足 -->
        <div v-if="errorMessage" class="error-message">
          <p>{{ errorMessage }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      accounts: [],
      showAddModal: false,
      showTransactionModal: false,
      loading: false,
      newAccount: {
        bankName: "",
        password: "",
      },
      transaction: {
        bankCardNumber: "",
        amount: null,
        password: "",
      },
      transactionType: "",
      errorMessage: "",
    };
  },
  methods: {
    async addAccount() {
      try {
        this.loading = true;
        const userInfo = JSON.parse(localStorage.getItem("userInfo"));
        if (!userInfo || !userInfo.data || !userInfo.data.userId) {
          alert("用户信息不存在，请重新登录！");
          return;
        }

        const userId = userInfo.data.userId;

        if (!this.newAccount.bankName || !this.newAccount.password) {
          alert("请填写完整的账户信息！");
          return;
        }

        const payload = {
          userId,
          bankName: this.newAccount.bankName,
          password: this.newAccount.password,
        };

        console.log("添加账户的请求数据:", payload);

        // 请求并捕获异常
        const response = await axios.post(
            "http://localhost:8080/account/add",
            payload,
            {
              headers: {
                role: "USER",
                session: userId,

              },
              timeout: 5000
            }
        );

        console.log("添加账户响应:", response);  // 打印整个 response 对象


        if (response.data) {
          this.newAccount = {
            bankName: "",
            password: "",
          };
          await this.fetchAccounts();
          this.showAddModal = false;
          alert("账户添加成功！");
        }
      } catch (error) {
        console.error("添加账户失败:", error);
        alert(error.response?.data?.message || "添加账户失败，请稍后重试");
      } finally {
        console.log('Finally block executed');
        this.loading = false;
      }
    },

    async fetchAccounts() {
      try {
        const userInfo = JSON.parse(localStorage.getItem("userInfo"));
        if (!userInfo || !userInfo.data || !userInfo.data.userId) {
          console.error("用户信息不存在！");
          return;
        }

        const userId = userInfo.data.userId;

        const response = await axios.get(
            `http://localhost:8080/account/view/${userId}`,
            {
              headers: {
                role: "USER",
                session: userId,
              },
            }
        );

        console.log("获取账户列表响应:", response.data);

        if (response.data && Array.isArray(response.data.data)) {
          this.accounts = response.data.data;
        } else {
          this.accounts = [];
          console.warn("获取到的账户数据格式不正确:", response.data);
        }
      } catch (error) {
        console.error("获取账户信息失败:", error);
        this.accounts = [];
        alert("获取账户信息失败，请刷新页面重试");
      }
    },

    showAddAccountModal() {
      this.showAddModal = true;
    },

    showDepositModal(account) {
      this.transactionType = "deposit";
      this.transaction.bankCardNumber = account.bankCardNumber;
      this.showTransactionModal = true;
    },

    showWithdrawModal(account) {
      this.transactionType = "withdraw";
      this.transaction.bankCardNumber = account.bankCardNumber;
      this.showTransactionModal = true;
    },

    handleTransaction() {
      const url =
          this.transactionType === "deposit"
              ? "http://localhost:8080/account/deposit"
              : "http://localhost:8080/account/withdraw";

      const payload = {
        bankCardNumber: this.transaction.bankCardNumber,
        amount: this.transaction.amount,
        password: this.transaction.password,
      };

      if (this.transactionType === "withdraw") {
        const account = this.accounts.find(
            (acc) => acc.bankCardNumber === this.transaction.bankCardNumber
        );

        if (account && account.balance < this.transaction.amount) {
          this.errorMessage = "余额不足";
          return;
        } else {
          this.errorMessage = "";
        }
      }

      axios
          .post(url, payload, {
            headers: {
              role: "USER",
            },
          })
          .then((response) => {
            this.fetchAccounts();
            this.closeModals();
          })
          .catch((error) => {
            console.error(`${this.transactionType === "deposit" ? "存款" : "取款"}失败:`, error);
          });
    },

    closeModals() {
      this.showAddModal = false;
      this.showTransactionModal = false;
      this.newAccount = { bankName: "", password: "" };
      this.transaction = { bankCardNumber: "", amount: null, password: "" };
      this.errorMessage = "";
    },
  },
  mounted() {
    this.fetchAccounts();
  },
};
</script>

<style scoped>
.account-management {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  min-width: 300px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.button-group {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 20px;
}

button {
  padding: 8px 16px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button[type="submit"] {
  background-color: #4CAF50;
  color: white;
}

button[type="button"] {
  background-color: #f44336;
  color: white;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

th, td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #ddd;
}

th {
  background-color: #f5f5f5;
}

.error-message {
  color: red;
  margin-top: 10px;
}

.add-account-btn {
  margin-top: 20px;
  background-color: #2196F3;
  color: white;
}

.no-accounts-message {
  text-align: center;
  color: #666;
  margin-top: 20px;
}
</style>
