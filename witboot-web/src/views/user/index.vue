<template>
  <div class="user-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <div class="search-bar">
        <el-input
          v-model="queryForm.keyword"
          placeholder="搜索用户名、姓名"
          clearable
          class="search-input"
          @keyup.enter="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
        <div class="search-actions">
          <el-button
            type="primary"
            @click="handleSearch"
            :loading="searchLoading"
            class="search-button"
          >
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button
            @click="handleReset"
            :disabled="!queryForm.keyword"
            class="reset-button"
            circle
          >
            <el-icon><Refresh /></el-icon>
          </el-button>
        </div>
      </div>
    </div>

    <!-- 操作区 -->
    <div class="option-container">
      <el-button type="primary" @click="handleAdd" class="add-button">
        <el-icon><Plus /></el-icon>
        新增用户
      </el-button>
      <el-button :disabled="selectedIds.length === 0" @click="handleBatchDelete" class="delete-button">
        <el-icon><Delete /></el-icon>
        批量删除
      </el-button>
    </div>

    <!-- 表格 -->
    <div class="table-wrapper">
      <el-table
        :data="tableData"
        row-key="id"
        :header-cell-style="{ background: '#f8fafc', color: '#374151' }"
        :row-style="{ color: '#606266' }"
        height="100%"
        stripe
        :loading="tableLoading"
        @selection-change="handleSelectionChange"
        class="user-table"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="username" label="用户名" min-width="120">
          <template #default="{ row }">
            <el-tag type="primary" effect="plain" class="username-tag">{{ row.username }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" min-width="100" />
        <el-table-column prop="mobile" label="手机号" min-width="120" />
        <el-table-column prop="gender" label="性别" width="80" align="center">
          <template #default="{ row }">
            <el-tag :type="row.gender === '1' ? 'primary' : 'danger'" effect="plain" size="small">
              {{ row.gender === '1' ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="birthday" label="生日" width="120" />
        <el-table-column prop="description" label="描述" min-width="150" show-overflow-tooltip />
        <el-table-column fixed="right" label="操作" width="150">
          <template #default="{ row }">
            <el-button link type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button link type="danger" size="small" @click="handleDelete(row)">
              删除
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
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        background
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
    keyword: queryForm.keyword,
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
  selectedIds.value = rows.map(r => r.id)
}

const handleDelete = (row: User) => {
  ElMessageBox.confirm(`确认删除 ${row.name}？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    await deleteUser({ ids: [row.id] })
    ElMessage.success('删除成功')
    fetchData()
  })
}

const handleBatchDelete = () => {
  ElMessageBox.confirm('确认批量删除？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
  }).then(async () => {
    await deleteUser({ ids: selectedIds.value })
    ElMessage.success('删除成功')
    fetchData()
  })
}

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

<style scoped lang="scss">
.user-container {
  height: 100%;
  display: flex;
  flex-direction: column;
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
    grid-template-columns: minmax(220px, 320px) auto;
    align-items: center;
    gap: 12px;
  }

  .search-input {
    width: 100%;

    :deep(.el-input__wrapper) {
      border-radius: 8px;
      box-shadow: none;
      border: 1px solid #e5e7eb;
      transition: border-color 0.3s;

      &:hover {
        border-color: #cbd5f5;
      }

      &.is-focus {
        border-color: #667eea;
      }

      .el-input__inner {
        &::placeholder {
          color: #999;
        }
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

  .add-button {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;
    padding: 8px 16px;
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

  .user-table {
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

    .username-tag {
      background: rgba(102, 126, 234, 0.1) !important;
      border-color: rgba(102, 126, 234, 0.2) !important;
      color: #667eea !important;
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
