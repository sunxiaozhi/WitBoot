import { ref } from 'vue'
import type { ElTree } from 'element-plus'

type MenuStatus = '启用' | '停用'

interface MenuFormData {
  id?: number
  name?: string
  path?: string
  component?: string
  sort?: number
  icon?: string
  status?: MenuStatus
  description?: string
}

export function useMenuPage() {
  const query = ref({ keyword: '' })
  const treeRef = ref<InstanceType<typeof ElTree>>()

  const menuTree = [
    {
      id: 1,
      label: '系统管理',
      path: '/system',
      children: [
        { id: 11, label: '人员管理', path: '/user' },
        { id: 12, label: '部门管理', path: '/department' },
        {
          id: 13,
          label: '字典管理',
          path: '/dictionary',
          children: [
            { id: 131, label: '字典分类', path: '/dictionary/category' },
            { id: 132, label: '字典明细', path: '/dictionary/detail' },
          ],
        },
      ],
    },
    {
      id: 2,
      label: '日志中心',
      path: '/log',
      children: [
        { id: 21, label: '登录日志', path: '/loginLog' },
        { id: 22, label: '操作日志', path: '/operationLog' },
      ],
    },
  ]

  const childMenus = [
    { name: '人员管理', path: '/user', level: '二级', component: 'views/user/index.vue', status: '启用' },
    {
      name: '部门管理',
      path: '/department',
      level: '二级',
      component: 'views/department/index.vue',
      status: '启用',
    },
    {
      name: '字典管理',
      path: '/dictionary',
      level: '二级',
      component: 'views/dictionary/index.vue',
      status: '停用',
    },
    {
      name: '字典分类',
      path: '/dictionary/category',
      level: '三级',
      component: 'views/dictionary/Category.vue',
      status: '启用',
    },
    {
      name: '字典明细',
      path: '/dictionary/detail',
      level: '三级',
      component: 'views/dictionary/Detail.vue',
      status: '启用',
    },
  ]

  const defaultProps = {
    children: 'children',
    label: 'label',
  }

  const drawerVisible = ref(false)
  const currentMenu = ref<MenuFormData | null>(null)

  const handleAddMenu = () => {
    currentMenu.value = null
    drawerVisible.value = true
  }

  const handleMenuSuccess = () => {
    drawerVisible.value = false
  }

  return {
    query,
    treeRef,
    menuTree,
    childMenus,
    defaultProps,
    drawerVisible,
    currentMenu,
    handleAddMenu,
    handleMenuSuccess,
  }
}
