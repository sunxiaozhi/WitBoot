import { login } from '@/api/user'
import { setAccessToken } from '@/utils/auth'
import { ElMessage } from 'element-plus'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  username: string
  password: string
}

interface LoginResult {
  data: {
    accessToken: string
  }
}

export function useLoginForm() {
  const ruleFormRef = ref<FormInstance>()
  const ruleForm = reactive<RuleForm>({
    username: '',
    password: '',
  })

  const rules = reactive<FormRules<RuleForm>>({
    username: [
      { required: true, message: '请填写用户名', trigger: 'blur' },
      { min: 6, max: 12, message: '用户名长度需6-12位', trigger: 'blur' },
    ],
    password: [
      { required: true, message: '请填写密码', trigger: 'blur' },
      { min: 6, max: 12, message: '密码长度需6-12位', trigger: 'blur' },
    ],
  })

  const rememberMe = ref(false)
  const loading = ref(false)
  const currentYear = new Date().getFullYear()
  const router = useRouter()

  const submitForm = async (formEl: FormInstance | undefined) => {
    if (!formEl || loading.value) return

    const valid = await formEl.validate()
    if (!valid) return

    loading.value = true
    try {
      const result = (await login({
        username: ruleForm.username,
        password: ruleForm.password,
      })) as LoginResult
      setAccessToken(result.data.accessToken)
      await router.push('/home')
    } catch (error: unknown) {
      const errorMessage = error instanceof Error ? error.message : '登录失败，请检查用户名和密码'
      ElMessage.error(errorMessage)
    } finally {
      loading.value = false
    }
  }

  return {
    ruleFormRef,
    ruleForm,
    rules,
    rememberMe,
    loading,
    currentYear,
    submitForm,
  }
}
