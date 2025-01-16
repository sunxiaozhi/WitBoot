<template>
  <el-header>
    <div class="header-left">
      <el-icon :size="size" :color="color" @click="changeCollapse()">
        <fold />
      </el-icon>
    </div>
    <div class="header-right">
      <el-dropdown placement="bottom-end">
        <el-icon :size="size" :color="color" @click="changeCollapse()">
          <user />
        </el-icon>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>个人信息</el-dropdown-item>
            <el-dropdown-item>退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup lang="ts">
const size = '25px'
const color = '#333'

const changeCollapse = () => {
  console.log('changeCollapse')
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
