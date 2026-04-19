import { GET } from '@/utils/request'

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

  return GET(SYSTEM_MONITOR_API.MONITOR_INFO, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true,
  })
}
