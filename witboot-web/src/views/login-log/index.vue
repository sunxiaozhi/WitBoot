<template>
  <div class="login-log-container">
    <!-- 搜索框 -->
    <div class="search-container">
      <div class="search-bar">
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
        <div class="search-actions">
          <el-button
            type="primary"
            @click="handleSearch"
            :loading="tableLoading"
            class="search-button"
          >
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button
            @click="handleReset"
            :disabled="!queryForm.keyword && !queryForm.method"
            class="reset-button"
            circle
          >
            <el-icon><Refresh /></el-icon>
          </el-button>
        </div>
      </div>
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
    <LoginLogDetail v-model="dialog" :data="currentRow" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { selectLoginLogList, loginLogInfo, deleteLoginLog } from '@/api/loginLog.ts'
import { debounce } from 'lodash-es'
import { Delete, Search, Refresh } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import LoginLogDetail from './LoginLogDetail.vue'

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
  height: 100%;
  padding: 16px;
  gap: 12px;
  background: #f5f7fa;

  --card-radius: 10px;
  --card-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
  --hover-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
  --button-transition: all 0.3s;
}

.search-container {
  padding: 12px 16px;
  background: #fff;
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);

  .search-bar {
    display: grid;
    grid-template-columns: minmax(220px, 320px) minmax(140px, 180px) auto;
    align-items: center;
    gap: 12px;
  }

  .search-input {
    width: 100%;

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

  .method-select {
    width: 100%;

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

  .search-actions {
    display: flex;
    align-items: center;
    gap: 8px;
    justify-self: start;
  }

  .search-button {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    min-width: 56px;
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

  .reset-button {
    border: 1px solid #dcdfe6;
    color: #606266;
    background: #fff;
    transition: var(--button-transition);

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
  padding: 0 4px;

  .delete-button {
    border: 1px solid #f56c6c;
    color: #f56c6c;
    background: #fff;
    padding: 8px 16px;
    font-weight: 500;
    transition: var(--button-transition);

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
  min-height: 0;
  background: #fff;
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
  overflow: auto;

  .login-log-table {
    height: 100%;

    :deep(.el-table__cell) {
      padding: 8px 0;
    }

    :deep(.el-table__body tr) {
      height: 40px;
    }

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
  padding: 10px 16px;
  background: #fff;
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);

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
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: #fff;
    }
  }
}

</style>
