<template>
  <el-drawer
    v-model="visible"
    title="日志详情"
    direction="rtl"
    size="40%"
    class="detail-drawer detail-drawer-theme"
  >
    <div class="drawer-content">
      <div class="detail-card">
        <div class="card-title">基础信息</div>
        <el-descriptions direction="vertical" :column="1" border class="detail-descriptions">
          <el-descriptions-item label="IP 地址" :span="1">
            {{ data?.ip || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="用户名" :span="1">
            {{ data?.userName || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="地址" :span="1">
            {{ data?.location || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="User-Agent" :span="1">
            <el-scrollbar height="80px">
              <pre class="user-agent-code">{{ data?.userAgent || '-' }}</pre>
            </el-scrollbar>
          </el-descriptions-item>
          <el-descriptions-item label="操作系统" :span="1">
            {{ data?.os || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="设备类型" :span="1">
            {{ data?.device || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="浏览器" :span="1">
            {{ data?.browser || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="登录时间" :span="1">
            {{ data?.loginTime || '-' }}
          </el-descriptions-item>
        </el-descriptions>
      </div>
    </div>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'

interface LoginLogDetail {
  ip: string
  userName: string
  location: string
  userAgent: string
  os: string
  device: string
  browser: string
  loginTime: string
}

const props = defineProps<{
  modelValue: boolean
  data: LoginLogDetail | null
}>()

const emit = defineEmits<{
  (e: 'update:modelValue', v: boolean): void
}>()

const visible = ref(false)

watch(
  () => props.modelValue,
  v => (visible.value = v),
)

watch(visible, v => emit('update:modelValue', v))
</script>

<style scoped lang="scss">
.detail-drawer {
  :deep(.el-drawer__header) {
    margin-bottom: 6px;
    padding: 20px 24px;
    border-bottom: 1px solid #eef2f7;

    .el-drawer__title {
      font-size: 18px;
      font-weight: 600;
      color: #1f2937;
    }
  }

  :deep(.el-drawer__body) {
    padding: 0;
  }
}

.drawer-content {
  padding: 8px 24px 20px;
  height: 100%;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 12px;

  .detail-card {
    background: #fff;
  }

  .card-title {
    font-size: 13px;
    font-weight: 600;
    color: #374151;
    margin: 0 0 8px 2px;
  }

  .detail-descriptions {
    :deep(.el-descriptions__body) {
      border-radius: 10px;
      box-shadow: 0 1px 4px rgba(0, 0, 0, 0.06);
      overflow: hidden;
    }

    :deep(.el-descriptions__label) {
      font-weight: 500;
      color: #6b7280;
      background: #fafafa;
      width: 120px;
    }

    :deep(.el-descriptions__content) {
      color: #374151;
    }

    .user-agent-code {
      background: #f8fafc;
      padding: 10px 12px;
      border-radius: 8px;
      font-size: 12.5px;
      line-height: 1.6;
      color: #374151;
      margin: 0;
      white-space: pre-wrap;
      word-break: break-word;
      font-family: 'Consolas', 'Monaco', 'Courier New', monospace;
    }
  }
}
</style>
