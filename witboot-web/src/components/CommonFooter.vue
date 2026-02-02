<template>
  <el-footer class="footer">
    <div class="footer-content">
      <p class="copyright">
        Copyright © 2026 Powered by
        <a :href="githubUrl" target="_blank" rel="noopener">{{ owner }}</a>
      </p>
    </div>
  </el-footer>
</template>

<script setup lang="ts">
import { SITE_OWNER as owner, GITHUB_URL as githubUrl } from '@/config/siteConfig'
</script>

<style lang="scss" scoped>
.footer {
  display: flex;
  align-items: center;
  justify-content: center;
  background: #fff;
  border-top: 1px solid #f0f0f0;
  padding: 0;
  height: 48px !important;

  .footer-content {
    .copyright {
      margin: 0;
      font-size: 13px;
      color: #999;
      line-height: 48px;

      a {
        color: #667eea;
        margin-left: 4px;
        text-decoration: none;
        transition: all 0.3s;

        &:hover {
          color: #764ba2;
          text-decoration: underline;
        }
      }
    }
  }
}
</style>
