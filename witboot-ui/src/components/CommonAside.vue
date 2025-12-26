<template>
  <el-aside :width="asideWidth" class="aside">
    <div class="logo">
      <h3>{{ isCollapse ? 'WB' : 'WitBoot' }}</h3>
    </div>
    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapse"
      :collapse-transition="false"
      router
      background-color="#ffffff"
      text-color="#303133"
      active-text-color="#409eff"
      @select="handleSelect"
    >
      <!-- 使用配置化菜单渲染 -->
      <template v-for="menu in menuList" :key="menu.index">
        <!-- 一级菜单无子菜单 -->
        <el-menu-item v-if="!menu.children" :index="menu.index">
          <el-icon><component :is="menu.icon" /></el-icon>
          <span>{{ menu.title }}</span>
        </el-menu-item>

        <!-- 有子菜单 -->
        <el-sub-menu v-else :index="menu.index">
          <template #title>
            <el-icon><component :is="menu.icon" /></el-icon>
            <span>{{ menu.title }}</span>
          </template>
          <template v-for="sub in menu.children" :key="sub.index">
            <el-menu-item v-if="!sub.children" :index="sub.index">
              <el-icon><component :is="sub.icon" /></el-icon>
              <span>{{ sub.title }}</span>
            </el-menu-item>
            <el-sub-menu v-else :index="sub.index">
              <template #title>
                <el-icon><component :is="sub.icon" /></el-icon>
                <span>{{ sub.title }}</span>
              </template>
              <el-menu-item
                v-for="third in sub.children"
                :key="third.index"
                :index="third.index"
              >
                <el-icon><component :is="third.icon" /></el-icon>
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
import { storeToRefs } from 'pinia'
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import { useMenuTabsStore } from '@/stores/menuTabsStore'
import {
  HomeFilled,
  Setting,
  User,
  List,
  Tickets,
  Memo,
  Document,
  Reading,
  InfoFilled
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const menuTabsStore = useMenuTabsStore()
/*const menuCollapseStore = useMenuCollapseStore()
const { isCollapse } = storeToRefs(menuCollapseStore)*/

const menuCollapseStore = useMenuCollapseStore()
const isCollapse = computed(() => menuCollapseStore.isCollapse)  // 直接用
// 或者更狠：const isCollapse = menuCollapseStore.isCollapse   // 因为本身就是 ref，模板里可以直接用

const asideWidth = computed(() => (isCollapse.value ? '64px' : '200px'))
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
          { index: '/loginLog', icon: Reading, title: '登录日志' }
        ]
      }
    ]
  },
  { index: '/about', icon: InfoFilled, title: '关于系统' }
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
  background: #fff;
  border-right: 1px solid #e6e6e6;
  overflow: hidden;
  transition: width 0.28s;
}
.logo {
  height: 45px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: #409eff;
  h3 {
    color: #fff;
    margin: 0;
    font-size: 18px;
    font-weight: bold;
  }
}
.el-menu {
  border-right: none;
  height: calc(100vh - 45px);
}
</style>