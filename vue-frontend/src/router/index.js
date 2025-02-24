import { createRouter, createWebHistory } from 'vue-router';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import UserHome from '../views/UserDashboard.vue';
import AdminHome from '../views/AdminDashboard.vue';
import ClerkHome from '../views/ClerkDashboard.vue';
import AssessorHome from '../views/AssessorDashboard.vue';


// 路由配置
const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login,
    },
    {
        path: '/register',
        name: 'Register',
        component: Register,
    },
    {
        path: '/user/home',
        name: 'UserHome',
        component: UserHome,
        meta: { role: 'user'}  // 仅限客户访问
    },
    {
        path: '/admin/home',
        name: 'AdminHome',
        component: AdminHome,
        meta: { role: 'administrator' }  // 仅限审核员访问
    },
    {
        path: '/clerk/home',
        name: 'ClerkHome',
        component: ClerkHome,
        meta: { role: 'clerk' }  // 仅限访问营业员访问
    },
    {
        path: '/assessor/home',
        name: 'AssessorHome',
        component: AssessorHome,
        meta: { role: 'assessor' }  // 仅限管理员访问
    },
];

const router = createRouter({
    history: createWebHistory(),

    //  base:process.env.BASE_URL,
    routes
})


// 路由守卫：根据角色控制访问权限
router.beforeEach((to, from, next) => {

    const Role = localStorage.getItem('Role')  // 从 localStorage 获取当前用户的角色
    const requiredRole = to.meta.role

    if (requiredRole && Role !== requiredRole) {
        // 如果用户的角色与访问页面的角色不匹配，重定向到登录页面
        next({ name: 'Login' })
    } else {
        next()
    }
})

export default router;
