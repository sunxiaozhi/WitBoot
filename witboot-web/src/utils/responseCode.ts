// 标准 HTTP 状态码（使用中文描述）
export const HTTP_STATUS_CODE = {
  400: '错误请求',
  401: '未授权',
  403: '禁止访问',
  404: '未找到',
  500: '服务器内部错误',
} as const

// Token 相关错误码（业务码）
export const TOKEN_ERROR_CODE = {
  1001: '登录信息已过期，请重新登录',
  1002: '登录凭证无效，请重新登录',
} as const

// 通用业务错误码
export const BUSINESS_ERROR_CODE = {
  2001: '参数错误',
  2002: '操作失败，请重试',
} as const

export type HttpStatusCode = keyof typeof HTTP_STATUS_CODE
export type TokenErrorCode = keyof typeof TOKEN_ERROR_CODE
export type BusinessErrorCode = keyof typeof BUSINESS_ERROR_CODE
