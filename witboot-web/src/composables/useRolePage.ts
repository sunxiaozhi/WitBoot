import { ref } from 'vue'
import type { ElTree } from 'element-plus'

export function useRolePage() {
  const query = ref({ keyword: '' })
  const permTreeRef = ref<InstanceType<typeof ElTree>>()

  const roleTable = [
    { name: '系统管理员', code: 'SUPER_ADMIN', status: '启用' },
    { name: '运营管理员', code: 'OPERATOR', status: '启用' },
    { name: '审计员', code: 'AUDITOR', status: '启用' },
    { name: '访客', code: 'GUEST', status: '停用' },
  ]

  const permissionTree = [
    {
      id: 1,
      label: '系统管理',
      path: '/system',
      children: [
        { id: 11, label: '人员管理', path: '/user' },
        { id: 12, label: '部门管理', path: '/department' },
        { id: 13, label: '字典管理', path: '/dictionary' },
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

  return {
    query,
    permTreeRef,
    roleTable,
    permissionTree,
  }
}
