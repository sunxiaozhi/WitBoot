import { defineStore } from 'pinia'
import router from '@/router'

interface TabItem {
  title: string
  path: string
  name?: string
  fullPath?: string
}

export const useMenuTabsStore = defineStore('menuTabs', {
  state: () => ({
    tabs: [] as TabItem[],
    activeTab: ''
  }),
  actions: {
    addTab(route: any) {
      if (!this.tabs.find(tab => tab.path === route.path)) {
        this.tabs.push({
          title: route.meta?.title as string || route.name?.toString() || route.path,
          path: route.path,
          name: route.name?.toString(),
          fullPath: route.fullPath
        })
      }
      this.activeTab = route.path
    },
    removeTab(path: string) {
      const index = this.tabs.findIndex(tab => tab.path === path)
      if (index !== -1) {
        this.tabs.splice(index, 1)
        if (this.activeTab === path) {
          this.activeTab = this.tabs[index - 1]?.path || this.tabs[0]?.path || ''
        }
      }
    },
    changeActiveTab(path: string) {
      this.activeTab = path
      console.log(path)
      router.push(path)
    }
  }
})
