import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login/index')
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('@/views/about/index')
    },
    {
      path: '/elemetContainerDemo',
      name: 'elemetContainerDemo',
      component: () => import('@/views/ElementContainerDemo')
    },
    {
      path: '/elemetDemo',
      name: 'elemetDemo',
      component: () => import('@/views/ElementDemo')
    }
  ]
})

export default router
