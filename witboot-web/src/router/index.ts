import { createRouter, createWebHistory } from 'vue-router'
import { routes } from './routes'
import { SITE_NAME } from '@/config/siteConfig'
import { useMenuTabsStore } from '@/stores/menuTabsStore'
import { getAccessToken } from '@/utils/auth'

const LOGIN_NAME = 'login'
const HOME_NAME = 'home'
const LOGIN_PATH = '/login'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// 全局前置守卫
router.beforeEach((to, _from, next) => {
  const token = getAccessToken()
  const requiresAuth = to.matched.some(record => record.meta?.requiresAuth)

  if (requiresAuth && !token) {
    next({ name: LOGIN_NAME, query: { redirect: to.fullPath } })
    return
  }

  if (to.name === LOGIN_NAME && token) {
    next({ name: HOME_NAME })
    return
  }

  // 只要不是登录页，就确保当前路由在 tabs 中
  const menuTabsStore = useMenuTabsStore()
  if (to.path !== LOGIN_PATH && to.meta?.title) {
    menuTabsStore.addTab(to)
  }

  next()
})

// 设置页面标题
router.afterEach(to => {
  const title = to.meta.title as string
  if (title) {
    document.title = SITE_NAME + ' - ' + title
  } else {
    document.title = SITE_NAME
  }
})

export default router
