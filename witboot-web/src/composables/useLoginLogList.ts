import { onMounted, onUnmounted, reactive, ref } from 'vue'
import { debounce } from 'lodash-es'
import { ElMessage, ElMessageBox, type ElTable } from 'element-plus'
import { deleteLoginLog, loginLogInfo, selectLoginLogList } from '@/api/loginLog'

export interface LoginLog {
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

interface LoginLogListData {
  list: LoginLog[]
  total: number
}

interface LoginLogListResponse {
  data: LoginLogListData
}

interface LoginLogDetailResponse {
  data: LoginLog
}

interface DeleteResponse {
  code: number
  message: string
}

const METHOD_OPTIONS = ['GET', 'POST', 'PUT', 'DELETE', 'PATCH', 'HEAD', 'OPTIONS']
const PAGE_SIZES = [10, 20, 30]

export function useLoginLogList() {
  const multipleTableRef = ref<InstanceType<typeof ElTable>>()
  const tableData = ref<LoginLog[]>([])
  const selectedRows = ref<LoginLog[]>([])
  const selectedIds = ref<number[]>([])
  const tableLoading = ref(false)
  const dialog = ref(false)
  const currentRow = ref<LoginLog | null>(null)

  const queryForm = reactive({
    keyword: '',
    method: '',
  })

  const pagination = reactive({
    currentPage: 1,
    pageSize: 10,
    total: 0,
  })

  const handleSelectionChange = (selection: LoginLog[]) => {
    selectedRows.value = selection
    selectedIds.value = selection.map(item => item.id)
  }

  const getDeviceType = (device: string) => {
    const typeMap: Record<string, string> = {
      PC: 'primary',
      Mobile: 'warning',
      Tablet: 'success',
    }
    return typeMap[device] || 'info'
  }

  const fetchData = async () => {
    tableLoading.value = true
    try {
      const res = (await selectLoginLogList({
        pageNo: pagination.currentPage,
        pageSize: pagination.pageSize,
        searchKeyword: queryForm.keyword,
        method: queryForm.method,
      })) as LoginLogListResponse
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
      ElMessage.warning('请先选择要删除的登录日志')
      return
    }

    ElMessageBox.confirm(
      `确定要删除选中的${selectedIds.value.length}条登录日志吗？此操作不可恢复！`,
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
          const res = (await deleteLoginLog({ ids: selectedIds.value })) as DeleteResponse
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

  const handleDetail = async (row: LoginLog) => {
    try {
      const res = (await loginLogInfo(row.id)) as LoginLogDetailResponse
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
    getDeviceType,
    handleSearch,
    handleReset,
    handleBatchDelete,
    handlePageSizeChange,
    handleCurrentPageChange,
    handleDetail,
  }
}
