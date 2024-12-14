import { GET, POST } from '@/utils/request'

export const login = (data = {}) => POST('http://localhost:8010/user/login', data)
