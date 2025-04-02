<template>
  <el-aside width="{isCollapse} ? 200px : 64px">
    <h3>WitBoot</h3>
    <el-menu default-active="/home" :collapse="isCollapse" :router="true">
      <el-menu-item index="/home">
        <el-icon>
          <HomeFilled />
        </el-icon>
        <span>首页</span>
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title>
          <el-icon>
            <Setting />
          </el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/user">
          <el-icon>
            <User />
          </el-icon>
          <span>人员管理</span>
        </el-menu-item>
        <el-menu-item index="/department">
          <el-icon>
            <List />
          </el-icon>
          <span>部门管理</span>
        </el-menu-item>
        <el-menu-item index="/dictionary">
          <el-icon>
            <Tickets />
          </el-icon>
          <span>字典管理</span>
        </el-menu-item>
        <el-sub-menu index="2-4">
          <template #title>
            <el-icon>
              <Memo />
            </el-icon>
            <span>日志管理</span>
          </template>
          <el-menu-item index="/operationLog">
            <el-icon>
              <Document />
            </el-icon>
            <span>操作日志</span>
          </el-menu-item>
          <el-menu-item index="/loginLog">
            <el-icon>
              <Reading />
            </el-icon>
            <span>登录日志</span>
          </el-menu-item>
        </el-sub-menu>
      </el-sub-menu>
      <el-menu-item index="/about">
        <el-icon>
          <InfoFilled />
        </el-icon>
        <span>关于系统</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script setup lang="ts">
import {useMenuStore} from '@/stores/menuStore'
import {storeToRefs} from 'pinia'

const menuStore = useMenuStore()
const { isCollapse } = storeToRefs(menuStore)
</script>

<style lang="scss" scoped>
.el-aside {
  background-color: #fff;
  height: 100vh;

  h3 {
    text-align: center;
    color: #409eff;
  }

  .el-menu {
    border-right: none;
  }
}
</style>
