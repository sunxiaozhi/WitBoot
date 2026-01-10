<template>
  <div class="login-log-container">
    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
        v-model="queryForm.keyword"
        placeholder="请输入IP"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
        maxlength="30"
      >
        <template #prepend>查询词</template>
      </el-input>

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

    <!-- 登录按钮区域 -->
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
        style="width: 100%"
        element-loading-text="数据加载中..."
        @selection-change="handleSelectionChange"
        v-loading="tableLoading"
        class="login-log-table"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="ip" label="IP" />
        <el-table-column prop="userName" label="用户名" />
        <el-table-column prop="location" label="地址" />
        <el-table-column prop="userAgent" label="User-Agent" />
        <el-table-column prop="os" label="操作系统" />
        <el-table-column prop="device" label="设备类型" />
        <el-table-column prop="browser" label="浏览器" />
        <el-table-column prop="loginTime" label="登录时间" />
        <el-table-column fixed="right" label="登录" width="150" align="center">
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
    <el-drawer v-model="dialog" title="登录日志" direction="rtl" size="40%" class="user-drawer">
      <div class="drawer__content">
        <el-descriptions :title="`详情`" direction="vertical" :column="1" border>
          <el-descriptions-item label="IP" :span="1">{{
            currentRow?.ip || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="用户名" :span="1">{{
            currentRow?.userName || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="地址" :span="1">{{
            currentRow?.location || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="User-Agent" :span="1">{{
            currentRow?.userAgent || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="操作系统" :span="1">{{
            currentRow?.os || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="设备类型" :span="1">{{
            currentRow?.device || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="浏览器" :span="1">{{
            currentRow?.browser || '-'
          }}</el-descriptions-item>
          <el-descriptions-item label="登录时间" :span="1">{{
            currentRow?.loginTime || '-'
          }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, onUnmounted } from 'vue'
import { selectLoginLogList, loginLogInfo, deleteLoginLog } from '@/api/loginLog.ts'
import { debounce } from 'lodash-es'
import { Delete, Document, Search, Refresh } from '@element-plus/icons-vue'
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
  method: ''
})

const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// -------------------- 方法 --------------------

// 选择框选中行登录
const handleSelectionChange = (selection: LoginLog[]) => {
  selectedRows.value = selection
  selectedIds.value = selection.map((item) => item.id)
}

// 搜索触发
const fetchData = async () => {
  tableLoading.value = true
  try {
    const res = await selectLoginLogList({
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
    ElMessage.warning('请先选择要删除的登录日志')
    return
  }

  ElMessageBox.confirm(
    `确定要删除选中的${selectedIds.value.length}条登录日志吗？此登录不可恢复！`,
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

.login-log-table {
  width: 100%;
}

.pagination-container {
  flex: 0 0 auto;
  display: flex;
  justify-content: flex-end;
  margin: 16px 0;
}
</style>
