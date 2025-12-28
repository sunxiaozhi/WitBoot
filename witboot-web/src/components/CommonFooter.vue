<template>
  <el-footer class="footer">
    Copyright © 2026 Powered by
    <a :href="githubUrl" target="_blank" rel="noopener">{{ owner }}</a>
  </el-footer>
</template>

<script setup>
import { SITE_OWNER as owner, GITHUB_URL as githubUrl } from '@/config/siteConfig'
</script>

<style lang="scss" scoped>
.footer {
  text-align: center;
  line-height: 60px;
  background: #fafafa;
  border-top: 1px solid #e6e6e6;
  color: #666;
  font-size: 14px;

  a {
    color: #409eff;
    margin-left: 4px;
  }
}
</style>
