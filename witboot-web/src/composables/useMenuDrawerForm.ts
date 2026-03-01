import { ElMessage, ElMessageBox } from 'element-plus'
import { reactive, ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { Ref } from 'vue'

export type MenuForm = {
  id?: number
  name: string
  path: string
  component: string
  sort: number
  icon: string
  status: '启用' | '停用'
  description: string
}

interface UseMenuDrawerFormOptions {
  visible: Ref<boolean>
  data: () => Partial<MenuForm> | null | undefined
  onSuccess: (_payload: MenuForm) => void
}

const createEmptyForm = (): MenuForm => ({
  id: undefined,
  name: '',
  path: '',
  component: '',
  sort: 1,
  icon: '',
  status: '启用',
  description: '',
})

export function useMenuDrawerForm(options: UseMenuDrawerFormOptions) {
  const submitLoading = ref(false)
  const formRef = ref<FormInstance>()
  const form = reactive(createEmptyForm())

  const rules: FormRules = {
    name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
    path: [{ required: true, message: '请输入菜单路径', trigger: 'blur' }],
    component: [{ required: true, message: '请输入组件地址', trigger: 'blur' }],
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

  const submit = async () => {
    if (!formRef.value) return
    await formRef.value.validate(async valid => {
      if (!valid) return
      submitLoading.value = true
      try {
        await new Promise(resolve => setTimeout(resolve, 300))
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
    submit,
    handleBeforeClose,
  }
}
