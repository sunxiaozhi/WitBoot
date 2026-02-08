import { GET } from '@/utils/request'
import { BASE_URL } from '@/config/siteConfig'

// 定义系统监控相关的 API 路径
const SYSTEM_MONITOR_API = {
  MONITOR_INFO: '/systemMonitor/monitorInfo',
}

/**
 * 获取系统监控信息
 * @param params 请求参数
 * @returns Promise
 */
export const selectSystemMonitorInfo = (params = {}) => {
  const { ...restParams } = params // 使用解构确保参数安全
  const url = BASE_URL + SYSTEM_MONITOR_API.MONITOR_INFO

  return GET(url, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true,
  })
}
