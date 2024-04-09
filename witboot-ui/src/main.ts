import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { devtools } from '@vue/devtools'

import App from './App.vue'
import router from './router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

if (process.env.NODE_ENV === 'development')
  devtools.connect('http://localhost', 8098)

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.use(ElementPlus)

app.mount('#app')
