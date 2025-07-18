import { POST } from '@/utils/request';
import { BASE_URL } from '@/config/siteConfig';

// 定义用户模块的 API 路径
const USER_API = {
  LOGIN: '/user/login'
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
