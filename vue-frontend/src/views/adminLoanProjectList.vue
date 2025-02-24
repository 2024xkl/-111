<template>
  <div>
    <!-- 项目列表表格 -->
    <table class="table">
      <thead>
      <tr>
        <th>项目名称</th>
        <th>最低贷款金额</th>
        <th>最高贷款金额</th>
        <th>最短贷款期限</th>
        <th>最长贷款期限</th>
        <th>最低利率</th>
        <th>最高利率</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="project in projects" :key="project.projectId">
        <td>{{ project.projectName }}</td>
        <td>{{ project.loanmoneyMin }}</td>
        <td>{{ project.loanmoneyMax }}</td>
        <td>{{ project.loanmonthMin }}</td>
        <td>{{ project.loanmonthMax }}</td>
        <td>{{ project.rateMin }}</td>
        <td>{{ project.rateMax }}</td>
        <td>
          <button class="delete-btn" @click="confirmDelete(project.projectId)">删除</button>
        </td>
      </tr>
      </tbody>
    </table>

    <!-- 添加按钮 -->
    <button class="add-btn" @click="openAddDialog">添加贷款项目</button>

    <!-- 添加项目弹窗 -->
    <div v-if="isAddDialogVisible" class="modal-overlay" @click.self="isAddDialogVisible = false">
      <div class="modal">
        <h3>添加贷款项目</h3>
        <form @submit.prevent="addProject">
          <label for="projectName">项目名称</label>
          <input type="text" id="projectName" v-model="newProject.projectName" placeholder="请输入项目名称" required>

          <label for="loanmoneyMin">最低贷款金额</label>
          <input type="number" id="loanmoneyMin" v-model="newProject.loanmoneyMin" placeholder="请输入最低贷款金额" required>

          <label for="loanmoneyMax">最高贷款金额</label>
          <input type="number" id="loanmoneyMax" v-model="newProject.loanmoneyMax" placeholder="请输入最高贷款金额" required>

          <label for="loanmonthMin">最短贷款期限</label>
          <input type="number" id="loanmonthMin" v-model="newProject.loanmonthMin" placeholder="请输入最短贷款期限" required>

          <label for="loanmonthMax">最长贷款期限</label>
          <input type="number" id="loanmonthMax" v-model="newProject.loanmonthMax" placeholder="请输入最长贷款期限" required>

          <label for="rateMin">最低利率</label>
          <input type="number" id="rateMin" v-model="newProject.rateMin" placeholder="请输入最低利率" required>

          <label for="rateMax">最高利率</label>
          <input type="number" id="rateMax" v-model="newProject.rateMax" placeholder="请输入最高利率" required>

          <div class="modal-footer">
            <button type="button" class="cancel-btn" @click="isAddDialogVisible = false">取消</button>
            <button type="submit" class="confirm-btn">确定</button>
          </div>
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
      projects: [],
      isAddDialogVisible: false,
      newProject: {
        projectName: '',
        loanmoneyMin: '',
        loanmoneyMax: '',
        loanmonthMin: '',
        loanmonthMax: '',
        rateMin: '',
        rateMax: ''
      }
    };
  },
  mounted() {
    this.fetchProjects();
  },
  methods: {
    fetchProjects() {
      axios.get('http://localhost:8080/projects/viewall', {
        headers: { role: 'ADMINISTRATOR' }
      }).then(response => {
        if (response.data.code === 200 && Array.isArray(response.data.data)) {
          this.projects = response.data.data;
        } else {
          alert('返回的数据格式不正确');
        }
      }).catch(error => {
        alert('获取项目列表失败');
        console.error(error);
      });
    },
    confirmDelete(projectId) {
      if (confirm('确定要删除该贷款项目吗？')) {
        this.deleteProject(projectId);
      }
    },
    deleteProject(projectId) {
      axios.delete(`http://localhost:8080/projects/delete/${projectId}`, {
        headers: { role: 'ADMINISTRATOR' }
      }).then(() => {
        alert('删除成功');
        this.fetchProjects();//更新这个项目列表
      }).catch(error => {
        alert('删除失败');
        console.error(error);
      });
    },
    openAddDialog() {
      this.isAddDialogVisible = true;
      this.resetNewProject();
    },
    addProject() {
      const project = this.newProject;
      if (!project.projectName || !project.loanmoneyMin || !project.loanmoneyMax ||
          !project.loanmonthMin || !project.loanmonthMax || !project.rateMin || !project.rateMax) {
        alert('请完整填写所有信息');
        return;
      }
      axios.post('http://localhost:8080/projects/add', project, {
        headers: { role: 'ADMINISTRATOR' }
      }).then(() => {
        alert('添加成功');
        this.isAddDialogVisible = false;
        this.fetchProjects();
      }).catch(error => {
        alert('添加失败');
        console.error(error);
      });
    },
    resetNewProject() {
      this.newProject = {
        projectName: '',
        loanmoneyMin: '',
        loanmoneyMax: '',
        loanmonthMin: '',
        loanmonthMax: '',
        rateMin: '',
        rateMax: ''
      };
    }
  }
};
</script>

<style scoped>
/* 通用样式 */
body {
  font-family: Arial, sans-serif;
  background-color: #f8f9fa;
  color: #333;
  margin: 0;
  padding: 0;
}

.container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #ffffff;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}

h2 {
  margin-bottom: 20px;
  color: #4caf50;
}

/* 表格样式 */
.table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 20px;
}

.table th, .table td {
  border: 1px solid #ddd;
  padding: 12px 15px;
  text-align: left;
}

.table th {
  background-color: #a83434;
  color: white;
  font-weight: bold;
}

.table tr:nth-child(even) {
  background-color: #f2f2f2;
}

.table tr:hover {
  background-color: #f1f1f1;
}

.table .delete-btn {
  background-color: #f44336;
  color: white;
  border: none;
  padding: 8px 12px;
  cursor: pointer;
  border-radius: 4px;
}

.table .delete-btn:hover {
  background-color: #d32f2f;
}

/* 添加按钮样式 */
.add-btn {
  background-color: #a83434;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 4px;
  display: inline-block;
}

.add-btn:hover {
  background-color: #a83434;
}

/* 弹窗样式 */
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 400px;
  background-color: white;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  z-index: 1000;
  padding: 20px;
}

.modal h3 {
  margin-top: 0;
  color: #4caf50;
}

.modal label {
  display: block;
  margin-bottom: 8px;
  font-weight: bold;
}

.modal input {
  width: calc(100% - 20px);
  padding: 8px;
  margin-bottom: 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 14px;
}

.modal .modal-footer {
  display: flex;
  justify-content: flex-end;
}

.modal .modal-footer button {
  padding: 10px 20px;
  margin-left: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.modal .modal-footer .confirm-btn {
  background-color: #4caf50;
  color: white;
}

.modal .modal-footer .confirm-btn:hover {
  background-color: #388e3c;
}

.modal .modal-footer .cancel-btn {
  background-color: #f44336;
  color: white;
}

.modal .modal-footer .cancel-btn:hover {
  background-color: #d32f2f;
}

/* 背景遮罩层 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  z-index: 999;
}
</style>
