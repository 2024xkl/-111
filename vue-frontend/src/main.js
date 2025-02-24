import { createApp } from 'vue'
import App from './App.vue'
import './registerServiceWorker'
import axios from "axios"
import router from './router/index.js'
import store from './store/index.js'  //引入vuex
import ElementPlus from 'element-plus';


const app = createApp(App);
// app.config.globalProperties.$axios = axios
app.use(router)
app.use(store)
app.use(ElementPlus);
// app.use(store)

app.mount('#app')
