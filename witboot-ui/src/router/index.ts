import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index.vue')
    },
    {
      path: '/',
      component: () => import('@/layouts/Main.vue'),
      children: [
        {
          path: '/home',
          name: 'home',
          component: () => import('@/views/home/index.vue'),
          meta: { title: '首页' }
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('@/views/user/index.vue'),
          meta: { title: '人员管理' }
        },
        {
          path: '/department',
          name: 'department',
          component: () => import('@/views/department/index.vue'),
          meta: { title: '部门管理' }
        },
        {
          path: '/dictionary',
          name: 'dictionary',
          component: () => import('@/views/dictionary/index.vue'),
          meta: { title: '字典管理' }
        },
        {
          path: '/loginLog',
          name: 'loginLog',
          component: () => import('@/views/login-log/index.vue'),
          meta: { title: '登录日志' }
        },
        {
          path: '/operationLog',
          name: 'operationLog',
          component: () => import('@/views/operation-log/index.vue'),
          meta: { title: '操作日志' }
        },
        {
          path: '/about',
          name: 'about',
          component: () => import('@/views/about/index.vue'),
          meta: { title: '关于系统' }
        }
      ]
    }
  ]
})

export default router
