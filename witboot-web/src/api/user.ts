import { DELETE, GET, POST } from '@/utils/request'

// 定义用户模块的 API 路径
const USER_API = {
  REGISTER: '/user/register',
  LOGIN: '/user/login',
  LIST: '/user/list',
  UPDATE: '/user/update',
  DELETE: '/user/delete',
}

/**
 * 用户注册接口
 * @param data - 注册请求参数
 * @returns Promise
 */
export const register = <T = unknown>(data: Record<string, unknown> = {}) => {
  const { ...restData } = data

  return POST<T>(USER_API.REGISTER, restData)
}

/**
 * 用户登录接口
 * @param data - 登录请求参数
 * @returns Promise
 */
export const login = <T = unknown>(data: Record<string, unknown> = {}) => {
  const { ...restData } = data

  return POST<T>(USER_API.LOGIN, restData)
}

/**
 * 获取用户列表
 * @param params 请求参数
 * @returns Promise
 */
export const selectUserList = <T = unknown>(params: Record<string, unknown> = {}) => {
  const { ...restParams } = params // 使用解构确保参数安全

  return GET<T>(USER_API.LIST, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true,
  })
}

/**
 * 获取更新用户信息
 * @param data 更新请求参数
 * @returns Promise
 */
export const updateUserInfo = <T = unknown>(data: Record<string, unknown> = {}) => {
  const { ...restData } = data // 使用解构确保参数安全

  return POST<T>(USER_API.UPDATE, restData, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true,
  })
}

/**
 * 删除用户
 * @param params 删除请求参数
 * @returns Promise
 */
export const deleteUser = <T = unknown>(params: Record<string, unknown> = {}) => {
  const { ...restParams } = params // 使用解构确保参数安全

  return DELETE<T>(USER_API.DELETE, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true,
  })
}
