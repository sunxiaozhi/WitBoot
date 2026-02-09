// src/stores/menuCollapseStore.ts
import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useMenuCollapseStore = defineStore(
  'menuCollapse',
  () => {
    const isCollapse = ref(false)

    const toggleCollapse = () => {
      isCollapse.value = !isCollapse.value
    }

    const setCollapse = (value: boolean) => {
      isCollapse.value = value
    }

    return { isCollapse, toggleCollapse, setCollapse }
  },
  {
    persist: {
      key: 'witboot-menu-collapse',
      storage: localStorage,
    },
  },
)
