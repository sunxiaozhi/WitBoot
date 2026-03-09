<template>
  <div class="dictionary-container admin-page">
    <div class="page-header">
      <div class="header-left">
        <h2></h2>
        <div class="header-actions">
          <el-button type="primary" class="primary-btn admin-btn-primary" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增字典
          </el-button>
          <el-button class="ghost-btn admin-btn-secondary">
            <el-icon><Upload /></el-icon>
            导入
          </el-button>
        </div>
      </div>
    </div>

    <div class="content-grid">
      <div class="table-panel admin-panel">
        <div class="panel-header">
          <span>字典列表</span>
          <div class="panel-actions">
            <el-input v-model="query.keyword" placeholder="搜索字典名称/编码" clearable class="filter-input admin-input-theme">
              <template #prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
            <el-button type="primary" class="search-btn admin-btn-primary">搜索</el-button>
            <el-button class="reset-btn admin-btn-secondary" circle>
              <el-icon><Refresh /></el-icon>
            </el-button>
          </div>
        </div>

        <el-table
          :data="tableData"
          stripe
          height="100%"
          :header-cell-class-name="() => 'admin-table-header-cell'"
          class="dict-table admin-table-theme"
        >
          <el-table-column prop="name" label="字典名称" min-width="140" />
          <el-table-column prop="code" label="字典编码" min-width="140" />
          <el-table-column prop="status" label="状态" width="120">
            <template #default="{ row }">
              <el-tag :type="row.status === '启用' ? 'success' : 'info'" effect="plain" size="small">
                {{ row.status }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="updatedAt" label="更新时间" width="160" />
          <el-table-column fixed="right" label="操作" width="160">
            <template #default>
              <el-button link type="primary" size="small">编辑</el-button>
              <el-button link type="danger" size="small">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination-container">
          <el-pagination
            v-model:current-page="pagination.page"
            v-model:page-size="pagination.pageSize"
            :total="pagination.total"
            :page-sizes="[10, 20, 50]"
            layout="total, sizes, prev, pager, next, jumper"
            background
            class="admin-pagination-theme"
          />
        </div>
      </div>

      <div class="detail-panel admin-panel">
        <div class="panel-header">
          <span>字典值</span>
          <div class="panel-actions">
            <el-button type="primary" class="primary-btn admin-btn-primary" @click="handleAddValue">新增字典值</el-button>
          </div>
        </div>

        <div class="detail-card">
          <div class="detail-row">
            <span class="detail-label">字典名称</span>
            <span class="detail-value">用户状态</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">字典编码</span>
            <span class="detail-value">user_status</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">备注</span>
            <span class="detail-value">用于控制用户账号状态显示</span>
          </div>
        </div>

        <div class="value-list">
          <div v-for="item in dictValues" :key="item.id" class="value-item">
            <div class="value-left">
              <span class="value-name">{{ item.label }}</span>
              <span class="value-code">{{ item.value }}</span>
            </div>
            <div class="value-right">
              <el-tag :type="item.status === '启用' ? 'success' : 'info'" effect="plain" size="small">
                {{ item.status }}
              </el-tag>
              <div class="value-actions">
                <el-button text size="small" @click="handleEditValue(item)">编辑</el-button>
                <el-button text size="small">禁用</el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <DictionaryDrawer v-model="drawerVisible" :data="currentDictionary" @success="handleSuccess" />
    <DictionaryValueDialog v-model="valueDialogVisible" :data="currentValue" @success="handleValueSuccess" />
  </div>
</template>

<script setup lang="ts">
import { useDictionaryPage } from '@/composables/useDictionaryPage'
import { Search, Refresh, Plus, Upload } from '@element-plus/icons-vue'
import DictionaryDrawer from './DictionaryDrawer.vue'
import DictionaryValueDialog from './DictionaryValueDialog.vue'

const {
  query,
  pagination,
  tableData,
  dictValues,
  drawerVisible,
  currentDictionary,
  valueDialogVisible,
  currentValue,
  handleAdd,
  handleSuccess,
  handleAddValue,
  handleEditValue,
  handleValueSuccess,
} = useDictionaryPage()
</script>

<style scoped lang="scss">
.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;

  h2 {
    margin: 0 0 4px;
    font-size: 20px;
    color: var(--wb-color-text-primary);
  }

  p {
    margin: 0;
    font-size: 13px;
    color: var(--wb-color-text-secondary);
  }
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-actions {
  display: flex;
  gap: 10px;
}

.content-grid {
  display: grid;
  grid-template-columns: 1.3fr 0.9fr;
  gap: 16px;
  flex: 1;
  min-height: 0;
}

.table-panel,
.detail-panel {
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-height: 0;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  color: var(--wb-color-text-primary);
}

.panel-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.filter-input {
  width: 220px;
}

.dict-table {
  flex: 1;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}

.detail-card {
  background: var(--wb-color-page-bg-soft);
  border: 1px solid var(--wb-color-border-soft);
  border-radius: 12px;
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.detail-row {
  display: grid;
  grid-template-columns: 80px 1fr;
  gap: 12px;
  font-size: 13px;
  color: var(--wb-color-text-regular);
}

.detail-label {
  color: var(--wb-color-text-secondary);
}

.value-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.value-item {
  display: flex;
  justify-content: space-between;
  gap: 12px;
  padding: 12px;
  border-radius: 12px;
  border: 1px solid var(--wb-color-border-soft);
  background: var(--wb-color-page-bg-soft);
  transition: var(--wb-transition-base);

  &:hover {
    border-color: #e2e8f0;
    box-shadow: 0 6px 14px rgba(15, 23, 42, 0.06);
  }
}

.value-left {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.value-name {
  font-size: 13px;
  color: var(--wb-color-text-primary);
  font-weight: 600;
}

.value-code {
  font-size: 12px;
  color: var(--wb-color-text-secondary);
}

.value-right {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  gap: 8px;
}

.value-actions {
  display: flex;
  gap: 6px;
}

@media (max-width: 1024px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
