<template>
  <div class="operation-log-container">
    <!-- 搜索框 -->
    <div class="search-container">
      <el-input v-model="searchKeyword" placeholder="请输入IP" clearable class="search-input"
        @keyup.enter="handleSearch" />
      <el-button type="primary" @click="handleSearch" :loading="searchLoading">
        <el-icon>
          <Search />
        </el-icon>
        搜索
      </el-button>
    </div>

    <!-- 操作按钮区域 -->
    <div class="option-container">
      <el-button type="danger" :disabled="selectedIds.length === 0" @click="handleBatchDelete">
        <el-icon>
          <Delete />
        </el-icon>
        删除
      </el-button>
    </div>

    <!-- 列表表格 -->
    <div class="table-wrapper">
      <el-table ref="multipleTableRef" :data="tableData" row-key="id" border style="width: 100%"
        element-loading-text="数据加载中..." @selection-change="handleSelectionChange" :loading="tableLoading"
        class="operation-log-table">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="ip" label="IP" />
        <el-table-column prop="location" label="地址" />
        <el-table-column prop="method" label="请求方法" />
        <el-table-column prop="uri" label="URI" />
        <el-table-column prop="requestTime" label="请求时间" />
        <el-table-column prop="wasteTime" label="耗时(ms)" />
        <el-table-column fixed="right" label="操作" width="150" align="center">
          <template #default="scope">
            <el-button link type="primary" size="small" @click.prevent="handleDetail(scope.row)" class="action-button">
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
      <el-pagination v-model:current-page="pagination.currentPage" v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 30]" layout="total, prev, pager, next, sizes, jumper" :total="pagination.total"
        @size-change="handlePageSizeChange" @current-change="handleCurrentPageChange"
        style="margin-top: 16px; text-align: right" />
    </div>

    <!-- 详情抽屉 -->
    <el-drawer v-model="dialog" title="操作日志" direction="rtl" size="40%" class="user-drawer">
      <div class="drawer__content">
        <el-descriptions :title="`详情`" direction="vertical" :column="1" border>
          <el-descriptions-item label="IP" :span="1">{{ currentRow?.ip || '-' }}</el-descriptions-item>
          <el-descriptions-item label="地址" :span="1">{{ currentRow?.location || '-' }}</el-descriptions-item>
          <el-descriptions-item label="请求方法" :span="1">{{ currentRow?.method || '-' }}</el-descriptions-item>
          <el-descriptions-item label="URI" :span="1">{{ currentRow?.uri || '-' }}</el-descriptions-item>
          <el-descriptions-item label="请求时间" :span="1">{{ currentRow?.requestTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="耗时(ms)" :span="1">{{ currentRow?.wasteTime || '-' }}</el-descriptions-item>
          <el-descriptions-item label="请求参数" :span="1">
            <el-scrollbar height="80px">
              <pre style="white-space: pre-wrap; word-break: break-word;">{{ currentRow?.requestParam || '-' }}</pre>
            </el-scrollbar>
          </el-descriptions-item>
          <el-descriptions-item label="请求体" :span="1">
            <el-scrollbar height="80px">
              <pre style="white-space: pre-wrap; word-break: break-word;">{{ currentRow?.requestBody || '-' }}</pre>
            </el-scrollbar>
          </el-descriptions-item>
          <el-descriptions-item label="响应结果" :span="1">
            <el-scrollbar height="300px">
              <pre style="white-space: pre-wrap; word-break: break-word;">{{ currentRow?.responseResult || '-' }}</pre>
            </el-scrollbar>
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </el-drawer>
  </div>

</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { selectOperationLogList, operationLogInfo, deleteOperationLog } from '@/api/operationLog.ts'
import { debounce } from 'lodash-es'
import { Delete, Document, Search } from '@element-plus/icons-vue'
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

const selectedRows = ref<OperationLog[]>([])
const selectedIds = ref<number[]>([])
const searchLoading = ref(false)
const tableLoading = ref(false)
const dialog = ref(false)
const currentRow = ref<OperationLog | null>(null)
const searchKeyword = ref('')
const tableData = ref([])
const loading = ref(false)
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 选择框选中行操作
const handleSelectionChange = (selection: OperationLog[]) => {
  selectedRows.value = selection
  selectedIds.value = selection.map((item) => item.id)
}

// 搜索触发
const handleSearch = () => {
  pagination.currentPage = 1
  debouncedSearch()
}

// 防抖搜索
const debouncedSearch = debounce(() => {
  fetchData()
}, 300)

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const res = await selectOperationLogList({
      pageNo: pagination.currentPage,
      pageSize: pagination.pageSize,
      searchKeyword: searchKeyword.value,
    })

    tableData.value = res.data.list
    pagination.total = res.data.total
  } catch (error) {
    console.error('Failed to fetch data:', error)
  } finally {
    loading.value = false
  }
}

// 批量删除
const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请先选择要删除的用户')
    return
  }

  ElMessageBox.confirm(
    `确定要删除选中的${selectedIds.value.length}个操作日志吗？此操作不可恢复！`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'error',
      draggable: true
    }
  ).then(async () => {
    try {
      // 调用删除API，传入ID数组
      const res = await deleteOperationLog({ ids: selectedIds.value })
      if (res.code === 200) {
        ElMessage.success('批量删除成功')
        await fetchData()
      } else {
        ElMessage.error(res.message || '批量删除失败')
      }
    } catch (error) {
      ElMessage.error('批量删除失败')
      console.error('Batch delete error:', error)
    }
  })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

// 页面条数改变时触发，重新获取数据变化
const handlePageSizeChange = (size) => {
  pagination.pageSize = size
  fetchData()
}

// 当前页码改变时触发，重新获取数据
const handleCurrentPageChange = (page) => {
  pagination.currentPage = page
  fetchData()
}

// 详情
const handleDetail = async (row) => {
  try {
    const res = await operationLogInfo(row.id)

    // 将获取的详情数据赋值给 currentRow
    currentRow.value = res.data

    // 显示抽屉
    dialog.value = true
  } catch (error) {
    console.error('Failed to fetch data:', error)
  }
}

// 初始化加载
onMounted(async () => {
  await fetchData()
})
</script>

<style scoped>
.operation-log-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.search-container {
  flex: 0 0 auto;
  margin-bottom: 16px;
  display: flex;
  gap: 8px;

  .search-input {
    flex: 1;
    max-width: 300px;
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
  margin-bottom: 16px;
}
</style>
