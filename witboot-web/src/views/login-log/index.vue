<template>
  <div class="login-log-container">
    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
        v-model="queryForm.keyword"
        placeholder="搜索 IP 地址"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon><Search /></el-icon>
        </template>
      </el-input>
      <el-select
        v-model="queryForm.method"
        placeholder="请求方法"
        clearable
        class="method-select"
      >
        <el-option v-for="item in METHOD_OPTIONS" :key="item" :label="item" :value="item" />
      </el-select>
      <el-button type="primary" @click="handleSearch" :loading="tableLoading" class="search-button">
        搜索
      </el-button>
      <el-button @click="handleReset" :disabled="!queryForm.keyword && !queryForm.method" class="reset-button">
        <el-icon><Refresh /></el-icon>
      </el-button>
    </div>

    <!-- 登录按钮区域 -->
    <div class="option-container">
      <el-button :disabled="selectedIds.length === 0" @click="handleBatchDelete" class="delete-button">
        <el-icon><Delete /></el-icon>
        批量删除
      </el-button>
    </div>

    <!-- 表格 -->
    <div class="table-wrapper">
      <el-table
        ref="multipleTableRef"
        :data="tableData"
        row-key="id"
        :header-cell-style="{ background: '#f8fafc', color: '#374151' }"
        :row-style="{ color: '#606266' }"
        stripe
        style="width: 100%"
        element-loading-text="数据加载中..."
        @selection-change="handleSelectionChange"
        v-loading="tableLoading"
        class="login-log-table"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="ip" label="IP 地址" min-width="130" />
        <el-table-column prop="userName" label="用户名" min-width="120" />
        <el-table-column prop="location" label="地址" min-width="150" show-overflow-tooltip />
        <el-table-column prop="userAgent" label="User-Agent" min-width="180" show-overflow-tooltip />
        <el-table-column prop="os" label="操作系统" width="120" />
        <el-table-column prop="device" label="设备类型" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getDeviceType(row.device)" size="small" effect="plain">
              {{ row.device }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="browser" label="浏览器" width="120" />
        <el-table-column prop="loginTime" label="登录时间" width="160" />
        <el-table-column fixed="right" label="操作" width="120" align="center">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click.prevent="handleDetail(row)">
              详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="PAGE_SIZES"
        layout="total, sizes, prev, pager, next, jumper"
        background
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentPageChange"
      />
    </div>

    <!-- 详情抽屉 -->
    <el-drawer v-model="dialog" title="登录日志详情" direction="rtl" size="40%" class="detail-drawer">
      <div class="drawer-content">
        <el-descriptions title="详细信息" direction="vertical" :column="1" border class="detail-descriptions">
          <el-descriptions-item label="IP 地址" :span="1">
            {{ currentRow?.ip || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="用户名" :span="1">
            {{ currentRow?.userName || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="地址" :span="1">
            {{ currentRow?.location || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="User-Agent" :span="1">
            <el-scrollbar height="80px">
              <pre class="user-agent-code">{{ currentRow?.userAgent || '-' }}</pre>
            </el-scrollbar>
          </el-descriptions-item>
          <el-descriptions-item label="操作系统" :span="1">
            {{ currentRow?.os || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="设备类型" :span="1">
            {{ currentRow?.device || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="浏览器" :span="1">
            {{ currentRow?.browser || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="登录时间" :span="1">
            {{ currentRow?.loginTime || '-' }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { selectLoginLogList, loginLogInfo, deleteLoginLog } from '@/api/loginLog.ts'
import { debounce } from 'lodash-es'
import { Delete, Search, Refresh } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

interface LoginLog {
  id: number
  ip: string
  userName: string
  location: string
  userAgent: string
  os: string
  device: string
  browser: string
  loginTime: string
}

// 常量
const METHOD_OPTIONS = ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'HEAD', 'OPTIONS']
const PAGE_SIZES = [10, 20, 30]

// 表格 & 状态
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const tableData = ref<LoginLog[]>([])
const selectedRows = ref<LoginLog[]>([])
const selectedIds = ref<number[]>([])
const tableLoading = ref(false)
const dialog = ref(false)
const currentRow = ref<LoginLog | null>(null)

// 搜索表单 & 分页
const queryForm = reactive({
  keyword: '',
  method: '',
})

const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0,
})

// 选择框选中行操作
const handleSelectionChange = (selection: LoginLog[]) => {
  selectedRows.value = selection
  selectedIds.value = selection.map(item => item.id)
}

// 获取设备类型
const getDeviceType = (device: string) => {
  const typeMap: Record<string, string> = {
    PC: 'primary',
    Mobile: 'warning',
    Tablet: 'success',
  }
  return typeMap[device] || 'info'
}

// 搜索触发
const fetchData = async () => {
  tableLoading.value = true
  try {
    const res = await selectLoginLogList({
      pageNo: pagination.currentPage,
      pageSize: pagination.pageSize,
      searchKeyword: queryForm.keyword,
      method: queryForm.method,
    })
    tableData.value = res.data.list
    pagination.total = res.data.total
  } catch (error) {
    console.error('Failed to fetch data:', error)
    ElMessage.error('数据获取失败')
  } finally {
    tableLoading.value = false
  }
}

// 防抖搜索
const debouncedSearch = debounce(fetchData, 300)

const handleSearch = () => {
  pagination.currentPage = 1
  debouncedSearch()
}

const handleReset = () => {
  queryForm.keyword = ''
  queryForm.method = ''
  pagination.currentPage = 1
  fetchData()
}

// 批量删除
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请先选择要删除的登录日志')
    return
  }

  ElMessageBox.confirm(
    `确定要删除选中的${selectedIds.value.length}条登录日志吗？此操作不可恢复！`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'error',
      draggable: true,
    },
  )
    .then(async () => {
      try {
        const res = await deleteLoginLog({ ids: selectedIds.value })
        if (res.code === 200) {
          ElMessage.success('批量删除成功')
          await fetchData()
        } else {
          ElMessage.error(res.message || '批量删除失败')
        }
      } catch (error) {
        console.error('Batch delete error:', error)
        ElMessage.error('批量删除失败')
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

// 分页事件
const handlePageSizeChange = (size: number) => {
  pagination.pageSize = size
  fetchData()
}

const handleCurrentPageChange = (page: number) => {
  pagination.currentPage = page
  fetchData()
}

// 详情
const handleDetail = async (row: LoginLog) => {
  try {
    const res = await loginLogInfo(row.id)
    currentRow.value = res.data
    dialog.value = true
  } catch (error) {
    console.error('Failed to fetch detail:', error)
    ElMessage.error('获取详情失败')
  }
}

// 初始化
onMounted(() => fetchData())
onUnmounted(() => debouncedSearch.cancel())
</script>

<style scoped lang="scss">
.login-log-container {
  display: flex;
  flex-direction: column;
  padding: 20px;
  height: 100%;
  background: #f5f7fa;
}

.search-container {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);

  .search-input {
    flex: 1;
    max-width: 320px;

    :deep(.el-input__wrapper) {
      border-radius: 8px;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
      transition: all 0.3s;

      &:hover {
        box-shadow: 0 1px 6px rgba(102, 126, 234, 0.15);
      }

      &.is-focus {
        box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
      }
    }
  }

  .method-select {
    max-width: 140px;

    :deep(.el-input__wrapper) {
      border-radius: 8px;
      box-shadow: 0 1px 3px rgba(0, 0, 0, 0.08);
      transition: all 0.3s;

      &:hover {
        box-shadow: 0 1px 6px rgba(102, 126, 234, 0.15);
      }

      &.is-focus {
        box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.2);
      }
    }
  }

  .search-button {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    padding: 10px 24px;
    font-weight: 500;
    transition: all 0.3s;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
    }

    &:active {
      transform: translateY(0);
    }
  }

  .reset-button {
    border: 1px solid #dcdfe6;
    color: #606266;
    background: #fff;
    transition: all 0.3s;

    &:hover {
      color: #667eea;
      border-color: #667eea;
      background: rgba(102, 126, 234, 0.05);
    }

    &:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
}

.option-container {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 20px;

  .delete-button {
    border: 1px solid #f56c6c;
    color: #f56c6c;
    background: #fff;
    padding: 10px 20px;
    font-weight: 500;
    transition: all 0.3s;

    &:hover {
      background: #fef0f0;
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(245, 108, 108, 0.2);
    }

    &:active {
      transform: translateY(0);
    }

    &:disabled {
      opacity: 0.5;
      cursor: not-allowed;
    }
  }
}

.table-wrapper {
  flex: 1;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
  overflow: hidden;

  .login-log-table {
    :deep(.el-table__header-wrapper) {
      border-radius: 12px 12px 0 0;
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
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
  padding: 20px;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);

  :deep(.el-pagination) {
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
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: #fff;
    }
  }
}

.detail-drawer {
  :deep(.el-drawer__header) {
    margin-bottom: 20px;
    padding: 24px;
    border-bottom: 1px solid #f0f0f0;

    .el-drawer__title {
      font-size: 20px;
      font-weight: 600;
      color: #333;
    }
  }

  :deep(.el-drawer__body) {
    padding: 0;
  }
}

.drawer-content {
  padding: 24px;
  height: 100%;
  overflow-y: auto;

  .detail-descriptions {
    :deep(.el-descriptions__label) {
      font-weight: 500;
      color: #606266;
    }

    .user-agent-code {
      background: #f8fafc;
      padding: 12px;
      border-radius: 8px;
      font-size: 13px;
      line-height: 1.6;
      color: #374151;
      margin: 0;
      white-space: pre-wrap;
      word-break: break-word;
      font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
    }
  }
}
</style>
