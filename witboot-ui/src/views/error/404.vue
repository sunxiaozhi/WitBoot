<template>
  <div class="not-found-container">
    <h1>404</h1>
    <h2>页面不存在</h2>
    <p>您访问的页面不存在或已被移除。</p>
    <el-button type="primary" @click="goHome">返回首页</el-button>
  </div>
</template>

<script lang="ts" setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goHome = () => {
  router.push({ name: 'home' })
}
</script>

<style scoped>
.not-found-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  text-align: center;
  color: #666;
}

h1 {
  font-size: 72px;
  color: #333;
}

h2 {
  font-size: 24px;
  margin: 10px 0;
}

p {
  font-size: 16px;
  margin-bottom: 20px;
  color: #999;
}
</style>
