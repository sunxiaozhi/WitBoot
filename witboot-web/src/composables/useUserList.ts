import { onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { deleteUser, selectUserList } from '@/api/user'

export interface User {
  id: number
  username: string
  name: string
  mobile: string
  gender: '1' | '2'
  birthday: string
  description: string
}

interface UserListData {
  list: User[]
  total: number
}

interface UserListResponse {
  data: UserListData
}

export function useUserList() {
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
    try {
      const res = (await selectUserList({
        pageNo: pagination.currentPage,
        pageSize: pagination.pageSize,
        keyword: queryForm.keyword,
      })) as UserListResponse
      tableData.value = res.data.list
      pagination.total = res.data.total
    } finally {
      tableLoading.value = false
    }
  }

  const handleSearch = async () => {
    pagination.currentPage = 1
    searchLoading.value = true
    try {
      await fetchData()
    } finally {
      searchLoading.value = false
    }
  }

  const handleReset = async () => {
    queryForm.keyword = ''
    await handleSearch()
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
    selectedIds.value = rows.map(row => row.id)
  }

  const handleDelete = (row: User) => {
    ElMessageBox.confirm(`确认删除 ${row.name}？`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }).then(async () => {
      await deleteUser({ ids: [row.id] })
      ElMessage.success('删除成功')
      await fetchData()
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
      await fetchData()
    })
  }

  const handlePageSizeChange = async (size: number) => {
    pagination.pageSize = size
    await fetchData()
  }

  const handleCurrentPageChange = async (page: number) => {
    pagination.currentPage = page
    await fetchData()
  }

  onMounted(fetchData)

  return {
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
  }
}
