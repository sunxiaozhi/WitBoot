<template>
  <el-card>
    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入用户名、姓名"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
    </div>

    <div class="option-container">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button type="danger" disabled @click="handleAdd">批量删除</el-button>
    </div>

    <!-- 列表表格 -->
    <div class="table-container"></div>
    <el-table :data="tableData" border style="width: 100%" :loading="loading">
      <el-table-column type="selection" :selectable="selectable" width="55" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="mobile" label="手机号" />
      <el-table-column prop="gender" label="性别" />
      <el-table-column prop="birthday" label="生日" />
      <el-table-column prop="description" label="描述" />
      <el-table-column fixed="right" label="操作" min-width="120">
        <template #default="scope">
          <el-button
            link
            type="primary"
            size="small"
            @click.prevent="handleEdit(scope.$index)"
          >
            编辑
          </el-button>

          <el-button
            link
            type="primary"
            size="small"
            @click.prevent="handleDelete(scope.$index)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
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
import { selectUserList } from '@/api/user.ts'
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
    const res = await selectUserList({
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

// 新增触发
const handleAdd = () => {
  console.log('新增')
}

// 编辑触发
const handleEdit = () => {
  console.log('编辑')
}

// 删除、批量触发
const handleDelete = () => {
  console.log('删除')
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
.option-container {
  margin-bottom: 15px;
  display: flex;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}

.search-input {
  width: 200px;
}
</style>
