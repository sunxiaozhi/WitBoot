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
      <el-button type="primary" @click="dialog = true">新增</el-button>
      <el-button type="danger" disabled @click="handleBatchDelete">批量删除</el-button>
    </div>

    <!-- 列表表格 -->
    <div class="table-container"></div>
    <el-table
      ref="multipleTableRef"
      :data="tableData"
      row-key="id" border
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

    <div class="drawer-container">
      <el-drawer
        v-model="dialog"
        title="人员-新增"
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
              <el-input v-model="form.password" autocomplete="off" />
            </el-form-item>
            <el-form-item label="确认密码" prop="rePassword" :label-width="formLabelWidth">
              <el-input v-model="form.rePassword" autocomplete="off" />
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
                type="date"
                aria-label="请选择日期"
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
            <el-button type="primary" :loading="loading" @click="onClick">
              {{ loading ? '提交中 ...' : '提交' }}
            </el-button>
          </div>
        </div>
      </el-drawer>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, watch, onMounted } from 'vue'
import { selectUserList } from '@/api/user.ts'
import { debounce } from 'lodash-es'
import { ElMessage, ElMessageBox } from 'element-plus'
import { register } from '@/api/user'
import type { FormInstance, FormRules } from 'element-plus'

type Gender = '1' | '2' // 1-男，2-女

interface User {
  id: number          // 用户唯一标识
  username: string    // 登录用户名
  name: string        // 用户真实姓名
  password: string    // 登录密码（加密存储）
  rePassword: string    // 登录密码（加密存储）
  mobile: string      // 手机号码
  gender: Gender      // 性别
  birthday: Date      // 生日
  description: string // 用户描述或备注
}

const formLabelWidth = '80px'
let timer

const dialog = ref(false)
//const loading = ref(false)

//const selectable = (row: User) => ![1, 2].includes(row.id)

// 存储选中的行数据
const selectedRows = ref<User[]>([])

// 存储选中行的 ID
const selectedIds = ref<number[]>([])

// 处理选中行变化
const handleSelectionChange = (selection: User[]) => {
  selectedRows.value = selection
  // 提取选中行的 ID
  selectedIds.value = selection.map(item => item.id)
  console.log('选中的ID:', selectedIds.value)
}

// 修改 handleDelete 方法以使用选中的 ID
const handleBatchDelete = () => {
  selectedRows.value = selection
  // 提取选中行的 ID
  selectedIds.value = selection.map(item => item.id)
  console.log('选中的ID:', selectedIds.value)
}

const form = reactive<User>({
  username: '',
  name: '',
  password: '',
  rePassword: '',
  mobile: '',
  gender: '',
  birthday: '',
  description: ''
})

const ruleFormRef = ref<FormInstance>()
// 补充完整的表单校验规则
const rules = reactive<FormRules<User>>({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '长度应为3到20个字符', trigger: 'blur' },
  ],
  name: [
    { required: true, message: '请输入姓名', trigger: 'blur' },
    { min: 2, max: 20, message: '长度应为2到20个字符', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度应为6到20个字符', trigger: 'blur' },
  ],
  rePassword: [
    { required: true, message: '请确认密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== form.password) {
          callback(new Error('两次输入的密码不一致'));
        } else {
          callback();
        }
      },
      trigger: 'blur'
    }
  ],
  mobile: [
    { required: false, message: '请输入手机号', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '手机号格式不正确', trigger: 'blur' }
  ],
  gender: [
    { required: false, message: '请选择性别', trigger: 'change' }
  ],
  birthday: [
    { required: false, message: '请选择生日', trigger: 'change' }
  ]
});


// 搜索关键词
const searchKeyword = ref('')

// 表格数据
const tableData = ref([])

// 加载状态
const loading = ref(false)

// 分页状态
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 获取数据
const fetchData = async () => {
  loading.value = true
  try {
    const res = await selectUserList({
      pageNo: pagination.currentPage,
      pageSize: pagination.pageSize
    })
    tableData.value = res.data.list
    pagination.total = res.data.total
  } catch (error) {
    console.error('Failed to fetch data:', error)
  } finally {
    loading.value = false
  }
}

// 防抖搜索
const debouncedSearch = debounce(() => {
  fetchData()
}, 300)

// 搜索触发
const handleSearch = () => {
  pagination.currentPage = 1
  debouncedSearch()
}

// 编辑触发
const handleEdit = (row: User) => {
  const id = row.id
  console.log('编辑行的ID:', id)
  console.log('编辑行的数据:', row)
  console.log('编辑')
}

// 删除、批量触发
const handleDelete = (row: User) => {
  const id = row.id
  console.log('删除行的ID:', id)
  console.log('删除')
}

// 页面大小变化
const handleSizeChange = (size) => {
  pagination.pageSize = size
  fetchData()
}

// 当前页码变化
const handleCurrentChange = (page) => {
  pagination.currentPage = page
  fetchData()
}

// 初始化加载
onMounted(async () => {
  await fetchData()
})

// 监听分页参数变化
watch(
  () => [pagination.currentPage, pagination.pageSize],
  () => {
    fetchData()
  }
)

const onClick = () => {
  if (!ruleFormRef.value) return

  ruleFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      register(form).then((res) => {
        if (res.code === 200) {
          ElMessage.success('提交成功')
          dialog.value = false
        } else {
          ElMessage.error('提交失败')
        }
      }).finally(() => {
        loading.value = false
      })
    }
  })
}

const handleClose = (done) => {
  if (loading.value) {
    return
  }
  ElMessageBox.confirm('Do you want to submit?')
    .then(() => {
      loading.value = true
      console.log('提交')
      register(form).then((res) => {
        if (res.code === 200) {
          ElMessage.success('提交成功')
        } else {
          ElMessage.error('提交失败')
        }
      })
      timer = setTimeout(() => {
        done()
        // 动画关闭需要一定的时间
        setTimeout(() => {
          loading.value = false
        }, 400)
      }, 2000)
    })
    .catch(() => {
      // catch error
    })
}

const cancelForm = () => {
  loading.value = false
  dialog.value = false
  clearTimeout(timer)
}
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
