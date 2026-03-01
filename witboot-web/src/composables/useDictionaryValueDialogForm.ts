import { ElMessage, ElMessageBox } from 'element-plus'
import { reactive, ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { Ref } from 'vue'

export type DictionaryValueForm = {
  id?: number
  label: string
  value: string
  status: '启用' | '停用'
}

interface UseDictionaryValueDialogFormOptions {
  visible: Ref<boolean>
  data: () => Partial<DictionaryValueForm> | null | undefined
  onSuccess: (_payload: DictionaryValueForm) => void
}

const createEmptyForm = (): DictionaryValueForm => ({
  id: undefined,
  label: '',
  value: '',
  status: '启用',
})

export function useDictionaryValueDialogForm(options: UseDictionaryValueDialogFormOptions) {
  const submitLoading = ref(false)
  const formRef = ref<FormInstance>()
  const form = reactive(createEmptyForm())

  const rules: FormRules = {
    label: [{ required: true, message: '请输入显示名称', trigger: 'blur' }],
    value: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
    status: [{ required: true, message: '请选择状态', trigger: 'change' }],
  }

  const resetForm = () => {
    Object.assign(form, createEmptyForm())
    formRef.value?.clearValidate()
  }

  watch(options.visible, value => {
    if (!value) resetForm()
  })

  watch(
    options.data,
    value => {
      if (value) {
        Object.assign(form, createEmptyForm(), value)
      } else {
        resetForm()
      }
    },
    { immediate: true },
  )

  const handleSubmit = async () => {
    if (!formRef.value) return
    await formRef.value.validate(async valid => {
      if (!valid) return
      submitLoading.value = true
      try {
        await new Promise(resolve => setTimeout(resolve, 250))
        options.onSuccess({ ...form })
        ElMessage.success(form.id ? '更新成功' : '创建成功')
        options.visible.value = false
      } finally {
        submitLoading.value = false
      }
    })
  }

  const handleBeforeClose = (done: () => void) => {
    ElMessageBox.confirm('确认关闭？未保存数据将丢失', '提示', {
      type: 'warning',
      confirmButtonText: '确定',
      cancelButtonText: '取消',
    })
      .then(() => done())
      .catch(() => {})
  }

  return {
    formRef,
    form,
    rules,
    submitLoading,
    handleSubmit,
    handleBeforeClose,
  }
}
