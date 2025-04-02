import { defineStore } from 'pinia'

export const useMenuStore = defineStore('menu', {
  state: () => ({
    isCollapse: false,
    activeMenu: '/'
  }),
  actions: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    },
    setActiveMenu(path: string) {
      this.activeMenu = path
    }
  }
})
