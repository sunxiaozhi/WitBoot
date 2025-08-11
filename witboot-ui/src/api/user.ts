import { GET, POST } from '@/utils/request'
import { BASE_URL } from '@/config/siteConfig';

// 定义用户模块的 API 路径
const USER_API = {
  LOGIN: '/user/login',
  LIST: '/user/list',
  REGISTER: '/user/register'
};

export const register = (data = {}) => {
  const url = BASE_URL + USER_API.REGISTER;
  const { ...restData } = data;

  return POST(url, restData);
};

/**
 * 用户登录接口
 * @param data - 登录请求参数
 * @returns Promise
 */
export const login = (data = {}) => {
  const url = BASE_URL + USER_API.LOGIN;
  const { ...restData } = data;

  return POST(url, restData);
};

/**
 * 获取用户列表
 * @param params 请求参数
 * @returns Promise
 */
export const selectUserList = (params = {}) => {
  const { ...restParams } = params; // 使用解构确保参数安全
  const url = BASE_URL + USER_API.LIST;

  return GET(url, restParams, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true
  });
};
