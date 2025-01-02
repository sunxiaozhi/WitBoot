/**
 * 设置accessToken
 * @param accessToken
 * @returns
 */
export const setAccessToken = (accessToken: string) =>
  localStorage.setItem('accessToken', accessToken)

/**
 * 获取accessToken
 * @returns
 */
export const getAccessToken = () => localStorage.getItem('accessToken')

/**
 * 获取accessToken
 * @returns
 */
export const clearAccessToken = () => localStorage.removeItem('accessToken')
