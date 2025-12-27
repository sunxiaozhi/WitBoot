import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import { SITE_NAME } from '@/config/siteConfig'
import { useMenuTabsStore } from '@/stores/menuTabsStore'

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

    const menuTabsStore = useMenuTabsStore()
    // 只要不是登录页，就确保当前路由在 tabs 中
    if (to.path !== '/login') {
      menuTabsStore.addTab(to)
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
