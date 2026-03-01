import { ElMessage } from 'element-plus'
import { ref } from 'vue'

const formatJson = (value: unknown) => {
  if (value === null || value === undefined || value === '') return '-'
  try {
    const parsed = typeof value === 'string' ? JSON.parse(value) : value
    return JSON.stringify(parsed, null, 2)
  } catch {
    return String(value)
  }
}

const escapeHtml = (value: string) =>
  value.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')

export function useOperationLogDetail() {
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
    out = out.replace(new RegExp(`${keyToken}(\\d+)${keyToken}`, 'g'), (_, index) => {
      const key = keys[Number(index)] || ''
      return `<span class="json-key">${key}</span>`
    })
    out = out.replace(
      /-?\d+(?:\.\d+)?(?:[eE][+-]?\d+)?/g,
      match => `<span class="json-number">${match}</span>`,
    )
    out = out.replace(/\btrue\b|\bfalse\b/g, match => `<span class="json-boolean">${match}</span>`)
    out = out.replace(/\bnull\b/g, '<span class="json-null">null</span>')
    return out
  }

  return {
    collapsed,
    toggleCollapse,
    getScrollbarHeight,
    copyJson,
    highlightJson,
  }
}
