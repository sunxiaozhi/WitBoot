<template>
  <el-drawer
    v-model="visible"
    :title="form.id ? '编辑用户' : '新增用户'"
    size="450px"
    :before-close="handleBeforeClose"
    class="user-drawer"
  >
    <div class="drawer-content">
      <el-form ref="formRef" :model="form" :rules="rules" class="user-form">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="form.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入姓名" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row v-if="!form.id" :gutter="20">
          <el-col :span="12">
            <el-form-item label="密码" prop="password">
              <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="确认密码" prop="rePassword">
              <el-input v-model="form.rePassword" type="password" show-password placeholder="请再次输入密码" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机号" prop="mobile">
              <el-input v-model="form.mobile" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="gender">
              <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                <el-option label="男" value="1" />
                <el-option label="女" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="生日" prop="birthday">
          <el-date-picker
            v-model="form.birthday"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择生日"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入描述信息" />
        </el-form-item>
      </el-form>

      <div class="drawer-footer">
        <el-button class="cancel-button" @click="visible = false">取消</el-button>
        <el-button class="submit-button" type="primary" :loading="submitLoading" @click="submit">
          {{ submitLoading ? '提交中...' : '提交' }}
        </el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script setup lang="ts">
import { useUserDrawerForm } from '@/composables/useUserDrawerForm'

/* ================= props / emits ================= */

const props = defineProps<{
  data: {
    id?: number
    username?: string
    name?: string
    password?: string
    rePassword?: string
    mobile?: string
    gender?: '' | '1' | '2'
    birthday?: string
    description?: string
  } | null
}>()

const visible = defineModel<boolean>({ required: true })

const emit = defineEmits<{
  (_e: 'success'): void
}>()

const { submitLoading, formRef, form, rules, submit, handleBeforeClose } = useUserDrawerForm({
  visible,
  data: () => props.data,
  onSuccess: () => emit('success'),
})
</script>

<style scoped lang="scss">
.user-drawer {
  :deep(.el-drawer__header) {
    margin-bottom: 20px;
    padding: 24px;
    border-bottom: 1px solid #f0f0f0;

    .el-drawer__title {
      font-size: 20px;
      font-weight: 600;
      color: #333;
    }
  }

  :deep(.el-drawer__body) {
    padding: 0 24px 24px;
  }
}

.drawer-content {
  .user-form {
    :deep(.el-form-item) {
      margin-bottom: 24px;

      .el-form-item__label {
        font-weight: 500;
        color: #606266;
        font-size: 14px;
      }

      .el-form-item__content {
        .el-input__wrapper {
          border-radius: 8px;
          border: 1px solid #e5e7eb;
          transition: border-color 0.3s;
          box-shadow: none;

          &:hover {
            border-color: #cbd5f5;
          }

          &.is-focus {
            border-color: #667eea;
          }
        }

        .el-textarea__inner {
          border-radius: 8px;
          box-shadow: none;
          border: 1px solid #e5e7eb;
          transition: border-color 0.3s;

          &:hover {
            border-color: #cbd5f5;
          }

          &:focus {
            border-color: #667eea;
          }
        }

        .el-select {
          .el-input__wrapper {
            border-radius: 8px;
            box-shadow: none;
            border: 1px solid #e5e7eb;
            transition: border-color 0.3s;

            &:hover {
              border-color: #cbd5f5;
            }

            &.is-focus {
              border-color: #667eea;
            }
          }
        }
      }
    }

    :deep(.el-form-item__error) {
      padding-top: 6px;
      font-size: 12px;
    }
  }

  .drawer-footer {
    display: flex;
    justify-content: flex-end;
    gap: 12px;
    margin-top: 30px;
    padding-top: 20px;
    border-top: 1px solid #f0f0f0;

    .cancel-button {
      min-width: 100px;
      height: 40px;
      border-radius: 8px;
      font-size: 14px;
      font-weight: 500;
      border: 1px solid #dcdfe6;
      color: #606266;
      background: #fff;
      transition: all 0.3s;

      &:hover {
        color: #667eea;
        border-color: #667eea;
        background: rgba(102, 126, 234, 0.05);
      }
    }

    .submit-button {
      min-width: 100px;
      height: 40px;
      border-radius: 8px;
      font-size: 14px;
      font-weight: 500;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      transition: all 0.3s;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
      }

      &:active {
        transform: translateY(0);
      }
    }
  }
}
</style>
