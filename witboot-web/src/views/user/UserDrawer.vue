<template>
  <el-drawer
    v-model="visible"
    :title="form.id ? '编辑用户' : '新增用户'"
    size="40%"
    :before-close="handleBeforeClose"
  >
    <el-form
      ref="formRef"
      :model="form"
      :rules="rules"
      label-width="100px"
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row v-if="!form.id" :gutter="20">
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input v-model="form.password" type="password" show-password />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="确认密码" prop="rePassword">
            <el-input v-model="form.rePassword" type="password" show-password />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="手机号" prop="mobile">
            <el-input v-model="form.mobile" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="form.gender" style="width: 100%">
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
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="描述" prop="description">
        <el-input v-model="form.description" type="textarea" :rows="3" />
      </el-form-item>
    </el-form>

    <template #footer>
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" :loading="submitLoading" @click="submit">
        提交
      </el-button>
    </template>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref, reactive, watch } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance, FormRules } from 'element-plus'
import { register, updateUserInfo } from '@/api/user'

/* ================= props / emits ================= */

const props = defineProps<{
  modelValue: boolean
  data: any | null
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', v: boolean): void
  (e: 'success'): void
}>()

/* ================= 表单工厂 ================= */

const createEmptyForm = () => ({
  id: undefined as number | undefined,
  username: '',
  name: '',
  password: '',
  rePassword: '',
  mobile: '',
  gender: '',
  birthday: '',
  description: ''
})

/* ================= state ================= */

const visible = ref(false)
const submitLoading = ref(false)
const formRef = ref<FormInstance>()
const form = reactive(createEmptyForm())

/* ================= 表单方法（必须在 watch 之前） ================= */

const resetForm = () => {
  Object.assign(form, createEmptyForm())
  formRef.value?.clearValidate()
}

/* ================= 校验规则 ================= */

const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  rePassword: [
    {
      validator: (_, value, cb) =>
        value === form.password ? cb() : cb(new Error('两次密码不一致')),
      trigger: 'blur'
    }
  ],
  mobile: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }]
}

/* ================= watch ================= */

watch(
  () => props.modelValue,
  (v) => (visible.value = v)
)

watch(visible, (v) => {
  emit('update:modelValue', v)
  if (!v) resetForm()
})

watch(
  () => props.data,
  (v) => {
    if (v) {
      Object.assign(form, createEmptyForm(), v)
      form.password = ''
      form.rePassword = ''
    } else {
      resetForm()
    }
  },
  { immediate: true }
)

/* ================= actions ================= */

const submit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    submitLoading.value = true
    try {
      await (form.id ? updateUserInfo : register)(form)
      ElMessage.success(form.id ? '更新成功' : '创建成功')
      emit('success')
      visible.value = false
    } finally {
      submitLoading.value = false
    }
  })
}

const handleBeforeClose = (done: () => void) => {
  ElMessageBox.confirm('确认关闭？未保存数据将丢失', '提示', {
    type: 'warning'
  })
    .then(() => done())
    .catch(() => {})
}
</script>
