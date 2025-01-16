<template>
  <div class="login-container">
    <el-row :gutter="20" type="flex" justify="center">
      <el-col :span="8" class="witboot-info">
        <h3>
          WitBoot基于SpringBoot3.x + COLA 4.0（整洁面向对象分层架构）+ Mybatis Plus + Vue3.X +
          Element Plus实现的前后端分离系统，支持RBAC动态权限、数据权限、日志记录...
        </h3>
      </el-col>

      <el-col :span="5" class="login-form" :offset="1">
        <div class="title">
          <h3>WitBoot</h3>
        </div>

        <el-form
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          label-width="auto"
          :size="formSize"
          label-position="top"
          hide-required-asterisk="true"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username" :prefix-icon="User" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="ruleForm.password" :prefix-icon="Lock" type="password" />
          </el-form-item>
          <el-form-item>
            <el-switch v-model="rememberMe" active-text="记住我" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm(ruleFormRef)"> 登录</el-button>
            <!-- <el-button @click="resetForm(ruleFormRef)">重置</el-button> -->
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
  <div class="login-footer">
    <span>
      Copyright © 2024 Powered by
      <a href="https://github.com/sunxiaozhi" target="_blank">sunxiaozhi</a>
    </span>
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
    { min: 6, max: 12, message: '用户名长度需6-12位', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请填写密码', trigger: 'blur' },
    { min: 6, max: 12, message: '密码长度需6-12位', trigger: 'blur' }
  ]
})

const rememberMe = ref(false)

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
  height: 90vh;

  .witboot-info {
    display: flex;
    align-items: center;

    h3 {
      text-indent: 2.5rem;
      line-height: 2.5rem;
      color: #333;
    }
  }

  .login-form {
    background-color: #f6f8fa;
    border: 1px solid #d1d9e0b3;
    border-radius: 10px;

    .title {
      text-align: center;
      color: #409eff;
    }
  }
}

.login-footer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 10vh;

  span {
    color: #333;

    a:link,
    a:visited,
    a:active,
    a:hover {
      color: #409eff;
      text-decoration: none;
    }
  }
}
</style>
