<template>
  <div class="home-page">
    <div class="overview-grid">
      <el-card class="overview-card status-card" shadow="never">
        <div class="overview-header">
          <span>系统状态</span>
          <el-tag size="small" type="success" effect="plain">运行正常</el-tag>
        </div>
        <div class="status-metrics">
          <div class="status-item">
            <span class="status-label">在线用户</span>
            <span class="status-value">1,284</span>
          </div>
          <div class="status-item">
            <span class="status-label">今日登录</span>
            <span class="status-value">362</span>
          </div>
          <div class="status-item warning">
            <span class="status-label">告警</span>
            <span class="status-value">2</span>
          </div>
        </div>
      </el-card>

      <el-card class="overview-card biz-card" shadow="never">
        <div class="overview-header">
          <span>业务数据</span>
        </div>
        <div class="biz-grid">
          <div class="biz-item is-clickable" @click="router.push('/user')">
            <span class="biz-label">人员管理</span>
            <span class="biz-value">1,284</span>
          </div>
          <div class="biz-item is-clickable" @click="router.push('/department')">
            <span class="biz-label">部门数量</span>
            <span class="biz-value">24</span>
          </div>
          <div class="biz-item is-clickable" @click="router.push('/menu')">
            <span class="biz-label">菜单数量</span>
            <span class="biz-value">38</span>
          </div>
          <div class="biz-item is-clickable" @click="router.push('/role')">
            <span class="biz-label">角色数量</span>
            <span class="biz-value">8</span>
          </div>
        </div>
      </el-card>

      <el-card class="overview-card" shadow="never">
        <div class="overview-header">
          <span>待办事项</span>
          <span class="panel-meta">今日 4 项</span>
        </div>
        <ul class="todo-list compact">
          <li v-for="item in todos" :key="item" class="todo-item">
            <span class="todo-dot"></span>
            {{ item }}
          </li>
        </ul>
      </el-card>
    </div>

    <el-row :gutter="16" class="equal-row">
      <el-col :span="16">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header header-left">
              <span>最近操作日志</span>
              <span class="panel-meta">近 1 小时</span>
              <span class="panel-spacer"></span>
              <el-button text size="small" class="panel-action" @click="router.push('/operationLog')">
                查看全部
              </el-button>
            </div>
          </template>
          <el-table :data="operationLogs" stripe height="100%" class="data-table logs-table">
            <el-table-column prop="time" label="时间" width="160" />
            <el-table-column prop="user" label="用户" width="120" />
            <el-table-column prop="action" label="操作" min-width="180" />
            <el-table-column prop="ip" label="IP 地址" width="140" />
            <el-table-column prop="browser" label="浏览器" width="140" />
            <el-table-column prop="result" label="结果" width="120">
              <template #default="{ row }">
                <el-tag
                  :type="row.result === '成功' ? 'success' : row.result === '失败' ? 'danger' : 'info'"
                  effect="plain"
                  size="small"
                >
                  {{ row.result }}
                </el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="panel-card activity-card" shadow="never">
          <template #header>
            <div class="panel-header header-left">
              <span>登录动态</span>
              <span class="panel-meta">近 1 小时</span>
              <span class="panel-spacer"></span>
              <el-button text size="small" class="panel-action" @click="router.push('/loginLog')">
                查看全部
              </el-button>
            </div>
          </template>
          <div class="activity-list scroll-list">
            <div v-for="item in loginActivities" :key="item.time" class="activity-item">
              <span class="activity-time">{{ item.time }}</span>
              <span class="activity-text">{{ item.text }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { selectOperationLogList } from '@/api/operationLog'
import { selectLoginLogList } from '@/api/loginLog'

const router = useRouter()

interface OperationLogApiItem {
  id?: number
  ip?: string
  location?: string
  method?: string
  uri?: string
  requestTime?: string
  wasteTime?: string | number
  result?: string
  browser?: string
  userAgent?: string
  username?: string
  createBy?: string
  operator?: string
}

interface HomeOperationLog {
  time: string
  user: string
  action: string
  ip: string
  browser: string
  result: string
}

const HOME_LOG_PAGE_SIZE = 20
const operationLogs = ref<HomeOperationLog[]>([])

const mapOperationLog = (item: OperationLogApiItem): HomeOperationLog => {
  const actionText =
    item.uri && item.method ? `${item.method} ${item.uri}` : item.uri || item.location || '-'
  return {
    time: item.requestTime || '-',
    user: item.username || item.createBy || item.operator || '-',
    action: actionText,
    ip: item.ip || '-',
    browser: item.browser || item.userAgent || '-',
    result: item.result || '-',
  }
}

const fetchOperationLogs = async () => {
  try {
    const res = await selectOperationLogList({
      pageNo: 1,
      pageSize: HOME_LOG_PAGE_SIZE,
    })
    const list = res?.data?.list || []
    operationLogs.value = list.map(mapOperationLog)
  } catch (error) {
    console.error('Failed to fetch operation logs:', error)
    ElMessage.error('操作日志获取失败')
  }
}

const todos = [
  '审批人员调岗申请',
  '确认部门权限变更',
  '复核异常登录',
  '更新菜单结构说明',
  '核对角色权限清单',
  '提交本周巡检报告',
  '更新部门负责人信息',
  '处理异常登录提醒',
  '核对菜单结构说明',
  '完成新员工账号启用',
  '复核系统参数变更',
  '确认字典项审批',
  '检查日志归档状态',
  '更新通知模板内容',
  '处理待分配工单',
]

interface LoginLogApiItem {
  id?: number
  ip?: string
  userName?: string
  location?: string
  userAgent?: string
  os?: string
  device?: string
  browser?: string
  loginTime?: string
  status?: string
  result?: string
}

interface HomeLoginActivity {
  time: string
  text: string
}

const HOME_LOGIN_PAGE_SIZE = 20
const loginActivities = ref<HomeLoginActivity[]>([])

const mapLoginLog = (item: LoginLogApiItem): HomeLoginActivity => {
  const name = item.userName || '-'
  const resultText = item.status || item.result || '登录'
  return {
    time: item.loginTime || '-',
    text: `${name} ${resultText}`,
  }
}

const fetchLoginActivities = async () => {
  try {
    const res = await selectLoginLogList({
      pageNo: 1,
      pageSize: HOME_LOGIN_PAGE_SIZE,
    })
    const list = res?.data?.list || []
    loginActivities.value = list.map(mapLoginLog)
  } catch (error) {
    console.error('Failed to fetch login logs:', error)
    ElMessage.error('登录日志获取失败')
  }
}

onMounted(() => {
  fetchOperationLogs()
  fetchLoginActivities()
})
</script>

<style scoped lang="scss">
.home-page {
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 16px;
  gap: 16px;
  background: #f5f7fa;
  min-height: 0;

  --card-radius: 14px;
  --card-shadow: 0 8px 24px rgba(15, 23, 42, 0.08);
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.overview-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 16px;
}

.overview-card,
.panel-card {
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
  border: none;
}

.overview-card {
  height: 100%;
}

:deep(.overview-card .el-card__body) {
  display: flex;
  flex-direction: column;
  height: 100%;
  gap: 10px;
  padding: 16px;
}

.overview-card.biz-card :deep(.el-card__body) {
  padding-bottom: 12px;
}

.overview-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 13px;
  color: #0f172a;
  min-height: 24px;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  color: #0f172a;
  min-height: 24px;
}

.panel-header.header-left {
  justify-content: flex-start;
  gap: 8px;
}

.panel-action {
  padding: 0;
}

.panel-spacer {
  flex: 1;
}

.panel-card.activity-card :deep(.el-card__body) {
  padding-bottom: 12px;
}

.panel-meta {
  font-size: 12px;
  color: #94a3b8;
}

.status-metrics {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 8px;
}

.status-item {
  border-radius: 10px;
  padding: 8px 10px;
  background: #f8fafc;
  border: 1px solid #eef2f7;
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.status-label {
  font-size: 12px;
  color: #64748b;
}

.status-value {
  font-size: 16px;
  font-weight: 700;
  color: #0f172a;
}

.status-item.warning .status-value {
  color: #f59e0b;
}

.status-foot {
  margin-top: auto;
  font-size: 12px;
  color: #94a3b8;
  text-align: right;
}

.metric-stack {
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.metric-number {
  font-size: 24px;
  font-weight: 700;
  color: #0f172a;
}

.metric-sub {
  font-size: 12px;
  color: #64748b;
}

.biz-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 10px;
}

.biz-item {
  border-radius: 10px;
  border: 1px solid #eef2f7;
  background: #f8fafc;
  padding: 8px 10px;
  display: flex;
  flex-direction: column;
  gap: 3px;
}

.biz-item.is-clickable {
  cursor: pointer;
  transition: border-color 0.2s ease, box-shadow 0.2s ease, transform 0.2s ease;
}

.biz-item.is-clickable:hover {
  border-color: rgba(102, 126, 234, 0.4);
  box-shadow: 0 6px 14px rgba(15, 23, 42, 0.08);
  transform: translateY(-1px);
}

.biz-label {
  font-size: 12px;
  color: #64748b;
}

.biz-value {
  font-size: 16px;
  font-weight: 700;
  color: #0f172a;
}


.data-table :deep(.el-table__cell) {
  padding: 8px 0;
}

.data-table :deep(.el-table__body tr) {
  height: 40px;
}

.equal-row :deep(.el-col) {
  display: flex;
}

.equal-row .panel-card {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.equal-row .panel-card :deep(.el-card__body) {
  display: flex;
  flex-direction: column;
  flex: 1;
}

.logs-table {
  flex: 1;
}

.logs-table :deep(.el-scrollbar__wrap) {
  max-height: 360px;
}

.logs-table :deep(.el-scrollbar__bar.is-vertical) {
  width: 6px;
}

.logs-table :deep(.el-scrollbar__bar.is-vertical .el-scrollbar__thumb) {
  background-color: rgba(148, 163, 184, 0.7);
  border-radius: 999px;
}

.logs-table :deep(.el-scrollbar__bar.is-vertical .el-scrollbar__thumb:hover) {
  background-color: rgba(100, 116, 139, 0.85);
}

.todo-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.todo-list.compact {
  gap: 8px;
  max-height: 150px;
  overflow-y: auto;
  padding-right: 6px;
  scrollbar-width: thin;
  scrollbar-color: rgba(148, 163, 184, 0.7) transparent;
}

.todo-list.compact::-webkit-scrollbar {
  width: 6px;
}

.todo-list.compact::-webkit-scrollbar-track {
  background: transparent;
}

.todo-list.compact::-webkit-scrollbar-thumb {
  background-color: rgba(148, 163, 184, 0.7);
  border-radius: 999px;
}

.todo-list.compact::-webkit-scrollbar-thumb:hover {
  background-color: rgba(100, 116, 139, 0.85);
}

.todo-item {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 13px;
  color: #374151;
}

.todo-dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: rgba(102, 126, 234, 0.6);
}

.activity-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.activity-list.scroll-list {
  max-height: 408px;
  overflow-y: auto;
  padding-right: 6px;
  scrollbar-width: thin;
  scrollbar-color: rgba(148, 163, 184, 0.7) transparent;
}

.activity-list.scroll-list::-webkit-scrollbar {
  width: 6px;
}

.activity-list.scroll-list::-webkit-scrollbar-track {
  background: transparent;
}

.activity-list.scroll-list::-webkit-scrollbar-thumb {
  background-color: rgba(148, 163, 184, 0.7);
  border-radius: 999px;
}

.activity-list.scroll-list::-webkit-scrollbar-thumb:hover {
  background-color: rgba(100, 116, 139, 0.85);
}

.activity-item {
  display: grid;
  grid-template-columns: 128px 1fr;
  gap: 12px;
  font-size: 13px;
  color: #374151;
}

.activity-time {
  font-weight: 600;
  color: #9ca3af;
}

@media (max-width: 1024px) {
  .overview-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }

  .status-metrics {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .overview-grid {
    grid-template-columns: 1fr;
  }
}
</style>
