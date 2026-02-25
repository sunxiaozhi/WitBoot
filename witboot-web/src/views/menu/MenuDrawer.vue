<template>
  <el-drawer
    v-model="visible"
    :title="form.id ? '编辑菜单' : '新增菜单'"
    size="500px"
    :before-close="handleBeforeClose"
    class="menu-drawer"
  >
    <div class="drawer-content">
      <el-form ref="formRef" :model="form" :rules="rules" class="menu-form">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单路径" prop="path">
              <el-input v-model="form.path" placeholder="请输入菜单路径" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="组件地址" prop="component">
              <el-input v-model="form.component" placeholder="请输入组件地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示顺序" prop="sort">
              <el-input-number v-model="form.sort" :min="1" :max="999" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="图标" prop="icon">
              <el-input v-model="form.icon" placeholder="如：Setting" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-select v-model="form.status" placeholder="请选择状态" style="width: 100%">
                <el-option label="启用" value="启用" />
                <el-option label="停用" value="停用" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

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
import { reactive, ref, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'

type MenuForm = {
  id?: number
  name: string
  path: string
  component: string
  sort: number
  icon: string
  status: '启用' | '停用'
  description: string
}

const props = defineProps<{
  modelValue: boolean
  data?: Partial<MenuForm> | null
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', value: boolean): void
  (e: 'success', payload: MenuForm): void
}>()

const createEmptyForm = (): MenuForm => ({
  id: undefined,
  name: '',
  path: '',
  component: '',
  sort: 1,
  icon: '',
  status: '启用',
  description: '',
})

const visible = ref(false)
const submitLoading = ref(false)
const formRef = ref<FormInstance>()
const form = reactive(createEmptyForm())

const rules: FormRules = {
  name: [{ required: true, message: '请输入菜单名称', trigger: 'blur' }],
  path: [{ required: true, message: '请输入菜单路径', trigger: 'blur' }],
  component: [{ required: true, message: '请输入组件地址', trigger: 'blur' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
}

const resetForm = () => {
  Object.assign(form, createEmptyForm())
  formRef.value?.clearValidate()
}

watch(
  () => props.modelValue,
  value => (visible.value = value)
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

const submit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async valid => {
    if (!valid) return
    submitLoading.value = true
    try {
      await new Promise(resolve => setTimeout(resolve, 300))
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
.menu-drawer {
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
  .menu-form {
    :deep(.el-form-item) {
      margin-bottom: 24px;

      .el-form-item__label {
        font-weight: 500;
        color: #606266;
        font-size: 14px;
      }

      .el-form-item__content {
        .el-input__wrapper,
        .el-input-number,
        .el-select .el-input__wrapper {
          border-radius: 8px;
          border: 1px solid #e5e7eb;
          transition: border-color 0.3s;
          box-shadow: none;

          &:hover {
            border-color: #cbd5f5;
          }
        }

        .el-input__wrapper.is-focus,
        .el-select .el-input__wrapper.is-focus {
          border-color: #667eea;
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
