<template>
  <el-container class="layout">
    <common-aside />
    <el-container class="main-container">
      <common-header />
      <el-main class="main-content">
        <common-menu-tab />
      </el-main>
      <common-footer />
    </el-container>
  </el-container>

  <el-backtop :right="40" :bottom="100" />
</template>

<script setup lang="ts">
import CommonAside from '@/components/CommonAside.vue'
import CommonHeader from '@/components/CommonHeader.vue'
import CommonFooter from '@/components/CommonFooter.vue'
import CommonMenuTab from '@/components/CommonMenuTab.vue'
</script>

<style lang="scss" scoped>
.layout {
  height: 100vh;
}

.main-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  overflow: hidden;
}

.main-content {
  padding: 0;
  flex: 1;
  overflow: hidden;
  display: flex;
  flex-direction: column;

  /*:deep(.el-main) {
    padding: 10px;
    overflow-y: auto;
    overflow-x: hidden;
  }*/
}
</style>