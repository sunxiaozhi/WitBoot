<template>
  <el-footer>
    <span>
      Copyright © 2024 Powered by
      <a :href="githubUrl" target="_blank">{{ owner }}</a>
    </span>
  </el-footer>
</template>

<script setup>
import { SITE_OWNER as owner, GITHUB_URL as githubUrl } from '@/config/siteConfig'
</script>

<style lang="scss" scoped>
.el-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  color: #333;

  a {
    color: #409eff;
    text-decoration: none;

    &:link,
    &:visited,
    &:hover,
    &:active {
      color: #409eff;
    }
  }
}
</style>
