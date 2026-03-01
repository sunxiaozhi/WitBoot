import { reactive, ref } from 'vue'

type DictionaryStatus = '启用' | '停用'

interface DictionaryItem {
  id: number
  name: string
  code: string
  status: DictionaryStatus
  updatedAt: string
}

interface DictionaryValueItem {
  id: number
  label: string
  value: string
  status: DictionaryStatus
}

type DictionaryFormData = {
  id?: number
  name?: string
  code?: string
  status?: string
  remark?: string
}

type DictionaryValueFormData = {
  id?: number
  label?: string
  value?: string
  status?: DictionaryStatus
}

export function useDictionaryPage() {
  const query = reactive({
    keyword: '',
  })

  const pagination = reactive({
    page: 1,
    pageSize: 10,
    total: 86,
  })

  const tableData: DictionaryItem[] = [
    { id: 1, name: '用户状态', code: 'user_status', status: '启用', updatedAt: '2026-02-05' },
    { id: 2, name: '订单状态', code: 'order_status', status: '启用', updatedAt: '2026-02-05' },
    { id: 3, name: '渠道类型', code: 'channel_type', status: '停用', updatedAt: '2026-02-01' },
    { id: 4, name: '支付方式', code: 'pay_method', status: '启用', updatedAt: '2026-02-04' },
  ]

  const dictValues = ref<DictionaryValueItem[]>([
    { id: 1, label: '启用', value: '1', status: '启用' },
    { id: 2, label: '禁用', value: '0', status: '停用' },
    { id: 3, label: '冻结', value: '2', status: '启用' },
  ])

  const drawerVisible = ref(false)
  const currentDictionary = ref<DictionaryFormData | null>(null)
  const valueDialogVisible = ref(false)
  const currentValue = ref<DictionaryValueFormData | null>(null)

  const handleAdd = () => {
    currentDictionary.value = null
    drawerVisible.value = true
  }

  const handleSuccess = () => {
    drawerVisible.value = false
  }

  const handleAddValue = () => {
    currentValue.value = null
    valueDialogVisible.value = true
  }

  const handleEditValue = (item: { id: number; label: string; value: string; status: string }) => {
    currentValue.value = { ...item, status: item.status === '启用' ? '启用' : '停用' }
    valueDialogVisible.value = true
  }

  const handleValueSuccess = (payload: {
    id?: number
    label: string
    value: string
    status: DictionaryStatus
  }) => {
    if (payload.id) {
      const index = dictValues.value.findIndex(item => item.id === payload.id)
      if (index >= 0) dictValues.value[index] = { ...dictValues.value[index], ...payload }
    } else {
      const nextId = Math.max(0, ...dictValues.value.map(item => item.id)) + 1
      dictValues.value.unshift({ ...payload, id: nextId })
    }
    valueDialogVisible.value = false
  }

  return {
    query,
    pagination,
    tableData,
    dictValues,
    drawerVisible,
    currentDictionary,
    valueDialogVisible,
    currentValue,
    handleAdd,
    handleSuccess,
    handleAddValue,
    handleEditValue,
    handleValueSuccess,
  }
}
