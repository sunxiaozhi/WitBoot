import { defineStore } from 'pinia'

export const useMenuCollapseStore = defineStore('menuCollapse', {
  state: () => ({
    isCollapse: false
  }),
  actions: {
    toggleCollapse() {
      this.isCollapse = !this.isCollapse
    }
  }
})
