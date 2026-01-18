<template>
  <div class="operation-log-container">
    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
        v-model="queryForm.keyword"
        placeholder="IP"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      />

      <el-select
        v-model="queryForm.method"
        placeholder="请选择请求方法"
        clearable
        class="method-select"
      >
        <el-option v-for="item in METHOD_OPTIONS" :key="item" :label="item" :value="item" />
      </el-select>

      <el-button type="primary" @click="handleSearch" :loading="tableLoading">
        <el-icon>
          <Search />
        </el-icon>
        搜索
      </el-button>

      <el-button @click="handleReset" :disabled="!queryForm.keyword && !queryForm.method">
        <el-icon><Refresh /></el-icon>
        重置
      </el-button>
    </div>

    <!-- 操作按钮区域 -->
    <div class="option-container">
      <el-button :disabled="selectedIds.length === 0" @click="handleBatchDelete">
        <el-icon>
          <Delete />
        </el-icon>
        批量删除
      </el-button>
    </div>

    <!-- 列表表格 -->
    <div class="table-wrapper">
      <el-table
        ref="multipleTableRef"
        :data="tableData"
        row-key="id"
        border
        stripe
        style="width: 100%"
        element-loading-text="数据加载中..."
        @selection-change="handleSelectionChange"
        v-loading="tableLoading"
        class="operation-log-table"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="ip" label="IP" />
        <el-table-column prop="location" label="地址" />
        <el-table-column prop="method" label="请求方法" />
        <el-table-column prop="uri" label="URI" />
        <el-table-column prop="requestTime" label="请求时间" />
        <el-table-column prop="wasteTime" label="耗时(ms)" />
        <el-table-column fixed="right" label="操作" width="150" align="center">
          <template #default="scope">
            <el-button
              link
              type="primary"
              size="small"
              @click.prevent="handleDetail(scope.row)"
              class="action-button"
            >
              <el-icon>
                <Document />
              </el-icon>
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
        :page-sizes="PAGE_SIZES"
        layout="total, prev, pager, next, sizes, jumper"
        :total="pagination.total"
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentPageChange"
      />
    </div>

    <!-- 详情抽屉 -->
    <el-drawer v-model="dialog" title="操作日志" direction="rtl" size="40%" class="user-drawer">
      <div class="drawer__content">
        <el-descriptions :title="`详情`" direction="vertical" :column="1" border>
          <el-descriptions-item label="IP" :span="1">{{
            currentRow?.ip || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="地址" :span="1">{{
            currentRow?.location || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="请求方法" :span="1">{{
            currentRow?.method || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="URI" :span="1">{{
            currentRow?.uri || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="请求时间" :span="1">{{
            currentRow?.requestTime || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="耗时(ms)" :span="1">{{
            currentRow?.wasteTime || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="请求参数" :span="1">
            <el-scrollbar height="80px">
              <pre style="white-space: pre-wrap; word-break: break-word">
            {{ currentRow?.requestParam || '-' }}
          </pre
              >
            </el-scrollbar>
          </el-descriptions-item>
          <el-descriptions-item label="请求体" :span="1">
            <el-scrollbar height="80px">
              <pre style="white-space: pre-wrap; word-break: break-word">
            {{ currentRow?.requestBody || '-' }}
          </pre
              >
            </el-scrollbar>
          </el-descriptions-item>
          <el-descriptions-item label="响应结果" :span="1">
            <el-scrollbar height="300px">
              <pre style="white-space: pre-wrap; word-break: break-word">
            {{ currentRow?.responseResult || '-' }}
          </pre
              >
            </el-scrollbar>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { selectOperationLogList, operationLogInfo, deleteOperationLog } from '@/api/operationLog.ts'
import { debounce } from 'lodash-es'
import { Delete, Document, Search, Refresh } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

interface OperationLog {
  id: number
  ip: string
  location: string
  method: string
  uri: string
  requestTime: string
  wasteTime: string
  requestParam: string
  requestBody: string
  responseResult: string
}

// 常量
const METHOD_OPTIONS = ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'HEAD', 'OPTIONS']
const PAGE_SIZES = [10, 20, 30]

// 表格 & 状态
const multipleTableRef = ref<InstanceType<typeof ElTable>>()
const tableData = ref<OperationLog[]>([])
const selectedRows = ref<OperationLog[]>([])
const selectedIds = ref<number[]>([])
const tableLoading = ref(false)
const dialog = ref(false)
const currentRow = ref<OperationLog | null>(null)

// 搜索表单 & 分页
const queryForm = reactive({
  keyword: '',
  method: ''
})

const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// -------------------- 方法 --------------------

// 选择框选中行操作
const handleSelectionChange = (selection: OperationLog[]) => {
  selectedRows.value = selection
  selectedIds.value = selection.map((item) => item.id)
}

// 搜索触发
const fetchData = async () => {
  tableLoading.value = true
  try {
    const res = await selectOperationLogList({
      pageNo: pagination.currentPage,
      pageSize: pagination.pageSize,
      searchKeyword: queryForm.keyword,
      method: queryForm.method
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
    ElMessage.warning('请先选择要删除的操作日志')
    return
  }

  ElMessageBox.confirm(
    `确定要删除选中的${selectedIds.value.length}条操作日志吗？此操作不可恢复！`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'error',
      draggable: true
    }
  )
    .then(async () => {
      try {
        const res = await deleteOperationLog({ ids: selectedIds.value })
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
const handleDetail = async (row: OperationLog) => {
  try {
    const res = await operationLogInfo(row.id)
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
.operation-log-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  min-height: 0;
}

.search-container {
  flex: 0 0 auto;
  margin-bottom: 16px;
  display: flex;
  gap: 8px;

  .search-input,
  .method-select {
    max-width: 200px;
  }
}

.option-container {
  flex: 0 0 auto;
  margin-bottom: 16px;
  display: flex;
  gap: 8px;
}

.table-wrapper {
  flex: 1;
  overflow: auto;
  min-height: 0;
}

.operation-log-table {
  width: 100%;
}

.pagination-container {
  flex: 0 0 auto;
  display: flex;
  justify-content: flex-end;
  margin: 16px 0;
}
</style>
