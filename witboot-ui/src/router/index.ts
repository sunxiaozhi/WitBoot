import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index.vue')
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('@/views/about/index.vue')
    },
    {
      path: '/elemetContainerDemo',
      name: 'elemetContainerDemo',
      component: () => import('@/views/ElementContainerDemo.vue')
    },
    {
      path: '/elemetDemo',
      name: 'elemetDemo',
      component: () => import('@/views/ElementDemo.vue')
    }
  ]
})

export default router
