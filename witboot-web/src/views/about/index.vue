<template>
  <div class="about-container">
    <div class="page-header">
      <div>
        <h2></h2>
      </div>
      <div class="header-actions">
        <el-button type="primary" class="primary-btn">
          <el-icon><Document /></el-icon>
          查看文档
        </el-button>
        <el-button class="ghost-btn">
          <el-icon><Clock /></el-icon>
          更新日志
        </el-button>
      </div>
    </div>

    <div class="content-grid">
      <div class="info-card">
        <h3>系统简介</h3>
        <p class="info-text">
          WitBoot 基于 SpringBoot 3.x + COLA 5.0（整洁面向对象分层架构）+ Mybatis Plus + Vue 3.x +
          Element Plus 实现的前后端分离系统，支持 RBAC 动态权限、数据权限、日志记录等能力，适用于通用级管理平台快速搭建。
        </p>
        <div class="version-row">
          <span class="version-label">当前版本</span>
          <span class="version-value">V{{ appVersion }}</span>
        </div>
        <div class="pill-group">
          <span class="pill">RBAC</span>
          <span class="pill">数据权限</span>
          <span class="pill">日志审计</span>
          <span class="pill">前后端分离</span>
        </div>
      </div>

      <div class="table-card">
        <div class="card-header">
          <span>前端技术组件</span>
        </div>
        <el-table :data="frontendTableData" stripe height="100%" class="stack-table">
          <el-table-column prop="componentName" label="组件名称" min-width="180" />
          <el-table-column prop="version" label="版本" min-width="120" />
          <el-table-column prop="description" label="说明" min-width="220" show-overflow-tooltip />
        </el-table>
      </div>

      <div class="table-card">
        <div class="card-header">
          <span>后端技术组件</span>
        </div>
        <el-table :data="backendTableData" stripe height="100%" class="stack-table">
          <el-table-column prop="componentName" label="组件名称" min-width="180" />
          <el-table-column prop="version" label="版本" min-width="120" />
          <el-table-column prop="description" label="说明" min-width="220" show-overflow-tooltip />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script lang="ts" setup>
import pkg from '../../../package.json'
import { Document, Clock } from '@element-plus/icons-vue'
const frontendTableData = [
  {
    componentName: 'Vue',
    version: '3.x',
    description: '前端框架，组合式 API',
  },
  {
    componentName: 'Element Plus',
    version: '2.x',
    description: '通用级 UI 组件库',
  },
  {
    componentName: 'Vite',
    version: '5.x',
    description: '前端构建与开发服务器',
  },
]

const backendTableData = [
  {
    componentName: 'SpringBoot',
    version: '3.5.3',
    description: '核心服务框架，稳定高效',
  },
  {
    componentName: 'COLA',
    version: '5.0.0',
    description: '整洁架构分层设计，强化领域建模能力',
  },
  {
    componentName: 'Mybatis Plus',
    version: '3.x',
    description: '数据访问层增强与通用 CRUD',
  },
]

const appVersion = pkg.version
</script>

<style scoped lang="scss">
.about-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 16px;
  gap: 16px;
  background: #f5f7fa;

  --card-radius: 12px;
  --card-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;

  h2 {
    margin: 0 0 4px;
    font-size: 20px;
    color: #111827;
  }

  p {
    margin: 0;
    font-size: 13px;
    color: #6b7280;
  }
}

.header-actions {
  display: flex;
  gap: 10px;

  .primary-btn {
    background: var(--primary-gradient);
    border: none;
  }

  .ghost-btn {
    border: 1px solid #e5e7eb;
  }
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 16px;
  flex: 1;
  min-height: 0;
}

.info-card,
.table-card {
  background: #fff;
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-height: 0;
}

.info-card h3 {
  margin: 0;
  font-size: 15px;
  color: #111827;
}

.info-text {
  margin: 0;
  font-size: 13px;
  color: #4b5563;
  line-height: 1.7;
  text-indent: 1em;
}

.pill-group {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.pill {
  padding: 4px 10px;
  border-radius: 999px;
  font-size: 12px;
  background: rgba(102, 126, 234, 0.12);
  color: #4f46e5;
}

.version-row {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: #6b7280;
}

.version-value {
  font-weight: 600;
  color: #111827;
}

.card-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  color: #111827;

  .card-meta {
    font-size: 12px;
    color: #9ca3af;
  }
}

.stack-table {
  flex: 1;

  :deep(.el-table__cell) {
    padding: 8px 0;
  }

  :deep(.el-table__body tr) {
    height: 40px;
  }
}

@media (max-width: 1024px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
