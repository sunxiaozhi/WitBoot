import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import { SITE_NAME } from '@/config/siteConfig'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

// 全局前置守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('accessToken') // 替换为你的 token 获取方式

  if (to.matched.some(record => record.meta?.requiresAuth)) {
    if (!token) {
      next({ name: 'login' })
    } else {
      next()
    }
  } else {
    if (to.name === 'login' && token) {
      next({ name: 'home' })
      return
    }
    next()
  }
})

// 设置页面标题
router.afterEach((to) => {
  const title = to.meta.title as string
  if (title) {
    document.title = SITE_NAME + ' - ' +title
  } else {
    document.title = SITE_NAME
  }
})

export default router
