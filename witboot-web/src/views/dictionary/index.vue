<template>
  <div class="dictionary-container">
    <div class="page-header">
      <div class="header-left">
        <h2></h2>
        <div class="header-actions">
          <el-button type="primary" class="primary-btn" @click="handleAdd">
            <el-icon><Plus /></el-icon>
            新增字典
          </el-button>
          <el-button class="ghost-btn">
            <el-icon><Upload /></el-icon>
            导入
          </el-button>
        </div>
      </div>
    </div>

    <div class="content-grid">
      <div class="table-panel">
        <div class="panel-header">
          <span>字典列表</span>
          <div class="panel-actions">
            <el-input v-model="query.keyword" placeholder="搜索字典名称/编码" clearable class="filter-input">
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

        <el-table
          :data="tableData"
          stripe
          height="100%"
          :header-cell-style="{ background: '#f8fafc', color: '#374151' }"
          class="dict-table"
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
          />
        </div>
      </div>

      <div class="detail-panel">
        <div class="panel-header">
          <span>字典值</span>
          <div class="panel-actions">
            <el-button type="primary" class="primary-btn" @click="handleAddValue">新增字典值</el-button>
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
import { reactive, ref } from 'vue'
import { Search, Refresh, Plus, Upload } from '@element-plus/icons-vue'
import DictionaryDrawer from './DictionaryDrawer.vue'
import DictionaryValueDialog from './DictionaryValueDialog.vue'

const query = reactive({
  keyword: '',
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 86,
})

const tableData = [
  { id: 1, name: '用户状态', code: 'user_status', status: '启用', updatedAt: '2026-02-05' },
  { id: 2, name: '订单状态', code: 'order_status', status: '启用', updatedAt: '2026-02-05' },
  { id: 3, name: '渠道类型', code: 'channel_type', status: '停用', updatedAt: '2026-02-01' },
  { id: 4, name: '支付方式', code: 'pay_method', status: '启用', updatedAt: '2026-02-04' },
]

const dictValues = ref([
  { id: 1, label: '启用', value: '1', status: '启用' },
  { id: 2, label: '禁用', value: '0', status: '停用' },
  { id: 3, label: '冻结', value: '2', status: '启用' },
])

const drawerVisible = ref(false)
const currentDictionary = ref<null | { id?: number; name?: string; code?: string; status?: string; remark?: string }>(null)
const valueDialogVisible = ref(false)
const currentValue = ref<null | { id?: number; label?: string; value?: string; status?: '启用' | '停用' }>(null)

const handleAdd = () => {
  currentDictionary.value = null
  drawerVisible.value = true
}

const handleSuccess = () => {
  drawerVisible.value = false
}

const handleAddValue = () => {
  currentValue.value = null
  valueDialogVisible.value = true
}

const handleEditValue = (item: { id: number; label: string; value: string; status: string }) => {
  currentValue.value = { ...item, status: item.status === '启用' ? '启用' : '停用' }
  valueDialogVisible.value = true
}

const handleValueSuccess = (payload: { id?: number; label: string; value: string; status: '启用' | '停用' }) => {
  if (payload.id) {
    const index = dictValues.value.findIndex(item => item.id === payload.id)
    if (index >= 0) dictValues.value[index] = { ...dictValues.value[index], ...payload }
  } else {
    const nextId = Math.max(0, ...dictValues.value.map(item => item.id)) + 1
    dictValues.value.unshift({ ...payload, id: nextId })
  }
  valueDialogVisible.value = false
}
</script>

<style scoped lang="scss">
.dictionary-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 16px;
  gap: 12px;
  background: #f5f7fa;

  --card-radius: 10px;
  --card-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
  --hover-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  --button-transition: all 0.3s;
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

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-actions {
  display: flex;
  gap: 10px;

  .primary-btn {
    background: var(--primary-gradient);
    border: none;
    font-weight: 500;
    transition: var(--button-transition);

    &:hover {
      transform: translateY(-2px);
      box-shadow: var(--hover-shadow);
    }

    &:active {
      transform: translateY(0);
    }
  }

  .ghost-btn {
    border: 1px solid #e5e7eb;
    transition: var(--button-transition);

    &:hover {
      color: #667eea;
      border-color: #667eea;
      background: rgba(102, 126, 234, 0.05);
    }
  }
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
  background: #fff;
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
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
  color: #0f172a;
}

.panel-actions {
  display: flex;
  gap: 8px;
  align-items: center;
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

  :deep(.el-input__inner) {
    &::placeholder {
      color: #999;
    }
  }
}

.search-btn {
  background: var(--primary-gradient);
  border: none;
  padding: 6px 12px;
  font-weight: 500;
  transition: var(--button-transition);

  &:hover {
    transform: translateY(-2px);
    box-shadow: var(--hover-shadow);
  }

  &:active {
    transform: translateY(0);
  }
}

.reset-btn {
  border: 1px solid #dcdfe6;
  color: #606266;
  background: #fff;
  transition: var(--button-transition);

  &:hover {
    color: #667eea;
    border-color: #667eea;
    background: rgba(102, 126, 234, 0.05);
  }
}

.dict-table {
  flex: 1;

  :deep(.el-table__cell) {
    padding: 8px 0;
  }

  :deep(.el-table__body tr) {
    height: 40px;
  }

  :deep(.el-table__inner-wrapper) {
    &::before {
      display: none;
    }
  }

  :deep(.el-table__border-left-patch) {
    display: none;
  }
}

.pagination-container {
  display: flex;
  justify-content: flex-end;

  :deep(.el-pagination) {
    --el-pagination-button-height: 28px;
    --el-pagination-item-height: 28px;

    .btn-prev,
    .btn-next {
      border-radius: 8px;
      transition: all 0.3s;

      &:hover {
        color: #667eea;
      }
    }

    .el-pager li {
      border-radius: 8px;
      transition: all 0.3s;

      &:hover {
        color: #667eea;
      }
    }

    .el-pager li.is-active {
      background: var(--primary-gradient);
      color: #fff;
    }
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
  grid-template-columns: 80px 1fr;
  gap: 12px;
  font-size: 13px;
  color: #374151;
}

.detail-label {
  color: #6b7280;
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
  border: 1px solid #eef2f7;
  background: #f8fafc;
  transition: var(--button-transition);

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
  color: #111827;
  font-weight: 600;
}

.value-code {
  font-size: 12px;
  color: #6b7280;
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
