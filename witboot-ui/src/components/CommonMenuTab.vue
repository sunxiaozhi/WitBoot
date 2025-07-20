<template>
  <el-tabs
    v-model="menuTabsStore.activeTab"
    type="card"
    closable
    class="menu-tabs"
    @tab-change="menuTabsStore.changeActiveTab"
    @tab-remove="menuTabsStore.removeTab"
  >
    <el-tab-pane
      v-for="item in menuTabsStore.tabs"
      :key="item.path"
      :label="item.title"
      :name="item.path"
    >
      <keep-alive>
        <router-view v-slot="{ Component }" v-if="item.title">
          <component :is="Component" :key="activeTab" />
        </router-view>
      </keep-alive>
    </el-tab-pane>
  </el-tabs>
</template>

<script lang="ts" setup>
import { useMenuTabsStore } from '@/stores/menuTabsStore'
import { watch } from 'vue'
import { useRoute } from 'vue-router'

const menuTabsStore = useMenuTabsStore()

const route = useRoute()

watch(
  () => route.fullPath,
  () => {
    if (route.meta?.title) {
      menuTabsStore.addTab(route)
      menuTabsStore.activeTab = route.path
    }
  },
  { immediate: true } // 刷新时也会触发
)
</script>

<style scoped>
/* 自定义标签页样式 */
.menu-tabs :deep(.el-tabs__item) {
  border-radius: 10px !important;
  margin-right: 5px;
  padding: 6px 16px;
  transition: all 0.2s;
  border: 2px solid #EEE;
}

.menu-tabs :deep(.el-tabs__header) {
  border: none;
}

.menu-tabs :deep(.el-tabs__header .el-tabs__nav) {
  border: none;
}

.menu-tabs :deep(.el-tabs__header .el-tabs__item:first-child) {
  border: 2px solid #EEE;
}

/* 设置激活标签颜色、边框等可选样式 */
.menu-tabs :deep(.el-tabs__item.is-active) {
  background-color: var(--el-color-primary-light-9);
  border-color: var(--el-color-primary);
  color: var(--el-color-primary);
}

/* 去除最后一个 tab 的 margin-right */
.menu-tabs :deep(.el-tabs__nav-wrap) {
  padding-right: 10px;
}
</style>
