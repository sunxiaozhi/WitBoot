<template>
  <el-header class="header">
    <div class="left">
      <el-icon :size="24" class="collapse-icon" @click="menuCollapseStore.toggleCollapse">
        <Expand v-if="menuCollapseStore.isCollapse" />
        <Fold v-else />
      </el-icon>
    </div>

    <div class="right">
      <el-dropdown trigger="click">
        <el-avatar :size="32" :src="avatarUrl" />
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="toProfile">个人信息</el-dropdown-item>
            <el-dropdown-item divided @click="handleLogout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { Fold, Expand } from '@element-plus/icons-vue'
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import { ElMessageBox } from 'element-plus'

const menuCollapseStore = useMenuCollapseStore()
const avatarUrl = 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'

const toProfile = () => {
  // router.push('/profile')
  console.log('去个人信息页')
}

const handleLogout = () => {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    type: 'warning',
  }).then(() => {
    // logout()
    localStorage.clear()
    location.href = '/login'
  })
}
</script>

<style lang="scss" scoped>
.header {
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 20px;
  height: 50px !important;

  .collapse-icon {
    cursor: pointer;
    transition: transform 0.3s;

    &:hover {
      transform: scale(1.1);
    }
  }
}
</style>
