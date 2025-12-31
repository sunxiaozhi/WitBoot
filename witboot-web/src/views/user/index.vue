<template>
  <div class="user-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-input
        v-model="searchKeyword"
        placeholder="请输入用户名、姓名"
        clearable
        class="search-input"
        @keyup.enter="handleSearch"
      />
      <el-button type="primary" @click="handleSearch" :loading="searchLoading">
        <el-icon><Search /></el-icon>
        搜索
      </el-button>
    </div>

    <div class="option-container">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        新增
      </el-button>
      <el-button :disabled="selectedIds.length === 0" @click="handleBatchDelete">
        <el-icon><Delete /></el-icon>
        批量删除
      </el-button>
    </div>

    <!-- 表格承载区 -->
    <div class="table-wrapper">
      <el-table
        ref="multipleTableRef"
        :data="tableData"
        row-key="id"
        border
        style="width: 100%"
        :loading="tableLoading"
        element-loading-text="数据加载中..."
        @selection-change="handleSelectionChange"
        class="user-table"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="username" label="用户名" min-width="120">
          <template #default="scope">
            <el-tag type="primary">{{ scope.row.username }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="name" label="姓名" min-width="100" />
        <el-table-column prop="mobile" label="手机号" min-width="120" />
        <el-table-column prop="gender" label="性别" width="80" align="center">
          <template #default="scope">
            <el-tag :type="scope.row.gender === '1' ? 'primary' : 'danger'">
              {{ scope.row.gender === '1' ? '男' : '女' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="birthday" label="生日" width="120" align="center" />
        <el-table-column prop="description" label="描述" min-width="150" show-overflow-tooltip />
        <el-table-column fixed="right" label="操作" width="150" align="center">
          <template #default="scope">
            <el-button
              link
              type="primary"
              size="small"
              @click.prevent="handleEdit(scope.row)"
              class="action-button"
            >
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button
              link
              type="danger"
              size="small"
              @click.prevent="handleDelete(scope.row)"
              class="action-button"
            >
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页组件 -->
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 30, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        background
      />
    </div>

    <!-- 新增/编辑抽屉 -->
    <el-drawer
      v-model="dialog"
      :title="form.id ? '编辑用户' : '新增用户'"
      :before-close="handleClose"
      direction="rtl"
      size="40%"
      class="user-drawer"
    >
      <div class="drawer__content">
        <el-form
          ref="ruleFormRef"
          :model="form"
          :rules="rules"
          label-position="right"
          label-width="100px"
        >
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="form.username" autocomplete="off" placeholder="请输入用户名" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" autocomplete="off" placeholder="请输入姓名" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="密码" prop="password" v-if="!form.id">
                <el-input
                  v-model="form.password"
                  type="password"
                  autocomplete="off"
                  placeholder="请输入密码"
                  show-password
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="确认密码" prop="rePassword" v-if="!form.id">
                <el-input
                  v-model="form.rePassword"
                  type="password"
                  autocomplete="off"
                  placeholder="请再次输入密码"
                  show-password
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="手机号" prop="mobile">
                <el-input v-model="form.mobile" autocomplete="off" placeholder="请输入手机号" />
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

          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="生日" prop="birthday">
                <el-date-picker
                  v-model="form.birthday"
                  type="date"
                  value-format="YYYY-MM-DD"
                  placeholder="请选择日期"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item label="描述" prop="description">
            <el-input
              v-model="form.description"
              type="textarea"
              :rows="3"
              placeholder="请输入描述信息"
            />
          </el-form-item>
        </el-form>

        <div class="drawer__footer">
          <el-button @click="cancelForm" size="large">取消</el-button>
          <el-button type="primary" :loading="submitLoading" @click="submitForm" size="large">
            <el-icon v-if="!submitLoading"><Check /></el-icon>
            {{ submitLoading ? '提交中...' : '提交' }}
          </el-button>
        </div>
      </div>
    </el-drawer>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref, watch } from 'vue'
import { debounce } from 'lodash-es'
import type { FormInstance, FormRules } from 'element-plus'
import { ElMessage, ElMessageBox } from 'element-plus'
import { register, selectUserList, updateUserInfo, deleteUser } from '@/api/user'
import { Check, Delete, Edit, Plus, Search } from '@element-plus/icons-vue'

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

const dialog = ref(false)
const searchKeyword = ref('')
const tableData = ref<User[]>([])
const tableLoading = ref(false)
const searchLoading = ref(false)
const submitLoading = ref(false)
const selectedRows = ref<User[]>([])
const selectedIds = ref<number[]>([])

const form = reactive<UserForm>({
  id: undefined,
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
  mobile: [{ pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  birthday: [{ required: false, message: '请选择生日', trigger: 'change' }]
})

const handleSelectionChange = (selection: User[]) => {
  selectedRows.value = selection
  selectedIds.value = selection.map((item) => item.id)
}

const fetchData = async () => {
  tableLoading.value = true
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
    tableLoading.value = false
  }
}

const debouncedSearch = debounce(() => {
  searchLoading.value = true
  pagination.currentPage = 1
  fetchData().finally(() => {
    searchLoading.value = false
  })
}, 300)

const handleSearch = () => {
  debouncedSearch()
}

const resetForm = () => {
  Object.assign(form, {
    id: undefined,
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
  Object.assign(form, row)
  form.password = ''
  form.rePassword = ''
  dialog.value = true
}

const handleDelete = (row: User) => {
  ElMessageBox.confirm(`确定要删除用户"${row.name}"吗？此操作不可恢复！`, '删除确认', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    draggable: true
  })
    .then(async () => {
      try {
        const res = await deleteUser({ ids: [row.id] })
        if (res.code === 200) {
          ElMessage.success('删除成功')
          await fetchData()
        } else {
          ElMessage.error(res.message || '删除失败')
        }
      } catch (error) {
        ElMessage.error('删除失败')
        console.error('Delete error:', error)
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
    })
}

const handleBatchDelete = () => {
  if (selectedIds.value.length === 0) {
    ElMessage.warning('请先选择要删除的用户')
    return
  }
  ElMessageBox.confirm(
    `确定要删除选中的${selectedIds.value.length}个用户吗？此操作不可恢复！`,
    '删除确认',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
      draggable: true
    }
  )
    .then(async () => {
      try {
        const res = await deleteUser({ ids: selectedIds.value })
        if (res.code === 200) {
          ElMessage.success('批量删除成功')
          await fetchData()
        } else {
          ElMessage.error(res.message || '批量删除失败')
        }
      } catch (error) {
        ElMessage.error('批量删除失败')
        console.error('Batch delete error:', error)
      }
    })
    .catch(() => {
      ElMessage.info('已取消删除')
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
      submitLoading.value = true
      try {
        const res = form.id ? await updateUserInfo(form as User) : await register(form)
        if (res.code === 200) {
          ElMessage.success(`${form.id ? '更新' : '创建'}成功`)
          dialog.value = false
          resetForm()
          await fetchData()
        } else {
          ElMessage.error(res.message || `${form.id ? '更新' : '创建'}失败`)
        }
      } catch (error) {
        ElMessage.error(`${form.id ? '更新' : '创建'}失败`)
        console.error('Submit error:', error)
      } finally {
        submitLoading.value = false
      }
    }
  })
}

const handleClose = (done: () => void) => {
  if (submitLoading.value) {
    ElMessage.warning('正在提交数据，请稍候')
    return
  }
  ElMessageBox.confirm('确定要关闭表单吗？未保存的数据将会丢失', '确认关闭', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    draggable: true
  })
    .then(() => done())
    .catch(() => {})
}

const cancelForm = () => {
  dialog.value = false
  resetForm()
}

onMounted(async () => {
  await fetchData()
})

watch(
  () => [pagination.currentPage, pagination.pageSize],
  () => {
    fetchData()
  }
)
</script>

<style scoped>
.user-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  min-height: 0;
}

.search-container {
  flex: 0 0 auto;
  margin-bottom: 16px;
  display: flex;
  gap: 8px;

  .search-input,
  .method-select {
    max-width: 200px;
  }
}

.option-container {
  flex: 0 0 auto;
  margin-bottom: 16px;
  display: flex;
  gap: 8px;
}

.table-wrapper {
  flex: 1;
  min-height: 0;
  overflow: auto;
}

.user-table {
  width: 100%;
}

.action-button {
  padding: 4px 8px;
}

.pagination-container {
  flex: 0 0 auto;
  display: flex;
  justify-content: flex-end;
  margin-bottom: 16px;
}

.drawer__content {
  height: 100%;
  display: flex;
  flex-direction: column;
}

.drawer__content :deep(.el-form) {
  flex: 1;
  overflow: auto;
  padding-right: 16px;
}

.drawer__footer {
  flex: 0 0 auto;
  padding-top: 16px;
  display: flex;
  justify-content: flex-end;
  gap: 16px;
  border-top: 1px solid var(--el-border-color-light);
}
</style>
