import { onMounted, onUnmounted, reactive, ref } from 'vue'
import { debounce } from 'lodash-es'
import { ElMessage, ElMessageBox, type ElTable } from 'element-plus'
import {
  deleteOperationLog,
  operationLogInfo,
  selectOperationLogList,
} from '@/api/operationLog'

export interface OperationLog {
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

interface OperationLogListData {
  list: OperationLog[]
  total: number
}

interface OperationLogListResponse {
  data: OperationLogListData
}

interface OperationLogDetailResponse {
  data: OperationLog
}

interface DeleteResponse {
  code: number
  message: string
}

const METHOD_OPTIONS = ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'HEAD', 'OPTIONS']
const PAGE_SIZES = [10, 20, 30]

export function useOperationLogList() {
  const multipleTableRef = ref<InstanceType<typeof ElTable>>()
  const tableData = ref<OperationLog[]>([])
  const selectedRows = ref<OperationLog[]>([])
  const selectedIds = ref<number[]>([])
  const tableLoading = ref(false)
  const dialog = ref(false)
  const currentRow = ref<OperationLog | null>(null)

  const queryForm = reactive({
    keyword: '',
    method: '',
  })

  const pagination = reactive({
    currentPage: 1,
    pageSize: 10,
    total: 0,
  })

  const handleSelectionChange = (selection: OperationLog[]) => {
    selectedRows.value = selection
    selectedIds.value = selection.map(item => item.id)
  }

  const getMethodType = (method: string) => {
    const typeMap: Record<string, string> = {
      GET: 'success',
      POST: 'primary',
      PUT: 'warning',
      DELETE: 'danger',
      PATCH: 'warning',
      HEAD: 'info',
      OPTIONS: 'info',
    }
    return typeMap[method] || 'info'
  }

  const getWasteTimeType = (time: string) => {
    const num = parseInt(time) || 0
    if (num < 100) return 'success'
    if (num < 500) return 'warning'
    return 'danger'
  }

  const fetchData = async () => {
    tableLoading.value = true
    try {
      const res = (await selectOperationLogList({
        pageNo: pagination.currentPage,
        pageSize: pagination.pageSize,
        searchKeyword: queryForm.keyword,
        method: queryForm.method,
      })) as OperationLogListResponse
      tableData.value = res.data.list
      pagination.total = res.data.total
    } catch (error) {
      console.error('Failed to fetch data:', error)
      ElMessage.error('数据获取失败')
    } finally {
      tableLoading.value = false
    }
  }

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

  const handleBatchDelete = () => {
    if (selectedIds.value.length === 0) {
      ElMessage.warning('请先选择要删除的操作日志')
      return
    }

    ElMessageBox.confirm(
      `确定要删除选中的${selectedIds.value.length}条操作日志吗？此操作不可恢复！`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error',
        draggable: true,
      },
    )
      .then(async () => {
        try {
          const res = (await deleteOperationLog({ ids: selectedIds.value })) as DeleteResponse
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

  const handlePageSizeChange = (size: number) => {
    pagination.pageSize = size
    fetchData()
  }

  const handleCurrentPageChange = (page: number) => {
    pagination.currentPage = page
    fetchData()
  }

  const handleDetail = async (row: OperationLog) => {
    try {
      const res = (await operationLogInfo(row.id)) as OperationLogDetailResponse
      currentRow.value = res.data
      dialog.value = true
    } catch (error) {
      console.error('Failed to fetch detail:', error)
      ElMessage.error('获取详情失败')
    }
  }

  onMounted(fetchData)
  onUnmounted(() => debouncedSearch.cancel())

  return {
    METHOD_OPTIONS,
    PAGE_SIZES,
    multipleTableRef,
    tableData,
    selectedRows,
    selectedIds,
    tableLoading,
    dialog,
    currentRow,
    queryForm,
    pagination,
    handleSelectionChange,
    getMethodType,
    getWasteTimeType,
    fetchData,
    handleSearch,
    handleReset,
    handleBatchDelete,
    handlePageSizeChange,
    handleCurrentPageChange,
    handleDetail,
  }
}
