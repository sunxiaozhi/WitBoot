import { GET, DELETE } from '@/utils/request'

// 定义登录日志相关的 API 路径
const LOGIN_LOG_API = {
  LIST: '/loginLog/list',
  INFO: '/loginLog/info',
  DELETE: '/loginLog/delete',
}

/**
 * 获取登录日志列表
 * @param params 请求参数
 * @returns Promise
 */
export const selectLoginLogList = (params = {}) => {
  const { ...restParams } = params // 使用解构确保参数安全

  return GET(LOGIN_LOG_API.LIST, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true,
  })
}

/**
 * 获取登录日志信息
 * @param id 请求参数
 * @returns Promise
 */
export const loginLogInfo = (id: string | number) => {
  return GET(
    `${LOGIN_LOG_API.INFO}/${id}`,
    {},
    {
      headers: { 'Content-Type': 'application/json' },
      isToken: true,
    },
  )
}

/**
 * 删除登录日志
 * @param params 删除请求参数
 * @returns Promise
 */
export const deleteLoginLog = (params = {}) => {
  const { ...restParams } = params // 使用解构确保参数安全

  return DELETE(LOGIN_LOG_API.DELETE, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true,
  })
}
