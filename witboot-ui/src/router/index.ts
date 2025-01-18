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
          path: '/user',
          name: 'user',
          component: () => import('@/views/user/index.vue')
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
