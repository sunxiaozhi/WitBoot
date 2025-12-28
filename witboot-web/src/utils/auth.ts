import { useMenuTabsStore } from '@/stores/menuTabsStore'
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import router from '@/router'

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

/**
 * 登出
 * @returns
 */
export const logout = () => {
  const stores = [useMenuTabsStore(), useMenuCollapseStore()]
  stores.forEach((store) => store.$reset())
  localStorage.clear()

  router.push('/login')
}
