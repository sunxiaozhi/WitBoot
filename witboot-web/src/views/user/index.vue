<template>
  <div class="user-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-input
        v-model="queryForm.keyword"
        placeholder="请输入用户名、姓名"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch" :loading="searchLoading">
        <el-icon><Search /></el-icon>
        搜索
      </el-button>
      <el-button @click="handleReset" :disabled="!queryForm.keyword">
        <el-icon><Refresh /></el-icon>
        重置
      </el-button>
    </div>

    <!-- 操作区 -->
    <div class="option-container">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        新增
      </el-button>
      <el-button :disabled="selectedIds.length === 0" @click="handleBatchDelete">
        <el-icon><Delete /></el-icon>
        批量删除
      </el-button>
    </div>

    <!-- 表格 -->
    <div class="table-wrapper">
      <el-table
        :data="tableData"
        row-key="id"
        border
        :loading="tableLoading"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="username" label="用户名" min-width="120">
          <template #default="{ row }">
            <el-tag type="primary">{{ row.username }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" min-width="100" />
        <el-table-column prop="mobile" label="手机号" min-width="120" />
        <el-table-column prop="gender" label="性别" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.gender === '1' ? 'primary' : 'danger'">
              {{ row.gender === '1' ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="birthday" label="生日" width="120" />
        <el-table-column prop="description" label="描述" min-width="150" show-overflow-tooltip />
        <el-table-column fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleEdit(row)"> 编辑 </el-button>
            <el-button link type="danger" size="small" @click="handleDelete(row)"> 删除 </el-button>
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
        layout="total, prev, pager, next, sizes, jumper"
        @current-change="handleCurrentPageChange"
        @size-change="handlePageSizeChange"
      />
    </div>

    <!-- 抽屉 -->
    <UserDrawer v-model="drawerVisible" :data="currentUser" @success="fetchData" />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { selectUserList, deleteUser } from '@/api/user'
import UserDrawer from './UserDrawer.vue'
import { Search, Refresh, Plus, Delete } from '@element-plus/icons-vue'

interface User {
  id: number
  username: string
  name: string
  mobile: string
  gender: '1' | '2'
  birthday: string
  description: string
}

const queryForm = reactive({ keyword: '' })
const pagination = reactive({ currentPage: 1, pageSize: 10, total: 0 })

const tableData = ref<User[]>([])
const tableLoading = ref(false)
const searchLoading = ref(false)

const selectedIds = ref<number[]>([])
const drawerVisible = ref(false)
const currentUser = ref<Partial<User> | null>(null)

const fetchData = async () => {
  tableLoading.value = true
  const res = await selectUserList({
    pageNo: pagination.currentPage,
    pageSize: pagination.pageSize,
    keyword: queryForm.keyword
  })
  tableData.value = res.data.list
  pagination.total = res.data.total
  tableLoading.value = false
}

const handleSearch = () => {
  pagination.currentPage = 1
  fetchData()
}

const handleReset = () => {
  queryForm.keyword = ''
  fetchData()
}

const handleAdd = () => {
  currentUser.value = null
  drawerVisible.value = true
}

const handleEdit = (row: User) => {
  currentUser.value = { ...row }
  drawerVisible.value = true
}

const handleSelectionChange = (rows: User[]) => {
  selectedIds.value = rows.map((r) => r.id)
}

const handleDelete = (row: User) => {
  ElMessageBox.confirm(`确认删除 ${row.name}？`, '提示').then(async () => {
    await deleteUser({ ids: [row.id] })
    ElMessage.success('删除成功')
    fetchData()
  })
}

const handleBatchDelete = () => {
  ElMessageBox.confirm('确认批量删除？', '提示').then(async () => {
    await deleteUser({ ids: selectedIds.value })
    ElMessage.success('删除成功')
    fetchData()
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

onMounted(fetchData)
</script>

<style scoped>
.user-container {
  height: 100%;
  display: flex;
  flex-direction: column;
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
  min-height: 0;
  overflow: auto;
}

.user-table {
  width: 100%;
}

.action-button {
  padding: 4px 8px;
}

.pagination-container {
  flex: 0 0 auto;
  display: flex;
  justify-content: flex-end;
  margin: 16px 0;
}
</style>
