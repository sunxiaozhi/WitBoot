<template>
  <div class="menu-page">
    <div class="page-header">
      <div class="header-left">
        <h2></h2>
        <div class="header-actions">
          <el-button type="primary" class="primary-btn" @click="handleAddMenu">
            <el-icon><Plus /></el-icon>
            新增菜单
          </el-button>
          <el-button class="ghost-btn">
            <el-icon><Upload /></el-icon>
            导入
          </el-button>
        </div>
      </div>
      <div class="header-right">
        <el-input v-model="query.keyword" placeholder="搜索菜单名称/路径" clearable class="filter-input">
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
              <span>菜单树</span>
              <span class="panel-meta">共 18 项</span>
            </div>
          </template>
          <el-tree
            ref="treeRef"
            class="menu-tree"
            :data="menuTree"
            :props="defaultProps"
            node-key="id"
            default-expand-all
            highlight-current
          >
            <template #default="{ node, data }">
              <div class="tree-node">
                <el-icon class="node-icon"><Menu /></el-icon>
                <span class="node-label">{{ node.label }}</span>
                <span class="node-path">{{ data.path }}</span>
                <span class="node-level">L{{ node.level }}</span>
              </div>
            </template>
          </el-tree>
        </el-card>
      </el-col>
      <el-col :span="14">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>菜单详情</span>
              <div class="panel-actions">
                <el-button text size="small">编辑</el-button>
                <el-button text size="small">新增子菜单</el-button>
              </div>
            </div>
          </template>
          <div class="detail-card">
            <div class="detail-row">
              <span class="detail-label">菜单名称</span>
              <span class="detail-value">系统管理</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">菜单路径</span>
              <span class="detail-value">/system</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">菜单级别</span>
              <span class="detail-value">一级</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">组件地址</span>
              <span class="detail-value">views/system/index.vue</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">显示顺序</span>
              <span class="detail-value">2</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">图标</span>
              <span class="detail-value">Setting</span>
            </div>
            <div class="detail-row">
              <span class="detail-label">状态</span>
              <span class="detail-value">
                <el-tag type="success" effect="plain" size="small">启用</el-tag>
              </span>
            </div>
            <div class="detail-row full">
              <span class="detail-label">描述</span>
              <span class="detail-value">系统基础配置与权限控制</span>
            </div>
          </div>

          <el-divider />

          <div class="child-table">
            <div class="panel-header">
              <span>子菜单列表</span>
              <span class="panel-meta">3 项</span>
            </div>
            <el-table :data="childMenus" stripe height="100%" class="menu-table">
              <el-table-column prop="name" label="名称" min-width="140" />
              <el-table-column prop="path" label="路径" min-width="160" />
              <el-table-column prop="level" label="级别" width="80" />
              <el-table-column prop="component" label="组件" min-width="180" show-overflow-tooltip />
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
          </div>
        </el-card>
      </el-col>
    </el-row>

    <MenuDrawer v-model="drawerVisible" :data="currentMenu" @success="handleMenuSuccess" />
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import type { ElTree } from 'element-plus'
import { Plus, Upload, Search, Refresh, Menu } from '@element-plus/icons-vue'
import MenuDrawer from './MenuDrawer.vue'

const query = ref({ keyword: '' })
const treeRef = ref<InstanceType<typeof ElTree>>()

const menuTree = [
  {
    id: 1,
    label: '系统管理',
    path: '/system',
    children: [
      { id: 11, label: '人员管理', path: '/user' },
      { id: 12, label: '部门管理', path: '/department' },
      {
        id: 13,
        label: '字典管理',
        path: '/dictionary',
        children: [
          { id: 131, label: '字典分类', path: '/dictionary/category' },
          { id: 132, label: '字典明细', path: '/dictionary/detail' },
        ],
      },
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

const childMenus = [
  { name: '人员管理', path: '/user', level: '二级', component: 'views/user/index.vue', status: '启用' },
  { name: '部门管理', path: '/department', level: '二级', component: 'views/department/index.vue', status: '启用' },
  {
    name: '字典管理',
    path: '/dictionary',
    level: '二级',
    component: 'views/dictionary/index.vue',
    status: '停用',
  },
  {
    name: '字典分类',
    path: '/dictionary/category',
    level: '三级',
    component: 'views/dictionary/Category.vue',
    status: '启用',
  },
  {
    name: '字典明细',
    path: '/dictionary/detail',
    level: '三级',
    component: 'views/dictionary/Detail.vue',
    status: '启用',
  },
]

const defaultProps = {
  children: 'children',
  label: 'label',
}

const drawerVisible = ref(false)
const currentMenu = ref<null | {
  id?: number
  name?: string
  path?: string
  component?: string
  sort?: number
  icon?: string
  status?: '启用' | '停用'
  description?: string
}>(null)

const handleAddMenu = () => {
  currentMenu.value = null
  drawerVisible.value = true
}

const handleMenuSuccess = () => {
  drawerVisible.value = false
}
</script>

<style scoped lang="scss">
.menu-page {
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

.node-level {
  font-size: 11px;
  color: #667eea;
  background: rgba(102, 126, 234, 0.12);
  padding: 2px 6px;
  border-radius: 999px;
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

  &.full {
    grid-template-columns: 90px 1fr;
  }
}

.detail-label {
  color: #6b7280;
}

.child-table {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.menu-table {
  :deep(.el-table__cell) {
    padding: 8px 0;
  }

  :deep(.el-table__body tr) {
    height: 40px;
  }
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
