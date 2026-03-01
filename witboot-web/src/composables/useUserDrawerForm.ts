import { register, updateUserInfo } from '@/api/user'
import { ElMessage, ElMessageBox } from 'element-plus'
import { reactive, ref, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import type { Ref } from 'vue'

export type UserForm = {
  id?: number
  username: string
  name: string
  password: string
  rePassword: string
  mobile: string
  gender: '' | '1' | '2'
  birthday: string
  description: string
}

interface UseUserDrawerFormOptions {
  visible: Ref<boolean>
  data: () => Partial<UserForm> | null
  onSuccess: () => void
}

const createEmptyForm = (): UserForm => ({
  id: undefined as number | undefined,
  username: '',
  name: '',
  password: '',
  rePassword: '',
  mobile: '',
  gender: '',
  birthday: '',
  description: '',
})

export function useUserDrawerForm(options: UseUserDrawerFormOptions) {
  const submitLoading = ref(false)
  const formRef = ref<FormInstance>()
  const form = reactive(createEmptyForm())

  const resetForm = () => {
    Object.assign(form, createEmptyForm())
    formRef.value?.clearValidate()
  }

  const rules: FormRules = {
    username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
    name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
    password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
    rePassword: [
      {
        validator: (_, value, cb) => (value === form.password ? cb() : cb(new Error('两次密码不一致'))),
        trigger: 'blur',
      },
    ],
    mobile: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误' }],
    gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  }

  watch(options.visible, value => {
    if (!value) resetForm()
  })

  watch(
    options.data,
    value => {
      if (value) {
        Object.assign(form, createEmptyForm(), value)
        form.password = ''
        form.rePassword = ''
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
        await (form.id ? updateUserInfo : register)(form)
        ElMessage.success(form.id ? '更新成功' : '创建成功')
        options.onSuccess()
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
