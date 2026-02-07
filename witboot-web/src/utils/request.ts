import axios, { type AxiosResponse, type InternalAxiosRequestConfig, type Method } from 'axios'
import { clearAccessToken, getAccessToken } from './auth'
import { TOKEN_ERROR_CODE } from './responseCode'
import type { RequestConfig, ResponseDataType } from './request.d'
import { ElMessage } from 'element-plus'
import router from '@/router'

const instance = axios.create({
  baseURL: '/api', // 开发环境下的跨域解决
  timeout: 5000, //配置超时时间
  withCredentials: true, //携带凭证允许
  headers: {
    'content-type': 'application/json',
  },
})

/* 请求拦截 */
instance.interceptors.request.use(requestAuth, error => Promise.reject(error))

/**
 * 请求开始前的检查
 */
function requestAuth(
  config: InternalAxiosRequestConfig & RequestConfig,
): InternalAxiosRequestConfig {
  if (config.headers && config.isToken) {
    const token = getAccessToken()
    if (token) config.headers.Authorization = `Bearer ${token}`
  }

  // 不需要使用token的请求，直接送出即可
  return config
}

/* 响应拦截 */
instance.interceptors.response.use(responseSuccess, error => {
  return Promise.reject(error)
})

/**
 * 请求成功,检查请求头
 */
function responseSuccess(response: AxiosResponse<ResponseDataType>) {
  return response
}

/**
 * request请求
 *
 * @param url
 * @param data
 * @param config
 * @param method
 * @returns
 */
function request<T>(
  url: string,
  data: Record<string, unknown> = {},
  config: RequestConfig = {},
  method: Method,
): Promise<T> {
  const cleanData = Object.fromEntries(
    Object.entries(data).filter(([, value]) => value !== null && value !== undefined),
  )

  return instance
    .request({
      url: url,
      method: method,
      data: method === 'GET' ? null : cleanData,
      params: method === 'GET' ? cleanData : null, // get请求不携带data，params放在url上
      ...config, // 用户自定义配置，可以覆盖前面的配置
    })
    .then(response => checkResponse<T>(response.data))
    .catch(error => responseError(error))
}

/**
 * 响应数据检查
 */
function checkResponse<T>(data: ResponseDataType<T>) {
  if (data === undefined) {
    return Promise.reject('服务器异常')
  } else if (data.code < 200 || data.code >= 400) {
    return Promise.reject(data)
  }
  return data
}

/**
 * 响应错误
 */
function responseError(error: any) {
  if (!error) {
    return Promise.reject({ reason: '未知错误' })
  }
  if (typeof error === 'string') {
    return Promise.reject({ reason: error })
  }

  if (error.response) {
    // 有报错响应
    const response = error.response

    if (response.status == 403) {
      ElMessage.error('登录信息已过期，请重新登录')
      clearAccessToken()
      router.replace('/login')
      return Promise.reject({ reason: '登录信息已过期，请重新登录' })
    }

    if (response.data.code in TOKEN_ERROR_CODE) {
      clearAccessToken()
      router.replace('/login')
      return Promise.reject({ reason: '登录信息已过期，请重新登录' })
    }

    return Promise.reject({ reason: response.data.reason })
  }

  return Promise.reject({
    ...error,
    reason: '网络出错啦，请检查您的网络或更换浏览器重试',
  })
}

/**
 * api请求方式
 * @param {string} url
 * @param {any} params
 * @param {object} config
 * @returns
 */
export function GET<T>(url: string, params: Record<string, unknown> = {}, config: RequestConfig = {}) {
  return request<T>(url, params, config, 'GET')
}

export function POST<T>(url: string, data: Record<string, unknown> = {}, config: RequestConfig = {}) {
  return request<T>(url, data, config, 'POST')
}

export function PUT<T>(url: string, data: Record<string, unknown> = {}, config: RequestConfig = {}) {
  return request<T>(url, data, config, 'PUT')
}

export function DELETE<T>(url: string, data: Record<string, unknown> = {}, config: RequestConfig = {}) {
  return request<T>(url, data, config, 'DELETE')
}
