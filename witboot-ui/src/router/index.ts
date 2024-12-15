import { createRouter, createWebHistory } from 'vue-router'
// import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: () => import('../views/login/LoginView.vue')
    },
    {
      path: '/about',
      name: 'about',
      component: () => import('../views/AboutView.vue')
    },
    {
      path: '/container',
      name: 'container',
      component: () => import('../views/Container.vue')
    },
    {
      path: '/elemetDemo',
      name: 'elemetDemo',
      component: () => import('../views/ElementDemo.vue')
    },
  ]
})

export default router
