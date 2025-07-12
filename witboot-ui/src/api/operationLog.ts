import { GET } from '@/utils/request'

import { BASE_URL } from '@/config/siteConfig'

export const selectOperationLogList = (params = {}) =>
  GET(BASE_URL + '/operationLog/list', params, {
    headers: { 'Content-Type': 'application/json' },
    isToken: true
  })
