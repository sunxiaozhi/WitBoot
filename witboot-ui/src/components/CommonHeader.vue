<template>
  <el-header class="header-wrapper">
    <div class="header-wrapper__left">
      <el-icon size="25px" color="#333" @click="changeCollapse">
        <fold-icon />
      </el-icon>
    </div>
    <div class="header-wrapper__right">
      <el-dropdown placement="bottom-end">
        <el-icon :size="25" :color="'#333'">
          <user-icon />
        </el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="profile">个人信息</el-dropdown-item>
            <el-dropdown-item @click="logout">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import { Fold as FoldIcon, User as UserIcon } from '@element-plus/icons-vue'
import { logout } from '@/utils/auth'

const menuCollapseStore = useMenuCollapseStore()

const changeCollapse = () => {
  menuCollapseStore.toggleCollapse()
}

const profile = () => {
  console.log('profile')
}
</script>

<style lang="scss" scoped>
.header-wrapper {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 45px;
  background-color: #fff;

  &__left,
  &__right {
    display: flex;
    align-items: center;
  }
}
</style>
