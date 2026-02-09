<template>
  <div class="department-container">
    <div class="page-header">
      <div class="header-left">
        <h2></h2>
        <div class="header-actions">
          <el-button type="primary" class="primary-btn">
            <el-icon><Plus /></el-icon>
            新增部门
          </el-button>
          <el-button class="ghost-btn">
            <el-icon><Upload /></el-icon>
            导入
          </el-button>
        </div>
      </div>
    </div>

    <div class="content-grid">
      <div class="tree-panel">
        <div class="panel-header">
          <span>部门管理</span>
          <div class="panel-actions">
            <el-input v-model="filterText" placeholder="搜索部门" clearable class="filter-input">
              <template #prefix>
                <el-icon><Search /></el-icon>
              </template>
            </el-input>
          </div>
        </div>

        <el-tree
          ref="treeRef"
          class="org-tree"
          :data="treeData"
          :props="defaultProps"
          :filter-node-method="filterNode"
          default-expand-all
          node-key="id"
          highlight-current
          @node-click="handleNodeClick"
        >
          <template #default="{ node, data }">
            <div class="tree-node">
              <el-icon class="node-icon"><OfficeBuilding /></el-icon>
              <span class="node-label">{{ node.label }}</span>
              <span class="node-count">{{ data.count }}人</span>
            </div>
          </template>
        </el-tree>
      </div>

      <div class="detail-panel">
        <div class="panel-header">
          <span>部门详情</span>
          <div class="panel-actions">
            <el-button text size="small">编辑</el-button>
            <el-button text size="small">配置成员</el-button>
          </div>
        </div>

        <div class="detail-card">
          <div class="detail-row">
            <span class="detail-label">部门名称</span>
            <span class="detail-value">{{ activeDepartment.name }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">负责人</span>
            <span class="detail-value">{{ activeDepartment.owner }}</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">成员数量</span>
            <span class="detail-value">{{ activeDepartment.count }} 人</span>
          </div>
          <div class="detail-row">
            <span class="detail-label">更新时间</span>
            <span class="detail-value">{{ activeDepartment.updatedAt }}</span>
          </div>
          <div class="detail-row full">
            <span class="detail-label">描述</span>
            <span class="detail-value">{{ activeDepartment.description }}</span>
          </div>
        </div>

        <div class="member-panel">
          <div class="panel-header">
            <span>核心成员</span>
            <div class="panel-actions">
              <el-button text size="small">查看全部</el-button>
            </div>
          </div>
          <div class="member-list">
            <div v-for="member in activeDepartment.members" :key="member.id" class="member-item">
              <el-avatar :size="36" :src="member.avatar" />
              <div class="member-info">
                <div class="member-name">{{ member.name }}</div>
                <div class="member-role">{{ member.role }}</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import type { ElTree } from 'element-plus'
import { OfficeBuilding, Search, Plus, Upload } from '@element-plus/icons-vue'

const treeRef = ref<InstanceType<typeof ElTree>>()
const filterText = ref('')

const treeData = [
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

const departments = {
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

const activeDepartment = ref(departments[1])

const handleNodeClick = (data: { id: number; label: string; count: number }) => {
  activeDepartment.value = departments[data.id as keyof typeof departments] ?? {
    name: data.label,
    owner: '待配置',
    count: data.count || 0,
    updatedAt: '—',
    description: '暂无描述信息',
    members: [],
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
</script>

<style scoped lang="scss">
.department-container {
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 16px;
  gap: 16px;
  background: #f5f7fa;

  --card-radius: 12px;
  --card-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;

  h2 {
    margin: 0 0 4px;
    font-size: 20px;
    color: #111827;
  }

  p {
    margin: 0;
    font-size: 13px;
    color: #6b7280;
  }
}

.header-left {
  display: flex;
  align-items: center;
  gap: 12px;
}

.header-actions {
  display: flex;
  gap: 10px;

  .primary-btn {
    background: var(--primary-gradient);
    border: none;
  }

  .ghost-btn {
    border: 1px solid #e5e7eb;
  }
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 1.2fr;
  gap: 16px;
  flex: 1;
  min-height: 0;
}

.tree-panel,
.detail-panel {
  background: #fff;
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 16px;
  min-height: 0;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  color: #111827;
}

.panel-actions {
  display: flex;
  gap: 8px;
  align-items: center;
}

.filter-input {
  width: 200px;

  :deep(.el-input__wrapper) {
    border-radius: 8px;
    border: 1px solid #e5e7eb;
    box-shadow: none;
    transition: border-color 0.3s;

    &:hover {
      border-color: #cbd5f5;
    }

    &.is-focus {
      border-color: #667eea;
    }
  }
}

.org-tree {
  flex: 1;
  overflow: auto;

  :deep(.el-tree-node__content) {
    height: 42px;
    border-radius: 8px;
    margin: 4px 0;
    transition: background 0.2s;

    &:hover {
      background: #f5f7fa;
    }
  }

  :deep(.el-tree-node__content.is-current) {
    background: rgba(102, 126, 234, 0.12);
  }
}

.tree-node {
  width: 100%;
  display: flex;
  align-items: center;
  gap: 8px;
}

.node-icon {
  color: #667eea;
}

.node-label {
  flex: 1;
  font-size: 13px;
  color: #1f2937;
}

.node-count {
  font-size: 12px;
  color: #9ca3af;
}

.detail-card {
  background: #f8fafc;
  border: 1px solid #eef2f7;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.detail-row {
  display: grid;
  grid-template-columns: 80px 1fr;
  gap: 12px;
  font-size: 13px;
  color: #374151;

  &.full {
    grid-template-columns: 80px 1fr;
  }
}

.detail-label {
  color: #6b7280;
}

.member-panel {
  background: #f8fafc;
  border: 1px solid #eef2f7;
  border-radius: 12px;
  padding: 16px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.member-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.member-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px;
  border-radius: 10px;
  background: #fff;
  border: 1px solid #eef2f7;
}

.member-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.member-name {
  font-size: 13px;
  color: #111827;
}

.member-role {
  font-size: 12px;
  color: #9ca3af;
}

@media (max-width: 1024px) {
  .content-grid {
    grid-template-columns: 1fr;
  }
}
</style>
