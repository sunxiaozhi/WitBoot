import '@/assets/witboot.scss'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from '@/App.vue'
import router from '@/router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import { useMenuTabsStore } from '@/stores/menuTabsStore'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

const app = createApp(App)

app.use(ElementPlus, { locale: zhCn})
app.use(pinia)
app.use(router)

const menuTabsStore = useMenuTabsStore()
menuTabsStore.initHomeTab()

app.mount('#app')
