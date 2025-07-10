<template>
  <div class="common-layout">
    <el-container>
      <common-aside />
      <el-container direction="vertical">
        <common-header />
        <el-main class="common-main">
          <common-menu-tab />
          <router-view />
        </el-main>
        <common-footer />
      </el-container>
    </el-container>
  </div>
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

  .common-main {
    //background-color: aliceblue;
  }
}
</style>