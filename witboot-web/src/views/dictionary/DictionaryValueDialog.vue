<template>
  <el-dialog
    v-model="visible"
    :title="form.id ? '编辑字典值' : '新增字典值'"
    width="520px"
    class="dictionary-value-dialog"
    :close-on-click-modal="false"
    :before-close="handleBeforeClose"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="88px" class="value-form">
      <el-form-item label="显示名称" prop="label">
        <el-input v-model="form.label" placeholder="请输入显示名称" />
      </el-form-item>
      <el-form-item label="字典值" prop="value">
        <el-input v-model="form.value" placeholder="请输入字典值" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
          <el-option label="启用" value="启用" />
          <el-option label="停用" value="停用" />
        </el-select>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button class="cancel-button" @click="visible = false">取消</el-button>
        <el-button type="primary" class="submit-button" :loading="submitLoading" @click="handleSubmit">
          {{ submitLoading ? '提交中...' : '提交' }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { useDictionaryValueDialogForm } from '@/composables/useDictionaryValueDialogForm'

const props = defineProps<{
  data?: {
    id?: number
    label?: string
    value?: string
    status?: '启用' | '停用'
  } | null
}>()

const visible = defineModel<boolean>({ required: true })

const emit = defineEmits<{
  (
    _e: 'success',
    _payload: {
      id?: number
      label: string
      value: string
      status: '启用' | '停用'
    },
  ): void
}>()

const { submitLoading, formRef, form, rules, handleSubmit, handleBeforeClose } =
  useDictionaryValueDialogForm({
    visible,
    data: () => props.data,
    onSuccess: payload => emit('success', payload),
  })
</script>

<style scoped lang="scss">
.dictionary-value-dialog {
  :deep(.el-dialog) {
    border-radius: 10px;
  }

  :deep(.el-dialog__header) {
    margin-right: 0;
    padding: 18px 20px 10px;
    border-bottom: 1px solid #f0f0f0;
  }

  :deep(.el-dialog__title) {
    font-size: 18px;
    font-weight: 600;
    color: #333;
  }

  :deep(.el-dialog__body) {
    padding: 18px 20px 8px;
  }

  :deep(.el-dialog__footer) {
    padding: 12px 20px 18px;
    border-top: 1px solid #f0f0f0;
  }
}

.value-form {
  :deep(.el-form-item) {
    margin-bottom: 20px;
  }

  :deep(.el-form-item__label) {
    color: #606266;
    font-weight: 500;
  }

  :deep(.el-input__wrapper) {
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

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.cancel-button {
  min-width: 92px;
  border: 1px solid #dcdfe6;

  &:hover {
    color: #667eea;
    border-color: #667eea;
    background: rgba(102, 126, 234, 0.05);
  }
}

.submit-button {
  min-width: 92px;
  border: none;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 4px 10px rgba(102, 126, 234, 0.25);
  }

  &:active {
    transform: translateY(0);
  }
}
</style>
