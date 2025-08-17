<template>
  <div class="common-layout">
    <el-container>
      <common-aside />
      <el-container direction="vertical">
        <common-header />
        <el-main class="common-main">
          <common-menu-tab />
<!--          <router-view />-->
        </el-main>
        <common-footer />
      </el-container>
    </el-container>
  </div>

  <el-backtop :right="20" :bottom="20" />
</template>

<script setup lang="ts">
import CommonAside from '@/components/CommonAside.vue'
import CommonHeader from '@/components/CommonHeader.vue'
import CommonFooter from '@/components/CommonFooter.vue'
import CommonMenuTab from '@/components/CommonMenuTab.vue'
</script>

<style lang="scss" scoped>
.common-layout {
  height: 100vh;
  overflow: hidden; // 防止整体出现滚动条

  .el-container {
    height: 100%;
  }

  .common-main {
    flex: 1;
    overflow-y: auto; // 允许主内容区域滚动
    //background-color: aliceblue;
  }
}
</style>