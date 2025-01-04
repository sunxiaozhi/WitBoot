<template>
  <div class="common-layout">
    <el-container>
      <common-aside />
      <el-container direction="vertical">
        <common-header />
        <el-main>
          <router-view />
        </el-main>
        <common-footer />
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import CommonHeader from '@/components/CommonHeader.vue'
import CommonAside from '@/components/CommonAside.vue'
import CommonFooter from '@/components/CommonFooter.vue'
</script>
