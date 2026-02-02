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

<style lang="scss" scoped>
.menu-tabs-wrapper {
  background: #fff;
  border-bottom: 1px solid #f0f0f0;
  padding: 8px 16px 0 16px;

  .menu-tabs {
    :deep(.el-tabs__header) {
      margin: 0;
      border: none;
    }

    :deep(.el-tabs__nav) {
      border: none;
      gap: 4px;
    }

    :deep(.el-tabs__item) {
      border: none;
      background: #f5f7fa;
      border-radius: 6px 6px 0 0;
      padding: 0 16px;
      height: 36px;
      line-height: 36px;
      font-size: 13px;
      color: #606266;
      transition: all 0.3s;

      &:hover {
        color: #667eea;
        background: #e8ecf5;
      }

      &.is-active {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: #fff;
        font-weight: 500;
        box-shadow: 0 2px 4px rgba(102, 126, 234, 0.2);
      }
    }

    :deep(.el-tabs__item .el-icon-close) {
      width: 14px;
      height: 14px;
      margin-left: 6px;
      font-size: 12px;
      border-radius: 50%;
      transition: all 0.3s;

      &:hover {
        background: rgba(255, 255, 255, 0.3);
        color: #fff;
      }
    }
  }
}

.tab-content {
  height: calc(100vh - 64px - 48px - 36px);
  padding: 20px;
  background: #f5f7fa;
  overflow: auto;
}
</style>
