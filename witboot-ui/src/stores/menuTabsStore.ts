import {defineStore} from 'pinia'

export const useMenuTabsStore = defineStore('menuTabs', {
  state: () => ({
    menuTabs: []
  }),
  actions: {
    addMenuTab(menuTab) {
      this.menuTabs.push(menuTab)
    },
    removeMenuTab(menuTab) {
      this.menuTabs.splice(this.menuTabs.indexOf(menuTab), 1)
    }
  }
})