<template>
  <el-aside :width="asideWidth" class="aside">
    <div class="logo">
      <el-icon :size="28" class="logo-icon"><Monitor /></el-icon>
      <h3 v-show="!isCollapse">WitBoot</h3>
    </div>
    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapse"
      :collapse-transition="false"
      router
      :popper-class="'aside-sub-menu-popper'"
      class="custom-menu"
      @select="handleSelect"
    >
      <!-- 使用配置化菜单渲染 -->
      <template v-for="menu in menuList" :key="menu.index">
        <!-- 一级菜单无子菜单 -->
        <el-menu-item v-if="!menu.children" :index="menu.index">
          <el-icon>
            <component :is="menu.icon" />
          </el-icon>
          <span>{{ menu.title }}</span>
        </el-menu-item>

        <!-- 有子菜单 -->
        <el-sub-menu v-else :index="menu.index">
          <template #title>
            <el-icon>
              <component :is="menu.icon" />
            </el-icon>
            <span>{{ menu.title }}</span>
          </template>
          <template v-for="sub in menu.children" :key="sub.index">
            <el-menu-item v-if="!sub.children" :index="sub.index">
              <el-icon>
                <component :is="sub.icon" />
              </el-icon>
              <span>{{ sub.title }}</span>
            </el-menu-item>
            <el-sub-menu v-else :index="sub.index">
              <template #title>
                <el-icon>
                  <component :is="sub.icon" />
                </el-icon>
                <span>{{ sub.title }}</span>
              </template>
              <el-menu-item v-for="third in sub.children" :key="third.index" :index="third.index">
                <el-icon>
                  <component :is="third.icon" />
                </el-icon>
                <span>{{ third.title }}</span>
              </el-menu-item>
            </el-sub-menu>
          </template>
        </el-sub-menu>
      </template>
    </el-menu>
  </el-aside>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import { useMenuTabsStore } from '@/stores/menuTabsStore'
import { Monitor } from '@element-plus/icons-vue'
import {
  HomeFilled,
  Setting,
  User,
  List,
  Tickets,
  Memo,
  Document,
  Reading,
  InfoFilled,
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const menuTabsStore = useMenuTabsStore()

const menuCollapseStore = useMenuCollapseStore()
const isCollapse = computed(() => menuCollapseStore.isCollapse)

const asideWidth = computed(() => (isCollapse.value ? '64px' : '240px'))
const activeMenu = computed(() => route.path)

// 完全配置化菜单（推荐后续都走这里维护）
const menuList = [
  { index: '/home', icon: HomeFilled, title: '首页' },
  {
    index: '2',
    icon: Setting,
    title: '系统管理',
    children: [
      { index: '/user', icon: User, title: '人员管理' },
      { index: '/department', icon: List, title: '部门管理' },
      { index: '/dictionary', icon: Tickets, title: '字典管理' },
      {
        index: '2-4',
        icon: Memo,
        title: '日志管理',
        children: [
          { index: '/operationLog', icon: Document, title: '操作日志' },
          { index: '/loginLog', icon: Reading, title: '登录日志' },
        ],
      },
    ],
  },
  { index: '/about', icon: InfoFilled, title: '关于系统' },
]

const handleSelect = (path: string) => {
  const matched = router.getRoutes().find(r => r.path === path)
  if (matched) {
    menuTabsStore.addTab(matched)
    router.push(path)
  }
}
</script>

<style lang="scss" scoped>
.aside {
  background: linear-gradient(180deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: width 0.3s ease;
  box-shadow: 2px 0 8px rgba(0, 0, 0, 0.08);
}

.logo {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 12px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);

  .logo-icon {
    color: #fff;
  }

  h3 {
    color: #fff;
    margin: 0;
    font-size: 20px;
    font-weight: 600;
    letter-spacing: 0.5px;
    white-space: nowrap;
  }
}

.custom-menu {
  border-right: none;
  background: transparent;
  flex: 1;
  overflow-y: auto;

  :deep(.el-menu-item),
  :deep(.el-sub-menu__title) {
    color: rgba(255, 255, 255, 0.8);
    border-radius: 8px;
    margin: 4px 12px;
    transition: all 0.3s;

    &:hover {
      background: rgba(255, 255, 255, 0.1);
      color: #fff;
    }

    .el-icon {
      font-size: 18px;
      margin-right: 8px;
    }
  }

  :deep(.el-menu-item.is-active) {
    background: rgba(255, 255, 255, 0.2);
    color: #fff;
    font-weight: 500;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  :deep(.el-sub-menu) {
    .el-menu {
      background: transparent;
    }

    .el-menu-item {
      padding-left: 56px !important;
      margin: 4px 12px;
      border-radius: 8px;
      color: rgba(255, 255, 255, 0.8) !important;

      span {
        color: rgba(255, 255, 255, 0.8) !important;
      }

      &:hover {
        background: rgba(255, 255, 255, 0.1) !important;
        color: #fff !important;

        span {
          color: #fff !important;
        }
      }
    }

    .el-menu-item.is-active {
      background: rgba(255, 255, 255, 0.2) !important;
      color: #fff !important;

      span {
        color: #fff !important;
      }
    }

    // 嵌套子菜单项（三级菜单）
    .el-sub-menu {
      .el-menu-item {
        padding-left: 84px !important;
      }
    }
  }
}

/* 折叠状态样式调整 */
:deep(.el-menu--collapse) {
  .el-menu-item,
  .el-sub-menu__title {
    justify-content: center;
    padding: 0 !important;
    margin: 8px 12px;
    min-width: 40px;

    .el-icon {
      margin-right: 0;
    }

    span {
      display: none;
    }
  }
}
</style>

<style lang="scss">
/* 全局子菜单弹出层样式 - 确保折叠展开时能正确显示 */
.aside-sub-menu-popper {
  .el-menu {
    background: linear-gradient(180deg, #667eea 0%, #764ba2 100%) !important;
    border: none;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
    border-radius: 8px;
    padding: 4px;
    min-width: 200px;
  }

  .el-menu-item,
  .el-sub-menu__title {
    color: rgba(255, 255, 255, 0.9) !important;
    background: transparent !important;
    border-radius: 6px;
    margin: 2px 4px;

    span,
    .el-icon {
      color: rgba(255, 255, 255, 0.9) !important;
    }

    &:hover {
      background: rgba(255, 255, 255, 0.15) !important;

      span,
      .el-icon {
        color: #fff !important;
      }
    }
  }

  .el-menu-item.is-active {
    background: rgba(255, 255, 255, 0.25) !important;

    span,
    .el-icon {
      color: #fff !important;
    }
  }

  // 三级菜单展开时的弹出
  .el-sub-menu__title {
    .el-sub-menu__icon-arrow {
      color: rgba(255, 255, 255, 0.9) !important;
    }
  }
}
</style>
