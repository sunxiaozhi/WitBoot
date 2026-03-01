import { ref, watch } from 'vue'
import type { ElTree } from 'element-plus'

type DepartmentMember = {
  id: number
  name: string
  role: string
  avatar: string
}

type DepartmentDetail = {
  name: string
  owner: string
  count: number
  updatedAt: string
  description: string
  members: DepartmentMember[]
}

type DepartmentNode = {
  id: number
  label: string
  count: number
  children?: DepartmentNode[]
}

const FALLBACK_DEPARTMENT: DepartmentDetail = {
  name: '未知部门',
  owner: '待配置',
  count: 0,
  updatedAt: '—',
  description: '暂无描述信息',
  members: [],
}

export function useDepartmentPage() {
  const treeRef = ref<InstanceType<typeof ElTree>>()
  const filterText = ref('')

  const treeData: DepartmentNode[] = [
    {
      id: 1,
      label: '集团总部',
      count: 260,
      children: [
        {
          id: 11,
          label: '产品研发中心',
          count: 120,
          children: [
            { id: 111, label: '前端组', count: 28 },
            { id: 112, label: '后端组', count: 42 },
            { id: 113, label: '测试组', count: 22 },
          ],
        },
        {
          id: 12,
          label: '业务运营中心',
          count: 86,
          children: [
            { id: 121, label: '增长运营', count: 24 },
            { id: 122, label: '客户成功', count: 18 },
          ],
        },
        {
          id: 13,
          label: '人力与行政',
          count: 54,
          children: [
            { id: 131, label: '招聘组', count: 12 },
            { id: 132, label: '员工发展', count: 10 },
          ],
        },
      ],
    },
  ]

  const departments: Record<number, DepartmentDetail> = {
    1: {
      name: '集团总部',
      owner: '陈晓',
      count: 260,
      updatedAt: '2026-02-05',
      description: '负责集团战略规划与资源统筹，覆盖各业务线的支持协同。',
      members: [
        { id: 1, name: '陈晓', role: '负责人', avatar: 'https://i.pravatar.cc/100?img=12' },
        { id: 2, name: '程璇', role: '战略负责人', avatar: 'https://i.pravatar.cc/100?img=32' },
        { id: 3, name: '邱涵', role: '运营负责人', avatar: 'https://i.pravatar.cc/100?img=18' },
      ],
    },
    11: {
      name: '产品研发中心',
      owner: '周启明',
      count: 120,
      updatedAt: '2026-02-04',
      description: '主导产品规划、架构设计与交付，负责端到端研发协作。',
      members: [
        { id: 4, name: '周启明', role: '负责人', avatar: 'https://i.pravatar.cc/100?img=5' },
        { id: 5, name: '李语', role: '前端负责人', avatar: 'https://i.pravatar.cc/100?img=49' },
        { id: 6, name: '马越', role: '后端负责人', avatar: 'https://i.pravatar.cc/100?img=52' },
      ],
    },
    12: {
      name: '业务运营中心',
      owner: '顾卿',
      count: 86,
      updatedAt: '2026-02-03',
      description: '推动业务增长、用户留存与收入提升，协同销售与产品落地。',
      members: [
        { id: 7, name: '顾卿', role: '负责人', avatar: 'https://i.pravatar.cc/100?img=20' },
        { id: 8, name: '宋婉', role: '增长负责人', avatar: 'https://i.pravatar.cc/100?img=14' },
        { id: 9, name: '郑齐', role: '客户成功', avatar: 'https://i.pravatar.cc/100?img=9' },
      ],
    },
    13: {
      name: '人力与行政',
      owner: '沈清',
      count: 54,
      updatedAt: '2026-02-02',
      description: '负责组织发展、人才招聘与行政支持，保障组织高效运转。',
      members: [
        { id: 10, name: '沈清', role: '负责人', avatar: 'https://i.pravatar.cc/100?img=45' },
        { id: 11, name: '姚宁', role: '招聘主管', avatar: 'https://i.pravatar.cc/100?img=22' },
      ],
    },
  }

  const activeDepartment = ref<DepartmentDetail>(departments[1] ?? FALLBACK_DEPARTMENT)

  const handleNodeClick = (data: { id: number; label: string; count: number }) => {
    activeDepartment.value = departments[data.id] ?? {
      ...FALLBACK_DEPARTMENT,
      name: data.label,
      count: data.count || 0,
    }
  }

  const defaultProps = {
    children: 'children',
    label: 'label',
  }

  const filterNode = (value: string, data: { label: string }) => {
    if (!value) return true
    return data.label.includes(value)
  }

  watch(filterText, val => {
    treeRef.value?.filter(val)
  })

  return {
    treeRef,
    filterText,
    treeData,
    activeDepartment,
    handleNodeClick,
    defaultProps,
    filterNode,
  }
}
