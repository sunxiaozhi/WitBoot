import { defineStore } from 'pinia'
import type { RouteLocationNormalizedLoaded, RouteRecordNormalized } from 'vue-router'

interface TabItem {
  title: string
  path: string
  name?: string
  fullPath?: string
}

const HOME_TAB: TabItem = { title: '首页', path: '/home', fullPath: '/home' }

export const useMenuTabsStore = defineStore('menuTabs', {
  state: () => ({
    tabs: [] as TabItem[],
    activeTab: '/home' as string,
  }),

  actions: {
    ensureHomeTab() {
      if (!this.tabs.some(tab => tab.path === HOME_TAB.path)) {
        this.tabs.unshift({ ...HOME_TAB })
      }
      if (!this.activeTab) this.activeTab = HOME_TAB.path
    },

    initHomeTab() {
      this.ensureHomeTab()
      if (this.tabs.length === 1) this.activeTab = HOME_TAB.path
    },

    addTab(route: RouteLocationNormalizedLoaded | RouteRecordNormalized) {
      const path = route.path
      const title = (route.meta?.title as string) || (route.name as string) || path

      if (!this.tabs.some(tab => tab.path === path)) {
        this.tabs.push({
          title,
          path,
          name: route.name as string | undefined,
          fullPath: (route as RouteLocationNormalizedLoaded).fullPath,
        })
      }
      this.activeTab = path
    },

    removeTab(path: string) {
      if (path === HOME_TAB.path) return
      const index = this.tabs.findIndex(tab => tab.path === path)
      if (index === -1) return

      this.tabs.splice(index, 1)

      if (this.activeTab === path) {
        const newTab = this.tabs[index] || this.tabs[index - 1] || this.tabs[0]
        this.activeTab = newTab?.path || HOME_TAB.path
      }
      this.ensureHomeTab()
    },

    changeActiveTab(path: string) {
      this.activeTab = path
    },

    closeAllTabs() {
      this.tabs = [{ ...HOME_TAB }]
      this.activeTab = HOME_TAB.path
    },
  },

  persist: {
    key: 'witboot-menu-tabs',
    storage: localStorage, // 也可以用 sessionStorage
  },
})
