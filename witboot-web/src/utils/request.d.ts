export interface ResponseDataType {
  code: number
  message: string
  data: any
}

export interface requestConfigType {
  headers?: { [key: string]: string }
  onDownloadProgress?: (progressEvent: AxiosProgressEvent) => void
  signal?: GenericAbortSignal
  hold?: boolean
  timeout?: number
  isToken?: boolean
}
