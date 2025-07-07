<template>
  <el-header>
    <div class="header-left">
      <el-icon size="25px" color="#333" @click="changeCollapse()">
        <fold />
      </el-icon>
    </div>
    <div class="header-right">
      <el-dropdown placement="bottom-end">
        <el-icon size="25px" color="#333">
          <user />
        </el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item @click="logout()">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { useMenuStore } from '@/stores/menuStore'
import router from '@/router'
import { Fold, User } from '@element-plus/icons-vue'

const menuStore = useMenuStore()
const { toggleCollapse } = menuStore

const changeCollapse = () => {
  toggleCollapse()
}

const logout = () => {
  localStorage.clear()
  router.push('/login')
}
</script>

<style lang="scss" scoped>
.el-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 45px;
  background-color: #fff;
}
</style>
