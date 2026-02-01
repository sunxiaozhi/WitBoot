import { defineStore } from 'pinia'
import type { RouteLocationNormalizedLoaded, RouteRecordNormalized } from 'vue-router'

interface TabItem {
  title: string
  path: string
  name?: string
  fullPath?: string
}

export const useMenuTabsStore = defineStore('menuTabs', {
  state: () => ({
    tabs: [] as TabItem[],
    activeTab: '/home' as string,
  }),

  actions: {
    initHomeTab() {
      if (this.tabs.length === 0) {
        this.tabs.push({
          title: '首页',
          path: '/home',
          fullPath: '/home',
        })
        this.activeTab = '/home'
      }
    },

    addTab(route: RouteLocationNormalizedLoaded | RouteRecordNormalized) {
      const path = route.path
      const title = (route.meta?.title as string) || (route.name as string) || path

      if (!this.tabs.some(tab => tab.path === path)) {
        this.tabs.push({
          title,
          path,
          name: route.name as string | undefined,
          fullPath: (route as any).fullPath,
        })
      }
      this.activeTab = path
    },

    removeTab(path: string) {
      const index = this.tabs.findIndex(tab => tab.path === path)
      if (index === -1) return

      this.tabs.splice(index, 1)

      if (this.activeTab === path) {
        const newTab = this.tabs[index] || this.tabs[index - 1] || this.tabs[0]
        this.activeTab = newTab?.path || '/home'
      }
    },

    changeActiveTab(path: string) {
      this.activeTab = path
    },

    closeAllTabs() {
      this.tabs = [{ title: '首页', path: '/home', fullPath: '/home' }]
      this.activeTab = '/home'
    },
  },

  persist: {
    key: 'witboot-menu-tabs',
    storage: localStorage, // 也可以用 sessionStorage
  },
})
