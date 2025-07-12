import { POST } from '@/utils/request'

import { BASE_URL } from '@/config/siteConfig'

export const login = (data = {}) => POST(BASE_URL + '/user/login', data)
