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
      <el-tab-pane
        v-for="tab in tabs"
        :key="tab.path"
        :label="tab.title"
        :name="tab.path"
        :closable="tab.path !== '/home'"
      >
      </el-tab-pane>
    </el-tabs>
    <div class="tab-content">
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" :key="`${$route.fullPath}-${refreshKey}`" />
        </keep-alive>
      </router-view>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMenuTabsStore } from '@/stores/menuTabsStore'

const HOME_PATH = '/home'

const menuTabsStore = useMenuTabsStore()
const route = useRoute()
const router = useRouter()

const tabs = computed(() => menuTabsStore.tabs)
const tabPaths = computed(() => tabs.value.map(tab => tab.path))
const refreshKey = ref(0)
const pendingRefresh = ref(false)
const activeTab = computed({
  get: () => menuTabsStore.activeTab,
  set: val => menuTabsStore.changeActiveTab(val),
})

const getNextPathAfterClose = (paths: string[], index: number) =>
  paths[index - 1] || paths[index + 1] || paths[0] || HOME_PATH

const removeTab = async (path: string) => {
  if (path === HOME_PATH) return
  const currentIndex = tabPaths.value.indexOf(path)
  if (currentIndex === -1) return
  const isActive = activeTab.value === path
  const targetPath = getNextPathAfterClose(tabPaths.value, currentIndex)

  menuTabsStore.removeTab(path)

  if (tabs.value.length === 0) {
    pendingRefresh.value = true
    await router.push(HOME_PATH)
    return
  }

  if (isActive) {
    pendingRefresh.value = true
    await router.push(targetPath)
  }
}

const changeTab = (path: string) => {
  if (path !== route.path) router.push(path)
}

const syncTabFromRoute = () => {
  if (route.path !== '/login' && route.meta?.title) {
    menuTabsStore.addTab(route)
    menuTabsStore.activeTab = route.path
  }
}

onMounted(syncTabFromRoute)

watch(
  () => route.path,
  () => {
    syncTabFromRoute()
    if (pendingRefresh.value) {
      refreshKey.value += 1
      pendingRefresh.value = false
    }
  },
)
</script>

<style lang="scss" scoped>
.menu-tabs-wrapper {
  background: #fff;
  border-bottom: 1px solid #eef2f7;
  padding: 6px 16px 0 16px;
  display: flex;
  flex-direction: column;
  height: 100%;

  .menu-tabs {
    flex: 0 0 auto;
    display: flex;
    flex-direction: column;
    min-height: 0;

    :deep(.el-tabs__header) {
      margin: 0;
      border: none;
    }

    :deep(.el-tabs__content) {
      display: none;
    }

    :deep(.el-tabs__nav) {
      border: none;
      gap: 6px;
      padding-bottom: 6px;
    }

    :deep(.el-tabs__item) {
      border: none;
      background: transparent;
      border-radius: 0;
      padding: 0 12px;
      height: 36px;
      line-height: 36px;
      font-size: 13px;
      color: #606266;
      transition: color 0.2s, background 0.2s;

      &:hover {
        color: #1f2937;
        background: rgba(31, 41, 55, 0.04);
        border-radius: 6px;
      }

      &.is-active {
        color: #1f2937;
        font-weight: 600;
        position: relative;

        &::after {
          content: '';
          position: absolute;
          left: 10px;
          right: 10px;
          bottom: 2px;
          height: 2px;
          background: #1f2937;
          border-radius: 2px;
        }
      }
    }

    :deep(.el-tabs__item .el-icon-close) {
      width: 14px;
      height: 14px;
      margin-left: 6px;
      font-size: 12px;
      border-radius: 50%;
      transition: color 0.2s, background 0.2s;

      &:hover {
        background: rgba(31, 41, 55, 0.08);
        color: #1f2937;
      }
    }
  }
}

.tab-content {
  flex: 1;
  height: 100%;
  min-height: 0;
  padding: 2px;
  background: #f5f7fa;
  overflow: auto;
}
</style>
