// 样式导入
import 'normalize.css'
import 'element-plus/dist/index.css'
import '@/assets/witboot.scss'

// Vue 核心
import { createApp } from 'vue'
import App from '@/App.vue'

// 路由
import router from '@/router'

// 状态管理
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

// UI 框架
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/es/locale/lang/zh-cn'

// 创建 pinia 实例
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

// 创建 app 实例
const app = createApp(App)

// 注册插件
app.use(ElementPlus, { locale: zhCn })
app.use(pinia)
app.use(router)

// 错误处理
app.config.errorHandler = (err, vm, info) => {
  console.error('Vue Error:', err, info)
}

// 挂载
app.mount('#app')
