<template>
  <div class="login-container">
    <el-row :gutter="20">
      <el-col :span="8" class="witboot-info" :offset="5">
        <h3>
          WitBoot基于SpringBoot3.x + COLA 4.0（整洁面向对象分层架构）+ Mybatis Plus + Vue3.X +
          Element Plus实现的前后端分离系统，支持RBAC动态权限、数据权限、日志记录...
        </h3>
      </el-col>

      <el-col :span="4" class="login-form" :offset="1">
        <div class="logo">
          <!-- <img alt="witboot logo" src="@/assets/logo.svg" width="100" height="100" /> -->
          <h3>WitBoot</h3>
        </div>

        <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          label-width="auto"
          :size="formSize"
        >
          <el-form-item label="" prop="username">
            <el-input v-model="ruleForm.username" :prefix-icon="User" />
          </el-form-item>
          <el-form-item label="" prop="password">
            <el-input v-model="ruleForm.password" :prefix-icon="Lock" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)"> 登录 </el-button>
            <el-button @click="resetForm(ruleFormRef)">重置</el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import { login } from '@/api/user'
import { setAccessToken } from '@/utils/auth'
import { useRouter } from 'vue-router'
import { reactive, ref } from 'vue'
import type { ComponentSize, FormInstance, FormRules } from 'element-plus'
import { User, Lock } from '@element-plus/icons-vue'

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

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #eee;

  .witboot-info {
    display: flex;
    align-items: center;

    h3 {
      text-indent: 2.5rem;
      line-height: 2.5rem;
      color: #4e4d4d;
    }
  }

  .login-form {
    background-color: #f6f8fa;
    border: 1px solid #d1d9e0b3;
    border-radius: 10px;

    .logo {
      text-align: center;
      color: #4e4d4d;
    }
  }
}
</style>
