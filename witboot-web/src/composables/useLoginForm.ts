import { login } from '@/api/user'
import { setAccessToken } from '@/utils/auth'
import { ElMessage } from 'element-plus'
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  username: string
  password: string
}

interface LoginData {
  accessToken: string
}

interface RequestError {
  reason?: string
}

export function useLoginForm() {
  const REMEMBERED_USERNAME_KEY = 'rememberedUsername'

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

  const rememberedUsername = localStorage.getItem(REMEMBERED_USERNAME_KEY)
  if (rememberedUsername) {
    ruleForm.username = rememberedUsername
  }

  const rememberMe = ref(Boolean(rememberedUsername))
  const loading = ref(false)
  const currentYear = new Date().getFullYear()
  const router = useRouter()
  const route = useRoute()

  const getErrorMessage = (error: unknown) => {
    if (error && typeof error === 'object' && 'reason' in error) {
      return (error as RequestError).reason ?? '登录失败，请检查用户名和密码'
    }
    if (error instanceof Error) {
      return error.message
    }
    return '登录失败，请检查用户名和密码'
  }

  const submitForm = async () => {
    const formEl = ruleFormRef.value
    if (!formEl || loading.value) return

    const valid = await formEl.validate()
    if (!valid) return

    loading.value = true
    try {
      const result = await login<LoginData>({
        username: ruleForm.username,
        password: ruleForm.password,
      })

      if (!result.data?.accessToken) {
        throw new Error('登录失败，未获取到访问令牌')
      }

      if (rememberMe.value) {
        localStorage.setItem(REMEMBERED_USERNAME_KEY, ruleForm.username)
      } else {
        localStorage.removeItem(REMEMBERED_USERNAME_KEY)
      }

      setAccessToken(result.data.accessToken)
      const redirect =
        typeof route.query.redirect === 'string' && route.query.redirect.startsWith('/')
          ? route.query.redirect
          : '/home'
      await router.push(redirect)
    } catch (error: unknown) {
      ElMessage.error(getErrorMessage(error))
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
