<template>
  <div class="user-container admin-page">
    <!-- 搜索区域 -->
    <div class="search-container admin-search-card">
      <div class="search-bar">
        <el-input
          v-model="queryForm.keyword"
          placeholder="搜索用户名、姓名"
          clearable
          class="search-input admin-input-theme"
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
            class="search-button admin-btn-primary"
          >
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button
            @click="handleReset"
            :disabled="!queryForm.keyword"
            class="reset-button admin-btn-secondary"
            circle
          >
            <el-icon><Refresh /></el-icon>
          </el-button>
        </div>
      </div>
    </div>

    <!-- 操作区 -->
    <div class="option-container admin-toolbar">
      <el-button type="primary" @click="handleAdd" class="add-button admin-btn-primary">
        <el-icon><Plus /></el-icon>
        新增用户
      </el-button>
      <el-button :disabled="selectedIds.length === 0" @click="handleBatchDelete" class="delete-button admin-btn-danger">
        <el-icon><Delete /></el-icon>
        批量删除
      </el-button>
    </div>

    <!-- 表格 -->
    <div class="table-wrapper admin-table-card">
      <el-table
        :data="tableData"
        row-key="id"
        :header-cell-class-name="() => 'admin-table-header-cell'"
        height="100%"
        stripe
        :loading="tableLoading"
        @selection-change="handleSelectionChange"
        class="user-table admin-table-theme"
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
    <div class="pagination-container admin-pagination-card">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        background
        class="admin-pagination-theme"
        @current-change="handleCurrentPageChange"
        @size-change="handlePageSizeChange"
      />
    </div>

    <!-- 抽屉 -->
    <UserDrawer v-model="drawerVisible" :data="currentUser" @success="fetchData" />
  </div>
</template>

<script setup lang="ts">
import { useUserList } from '@/composables/useUserList'
import UserDrawer from './UserDrawer.vue'
import { Search, Refresh, Plus, Delete } from '@element-plus/icons-vue'

const {
  queryForm,
  pagination,
  tableData,
  tableLoading,
  searchLoading,
  selectedIds,
  drawerVisible,
  currentUser,
  fetchData,
  handleSearch,
  handleReset,
  handleAdd,
  handleEdit,
  handleSelectionChange,
  handleDelete,
  handleBatchDelete,
  handlePageSizeChange,
  handleCurrentPageChange,
} = useUserList()
</script>

<style scoped lang="scss">
.search-container {
  .search-bar {
    display: grid;
    grid-template-columns: minmax(220px, 320px) auto;
    align-items: center;
    gap: 12px;
  }

  .search-input {
    width: 100%;
  }

  .search-actions {
    display: flex;
    align-items: center;
    gap: 8px;
    justify-self: start;
  }

  .search-button {
    min-width: 56px;
    padding: 6px 12px;
  }
}

.option-container {
  .add-button {
    padding: 8px 16px;
  }

  .delete-button {
    padding: 8px 16px;
  }
}

.table-wrapper {
  .user-table {
    .username-tag {
      background: var(--wb-color-primary-tag-bg) !important;
      border-color: var(--wb-color-primary-tag-border) !important;
      color: var(--wb-color-primary) !important;
    }
  }
}
</style>
