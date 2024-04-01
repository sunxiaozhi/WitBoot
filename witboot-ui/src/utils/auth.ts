/**
 * 设置accessToken
 * @param accessToken
 * @returns
 */
export const setAccessToken = (accessToken: string) => sessionStorage.setItem('accessToken', accessToken)

/**
 * 获取accessToken
 * @returns
 */
export const getAccessToken = () => window.localStorage.getItem('accessToken')

/**
 * 获取accessToken
 * @returns
 */
export const clearAccessToken = () => window.localStorage.removeItem('accessToken')
