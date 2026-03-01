import type { AxiosProgressEvent, GenericAbortSignal } from 'axios'

export interface ResponseDataType<T = unknown> {
  code: number
  message: string
  data: T
}

export interface RequestConfig {
  headers?: Record<string, string>
  onDownloadProgress?: (_progressEvent: AxiosProgressEvent) => void
  signal?: GenericAbortSignal
  hold?: boolean
  timeout?: number
  isToken?: boolean
}
