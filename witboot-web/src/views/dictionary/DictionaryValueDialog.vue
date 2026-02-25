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
import { reactive, ref, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'

type DictionaryValueForm = {
  id?: number
  label: string
  value: string
  status: '启用' | '停用'
}

const props = defineProps<{
  modelValue: boolean
  data?: Partial<DictionaryValueForm> | null
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'success', payload: DictionaryValueForm): void
}>()

const createEmptyForm = (): DictionaryValueForm => ({
  id: undefined,
  label: '',
  value: '',
  status: '启用',
})

const visible = ref(false)
const submitLoading = ref(false)
const formRef = ref<FormInstance>()
const form = reactive(createEmptyForm())

const rules: FormRules = {
  label: [{ required: true, message: '请输入显示名称', trigger: 'blur' }],
  value: [{ required: true, message: '请输入字典值', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
}

const resetForm = () => {
  Object.assign(form, createEmptyForm())
  formRef.value?.clearValidate()
}

watch(
  () => props.modelValue,
  value => {
    visible.value = value
  }
)

watch(visible, value => {
  emit('update:modelValue', value)
  if (!value) resetForm()
})

watch(
  () => props.data,
  value => {
    if (value) {
      Object.assign(form, createEmptyForm(), value)
    } else {
      resetForm()
    }
  },
  { immediate: true }
)

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async valid => {
    if (!valid) return
    submitLoading.value = true
    try {
      await new Promise(resolve => setTimeout(resolve, 250))
      emit('success', { ...form })
      ElMessage.success(form.id ? '更新成功' : '创建成功')
      visible.value = false
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
