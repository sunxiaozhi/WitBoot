// src/stores/menuCollapseStore.ts
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useMenuCollapseStore = defineStore('menuCollapse', () => {
  const isCollapse = ref(false)

  const toggleCollapse = () => {
    isCollapse.value = !isCollapse.value
  }

  return { isCollapse, toggleCollapse }
})
