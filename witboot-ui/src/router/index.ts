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
          component: () => import('@/views/home/index.vue')
        },
        {
          path: '/user',
          name: 'user',
          component: () => import('@/views/user/index.vue')
        },
        {
          path: '/department',
          name: 'department',
          component: () => import('@/views/department/index.vue')
        },
        {
          path: '/dictionary',
          name: 'dictionary',
          component: () => import('@/views/dictionary/index.vue')
        },
        {
          path: '/loginLog',
          name: 'loginLog',
          component: () => import('@/views/login-log/index.vue')
        },
        {
          path: '/operationLog',
          name: 'operationLog',
          component: () => import('@/views/operation-log/index.vue')
        },
        {
          path: '/about',
          name: 'about',
          component: () => import('@/views/about/index.vue')
        }
      ]
    }
  ]
})

export default router
