<template>
  <el-card>
    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入用户名、姓名"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch">搜索</el-button>
    </div>

    <div class="option-container">
      <el-button type="primary" @click="handleAdd">新增</el-button>
      <el-button
        type="danger"
        :disabled="selectedIds.length === 0"
        @click="handleBatchDelete"
      >
        批量删除
      </el-button>
    </div>

    <!-- 列表表格 -->
    <el-table
      ref="multipleTableRef"
      :data="tableData"
      row-key="id"
      border
      style="width: 100%"
      :loading="loading"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" :selectable="selectable" width="55" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="name" label="姓名" />
      <el-table-column prop="mobile" label="手机号" />
      <el-table-column prop="gender" label="性别" />
      <el-table-column prop="birthday" label="生日" />
      <el-table-column prop="description" label="描述" />
      <el-table-column fixed="right" label="操作" min-width="120">
        <template #default="scope">
          <el-button link type="primary" size="small" @click.prevent="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-button link type="primary" size="small" @click.prevent="handleDelete(scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 30]"
        layout="total, prev, pager, next, sizes, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 16px; text-align: right"
      />
    </div>

    <!-- 新增/编辑抽屉 -->
    <el-drawer
      v-model="dialog"
      title="人员管理"
      :before-close="handleClose"
      direction="rtl"
      class="drawer"
    >
      <div class="drawer__content">
        <el-form
          ref="ruleFormRef"
          :model="form"
          :rules="rules"
        >
          <el-form-item label="用户名" prop="username" :label-width="formLabelWidth">
            <el-input v-model="form.username" autocomplete="off" />
          </el-form-item>
          <el-form-item label="姓名" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off" />
          </el-form-item>
          <el-form-item label="密码" prop="password" :label-width="formLabelWidth">
            <el-input v-model="form.password" type="password" autocomplete="off" />
          </el-form-item>
          <el-form-item label="确认密码" prop="rePassword" :label-width="formLabelWidth">
            <el-input v-model="form.rePassword" type="password" autocomplete="off" />
          </el-form-item>
          <el-form-item label="手机号" prop="mobile" :label-width="formLabelWidth">
            <el-input v-model="form.mobile" autocomplete="off" />
          </el-form-item>
          <el-form-item label="性别" prop="gender" :label-width="formLabelWidth">
            <el-select v-model="form.gender" placeholder="请选择性别">
              <el-option label="男" value="1" />
              <el-option label="女" value="2" />
            </el-select>
          </el-form-item>
          <el-form-item label="生日" prop="birthday" :label-width="formLabelWidth">
            <el-date-picker
              v-model="form.birthday"
              value-format="YYYY-MM-DD"
              placeholder="请选择日期"
              style="width: 100%"
            />
          </el-form-item>
          <el-form-item label="描述" prop="description" :label-width="formLabelWidth">
            <el-input v-model="form.description" autocomplete="off" />
          </el-form-item>
        </el-form>
        <div class="drawer__footer">
          <el-button @click="cancelForm">取消</el-button>
          <el-button type="primary" :loading="loading" @click="submitForm">
            {{ loading ? '提交中 ...' : '提交' }}
          </el-button>
        </div>
      </div>
    </el-drawer>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, watch, onMounted } from 'vue'
import { selectUserList } from '@/api/user.ts'
import { debounce } from 'lodash-es'
import { ElMessage, ElMessageBox } from 'element-plus'
import { register } from '@/api/user'
import type { FormInstance, FormRules } from 'element-plus'

// 类型定义
type Gender = '1' | '2'

interface User {
  id: number
  username: string
  name: string
  password: string
  rePassword: string
  mobile: string
  gender: Gender
  birthday: string
  description: string
}

interface UserForm extends Omit<User, 'id'> {
  id?: number
}

interface Pagination {
  currentPage: number
  pageSize: number
  total: number
}

interface QueryParams {
  pageNo: number
  pageSize: number
  keyword?: string
}

// 常量
const formLabelWidth = '80px'

// 响应式数据
const dialog = ref(false)
const searchKeyword = ref('')
const tableData = ref<User[]>([])
const loading = ref(false)
const selectedRows = ref<User[]>([])
const selectedIds = ref<number[]>([])

const form = reactive<UserForm>({
  username: '',
  name: '',
  password: '',
  rePassword: '',
  mobile: '',
  gender: '',
  birthday: '',
  description: ''
})

const pagination = reactive<Pagination>({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

const ruleFormRef = ref<FormInstance>()

// 表单校验规则
const rules = reactive<FormRules<UserForm>>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度应为3到20个字符', trigger: 'blur' }
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度应为2到20个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度应为6到20个字符', trigger: 'blur' }
  ],
  rePassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  mobile: [
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  gender: [
    { required: false, message: '请选择性别', trigger: 'change' }
  ],
  birthday: [
    { required: false, message: '请选择生日', trigger: 'change' }
  ]
})

// 方法定义
const selectable = (row: User) => {
  return ![1, 2].includes(row.id)
}

const handleSelectionChange = (selection: User[]) => {
  selectedRows.value = selection
  selectedIds.value = selection.map(item => item.id)
}

const fetchData = async () => {
  loading.value = true
  try {
    const params: QueryParams = {
      pageNo: pagination.currentPage,
      pageSize: pagination.pageSize
    }

    if (searchKeyword.value) {
      params.keyword = searchKeyword.value
    }

    const userList = await selectUserList(params)
    tableData.value = userList.data.list
    pagination.total = userList.data.total
  } catch (error) {
    ElMessage.error('获取用户列表失败')
    console.error('Failed to fetch data:', error)
  } finally {
    loading.value = false
  }
}

const debouncedSearch = debounce(() => {
  pagination.currentPage = 1
  fetchData()
}, 300)

const handleSearch = () => {
  debouncedSearch()
}

const resetForm = () => {
  Object.assign(form, {
    username: '',
    name: '',
    password: '',
    rePassword: '',
    mobile: '',
    gender: '',
    birthday: '',
    description: ''
  })

  if (ruleFormRef.value) {
    ruleFormRef.value.resetFields()
  }
}

const handleAdd = () => {
  resetForm()
  dialog.value = true
}

const handleEdit = (row: User) => {
  ElMessage.info('编辑功能开发中')
}

const handleDelete = (row: User) => {
  ElMessageBox.confirm(`确定要删除用户"${row.name}"吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    ElMessage.success('删除成功')
    await fetchData()
  }).catch(() => {
    // 用户取消删除
  })
}

const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请先选择要删除的用户')
    return
  }

  ElMessageBox.confirm(`确定要删除选中的${selectedIds.value.length}个用户吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    ElMessage.success('批量删除成功')
    await fetchData()
  }).catch(() => {
    // 用户取消删除
  })
}

const handleSizeChange = (size: number) => {
  pagination.pageSize = size
  fetchData()
}

const handleCurrentChange = (page: number) => {
  pagination.currentPage = page
  fetchData()
}

const submitForm = async () => {
  if (!ruleFormRef.value) return

  await ruleFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const res = await register(form)
        if (res.code === 200) {
          ElMessage.success('提交成功')
          dialog.value = false
          resetForm()
          await fetchData()
        } else {
          ElMessage.error(res.message || '提交失败')
        }
      } catch (error) {
        ElMessage.error('提交失败')
        console.error('Submit error:', error)
      } finally {
        loading.value = false
      }
    }
  })
}

const handleClose = (done: () => void) => {
  if (loading.value) {
    return
  }

  ElMessageBox.confirm('确定要提交表单吗？')
    .then(() => {
      submitForm().then(() => {
        if (!loading.value) {
          done()
        }
      })
    })
    .catch(() => {
      // 用户取消操作
    })
}

const cancelForm = () => {
  dialog.value = false
  resetForm()
}

// 生命周期钩子
onMounted(async () => {
  await fetchData()
})

// 监听器
watch(
  () => [pagination.currentPage, pagination.pageSize],
  () => {
    fetchData()
  }
)
</script>

<style scoped>
.search-container {
  margin-bottom: 16px;
  display: flex;
  gap: 8px;
}

.option-container {
  margin-bottom: 15px;
  display: flex;
}

.pagination-container {
  display: flex;
  justify-content: flex-end;
}

.search-input {
  width: 200px;
}
</style>
