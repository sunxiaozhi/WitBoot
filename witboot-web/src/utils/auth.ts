import { useMenuTabsStore } from '@/stores/menuTabsStore'
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import router from '@/router'

/**
 * 设置accessToken
 * @param accessToken
 * @returns
 */
const ACCESS_TOKEN_KEY = 'accessToken'
const PERSIST_KEYS = ['witboot-menu-tabs', 'witboot-menu-collapse']

const getStorage = () => localStorage

export const setAccessToken = (accessToken: string) =>
  getStorage().setItem(ACCESS_TOKEN_KEY, accessToken)

/**
 * 获取accessToken
 * @returns
 */
export const getAccessToken = () => getStorage().getItem(ACCESS_TOKEN_KEY)

/**
 * 清除accessToken
 * @returns
 */
export const clearAccessToken = () => getStorage().removeItem(ACCESS_TOKEN_KEY)

/**
 * 登出
 * @returns
 */
export const logout = () => {
  const menuTabsStore = useMenuTabsStore()
  const menuCollapseStore = useMenuCollapseStore()
  menuTabsStore.closeAllTabs()
  menuCollapseStore.$reset()
  clearAccessToken()
  PERSIST_KEYS.forEach(key => localStorage.removeItem(key))

  router.replace('/login')
}
