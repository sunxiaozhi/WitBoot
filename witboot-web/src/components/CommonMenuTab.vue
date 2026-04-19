<template>
  <div class="menu-tabs-wrapper" @click="closeContextMenu">
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
        :name="tab.path"
        :closable="tab.path !== '/home'"
      >
        <template #label>
          <span class="tab-label" @contextmenu.prevent="openContextMenu($event, tab)">
            {{ tab.title }}
          </span>
        </template>
      </el-tab-pane>
    </el-tabs>
    <div
      v-if="contextMenu.visible"
      class="tab-context-menu"
      :style="contextMenuStyle"
      @click.stop
      @contextmenu.prevent
    >
      <button
        v-for="item in contextMenuItems"
        :key="item.key"
        type="button"
        class="tab-context-menu__item"
        :disabled="item.disabled"
        @click="handleContextMenuAction(item.key)"
      >
        {{ item.label }}
      </button>
    </div>
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
import { computed, onBeforeUnmount, onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMenuTabsStore } from '@/stores/menuTabsStore'

const HOME_PATH = '/home'

interface TabItem {
  title: string
  path: string
  name?: string
  fullPath?: string
}

type ContextMenuActionKey =
  | 'refresh'
  | 'close-current'
  | 'close-other'
  | 'close-left'
  | 'close-right'
  | 'close-all'

const CONTEXT_MENU_WIDTH = 96
const CONTEXT_MENU_GAP = 8
const CONTEXT_MENU_PADDING = 8
const CONTEXT_MENU_ITEM_HEIGHT = 34

const menuTabsStore = useMenuTabsStore()
const route = useRoute()
const router = useRouter()

const tabs = computed(() => menuTabsStore.tabs)
const tabPaths = computed(() => tabs.value.map(tab => tab.path))
const refreshKey = ref(0)
const pendingRefreshPath = ref<string | null>(null)
const contextMenuTargetPath = ref<string>(HOME_PATH)
const contextMenu = reactive({
  visible: false,
  x: 0,
  y: 0,
})
const activeTab = computed({
  get: () => menuTabsStore.activeTab,
  set: val => menuTabsStore.changeActiveTab(val),
})
const contextMenuTargetTab = computed(
  () => tabs.value.find(tab => tab.path === contextMenuTargetPath.value) || tabs.value[0],
)
const contextMenuTargetIndex = computed(() =>
  tabs.value.findIndex(tab => tab.path === contextMenuTargetPath.value),
)
const closableTabs = computed(() => tabs.value.filter(tab => tab.path !== HOME_PATH))
const hasClosableLeftTabs = computed(() =>
  tabs.value
    .slice(0, Math.max(contextMenuTargetIndex.value, 0))
    .some(tab => tab.path !== HOME_PATH),
)
const hasClosableRightTabs = computed(() =>
  tabs.value.slice(contextMenuTargetIndex.value + 1).some(tab => tab.path !== HOME_PATH),
)
const contextMenuStyle = computed(() => ({
  left: `${contextMenu.x}px`,
  top: `${contextMenu.y}px`,
}))
const contextMenuItems = computed(() => {
  const targetPath = contextMenuTargetTab.value?.path || HOME_PATH
  const isHomeTab = targetPath === HOME_PATH
  const hasOtherClosableTabs = closableTabs.value.some(tab => tab.path !== targetPath)

  return [
    { key: 'refresh', label: '刷新当前', disabled: false },
    { key: 'close-current', label: '关闭当前', disabled: isHomeTab },
    { key: 'close-other', label: '关闭其他', disabled: isHomeTab || !hasOtherClosableTabs },
    { key: 'close-left', label: '关闭左侧', disabled: isHomeTab || !hasClosableLeftTabs.value },
    { key: 'close-right', label: '关闭右侧', disabled: isHomeTab || !hasClosableRightTabs.value },
    { key: 'close-all', label: '关闭全部', disabled: closableTabs.value.length === 0 },
  ] satisfies Array<{ key: ContextMenuActionKey; label: string; disabled: boolean }>
})

const getNextPathAfterClose = (paths: string[], index: number) =>
  paths[index - 1] || paths[index + 1] || paths[0] || HOME_PATH

const closeContextMenu = () => {
  contextMenu.visible = false
}

const triggerRefresh = (path: string) => {
  pendingRefreshPath.value = path
  if (route.path === path) {
    refreshKey.value += 1
    pendingRefreshPath.value = null
  }
}

const openContextMenu = (event: MouseEvent, tab: TabItem) => {
  const menuHeight =
    contextMenuItems.value.length * CONTEXT_MENU_ITEM_HEIGHT + CONTEXT_MENU_PADDING * 2
  const maxX = Math.max(CONTEXT_MENU_GAP, window.innerWidth - CONTEXT_MENU_WIDTH - CONTEXT_MENU_GAP)
  const maxY = Math.max(CONTEXT_MENU_GAP, window.innerHeight - menuHeight - CONTEXT_MENU_GAP)

  contextMenuTargetPath.value = tab.path
  contextMenu.visible = true
  contextMenu.x = Math.min(event.clientX + CONTEXT_MENU_GAP, maxX)
  contextMenu.y = Math.min(event.clientY + CONTEXT_MENU_GAP, maxY)
}

const removeTab = async (path: string) => {
  if (path === HOME_PATH) return
  const currentIndex = tabPaths.value.indexOf(path)
  if (currentIndex === -1) return
  const isActive = activeTab.value === path
  const targetPath = getNextPathAfterClose(tabPaths.value, currentIndex)

  menuTabsStore.removeTab(path)

  if (tabs.value.length === 0) {
    pendingRefreshPath.value = HOME_PATH
    await router.push(HOME_PATH)
    return
  }

  if (isActive) {
    pendingRefreshPath.value = targetPath
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

const ensureRouteAfterBatchClose = async (fallbackPath: string) => {
  const currentPathExists = tabs.value.some(tab => tab.path === route.path)
  if (!currentPathExists && route.path !== fallbackPath) {
    await router.push(fallbackPath)
    return
  }

  if (!currentPathExists && route.path === fallbackPath) {
    triggerRefresh(fallbackPath)
  }
}

const handleContextMenuAction = async (action: ContextMenuActionKey) => {
  const targetPath = contextMenuTargetTab.value?.path
  closeContextMenu()
  if (!targetPath) return

  switch (action) {
    case 'refresh':
      if (route.path !== targetPath) {
        triggerRefresh(targetPath)
        await router.push(targetPath)
        return
      }
      triggerRefresh(targetPath)
      return
    case 'close-current':
      await removeTab(targetPath)
      return
    case 'close-other':
      menuTabsStore.closeOtherTabs(targetPath)
      await ensureRouteAfterBatchClose(targetPath)
      return
    case 'close-left':
      menuTabsStore.closeLeftTabs(targetPath)
      await ensureRouteAfterBatchClose(targetPath)
      return
    case 'close-right':
      menuTabsStore.closeRightTabs(targetPath)
      await ensureRouteAfterBatchClose(targetPath)
      return
    case 'close-all':
      menuTabsStore.closeAllTabs()
      triggerRefresh(HOME_PATH)
      if (route.path !== HOME_PATH) await router.push(HOME_PATH)
      return
  }
}

onMounted(() => {
  syncTabFromRoute()
  window.addEventListener('click', closeContextMenu)
  window.addEventListener('resize', closeContextMenu)
  window.addEventListener('scroll', closeContextMenu, true)
})

onBeforeUnmount(() => {
  window.removeEventListener('click', closeContextMenu)
  window.removeEventListener('resize', closeContextMenu)
  window.removeEventListener('scroll', closeContextMenu, true)
})

watch(
  () => route.path,
  () => {
    syncTabFromRoute()
    closeContextMenu()
    if (pendingRefreshPath.value === route.path) {
      refreshKey.value += 1
      pendingRefreshPath.value = null
    }
  },
)
</script>

<style lang="scss" scoped>
.menu-tabs-wrapper {
  position: relative;
  background: var(--wb-color-card-bg);
  border-bottom: 1px solid var(--wb-color-border-soft);
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
      color: var(--wb-color-text-body);
      transition:
        color 0.2s,
        background 0.2s;

      &:hover {
        color: var(--wb-color-text-primary);
        background: rgba(31, 41, 55, 0.04);
        border-radius: 6px;
      }

      &.is-active {
        color: var(--wb-color-text-primary);
        font-weight: 600;
        position: relative;

        &::after {
          content: '';
          position: absolute;
          left: 10px;
          right: 10px;
          bottom: 2px;
          height: 2px;
          background: var(--wb-color-text-primary);
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
      transition:
        color 0.2s,
        background 0.2s;

      &:hover {
        background: rgba(31, 41, 55, 0.08);
        color: var(--wb-color-text-primary);
      }
    }
  }
}

.tab-label {
  display: inline-flex;
  align-items: center;
  min-width: 0;
}

.tab-context-menu {
  position: fixed;
  z-index: 2100;
  width: 96px;
  max-width: calc(100vw - 16px);
  padding: 4px;
  background: var(--wb-color-card-bg);
  border: 1px solid var(--wb-color-border-soft);
  border-radius: 8px;
  box-shadow: 0 10px 24px rgba(15, 23, 42, 0.14);
}

.tab-context-menu__item {
  width: 100%;
  border: none;
  border-radius: 6px;
  background: transparent;
  color: var(--wb-color-text-body);
  font-size: 12px;
  line-height: 18px;
  text-align: left;
  white-space: nowrap;
  padding: 7px 6px;
  cursor: pointer;
  transition:
    background 0.2s,
    color 0.2s;

  &:hover:not(:disabled) {
    background: rgba(31, 41, 55, 0.06);
    color: var(--wb-color-text-primary);
  }

  &:disabled {
    color: var(--wb-color-text-muted);
    cursor: not-allowed;
  }
}

.tab-content {
  flex: 1;
  height: 100%;
  min-height: 0;
  padding: 2px;
  background: var(--wb-color-page-bg);
  overflow: auto;
}
</style>
