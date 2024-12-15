import './assets/witboot.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { devtools } from '@vue/devtools'

import App from './App.vue'
import router from './router'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

// if (process.env.NODE_ENV === 'development') {
//   devtools.connect('http://localhost', 8098)
// }

const app = createApp(App)

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(ElementPlus)
app.use(createPinia())
app.use(router)

app.mount('#app')
