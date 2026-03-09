<template>
  <div class="login-log-container admin-page">
    <!-- 搜索框 -->
    <div class="search-container admin-search-card">
      <div class="search-bar">
        <el-input
          v-model="queryForm.keyword"
          placeholder="搜索 IP 地址"
          clearable
          class="search-input admin-input-theme"
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
          class="method-select admin-input-theme"
        >
          <el-option v-for="item in METHOD_OPTIONS" :key="item" :label="item" :value="item" />
        </el-select>
        <div class="search-actions">
          <el-button
            type="primary"
            @click="handleSearch"
            :loading="tableLoading"
            class="search-button admin-btn-primary"
          >
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button
            @click="handleReset"
            :disabled="!queryForm.keyword && !queryForm.method"
            class="reset-button admin-btn-secondary"
            circle
          >
            <el-icon><Refresh /></el-icon>
          </el-button>
        </div>
      </div>
    </div>

    <!-- 登录按钮区域 -->
    <div class="option-container admin-toolbar">
      <el-button :disabled="selectedIds.length === 0" @click="handleBatchDelete" class="delete-button admin-btn-danger">
        <el-icon><Delete /></el-icon>
        批量删除
      </el-button>
    </div>

    <!-- 表格 -->
    <div class="table-wrapper admin-table-card">
      <el-table
        ref="multipleTableRef"
        :data="tableData"
        row-key="id"
        :header-cell-class-name="() => 'admin-table-header-cell'"
        stripe
        style="width: 100%"
        element-loading-text="数据加载中..."
        @selection-change="handleSelectionChange"
        v-loading="tableLoading"
        class="login-log-table admin-table-theme"
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
    <div class="pagination-container admin-pagination-card">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="PAGE_SIZES"
        layout="total, sizes, prev, pager, next, jumper"
        background
        class="admin-pagination-theme"
        @size-change="handlePageSizeChange"
        @current-change="handleCurrentPageChange"
      />
    </div>

    <!-- 详情抽屉 -->
    <LoginLogDetail v-model="dialog" :data="currentRow" />
  </div>
</template>

<script setup lang="ts">
import { useLoginLogList } from '@/composables/useLoginLogList'
import { Delete, Search, Refresh } from '@element-plus/icons-vue'
import LoginLogDetail from './LoginLogDetail.vue'
const {
  METHOD_OPTIONS,
  PAGE_SIZES,
  multipleTableRef,
  tableData,
  selectedIds,
  tableLoading,
  dialog,
  currentRow,
  queryForm,
  pagination,
  handleSelectionChange,
  getDeviceType,
  handleSearch,
  handleReset,
  handleBatchDelete,
  handlePageSizeChange,
  handleCurrentPageChange,
  handleDetail,
} = useLoginLogList()
</script>

<style scoped lang="scss">
.search-container {
  .search-bar {
    display: grid;
    grid-template-columns: minmax(220px, 320px) minmax(140px, 180px) auto;
    align-items: center;
    gap: 12px;
  }

  .search-input {
    width: 100%;
  }

  .method-select {
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
  .delete-button {
    padding: 8px 16px;
  }
}

.table-wrapper {
}

</style>
