<template>
  <el-card>
    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入ip"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
    </div>

    <!-- 列表表格 -->
    <el-table :data="tableData" border style="width: 100%" :loading="loading">
      <el-table-column prop="ip" label="ip" />
      <el-table-column prop="location" label="地址" />
      <el-table-column prop="method" label="请求方法" />
      <el-table-column prop="uri" label="URI" />
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 30]"
        layout="total, prev, pager, next, sizes, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 16px; text-align: right"
      />
    </div>
  </el-card>
</template>

<script setup>
import { ref, reactive, watch, onMounted } from 'vue'
import { selectOperationLogList } from '@/api/operationLog.ts'
import { debounce } from 'lodash-es'

// 搜索关键词
const searchKeyword = ref('')

// 表格数据
const tableData = ref([])

// 加载状态
const loading = ref(false)

// 分页状态
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const res = await selectOperationLogList({
      pageNo: pagination.currentPage,
      pageSize: pagination.pageSize
    })
    tableData.value = res.data.list
    pagination.total = res.data.total
  } catch (error) {
    console.error('Failed to fetch data:', error)
  } finally {
    loading.value = false
  }
}

// 防抖搜索
const debouncedSearch = debounce(() => {
  fetchData()
}, 300)

// 搜索触发
const handleSearch = () => {
  pagination.currentPage = 1
  debouncedSearch()
}

// 页面大小变化
const handleSizeChange = (size) => {
  pagination.pageSize = size
  fetchData()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  pagination.currentPage = page
  fetchData()
}

// 初始化加载
onMounted(async () => {
  await fetchData()
})

// 监听分页参数变化
watch(
  () => [pagination.currentPage, pagination.pageSize],
  () => {
    fetchData()
  }
)
</script>

<style scoped>
.search-container {
  margin-bottom: 16px;
  display: flex;
  gap: 8px;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}

.search-input {
  width: 200px;
}
</style>
