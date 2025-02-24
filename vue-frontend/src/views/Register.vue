<template>
  <div class="register">
    <!-- 大标题 -->
    <div class="welcome-title-container">
      <h1 class="welcome-title">欢迎来到存贷款系统!</h1>
    </div>
    <div class="register-container">

      <h2>注册</h2>



      <!-- 用户注册表单 -->
      <div v-if="selectedRole === 'user'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="username">用户名：</label>
            <input type="text" v-model="username" @input="clearErrors('username')" required />
            <span v-if="errors.username" class="error">{{ errors.username }}</span>
          </div>
          <div>
            <label for="password">密码：</label>
            <input type="password" v-model="password" @input="clearErrors('password')" required />
            <span v-if="errors.password" class="error">{{ errors.password }}</span>
          </div>
          <div>
            <label for="email">邮箱：</label>
            <input type="email" v-model="email" @input="clearErrors('email')" required />
            <span v-if="errors.email" class="error">{{ errors.email }}</span>
          </div>
          <div>
            <label for="sex">性别：</label>
            <select v-model="sex" required>
              <option value="男">男</option>
              <option value="女">女</option>
            </select>
          </div>
          <div>
            <label for="cardId">身份证号：</label>
            <input type="text" v-model="cardId" @input="clearErrors('cardId')" required />
            <span v-if="errors.cardId" class="error">{{ errors.cardId }}</span>
          </div>
          <div>
            <label for="phone">手机号：</label>
            <input type="text" v-model="phone" @input="clearErrors('phone')" required />
            <span v-if="errors.phone" class="error">{{ errors.phone }}</span>
          </div>
          <div>
            <label for="yearIncome">年收入：</label>
            <input type="number" v-model="yearIncome" @input="clearErrors('yearIncome')" required />
            <span v-if="errors.yearIncome" class="error">{{ errors.yearIncome }}</span>
          </div>
          <div>
            <label for="workYears">工作时长：</label>
            <input type="number" v-model="workYears" @input="clearErrors('workYears')" required />
            <span v-if="errors.workYears" class="error">{{ errors.workYears }}</span>
          </div>
          <div>
            <label for="ismarry">婚姻状况：</label>
            <select v-model="ismarry" required>
              <option value="已婚">已婚</option>
              <option value="未婚">未婚</option>
            </select>
          </div>
          <button type="submit" :disabled="isFormInvalid">注册</button>
        </form>
        <p >
          已有账户？<router-link to="/">快进行登录吧！</router-link>
        </p>
      </div>
      <!-- 这下面部分并不需要了，但是因为本身不会显示先放着-->
      <!-- 营业员注册表单 -->
      <div v-if="selectedRole === 'clerk'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="clerkName">营业员姓名：</label>
            <input type="text" v-model="clerkName" @input="clearErrors('clerkName')" required />
            <span v-if="errors.clerkName" class="error">{{ errors.clerkName }}</span>
          </div>
          <div>
            <label for="clerkPassword">营业员密码：</label>
            <input type="password" v-model="clerkPassword" @input="clearErrors('clerkPassword')" required />
            <span v-if="errors.clerkPassword" class="error">{{ errors.clerkPassword }}</span>
          </div>
          <button type="submit" :disabled="isFormInvalid">注册</button>
        </form>
      </div>

      <!-- 审核员注册表单 -->
      <div v-if="selectedRole === 'assessor'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="assessorName">审核员姓名：</label>
            <input type="text" v-model="assessorName" @input="clearErrors('assessorName')" required />
            <span v-if="errors.assessorName" class="error">{{ errors.assessorName }}</span>
          </div>
          <div>
            <label for="assessorPassword">审核员密码：</label>
            <input type="password" v-model="assessorPassword" @input="clearErrors('assessorPassword')" required />
            <span v-if="errors.assessorPassword" class="error">{{ errors.assessorPassword }}</span>
          </div>
          <button type="submit" :disabled="isFormInvalid">注册</button>
        </form>
      </div>

      <!-- 管理员注册表单 -->
      <div v-if="selectedRole === 'administrator'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="adminName">管理员姓名：</label>
            <input type="text" v-model="adminName" @input="clearErrors('adminName')" required />
            <span v-if="errors.adminName" class="error">{{ errors.adminName }}</span>
          </div>
          <div>
            <label for="adminPassword">管理员密码：</label>
            <input type="password" v-model="adminPassword" @input="clearErrors('adminPassword')" required />
            <span v-if="errors.adminPassword" class="error">{{ errors.adminPassword }}</span>
          </div>
          <button type="submit" :disabled="isFormInvalid">注册</button>
        </form>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      selectedRole: 'user', // 默认选中身份为用户
      username: '',
      password: '',
      email: '',
      sex: '男',
      cardId: '',
      phone: '',
      yearIncome: '',
      workYears: '',
      ismarry: '未婚',
      // 营业员注册数据
      clerkName: '',
      clerkPassword: '',
      // 审核员注册数据
      assessorName: '',
      assessorPassword: '',
      // 管理员注册数据
      adminName: '',
      adminPassword: '',
      errors: {
        username: '',
        password: '',
        email: '',
        cardId: '',
        phone: '',
        yearIncome: '',
        workYears: '',
        clerkName: '',
        clerkPassword: '',
        assessorName: '',
        assessorPassword: '',
        adminName: '',
        adminPassword: ''
      }
    };
  },
  computed: {
    isFormInvalid() {
      return Object.values(this.errors).some(error => error !== '');
    }
  },
  methods: {
    // 角色选择变化时，清空相关字段
    handleRoleChange() {
      this.clearErrors();
      this.username = '';
      this.password = '';
      this.email = '';
      this.sex = '男';
      this.cardId = '';
      this.phone = '';
      this.yearIncome = '';
      this.workYears = '';
      this.ismarry = '未婚';
      this.clerkName = '';
      this.clerkPassword = '';
      this.assessorName = '';
      this.assessorPassword = '';
      this.adminName = '';
      this.adminPassword = '';
    },
    // 清空错误信息
    clearErrors() {
      this.errors = {
        username: '',
        password: '',
        email: '',
        cardId: '',
        phone: '',
        yearIncome: '',
        workYears: '',
        clerkName: '',
        clerkPassword: '',
        assessorName: '',
        assessorPassword: '',
        adminName: '',
        adminPassword: ''
      };
    },
    // 提交注册表单
    async handleSubmit() {
      this.clearErrors();
      const formData = this.getFormData();
      if (!formData) return; // 如果没有获取到有效的表单数据，则退出

      const isValid = this.validateForm();
      if (!isValid) return;




      let apiUrl = 'http://localhost:8080/user/add';  // 默认的用户注册地址
      if (this.selectedRole === 'clerk') {
        apiUrl = 'http://localhost:8080/clerk/add'; // 营业员的注册地址
      } else if (this.selectedRole === 'assessor') {
        apiUrl = 'http://localhost:8080/assessor/add'; // 审核员的注册地址
      } else if (this.selectedRole === 'administrator') {
        apiUrl = 'http://localhost:8080/administrator/add'; // 管理员的注册地址
      }

      // 打印 API 地址和表单数据，确保它们是正确的
      console.log('API URL:', apiUrl);
      console.log('Form Data:', formData);

      try {
        const response = await axios.post(apiUrl, formData);
        alert('注册成功！');
        this.$router.push({ name: 'Login' });

      } catch (error) {
        console.error('Error details:', error);  // 打印详细错误信息
        alert('注册失败，请重试');
      }

    },

    // 根据角色返回不同的表单数据
    getFormData() {
      if (this.selectedRole === 'user') {
        return {
          userName: this.username,
          password: this.password,
          email: this.email,
          sex: this.sex,
          cardId: this.cardId,
          phone: this.phone,
          yearIncome: this.yearIncome,
          workYears: this.workYears,
          ismarry: this.ismarry
        };
      } else if (this.selectedRole === 'clerk') {
        return {
          clerkName: this.clerkName,
          clerkPassword: this.clerkPassword
        };
      } else if (this.selectedRole === 'assessor') {
        return {
          assessorName: this.assessorName,
          assessorPassword: this.assessorPassword
        };
      } else if (this.selectedRole === 'administrator') {
        return {
          adminName: this.adminName,
          adminPassword: this.adminPassword
        };
      }
      return null;
    },
    // 验证表单数据
    validateForm() {
      let isValid = true;

      if (this.selectedRole === 'user') {
        if (!this.username) {
          this.errors.username = '用户名不能为空';
          isValid = false;
        }
        if (!this.password || this.password.length < 6 || this.password.length > 12) {
          this.errors.password = '密码长度必须在6到12个字符之间';
          isValid = false;
        }
        const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        if (!emailRegex.test(this.email)) {
          this.errors.email = '邮箱格式不正确';
          isValid = false;
        }
        const cardIdRegex = /^\d{17}(\d|X)$/;
        if (!cardIdRegex.test(this.cardId)) {
          this.errors.cardId = '身份证号格式不正确';
          isValid = false;
        }
        const phoneRegex = /^1[3-9]\d{9}$/;
        if (!phoneRegex.test(this.phone)) {
          this.errors.phone = '手机号格式不正确';
          isValid = false;
        }
        if (this.yearIncome < 0) {
          this.errors.yearIncome = '年收入不能为负数';
          isValid = false;
        }
        if (this.workYears < 0) {
          this.errors.workYears = '工作时长不能为负数';
          isValid = false;
        }
      } else if (this.selectedRole === 'clerk') {
        if (!this.clerkName) {
          this.errors.clerkName = '营业员姓名不能为空';
          isValid = false;
        }
        if (!this.clerkPassword || this.clerkPassword.length < 6) {
          this.errors.clerkPassword = '密码长度不能少于6个字符';
          isValid = false;
        }
      } else if (this.selectedRole === 'assessor') {
        if (!this.assessorName) {
          this.errors.assessorName = '审核员姓名不能为空';
          isValid = false;
        }
        if (!this.assessorPassword || this.assessorPassword.length < 6) {
          this.errors.assessorPassword = '密码长度不能少于6个字符';
          isValid = false;
        }
      } else if (this.selectedRole === 'administrator') {
        if (!this.adminName) {
          this.errors.adminName = '管理员姓名不能为空';
          isValid = false;
        }
        if (!this.adminPassword || this.adminPassword.length < 6) {
          this.errors.adminPassword = '密码长度不能少于6个字符';
          isValid = false;
        }
      }

      return isValid;
    }
  }
};
</script>

<style scoped>
/* 样式 */
.register {

  width: 100%;
  height:96vh;
  background-image:url('../assets/login.png');
  background-size: cover;
  background-position: center;
  flex-direction: column; /* 改为纵向排列 */
  display: flex;
  justify-content: center;
  align-items: center;
  background-attachment: fixed;
}
.register-container {
  background: rgba(255, 255, 255, 0.8); /* 半透明背景 */
  padding: 40px;
  border-radius: 10px;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}


form {
  display: flex;
  flex-direction: column;
  text-align: center;

}
.welcome-title-container {
  margin-bottom: 20px; /* 为标题与表单之间添加间距 */
}

.welcome-title {
  font-size: 32px;
  color: #333;
  font-weight: bold;
  text-align: center;
}
label{
  text-align: left;
  margin-bottom: 5px;
  font-weight: bold;
}

input, select, button {
  margin-bottom: 5px;
  padding: 5px;
}

.error {
  color: red;
  font-size: 12px;
}
</style>
