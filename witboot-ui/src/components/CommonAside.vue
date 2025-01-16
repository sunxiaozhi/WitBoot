<template>
  <el-aside width="200px">
    <h3>WitBoot</h3>
    <el-menu default-active="1" :collapse="isCollapse">
      <el-menu-item index="1">
        <el-icon>
          <home-filled />
        </el-icon>
        <span>首页</span>
      </el-menu-item>
      <el-sub-menu index="2">
        <template #title>
          <el-icon>
            <setting />
          </el-icon>
          <span>系统管理</span>
        </template>
        <el-menu-item index="2-1">
          <el-icon>
            <user />
          </el-icon>
          <span>人员管理</span>
        </el-menu-item>
        <el-menu-item index="2-2">
          <el-icon>
            <setting />
          </el-icon>
          <span>部门管理</span>
        </el-menu-item>
        <el-menu-item index="2-3">
          <el-icon>
            <setting />
          </el-icon>
          <span>字典管理</span>
        </el-menu-item>
        <el-sub-menu index="2-4">
          <template #title>
            <el-icon>
              <setting />
            </el-icon>
            <span>日志管理</span></template
          >
          <el-menu-item index="2-4-1">
            <el-icon>
              <setting />
            </el-icon>
            <span>操作日志</span></el-menu-item
          >
          <el-menu-item index="2-4-2">
            <el-icon>
              <setting />
            </el-icon>
            <span>登录日志</span></el-menu-item
          >
        </el-sub-menu>
      </el-sub-menu>
      <el-menu-item index="3">
        <el-icon>
          <info-filled />
        </el-icon>
        <span>关于系统</span>
      </el-menu-item>
    </el-menu>
  </el-aside>
</template>

<script setup lang="ts">
import { ref } from 'vue'

const isCollapse = ref(false)
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