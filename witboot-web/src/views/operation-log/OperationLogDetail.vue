<template>
  <el-drawer
    v-model="visible"
    title="日志详情"
    direction="rtl"
    size="40%"
    class="detail-drawer detail-drawer-theme"
  >
    <div class="drawer-content">
      <el-tabs tab-position="left" class="detail-tabs">
        <el-tab-pane label="基础信息">
          <el-descriptions direction="vertical" :column="1" border class="detail-descriptions">
            <el-descriptions-item label="IP 地址" :span="1">
              {{ data?.ip || '-' }}
            </el-descriptions-item>
            <el-descriptions-item label="请求地址" :span="1">
              {{ data?.location || '-' }}
            </el-descriptions-item>
            <el-descriptions-item label="请求方法" :span="1">
              {{ data?.method || '-' }}
            </el-descriptions-item>
            <el-descriptions-item label="URI" :span="1">
              {{ data?.uri || '-' }}
            </el-descriptions-item>
            <el-descriptions-item label="请求时间" :span="1">
              {{ data?.requestTime || '-' }}
            </el-descriptions-item>
            <el-descriptions-item label="耗时(ms)" :span="1">
              {{ data?.wasteTime || '-' }}
            </el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
        <el-tab-pane label="请求与响应">
          <el-descriptions direction="vertical" :column="1" border class="detail-descriptions">
            <el-descriptions-item label="请求参数" :span="2">
              <div class="json-block">
                <div class="json-toolbar">
                  <span class="json-title">JSON</span>
                  <div class="json-actions">
                    <el-button text size="small" @click="toggleCollapse('requestParam')">
                      {{ collapsed.requestParam ? '展开' : '收起' }}
                    </el-button>
                    <el-button text size="small" @click="copyJson(data?.requestParam)">
                      复制
                    </el-button>
                  </div>
                </div>
                <el-scrollbar :height="getScrollbarHeight('requestParam')">
                  <pre class="json-code" v-html="highlightJson(data?.requestParam)"></pre>
                </el-scrollbar>
              </div>
            </el-descriptions-item>
            <el-descriptions-item label="请求体" :span="2">
              <div class="json-block">
                <div class="json-toolbar">
                  <span class="json-title">JSON</span>
                  <div class="json-actions">
                    <el-button text size="small" @click="toggleCollapse('requestBody')">
                      {{ collapsed.requestBody ? '展开' : '收起' }}
                    </el-button>
                    <el-button text size="small" @click="copyJson(data?.requestBody)">
                      复制
                    </el-button>
                  </div>
                </div>
                <el-scrollbar :height="getScrollbarHeight('requestBody')">
                  <pre class="json-code" v-html="highlightJson(data?.requestBody)"></pre>
                </el-scrollbar>
              </div>
            </el-descriptions-item>
            <el-descriptions-item label="响应结果" :span="2">
              <div class="json-block">
                <div class="json-toolbar">
                  <span class="json-title">JSON</span>
                  <div class="json-actions">
                    <el-button text size="small" @click="toggleCollapse('responseResult')">
                      {{ collapsed.responseResult ? '展开' : '收起' }}
                    </el-button>
                    <el-button text size="small" @click="copyJson(data?.responseResult)">
                      复制
                    </el-button>
                  </div>
                </div>
                <el-scrollbar :height="getScrollbarHeight('responseResult')">
                  <pre class="json-code" v-html="highlightJson(data?.responseResult)"></pre>
                </el-scrollbar>
              </div>
            </el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
      </el-tabs>
    </div>
  </el-drawer>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { ElMessage } from 'element-plus'

interface OperationLogDetail {
  ip: string
  location: string
  method: string
  uri: string
  requestTime: string
  wasteTime: string
  requestParam: string
  requestBody: string
  responseResult: string
}

const props = defineProps<{
  modelValue: boolean
  data: OperationLogDetail | null
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

const formatJson = (value: unknown) => {
  if (value === null || value === undefined || value === '') return '-'
  try {
    const parsed = typeof value === 'string' ? JSON.parse(value) : value
    return JSON.stringify(parsed, null, 2)
  } catch {
    return String(value)
  }
}

const collapsed = ref({
  requestParam: false,
  requestBody: false,
  responseResult: false,
})

type JsonSection = keyof typeof collapsed.value

const toggleCollapse = (key: JsonSection) => {
  collapsed.value[key] = !collapsed.value[key]
}

const getScrollbarHeight = (key: JsonSection) => {
  const map = {
    requestParam: { collapsed: '80px', expanded: '160px' },
    requestBody: { collapsed: '80px', expanded: '160px' },
    responseResult: { collapsed: '120px', expanded: '280px' },
  }
  return collapsed.value[key] ? map[key].collapsed : map[key].expanded
}

const copyJson = async (value: unknown) => {
  const text = formatJson(value)
  try {
    await navigator.clipboard.writeText(text)
    ElMessage.success('已复制')
  } catch {
    const textarea = document.createElement('textarea')
    textarea.value = text
    textarea.style.position = 'fixed'
    textarea.style.opacity = '0'
    document.body.appendChild(textarea)
    textarea.select()
    try {
      document.execCommand('copy')
      ElMessage.success('已复制')
    } catch {
      ElMessage.error('复制失败')
    } finally {
      document.body.removeChild(textarea)
    }
  }
}

const escapeHtml = (value: string) =>
  value.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')

const highlightJson = (value: unknown) => {
  const pretty = formatJson(value)
  if (pretty === '-') return '-'
  const escaped = escapeHtml(pretty)
  const keyToken = '__JSON_KEY__'
  const keys: string[] = []

  let out = escaped.replace(/"(?:\\.|[^"\\])*"(?=\\s*:)/g, match => {
    keys.push(match)
    return `${keyToken}${keys.length - 1}${keyToken}`
  })

  out = out.replace(/"(?:\\.|[^"\\])*"/g, match => `<span class="json-string">${match}</span>`)
  out = out.replace(new RegExp(`${keyToken}(\\d+)${keyToken}`, 'g'), (_, i) => {
    const key = keys[Number(i)] || ''
    return `<span class="json-key">${key}</span>`
  })

  out = out.replace(
    /-?\d+(?:\.\d+)?(?:[eE][+-]?\d+)?/g,
    match => `<span class="json-number">${match}</span>`,
  )
  out = out.replace(/\btrue\b|\bfalse\b/g, match => `<span class="json-boolean">${match}</span>`)
  out = out.replace(/\bnull\b/g, match => `<span class="json-null">null</span>`)
  return out
}
</script>

<style scoped lang="scss">
.drawer-content {
  padding: 8px 24px 20px;
  height: 100%;
  overflow-y: auto;
  display: flex;
  flex-direction: column;
  gap: 12px;

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

    .json-code {
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

  :deep(.detail-tabs) {
    width: 100%;
  }

  :deep(.detail-tabs .el-tabs__header) {
    margin-right: 16px;
  }

  :deep(.detail-tabs .el-tabs__nav-wrap::after) {
    background-color: transparent;
  }

  :deep(.detail-tabs .el-tabs__item) {
    padding: 10px 12px;
    font-size: 13px;
    color: #6b7280;
    background: transparent;
  }

  :deep(.detail-tabs .el-tabs__item:hover) {
    background: transparent;
  }

  :deep(.detail-tabs .el-tabs__item.is-active) {
    color: #1f2937;
  }

  :deep(.detail-tabs .el-tabs__active-bar) {
    background-color: #1f2937;
    height: 2px;
  }



  .json-block {
    display: flex;
    flex-direction: column;
    gap: 8px;
  }

  .json-toolbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: #6b7280;
    font-size: 12px;
  }

  .json-title {
    font-weight: 600;
    letter-spacing: 0.3px;
  }

  .json-actions {
    display: flex;
    align-items: center;
    gap: 6px;
  }

  :deep(.json-actions .el-button) {
    padding: 4px 6px;
  }

  :deep(.json-actions .el-button + .el-button) {
    margin-left: 0;
  }

  .json-key {
    color: #667eea;
  }

  .json-string {
    color: #16a34a;
  }

  .json-number {
    color: #f59e0b;
  }

  .json-boolean {
    color: #ef4444;
  }

  .json-null {
    color: #9ca3af;
  }
}
</style>
