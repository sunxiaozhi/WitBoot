<template>
  <RouterView />
</template>

<script setup lang="ts" name="App">
import { RouterView } from 'vue-router'
import { useMenuTabsStore } from '@/stores/menuTabsStore'

// 初始化菜单标签页（必须在 pinia 注册后）
const menuTabsStore = useMenuTabsStore()
menuTabsStore.initHomeTab()
</script>
