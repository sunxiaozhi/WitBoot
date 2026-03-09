<template>
  <el-header class="header">
    <div class="left">
      <el-button
        text
        :icon="menuCollapseStore.isCollapse ? Fold : Expand"
        class="collapse-icon"
        @click="menuCollapseStore.toggleCollapse"
      />
      <el-breadcrumb class="breadcrumb">
        <template #separator>
          <el-icon><ArrowRight /></el-icon>
        </template>
        <el-breadcrumb-item
          v-for="(item, index) in breadcrumbItems"
          :key="item.path"
          :to="index < breadcrumbItems.length - 1 ? item.path : undefined"
        >
          {{ item.title }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>

    <div class="right">
      <el-dropdown trigger="click" @command="handleThemeChange">
        <el-button text class="theme-btn">
          <el-icon><Brush /></el-icon>
        </el-button>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="indigo">靛蓝</el-dropdown-item>
            <el-dropdown-item command="emerald">翡翠</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-button text class="fullscreen-btn" @click="toggleFullscreen">
        <el-icon>
          <FullScreen v-if="!isFullscreen" />
          <Close v-else />
        </el-icon>
      </el-button>
      <el-dropdown trigger="click">
        <div class="user-dropdown">
          <el-avatar :size="36" :src="avatarUrl" class="user-avatar" />
          <span class="username">管理员</span>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="toProfile">
              <el-icon><User /></el-icon>
              <span>个人信息</span>
            </el-dropdown-item>
            <el-dropdown-item divided @click="handleLogout">
              <el-icon><SwitchButton /></el-icon>
              <span>退出登录</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { Expand, Fold, User, SwitchButton, ArrowRight, FullScreen, Close, Brush } from '@element-plus/icons-vue'
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import { useRoute } from 'vue-router'
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import { ElMessageBox } from 'element-plus'

const menuCollapseStore = useMenuCollapseStore()
const avatarUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
const route = useRoute()
const isFullscreen = ref(false)
const activeTheme = ref(localStorage.getItem('theme') || 'indigo')

const breadcrumbItems = computed(() => {
  const items = route.matched
    .filter(r => r.meta?.title)
    .map(r => ({ title: r.meta?.title as string, path: r.path }))
  const hasHome = items.some(i => i.path === '/home')
  return hasHome ? items : [{ title: '首页', path: '/home' }, ...items]
})

const updateFullscreenState = () => {
  isFullscreen.value = !!document.fullscreenElement
}

const toggleFullscreen = async () => {
  if (!document.fullscreenElement) {
    await document.documentElement.requestFullscreen()
  } else {
    await document.exitFullscreen()
  }
  updateFullscreenState()
}

const applyTheme = (theme: string) => {
  activeTheme.value = theme
  document.documentElement.dataset.theme = theme
  localStorage.setItem('theme', theme)
}

const handleThemeChange = (theme: string) => {
  applyTheme(theme)
}

onMounted(() => {
  applyTheme(activeTheme.value)
  updateFullscreenState()
  document.addEventListener('fullscreenchange', updateFullscreenState)
})

onBeforeUnmount(() => {
  document.removeEventListener('fullscreenchange', updateFullscreenState)
})

const toProfile = () => {
  // router.push('/profile')
  console.log('去个人信息页')
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    type: 'warning',
    confirmButtonText: '确定',
    cancelButtonText: '取消',
  }).then(() => {
    // logout()
    localStorage.clear()
    location.href = '/login'
  })
}
</script>

<style lang="scss" scoped>
.header {
  background: var(--wb-color-card-bg);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 56px !important;
  box-shadow: var(--wb-shadow-card);
  border-bottom: 1px solid var(--wb-color-border-muted);

  .left {
    display: flex;
    align-items: center;
    gap: 8px;

    .collapse-icon {
      cursor: pointer;
      color: var(--wb-color-text-secondary);
      transition: all 0.3s;
      padding: 8px;
      border-radius: 8px;
      font-size: 20px;
      height: 36px;
      width: 36px;

      &:hover {
        color: var(--wb-color-primary);
        background: var(--wb-color-primary-soft-hover);
        transform: scale(1.05);
      }
    }

    :deep(.collapse-icon .el-icon) {
      font-size: 20px;
    }

    .breadcrumb {
      margin-left: 4px;
      font-size: 13px;
      color: var(--wb-color-text-secondary);

      :deep(.el-breadcrumb__inner) {
        color: var(--wb-color-text-secondary);
        font-weight: 400;
      }

      :deep(.el-breadcrumb__inner.is-link) {
        color: var(--wb-color-text-secondary);
      }
    }
  }

  .right {
    display: flex;
    align-items: center;
    gap: 8px;

    .fullscreen-btn {
      color: var(--wb-color-text-secondary);
      transition: all 0.3s;
    }

    .theme-btn {
      color: var(--wb-color-text-secondary);
      transition: all 0.3s;

      &:hover {
        color: var(--wb-color-text-primary);
        background: rgba(31, 41, 55, 0.06);
      }
    }

    .user-dropdown {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 4px 8px;
      border-radius: 24px;
      cursor: pointer;
      transition: all 0.3s;

      &:hover {
        background: var(--wb-color-primary-soft);
      }

      .user-avatar {
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
      }

      .username {
        font-size: 14px;
        color: var(--wb-color-text-primary);
        font-weight: 500;
      }
    }
  }
}

:deep(.el-dropdown-menu) {
  .el-dropdown-menu__item {
    display: flex;
    align-items: center;
    gap: 8px;
    padding: 10px 16px;

    &:hover {
      background: var(--wb-color-primary-soft-hover);
      color: var(--wb-color-primary);
    }
  }
}
</style>
