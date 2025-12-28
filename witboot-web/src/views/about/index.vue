<template>
  <div class="about">
    <div class="about-content">
      <p class="about-content__text">
        WitBoot基于SpringBoot3.x + COLA 4.0（整洁面向对象分层架构）+ Mybatis Plus + Vue3.X + Element
        Plus实现的前后端分离系统，支持RBAC动态权限、数据权限、日志记录...
      </p>
    </div>

    <div class="about-content">
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column prop="componentName" label="组件名称" width="180" />
        <el-table-column prop="version" label="版本" width="180" />
      </el-table>
    </div>

  </div>
</template>

<script lang="ts" setup>
const tableData = [
  {
    componentName: 'COLA',
    version: '5.0.0',
  },
  {
    componentName: 'SpringBoot',
    version: '3.5.3',
  },
]
</script>

<style scoped>
.about {
  .about-content {
    border: 1px solid #ebeef5;
    border-radius: 15px;
    padding: 10px;
    background-color: #fff;

    &:first-child {
      margin-bottom: 10px;
    }

    &__text {
      text-align: left;
      text-indent: 1em;
      line-height: 1.5;
      font-size: 16px;
    }
  }
}
</style>
