<template>
  <div class="menu-tabs-wrapper">
    <el-tabs
      v-model="activeTab"
      type="card"
      closable
      class="menu-tabs"
      @tab-remove="removeTab"
      @tab-change="changeTab"
    >
      <el-tab-pane v-for="tab in tabs" :key="tab.path" :label="tab.title" :name="tab.path">
        <!-- 关键：只有在激活时才渲染内容，避免缓存所有页面 -->
        <div v-if="activeTab === tab.path" class="tab-content">
          <keep-alive>
            <router-view v-slot="{ Component }">
              <component :is="Component" :key="$route.fullPath" />
            </router-view>
          </keep-alive>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMenuTabsStore } from '@/stores/menuTabsStore'

const menuTabsStore = useMenuTabsStore()
const route = useRoute()
const router = useRouter()

const tabs = computed(() => menuTabsStore.tabs)
const activeTab = computed({
  get: () => menuTabsStore.activeTab,
  set: val => menuTabsStore.changeActiveTab(val),
})

const removeTab = (path: string) => {
  menuTabsStore.removeTab(path)
  if (activeTab.value === path && tabs.value.length > 0) {
    router.push(tabs.value[tabs.value.length - 1].path)
  }
}

const changeTab = (path: string) => {
  router.push(path)
}

onMounted(() => {
  if (route.path !== '/login' && route.meta?.title) {
    menuTabsStore.addTab(route)
    menuTabsStore.activeTab = route.path
  }
})

// 路由变化时添加
watch(
  () => route.path,
  newPath => {
    if (newPath !== '/login' && route.meta?.title) {
      menuTabsStore.addTab(route)
    }
  },
)
</script>

<style scoped>
.menu-tabs-wrapper {
  background: #fff;
  border-bottom: 1px solid #e6e6e6;
  padding: 0 10px;
}

.tab-content {
  height: calc(100vh - 160px);
  /* 根据你的 header + tabs + footer 高度调整 */
  overflow: auto;
}
</style>
