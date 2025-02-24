<template>

  <div class="login">
    <!-- 大标题 -->
    <div class="welcome-title-container">
      <h1 class="welcome-title">欢迎来到存贷款系统!</h1>
    </div>

    <div class="login-container">

      <h2>登录</h2>

      <!-- 选择身份 -->
      <div>
        <label for="role">选择身份：</label>
        <select v-model="selectedRole" @change="handleRoleChange">
          <option value="user">用户</option>
          <option value="clerk">营业员</option>
          <option value="assessor">审核员</option>
          <option value="administrator">管理员</option>
        </select>
      </div>


      <!-- 用户登录表单 -->
      <div v-if="selectedRole === 'user'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="cardId">身份证号：</label>
            <input type="text" v-model="cardId" required />
          </div>
          <div>
            <label for="password">密码：</label>
            <input type="password" v-model="password" required />
          </div>
          <button type="submit">登录</button>
        </form>
        <p v-if="!isUserLoggedIn">
          还没有账户？<router-link to="/register">注册新账户</router-link>
        </p>
      </div>

      <!-- 营业员登录表单 -->
      <div v-if="selectedRole === 'clerk'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="clerkId">营业员编号：</label>
            <input type="text" v-model="clerkId" required />
          </div>
          <div>
            <label for="clerkPassword">营业员密码：</label>
            <input type="password" v-model="clerkPassword" required />
          </div>
          <button type="submit">登录</button>
        </form>
      </div>

      <!-- 审核员登录表单 -->
      <div v-if="selectedRole === 'assessor'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="assessorId">审核员编号：</label>
            <input type="text" v-model="assessorId" required />
          </div>
          <div>
            <label for="assessorPassword">审核员密码：</label>
            <input type="password" v-model="assessorPassword" required />
          </div>
          <button type="submit">登录</button>
        </form>
      </div>

      <!-- 管理员登录表单 -->
      <div v-if="selectedRole === 'administrator'">
        <form @submit.prevent="handleSubmit">
          <div>
            <label for="adminId">管理员编号：</label>
            <input type="text" v-model="adminId" required />
          </div>
          <div>
            <label for="adminPassword">管理员密码：</label>
            <input type="password" v-model="adminPassword" required />
          </div>
          <button type="submit">登录</button>
        </form>
      </div>

    </div>


  </div>
</template>

<script>
import axios from 'axios';
import { useStore } from "vuex"


export default {
  data() {
    return {
      selectedRole: 'user', // 默认选中身份为用户
      cardId: '', // 用身份证号替代用户名
      password: '',
      clerkId: '',
      clerkPassword: '',
      assessorId: '',
      assessorPassword: '',
      adminId: '',
      adminPassword: '',
      isUserLoggedIn: false, // 用来判断是否是用户角色登录
    };
  },
  methods: {
    // 角色选择变化时，清空相关字段
    handleRoleChange() {
      this.cardId = '';
      this.password = '';
      this.clerkId = '';
      this.clerkPassword = '';
      this.assessorId = '';
      this.assessorPassword = '';
      this.adminId = '';
      this.adminPassword = '';
    },
    // 提交登录表单
    async handleSubmit() {
      let loginData = {};
      let apiUrl = '';

      // 根据选择的身份填写表单数据并设置对应的API URL
      if (this.selectedRole === 'user') {
        loginData = { cardId: this.cardId, password: this.password };
        apiUrl = 'http://localhost:8080/user/login';
      } else if (this.selectedRole === 'clerk') {
        loginData = { clerkId: this.clerkId, password: this.clerkPassword };
        apiUrl = 'http://localhost:8080/clerk/login';
      } else if (this.selectedRole === 'assessor') {
        loginData = { assessorId: this.assessorId, password: this.assessorPassword };
        apiUrl = 'http://localhost:8080/assessor/login';
      } else if (this.selectedRole === 'administrator') {
        loginData = { adminId: this.adminId, password: this.adminPassword };
        apiUrl = 'http://localhost:8080/administrator/login';
      }

      // 在这里打印请求数据
      console.log('Login Data:', loginData); // 打印请求数据，检查数据是否正确
      //提交登录请求
      try {
        // const response = await axios.post(apiUrl, loginData);
        const response = await axios.post(apiUrl, loginData, {
          headers: {
            'Content-Type': 'application/json'
          },
          withCredentials: true  // 确保携带 session 信息
        });
        console.log('Response:', response); // 打印响应数据
        if (response.data.code=== 500 ){
          alert('输入有误');

        } else {
          alert('登录成功');
          localStorage.setItem('Role', this.selectedRole); // 存储角色信息


          // 根据角色跳转到对应的首页
          if (this.selectedRole === 'user') {
            // 获取用户信息
            const userInfoResponse = await axios.post(
                'http://localhost:8080/user/getUserInfo',
                { cardId: this.cardId },
                {
                  headers: {
                    'Content-Type': 'application/json',
                  },
                  withCredentials: true, // 确保携带 session 信息
                }
            );
            console.log('User Info Response:', userInfoResponse.data); // 打印用户信息
            localStorage.setItem('userInfo', JSON.stringify(userInfoResponse.data)); // 存储用户信息
            localStorage.setItem('token', userInfoResponse.data.token);

            this.$router.push({ name: 'UserHome' });  // 用户首页
          }
          else if (this.selectedRole === 'clerk') {

            localStorage.setItem('clerkInfo', JSON.stringify(response.data)); // 存储用户信息

            //将营业员的信息存储到localstorage中

            this.$router.push({ name: 'ClerkHome' });  // 营业员首页
          } else if (this.selectedRole === 'assessor') {

            localStorage.setItem('assessorInfo', JSON.stringify(response)); // 存储用户信息
            //同理
            this.$router.push({ name: 'AssessorHome' });  // 审核员首页
          } else if (this.selectedRole === 'administrator') {
            localStorage.setItem('adminInfo', JSON.stringify(response.data)); // 存储用户信息
            //同理
            this.$router.push({ name: 'AdminHome' });  // 管理员首页
          }



        }
      }catch (error) {
        if (error.response && error.response.status === 401) {
          alert('用户名或密码错误');
        } else {
          console.error('Error details:', error);
          alert('登录失败，请重试');
        }
      }
      //   // 临时模拟返回成功的结果
      //   const response = { data: { success: true } };
      //   if (response.data.success) {
      //     alert('登录成功');
      //     localStorage.setItem('userRole', this.selectedRole);
      //   } else {
      //     alert('用户名或密码错误');
      //   }
      // }
    }
  }
};
</script>

<style scoped>
.login {

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
.login-container {
  background: rgba(255, 255, 255, 0.8); /* 半透明背景 */
  padding: 40px;
  border-radius: 10px;
  width: 400px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}


form {
  display: flex;
  flex-direction: column;
  padding-left: 2px;

}
label {
  text-align: left;
  margin-bottom: 5px;
  font-weight: bold;
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

input {
  margin-bottom: 10px;
}

button {
  margin-top: 10px;
}
</style>
