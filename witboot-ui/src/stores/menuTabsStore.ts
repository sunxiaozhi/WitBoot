import { defineStore } from 'pinia'
import {useRouter} from 'vue-router'

export const useMenuTabsStore = defineStore('menuTabs', {
  state: () => ({
    tabs: [
      { title: '首页', path: '/home' } // 默认首页
    ],
    activeTab: '/home'
  }),
  actions: {
    addTab(tab: any) {
      if (!this.tabs.find((t) => t.path === tab.path)) {
        this.tabs.push(tab)
      }
      this.activeTab = tab.path
    },
    removeTab(path: string) {
      const index = this.tabs.findIndex((t) => t.path === path)
      if (index !== -1) {
        this.tabs.splice(index, 1)
        // 自动激活上一个
        const nextTab = this.tabs[index - 1] || this.tabs[0]
        this.activeTab = nextTab.path
      }
    },
    setActiveTab(path: string) {
      this.activeTab = path
      console.log(path)
      useRouter().push(path)
    }
  }
})
