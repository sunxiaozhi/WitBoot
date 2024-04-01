import { GET, POST } from '@/utils/request'

export const login = (data = {}) => POST('http://localhost:8080/user/login', data)
