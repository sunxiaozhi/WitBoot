<template>
  <div class="operation-log-container admin-page">
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

    <!-- 操作按钮区域 -->
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
        class="operation-log-table admin-table-theme"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="ip" label="IP 地址" min-width="130" />
        <el-table-column prop="location" label="请求地址" min-width="150" show-overflow-tooltip />
        <el-table-column prop="method" label="请求方法" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getMethodType(row.method)" size="small" effect="plain">
              {{ row.method }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="uri" label="URI" min-width="150" show-overflow-tooltip />
        <el-table-column prop="requestTime" label="请求时间" width="160" />
        <el-table-column prop="wasteTime" label="耗时(ms)" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getWasteTimeType(row.wasteTime)" size="small">
              {{ row.wasteTime }}
            </el-tag>
          </template>
        </el-table-column>
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
    <OperationLogDetail v-model="dialog" :data="currentRow" />
  </div>
</template>

<script setup lang="ts">
import { useOperationLogList } from '@/composables/useOperationLogList'
import { Delete, Search, Refresh } from '@element-plus/icons-vue'
import OperationLogDetail from './OperationLogDetail.vue'
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
  getMethodType,
  getWasteTimeType,
  handleSearch,
  handleReset,
  handleBatchDelete,
  handlePageSizeChange,
  handleCurrentPageChange,
  handleDetail,
} = useOperationLogList()
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

.table-wrapper {}

.pagination-container {}

</style>
