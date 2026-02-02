<template>
  <el-header class="header">
    <div class="left">
      <el-icon :size="20" class="collapse-icon" @click="menuCollapseStore.toggleCollapse">
        <Expand v-if="!menuCollapseStore.isCollapse" />
        <Fold v-else />
      </el-icon>
    </div>

    <div class="right">
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
import { Expand, User, SwitchButton } from '@element-plus/icons-vue'
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
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  height: 64px !important;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
  border-bottom: 1px solid #f0f0f0;

  .left {
    display: flex;
    align-items: center;

    .collapse-icon {
      cursor: pointer;
      color: #666;
      transition: all 0.3s;
      padding: 8px;
      border-radius: 8px;

      &:hover {
        color: #667eea;
        background: rgba(102, 126, 234, 0.1);
        transform: scale(1.05);
      }
    }
  }

  .right {
    .user-dropdown {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 4px 8px;
      border-radius: 24px;
      cursor: pointer;
      transition: all 0.3s;

      &:hover {
        background: rgba(102, 126, 234, 0.08);
      }

      .user-avatar {
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.12);
      }

      .username {
        font-size: 14px;
        color: #333;
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
      background: rgba(102, 126, 234, 0.1);
      color: #667eea;
    }
  }
}
</style>
