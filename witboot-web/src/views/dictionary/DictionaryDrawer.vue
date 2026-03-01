<template>
  <el-drawer
    v-model="visible"
    :title="form.id ? '编辑字典' : '新增字典'"
    size="450px"
    :before-close="handleBeforeClose"
    class="dictionary-drawer"
  >
    <div class="drawer-content">
      <el-form ref="formRef" :model="form" :rules="rules" class="dictionary-form">
        <el-form-item label="字典名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入字典名称" />
        </el-form-item>
        <el-form-item label="字典编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入字典编码" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
            <el-option label="启用" value="启用" />
            <el-option label="停用" value="停用" />
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注信息" />
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
import { useDictionaryDrawerForm } from '@/composables/useDictionaryDrawerForm'

const props = defineProps<{
  data?: {
    id?: number
    name?: string
    code?: string
    status?: string
    remark?: string
  } | null
}>()

const visible = defineModel<boolean>({ required: true })

const emit = defineEmits<{
  (_e: 'success'): void
}>()

const { submitLoading, formRef, form, rules, submit, handleBeforeClose } = useDictionaryDrawerForm({
  visible,
  data: () => props.data,
  onSuccess: () => emit('success'),
})
</script>

<style scoped lang="scss">
.dictionary-drawer {
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
  .dictionary-form {
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
