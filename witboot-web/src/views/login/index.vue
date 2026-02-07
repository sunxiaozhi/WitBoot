<template>
  <div class="login-page">
    <!-- 背景装饰 -->
    <div class="background-shapes">
      <div class="shape shape-1"></div>
      <div class="shape shape-2"></div>
      <div class="shape shape-3"></div>
    </div>

    <!-- 主容器 -->
    <div class="login-wrapper">
      <!-- 左侧介绍区域 -->
      <div class="introduction-section">
        <div class="introduction-content">
          <div class="logo">
            <el-icon :size="40" color="#fff"><Monitor /></el-icon>
            <span class="logo-text">WitBoot</span>
          </div>
          <h1 class="introduction-title">通用级管理系统</h1>
          <p class="introduction-description">
            基于 SpringBoot 3.5.x + COLA 5.0 + Vue 3.x 的前后端分离解决方案
          </p>
          <ul class="feature-list">
            <li class="feature-item">
              <el-icon><Check /></el-icon>
              <span>RBAC 动态权限</span>
            </li>
            <li class="feature-item">
              <el-icon><Check /></el-icon>
              <span>数据权限控制</span>
            </li>
            <li class="feature-item">
              <el-icon><Check /></el-icon>
              <span>日志记录</span>
            </li>
            <li class="feature-item">
              <el-icon><Check /></el-icon>
              <span>RESTful API 设计</span>
            </li>
          </ul>
        </div>
      </div>

      <!-- 右侧登录表单 -->
      <div class="form-section">
        <div class="form-card">
          <div class="form-header">
            <h2>欢迎回来</h2>
            <p>请登录您的账户</p>
          </div>

          <el-form
            ref="ruleFormRef"
            :model="ruleForm"
            :rules="rules"
            label-position="top"
            class="login-form"
            @submit.prevent="submitForm(ruleFormRef)"
          >
            <el-form-item prop="username">
              <div class="input-wrapper">
                <span class="input-icon">
                  <el-icon><User /></el-icon>
                </span>
                <el-input
                  v-model="ruleForm.username"
                  placeholder="请输入用户名"
                  size="large"
                  clearable
                  autocomplete="username"
                  aria-label="用户名"
                  autocapitalize="none"
                  spellcheck="false"
                  :disabled="loading"
                />
              </div>
            </el-form-item>

            <el-form-item prop="password">
              <div class="input-wrapper">
                <span class="input-icon">
                  <el-icon><Lock /></el-icon>
                </span>
                <el-input
                  v-model="ruleForm.password"
                  type="password"
                  placeholder="请输入密码"
                  size="large"
                  show-password
                  autocomplete="current-password"
                  aria-label="密码"
                  :disabled="loading"
                />
              </div>
            </el-form-item>

            <div class="form-options">
              <el-checkbox v-model="rememberMe">记住我</el-checkbox>
            </div>

            <el-form-item class="submit-item">
              <el-button
                type="primary"
                size="large"
                :loading="loading"
                :disabled="loading"
                native-type="submit"
                @click="submitForm(ruleFormRef)"
                class="login-button"
              >
                {{ loading ? '登录中...' : '登录' }}
              </el-button>
            </el-form-item>
          </el-form>

          <div class="form-footer">
            <p>Copyright © {{ currentYear }}</p>
            <a
              href="https://github.com/sunxiaozhi"
              target="_blank"
              rel="noopener noreferrer"
            >
              Powered by sunxiaozhi
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { login } from '@/api/user'
import { setAccessToken } from '@/utils/auth'
import { useRouter } from 'vue-router'
import { reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { User, Lock, Monitor, Check } from '@element-plus/icons-vue'

interface RuleForm {
  username: string
  password: string
}

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
  if (!formEl) return
  if (loading.value) return

  const valid = await formEl.validate()
  if (!valid) return

  loading.value = true
  try {
    const result: any = await login({
      username: ruleForm.username,
      password: ruleForm.password,
    })
    setAccessToken(result.data.accessToken)
    await router.push('/home')
  } catch (error: any) {
    const errorMessage = error?.message || '登录失败，请检查用户名和密码'
    ElMessage.error(errorMessage)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped lang="scss">
.login-page {
  position: relative;
  width: 100%;
  height: 100vh;
  min-height: 600px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  overflow: hidden;

  --card-radius: 16px;
  --card-shadow: 0 24px 48px rgba(0, 0, 0, 0.18);
  --panel-gap: 24px;
}

/* 背景装饰图形 */
.background-shapes {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  overflow: hidden;
  z-index: 0;

  .shape {
    position: absolute;
    border-radius: 50%;
    opacity: 0.1;
    animation: float 20s infinite ease-in-out;
  }

  .shape-1 {
    width: 400px;
    height: 400px;
    background: #fff;
    top: -100px;
    right: -100px;
    animation-delay: 0s;
  }

  .shape-2 {
    width: 300px;
    height: 300px;
    background: #fff;
    bottom: -50px;
    left: -50px;
    animation-delay: -5s;
  }

  .shape-3 {
    width: 200px;
    height: 200px;
    background: #fff;
    top: 50%;
    left: 30%;
    animation-delay: -10s;
  }

  @keyframes float {
    0%,
    100% {
      transform: translate(0, 0) rotate(0deg);
    }
    33% {
      transform: translate(30px, -30px) rotate(5deg);
    }
    66% {
      transform: translate(-20px, 20px) rotate(-5deg);
    }
  }
}

.login-wrapper {
  position: relative;
  z-index: 1;
  display: flex;
  width: 90%;
  max-width: 980px;
  height: 560px;
  background: rgba(255, 255, 255, 0.96);
  backdrop-filter: blur(20px);
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
  overflow: hidden;
}

/* 左侧介绍区域 */
.introduction-section {
  flex: 1;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 48px;
  display: flex;
  align-items: center;
  color: #fff;

  .introduction-content {
    width: 100%;
  }

  .logo {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 32px;

    .logo-text {
      font-size: 28px;
      font-weight: 700;
      letter-spacing: 1px;
    }
  }

  .introduction-title {
    font-size: 30px;
    font-weight: 700;
    margin-bottom: 12px;
    line-height: 1.3;
  }

  .introduction-description {
    font-size: 16px;
    opacity: 0.9;
    margin-bottom: 28px;
    line-height: 1.6;
  }

  .feature-list {
    list-style: none;
    padding: 0;
    margin: 0;

    .feature-item {
      display: flex;
      align-items: center;
      gap: 12px;
      padding: 10px 0;
      font-size: 15px;
      opacity: 0.9;

      .el-icon {
        color: #4ade80;
      }
    }
  }
}

/* 右侧表单区域 */
.form-section {
  flex: 1;
  padding: 40px 48px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.form-card {
  width: 100%;
  max-width: 360px;
}

.form-header {
  text-align: center;
  margin-bottom: 28px;

  h2 {
    font-size: 26px;
    font-weight: 700;
    color: #1f2937;
    margin-bottom: 8px;
  }

  p {
    font-size: 14px;
    color: #6b7280;
    margin: 0;
  }
}

.login-form {
  :deep(.el-form-item) {
    margin-bottom: 22px;
  }

  :deep(.el-form-item__error) {
    padding-top: 4px;
  }

  .input-wrapper {
    position: relative;
    width: 100%;

    .input-icon {
      position: absolute;
      left: 12px;
      top: 50%;
      transform: translateY(-50%);
      color: #9ca3af;
      z-index: 10;

      .el-icon {
        font-size: 18px;
      }
    }

    :deep(.el-input__wrapper) {
      padding-left: 40px;
      border-radius: 8px;
      border: 1px solid #e5e7eb;
      box-shadow: none;
      transition: border-color 0.3s;

      &:hover {
        border-color: #cbd5f5;
      }

      &.is-focus {
        border-color: #667eea;
      }
    }
  }

  .form-options {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 18px;

    :deep(.el-checkbox__label) {
      color: #6b7280;
      font-size: 14px;
    }
  }

  .submit-item {
    margin-bottom: 0;

    .login-button {
      width: 100%;
      height: 44px;
      border-radius: 8px;
      font-size: 16px;
      font-weight: 600;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      transition: all 0.3s;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
      }

      &:active {
        transform: translateY(0);
      }
    }
  }
}

.form-footer {
  margin-top: 22px;
  text-align: center;
  font-size: 13px;
  color: #9ca3af;

  p {
    margin: 0 0 4px;
  }

  a {
    color: #667eea;
    text-decoration: none;
    transition: color 0.3s;

    &:hover {
      color: #764ba2;
    }
  }
}

/* 响应式设计 */
@media (max-width: 900px) {
  .introduction-section {
    display: none;
  }

  .login-wrapper {
    width: 90%;
    max-width: 450px;
    height: auto;
    padding: 30px;
  }

  .form-section {
    padding: 0;
  }
}

@media (max-width: 480px) {
  .login-page {
    align-items: flex-start;
    padding-top: 60px;
    background: #f9fafb;
  }

  .background-shapes {
    display: none;
  }

  .login-wrapper {
    box-shadow: none;
    background: transparent;
  }

  .form-card {
    max-width: none;
  }
}
</style>
