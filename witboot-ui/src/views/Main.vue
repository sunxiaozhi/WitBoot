<template>
    <div class="common-layout">
      <el-container>
        <common-aside />
        <el-container>
         <common-header />
          <el-main>
            <router-view />
          </el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </div>
  </template>

  <script setup lang="ts">
  import CommonHeader from '@/components/CommonHeader.vue'
  import CommonAside from '@/components/CommonAside.vue'
  </script>
  