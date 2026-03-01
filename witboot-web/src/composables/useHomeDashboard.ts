import { onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { selectOperationLogList } from '@/api/operationLog'
import { selectLoginLogList } from '@/api/loginLog'

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

const HOME_LOG_PAGE_SIZE = 20
const HOME_LOGIN_PAGE_SIZE = 20

const TODOS = [
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

const mapLoginLog = (item: LoginLogApiItem): HomeLoginActivity => {
  const name = item.userName || '-'
  const resultText = item.status || item.result || '登录'
  return {
    time: item.loginTime || '-',
    text: `${name} ${resultText}`,
  }
}

export function useHomeDashboard() {
  const operationLogs = ref<HomeOperationLog[]>([])
  const loginActivities = ref<HomeLoginActivity[]>([])
  const todos = TODOS

  const fetchOperationLogs = async () => {
    try {
      const res = (await selectOperationLogList({
        pageNo: 1,
        pageSize: HOME_LOG_PAGE_SIZE,
      })) as { data?: { list?: OperationLogApiItem[] } }
      const list = res.data?.list || []
      operationLogs.value = list.map(mapOperationLog)
    } catch (error) {
      console.error('Failed to fetch operation logs:', error)
      ElMessage.error('操作日志获取失败')
    }
  }

  const fetchLoginActivities = async () => {
    try {
      const res = (await selectLoginLogList({
        pageNo: 1,
        pageSize: HOME_LOGIN_PAGE_SIZE,
      })) as { data?: { list?: LoginLogApiItem[] } }
      const list = res.data?.list || []
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

  return {
    operationLogs,
    loginActivities,
    todos,
  }
}
