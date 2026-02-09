<template>
  <div class="role-page">
    <div class="page-header">
      <div class="header-left">
        <h2></h2>
        <div class="header-actions">
          <el-button type="primary" class="primary-btn">
            <el-icon><Plus /></el-icon>
            新增角色
          </el-button>
          <el-button class="ghost-btn">
            <el-icon><Upload /></el-icon>
            导入
          </el-button>
        </div>
      </div>
      <div class="header-right">
        <el-input v-model="query.keyword" placeholder="搜索角色名称/编码" clearable class="filter-input">
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <el-button type="primary" class="search-btn">搜索</el-button>
        <el-button class="reset-btn" circle>
          <el-icon><Refresh /></el-icon>
        </el-button>
      </div>
    </div>

    <el-row :gutter="16" class="content-row">
      <el-col :span="10">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>角色列表</span>
              <span class="panel-meta">共 8 项</span>
            </div>
          </template>
          <el-table :data="roleTable" stripe height="100%" class="role-table">
            <el-table-column prop="name" label="角色名称" min-width="140" />
            <el-table-column prop="code" label="角色编码" min-width="140" />
            <el-table-column prop="status" label="状态" width="120">
              <template #default="{ row }">
                <el-tag :type="row.status === '启用' ? 'success' : 'info'" effect="plain" size="small">
                  {{ row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="140">
              <template #default>
                <el-button link type="primary" size="small">编辑</el-button>
                <el-button link type="danger" size="small">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>权限配置</span>
              <div class="panel-actions">
                <el-button text size="small">保存</el-button>
                <el-button text size="small">重置</el-button>
              </div>
            </div>
          </template>

          <div class="detail-card">
            <div class="detail-row">
              <span class="detail-label">角色名称</span>
              <span class="detail-value">系统管理员</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">角色编码</span>
              <span class="detail-value">SUPER_ADMIN</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">描述</span>
              <span class="detail-value">拥有系统全部访问权限</span>
            </div>
          </div>

          <el-divider />

          <div class="permission-tree">
            <div class="panel-header">
              <span>菜单权限</span>
              <span class="panel-meta">已选 12 项</span>
            </div>
            <el-tree
              ref="permTreeRef"
              :data="permissionTree"
              show-checkbox
              node-key="id"
              default-expand-all
              class="menu-tree"
            >
              <template #default="{ node, data }">
                <div class="tree-node">
                  <el-icon class="node-icon"><Lock /></el-icon>
                  <span class="node-label">{{ node.label }}</span>
                  <span class="node-path">{{ data.path }}</span>
                </div>
              </template>
            </el-tree>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { ElTree } from 'element-plus'
import { Plus, Upload, Search, Refresh, Lock } from '@element-plus/icons-vue'

const query = ref({ keyword: '' })
const permTreeRef = ref<InstanceType<typeof ElTree>>()

const roleTable = [
  { name: '系统管理员', code: 'SUPER_ADMIN', status: '启用' },
  { name: '运营管理员', code: 'OPERATOR', status: '启用' },
  { name: '审计员', code: 'AUDITOR', status: '启用' },
  { name: '访客', code: 'GUEST', status: '停用' },
]

const permissionTree = [
  {
    id: 1,
    label: '系统管理',
    path: '/system',
    children: [
      { id: 11, label: '人员管理', path: '/user' },
      { id: 12, label: '部门管理', path: '/department' },
      { id: 13, label: '字典管理', path: '/dictionary' },
    ],
  },
  {
    id: 2,
    label: '日志中心',
    path: '/log',
    children: [
      { id: 21, label: '登录日志', path: '/loginLog' },
      { id: 22, label: '操作日志', path: '/operationLog' },
    ],
  },
]
</script>

<style scoped lang="scss">
.role-page {
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
  gap: 12px;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-right {
  display: flex;
  align-items: center;
  gap: 8px;
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

.filter-input {
  width: 220px;

  :deep(.el-input__wrapper) {
    border-radius: 8px;
    border: 1px solid #e5e7eb;
    box-shadow: none;
    transition: border-color 0.3s;

    &:hover {
      border-color: #cbd5f5;
    }

    &.is-focus {
      border-color: #667eea;
    }
  }
}

.search-btn {
  background: var(--primary-gradient);
  border: none;
  padding: 6px 12px;
}

.reset-btn {
  border: 1px solid #e5e7eb;
}

.panel-card {
  background: #fff;
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
  border: none;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  color: #111827;

  .panel-meta {
    font-size: 12px;
    color: #9ca3af;
  }
}

.panel-actions {
  display: flex;
  gap: 6px;
}

.role-table {
  :deep(.el-table__cell) {
    padding: 8px 0;
  }

  :deep(.el-table__body tr) {
    height: 40px;
  }
}

.detail-card {
  background: #f8fafc;
  border: 1px solid #eef2f7;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.detail-row {
  display: grid;
  grid-template-columns: 90px 1fr;
  gap: 12px;
  font-size: 13px;
  color: #374151;
}

.detail-label {
  color: #6b7280;
}

.permission-tree {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.menu-tree {
  :deep(.el-tree-node__content) {
    height: 40px;
    border-radius: 8px;
    margin: 4px 0;
    transition: background 0.2s;

    &:hover {
      background: #f5f7fa;
    }
  }

  :deep(.el-tree-node__content.is-current) {
    background: rgba(102, 126, 234, 0.12);
  }
}

.tree-node {
  display: flex;
  align-items: center;
  gap: 8px;
  width: 100%;
}

.node-icon {
  color: #667eea;
}

.node-label {
  flex: 1;
  font-size: 13px;
  color: #1f2937;
}

.node-path {
  font-size: 12px;
  color: #9ca3af;
}

@media (max-width: 1200px) {
  .header-right {
    flex-wrap: wrap;
    justify-content: flex-end;
  }
}

@media (max-width: 1024px) {
  .content-row {
    flex-direction: column;
  }
}
</style>
