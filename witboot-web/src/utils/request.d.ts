import type { AxiosProgressEvent } from 'axios'

export interface ResponseDataType<T = unknown> {
  code: number
  message: string
  data: T
}

export interface RequestConfig {
  headers?: Record<string, string>
  onDownloadProgress?: (progressEvent: AxiosProgressEvent) => void
  signal?: AbortSignal
  hold?: boolean
  timeout?: number
  isToken?: boolean
}
