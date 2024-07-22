<script lang="ts" setup>
import { login } from '@/api/user'
import { setAccessToken } from '@/utils/auth'
import { useRouter } from 'vue-router'
import HelloWorld from '@/components/HelloWorld.vue'
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  username: string
  password: string
}

const formSize = ref<ComponentSize>('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({
  username: '',
  password: ''
})

const rules = reactive<FormRules<RuleForm>>({
  username: [
    { required: true, message: '请填写用户名', trigger: 'blur' },
    { min: 3, max: 12, message: '字段长度3-12', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请填写密码', trigger: 'blur' },
    { min: 3, max: 12, message: '字段长度3-12', trigger: 'blur' }
  ]
})

const router = useRouter()

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return
  await formEl.validate((valid, fields) => {
    if (valid) {
      login({ username: ruleForm.username, password: ruleForm.password }).then((res: any) => {
        console.log(res)

        let accessToken = res.accessToken
        setAccessToken(accessToken)

        router.push('/about')
      })
      console.log('submit!')
    } else {
      console.log('error submit!', fields)
    }
  })
}
</script>

<template>
  <div class="login-container">
    <el-row :gutter="20">
      <el-col :span="16">
        <header>
          <img alt="Vue logo" class="logo" src="@/assets/logo.svg" width="125" height="125" />

          <div class="wrapper">
            <HelloWorld msg="WitBoot" />
          </div>
        </header>
      </el-col>

      <el-col :span="8">
        <el-form
          ref="ruleFormRef"
          style="max-width: 600px"
          :model="ruleForm"
          :rules="rules"
          label-width="auto"
          class="ruleForm"
          :size="formSize"
          status-icon
        >
          <el-form-item label="" prop="username">
            <el-input v-model="ruleForm.username" placeholder="用户名" />
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input v-model="ruleForm.password" placeholder="密码" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)">登录</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped>
header {
  line-height: 1.5;
  max-height: 100vh;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

/* .ruleForm {
    border: 1px silver solid;
    padding: 10px;
} */

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
}

nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }

  nav {
    text-align: left;
    margin-left: -1rem;
    font-size: 1rem;

    padding: 1rem 0;
    margin-top: 1rem;
  }
}
</style>
