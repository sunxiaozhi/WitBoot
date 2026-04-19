import { useMenuTabsStore } from '@/stores/menuTabsStore'
import { useMenuCollapseStore } from '@/stores/menuCollapseStore'
import { pinia } from '@/stores'
import router from '@/router'

/**
 * 设置accessToken
 * @param accessToken
 * @returns
 */
const ACCESS_TOKEN_KEY = 'accessToken'
const PERSIST_KEYS = ['witboot-menu-tabs', 'witboot-menu-collapse']

const getStorage = () => localStorage
const LOGIN_PATH = '/login'

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

const clearAuthState = () => {
  try {
    const menuTabsStore = useMenuTabsStore(pinia)
    const menuCollapseStore = useMenuCollapseStore(pinia)
    menuTabsStore.closeAllTabs()
    menuCollapseStore.$reset()
  } catch {
    // store 不可用时仍继续清理本地登录态
  }

  clearAccessToken()
  PERSIST_KEYS.forEach(key => localStorage.removeItem(key))
}

/**
 * 登出
 * @returns
 */
export const logout = () => {
  clearAuthState()
  const loginUrl = router.resolve(LOGIN_PATH).href
  window.location.replace(loginUrl)
}
