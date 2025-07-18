import { GET } from '@/utils/request';
import { BASE_URL } from '@/config/siteConfig';

// 定义操作日志相关的 API 路径
const OPERATION_LOG_API = {
  LIST: '/operationLog/list'
};

/**
 * 获取操作日志列表
 * @param params 请求参数
 * @returns Promise
 */
export const selectOperationLogList = (params = {}) => {
  const { ...restParams } = params; // 使用解构确保参数安全
  const url = BASE_URL + OPERATION_LOG_API.LIST;

  return GET(url, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true
  });
};
