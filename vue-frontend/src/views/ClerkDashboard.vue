<template>
  <div>
    <div class="top-nav">
      <div class="nav-left">
        <span>存贷款系统</span>
      </div>
      <div class="nav-right">
        <span @mouseover="InfoVisible" @mouseleave="handleMouseLeave">{{ clerkName || '加载中...' }}</span>
        <!-- 当鼠标悬浮时显示营业员账号信息 -->
        <div v-if="isUserInfoVisible" class="user-info-popup" @mouseover="InfoVisible" @mouseleave="handleMouseLeave">
          <p>编号: {{ clerkInfo.clerkId }}</p>
          <p>姓名: {{ clerkInfo.clerkName }}</p>
        </div>
        <button @click="logout">退出登录</button>
      </div>
    </div>

    <!-- 首页内容 -->
    <div class="clerk-content">
      <div class="button-group">
        <button @click="showLoanProjects" :class="{'active': isLoanProjectSelected}">
          贷款项目
        </button>
        <button @click="showUserInfo" :class="{'active': !isLoanProjectSelected}">
          用户信息
        </button>
      </div>

      <div class="content-display">
        <!-- 贷款项目部分 -->
        <div v-if="isLoanProjectSelected" class="loan-projects">
          <h2>贷款项目</h2>
          <div v-if="projects.length > 0">
            <table class="loan-table">
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


        <!-- 用户信息部分 -->
        <div v-else class="user-info">
          <h3>用户信息</h3>
          <div class="filter-container">
            <!--            <label>-->
            <!--              姓名：-->
            <!--              <input type="text" v-model="filterQuery" placeholder="输入姓名" />-->
            <!--            </label>-->
            <label>
              出生日期范围：
              <input type="number" v-model="birthdayFrom" placeholder="最早日期" />
              -
              <input type="number" v-model="birthdayTo" placeholder="最晚日期" />
            </label>
            <label>
              年收入范围：
              <input type="number" v-model="yearIncomeMin" placeholder="最小年收入" />
              -
              <input type="number" v-model="yearIncomeMax" placeholder="最大年收入" />
            </label>
            <label>
              工作时长范围：
              <input type="number" v-model="workYearsMin" placeholder="最短工作时长" />
              -
              <input type="number" v-model="workYearsMax" placeholder="最长工作时长" />
            </label>

            <label>
              性别：
              <select v-model="sex">
                <option value="">请选择</option>
                <option value="男">男</option>
                <option value="女">女</option>
              </select>
            </label>
            <label>
              婚姻状况：
              <select v-model="ismarry">
                <option value="">请选择</option>
                <option value="已婚">已婚</option>
                <option value="未婚">未婚</option>
              </select>
            </label>
            <button @click="filterUsers">筛选</button>
            <button @click="resetFilter">重置筛选</button>
          </div>
          <!-- 显示筛选后无符合条件的用户时的提示 -->
          <p v-if="noUsersMessage" class="no-users-message">{{ noUsersMessage }}</p>


          <div v-if="!isLoanProjectSelected" class="user-info">

            <div class="user-list">
              <table>
                <thead>
                <tr>
                  <th>姓名</th>
                  <th>身份证号</th>
                  <th>生日</th>
                  <th>性别</th>
                  <th>公司</th>
                  <th>职业</th>
                  <th>年收入(元)</th>
                  <th>邮箱</th>
                  <th>手机号</th>
                  <th>婚姻状况</th>
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="user in allUsers"
                    :key="user.id"
                    :class="{ 'filtered-user': user.isFiltered }"
                >
                  <td>{{ user.userName }}</td>
                  <td>{{ user.cardId }}</td>
                  <td>{{ user.birthday }}</td>
                  <td>{{ user.sex }}</td>
                  <td>{{ user.company }}</td>
                  <td>{{ user.job }}</td>
                  <td>{{ user.yearIncome }}</td>
                  <td>{{ user.email }}</td>
                  <td>{{ user.phone }}</td>
                  <td>{{ user.ismarry }}</td>
                </tr>
                </tbody>

              </table>
            </div>
          </div>
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
      clerkInfo: null,
      clerkName: "",
      isUserInfoVisible: false,
      projects: [],
      filteredUsers: [],//筛选之后的用户数据
      filterQuery: "",
      birthdayFrom: "",
      birthdayTo: "",
      yearIncomeMin: "",
      yearIncomeMax: "",
      workYearsMin: "",
      workYearsMax: "",
      sex: "",
      ismarry:"",
      isLoanProjectSelected: true, // 默认显示贷款项目
      allUsers: [], // 存放所有用户信息
      job:"",
      company:"",
      noUsersMessage: "", // 用于存储筛选后无符合条件的用户时的提示信息

    };
  },
  mounted() {
    this.fetchProjects();
    this.fetchAllUsers(); // 获取所有用户信息
    const clerkInfoJson = localStorage.getItem("clerkInfo");
    if (clerkInfoJson) {
      try {
        this.clerkInfo = JSON.parse(clerkInfoJson);
        this.clerkName = this.clerkInfo.clerkName;
      } catch (error) {
        alert("用户信息无效，请重新登录");
        this.$router.push({name: "Login"});
      }
    } else {
      alert("营业员未登录，请先登录");
      this.$router.push({name: "Login"});
    }
  },


  methods: {

    handleMouseLeave() {
      this.isUserInfoVisible = false;
    },

    InfoVisible(){
      this.isUserInfoVisible = true;
    },
    //获取所有项目的信息
    fetchProjects() {
      axios
          .get("http://localhost:8080/projects/viewall", {headers: {role: "USER"}})
          .then((response) => {
            this.projects = response.data.data || [];
          })
          .catch((error) => {
            console.error("获取贷款项目失败:", error);
          });
    },
    // 获取所有用户信息
    fetchAllUsers() {
      axios
          .get("http://localhost:8080/user/findall", { headers: { role: "CLERK" } })
          .then((response) => {
            console.log("用户信息：",response.data.data)
            this.allUsers = response.data.data || [];
          })
          .catch((error) => {
            console.error("获取用户信息失败:", error);
          });
    },

    filterUsers() {
      const filters = {
        sex: this.sex || null,
        birthdayFrom: this.birthdayFrom || null,
        birthdayTo: this.birthdayTo || null,
        yearIncomeMin: this.yearIncomeMin || null,
        yearIncomeMax: this.yearIncomeMax || null,
        workYearsMin: this.workYearsMin || null,
        workYearsMax: this.workYearsMax || null,
        ismarry: this.ismarry || null,
        job: this.job || null,
        company: this.company || null,
      };

      axios
          .post("http://localhost:8080/user/filter", filters, {
            headers: { role: "CLERK" },
          })
          .then((response) => {
            const filteredUsers = response.data || [];

            // 如果没有符合条件的用户
            if (filteredUsers.length === 0) {
              this.noUsersMessage = "没有符合条件的用户";
            } else {
              // 清除提示信息
              this.noUsersMessage = "";
            }

            // 标记筛选用户并将其优先排序
            filteredUsers.forEach((user) => (user.isFiltered = true));
            // 更新显示的用户列表，筛选用户优先
            this.allUsers = [
              ...filteredUsers,
              ...this.allUsers.filter((user) => !filteredUsers.some((fUser) => fUser.id === user.id)),
            ];
          })
          .catch((error) => {
            console.error("筛选用户失败:", error);
          });
    },

// 重置筛选条件并恢复所有用户
    resetFilter() {
      // 清空筛选条件
      this.filterQuery = "";
      this.birthdayFrom = "";
      this.birthdayTo = "";
      this.yearIncomeMin = "";
      this.yearIncomeMax = "";
      this.workYearsMin = "";
      this.workYearsMax = "";
      this.sex = "";
      this.ismarry = "";
      this.job = "";
      this.company = "";
      this.noUsersMessage="";
      // 重新获取所有用户
      this.fetchAllUsers();
    },
    logout() {
      localStorage.clear();
      this.$router.push({name: "Login"});
    },
    showLoanProjects() {
      this.isLoanProjectSelected = true;
    },
    showUserInfo() {
      this.isLoanProjectSelected = false;
    },
  },
};
</script>

<style scoped>
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
.user-info-popup {
  position: absolute;
  top: 40px;
  right: 20px;
  background-color: #fff;
  border: 1px solid #ccc;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  width: 250px;
  z-index: 200;
  animation: fadeIn 0.3s ease;
}





.user-info-popup p {
  margin: 5px 0;
  color: #000000;
}

.nav-right span {
  margin-right: 20px;
  cursor: pointer;
}
.filtered-user {
  background-color: #e6f7ff; /* 高亮显示 */
  font-weight: bold;
}




.nav-right button {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 6px 27px;
  cursor: pointer;
  margin-right: 20px;
}

.clerk-content {
  margin-top: 64px;
}

.button-group {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.button-group button {
  margin: 0 10px;
  padding: 10px 20px;
  border: none;
  background-color: #ddd;
  cursor: pointer;
}

.button-group button.active {
  background-color: #007bff;
  color: white;
}

.content-display {
  padding: 20px;
}

.loan-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.loan-table th, .loan-table td {
  padding: 10px;
  border: 1px solid #ddd;
}

.loan-table th {
  background-color: #f4f4f4;
}

.user-list table {
  width: 100%;
  border-collapse: collapse;
}

.user-list th, .user-list td {
  padding: 10px;
  border: 1px solid #ddd;
}
</style>
