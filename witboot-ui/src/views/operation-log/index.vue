<template>
  <el-card>
    <!-- 搜索框 -->
    <div style="margin-bottom: 16px; display: flex; gap: 8px;">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入用户名"
        clearable
        style="width: 200px"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
    </div>

    <!-- 列表表格 -->
    <el-table :data="pagedList" border style="width: 100%">
      <el-table-column prop="ip" label="ip" />
      <el-table-column prop="location" label="地址" />
      <el-table-column prop="method" label="请求方法" />
      <el-table-column prop="uri" label="URI" />
    </el-table>

    <!-- 分页 -->
<!--    <el-pagination
      background
      layout="prev, pager, next"
      :total="filteredList.length"
      :page-size="pageSize"
      :current-page="currentPage"
      @current-change="handlePageChange"
      style="margin-top: 16px; text-align: right"
    />-->
    <el-pagination
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[10, 15, 30, 50]"
      :size="size"
      :disabled="disabled"
      :background="background"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      style="margin-top: 16px; text-align: right"
    />
  </el-card>
</template>

<script setup>
import { ref, onMounted, computed } from 'vue'

import { selectOperationLogList } from '@/api/operationLog.ts'

const searchKeyword = ref('')
const currentPage = ref(1)
const total = ref(1)
const pageSize = 10

const tableData = ref([])

// 过滤搜索
const filteredList = computed(() => {
  if (!searchKeyword.value.trim()) return tableData.value
  return tableData.value.filter(item =>
    item.name.includes(searchKeyword.value.trim())
  )
})

onMounted(async () => {
  selectOperationLogList().then(res => {
    tableData.value = res.rows
    total.value = res.total
  })
})

// 当前页数据
const pagedList = computed(() => {
  const start = (currentPage.value - 1) * pageSize
  return filteredList.value.slice(start, start + pageSize)
})

// 搜索触发（重置页码）
const handleSearch = () => {
  selectOperationLogList().then(res => {
    tableData.value = res.rows
  })
  currentPage.value = 1
}

// 翻页切换
const handlePageChange = (page) => {
  currentPage.value = page
}
</script>