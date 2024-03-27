import { httpGet, httpPost } from '@/utils/request'

export const login = (data = {}, params = {}) => httpPost({ url: 'http://localhost:8080/user/login', data, params })