import { computed, onMounted, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { selectSystemMonitorInfo } from '@/api/systemMonitor'

type SectionData = Record<string, unknown>
type MonitorInfo = Record<string, unknown>

const asSection = (value: unknown): SectionData => {
  if (!value || typeof value !== 'object') return {}
  return value as SectionData
}

const asSectionList = (value: unknown): SectionData[] => {
  if (!Array.isArray(value)) return []
  return value.filter((item): item is SectionData => !!item && typeof item === 'object')
}

const toNumber = (value: unknown) => (typeof value === 'number' ? value : Number(value ?? 0) || 0)
const toStringOr = (value: unknown, fallback = '-') => (typeof value === 'string' ? value : fallback)

const formatDateTime = (value?: string) => {
  if (!value) return '-'
  const date = new Date(value)
  if (Number.isNaN(date.getTime())) return '-'
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}`
}

const formatPercentValue = (value: number) => Number(value.toFixed(2))

const formatBytes = (bytes?: number) => {
  if (bytes == null) return '-'
  const units = ['B', 'KiB', 'MiB', 'GiB', 'TiB', 'PiB']
  let size = bytes
  let index = 0
  while (size >= 1024 && index < units.length - 1) {
    size /= 1024
    index += 1
  }
  return `${size.toFixed(1)} ${units[index]}`
}

const formatHertz = (value?: number) => {
  if (!value && value !== 0) return '-'
  if (value >= 1_000_000_000) return `${(value / 1_000_000_000).toFixed(2)} GHz`
  if (value >= 1_000_000) return `${(value / 1_000_000).toFixed(2)} MHz`
  if (value >= 1_000) return `${(value / 1_000).toFixed(2)} KHz`
  return `${value.toFixed(0)} Hz`
}

const formatDuration = (milliseconds?: number) => {
  if (!milliseconds && milliseconds !== 0) return '-'
  const totalSeconds = Math.floor(milliseconds / 1000)
  const days = Math.floor(totalSeconds / 86400)
  const hours = Math.floor((totalSeconds % 86400) / 3600)
  const minutes = Math.floor((totalSeconds % 3600) / 60)
  if (days > 0) return `${days} 天 ${hours} 小时`
  if (hours > 0) return `${hours} 小时 ${minutes} 分钟`
  return `${minutes} 分钟`
}

const formatRate = (value?: number) => {
  if (!value && value !== 0) return '-'
  return Number(value.toFixed(2))
}

const formatIpList = (value?: string[]) => {
  if (!Array.isArray(value) || value.length === 0) return '-'
  return value.join(', ')
}

const statusFromPercent = (value: number, warn = 70, danger = 85) => {
  if (value >= danger) return { label: '预警', tagType: 'danger', progressStatus: 'exception' }
  if (value >= warn) return { label: '偏高', tagType: 'warning', progressStatus: 'warning' }
  return { label: '稳定', tagType: 'success', progressStatus: 'success' }
}

export function useSystemMonitor() {
  const monitorInfo = ref<MonitorInfo | null>(null)

  const cpuInfo = computed(() => asSection(monitorInfo.value?.cpuInfo))
  const memoryInfo = computed(() => asSection(monitorInfo.value?.memoryInfo))
  const diskInfo = computed(() => asSection(monitorInfo.value?.diskInfo))
  const jvmInfo = computed(() => asSection(monitorInfo.value?.jvmInfo))
  const serverInfo = computed(() => asSection(monitorInfo.value?.serverInfo))
  const networkInfo = computed(() => asSection(monitorInfo.value?.networkInfo))

  const cpuUsagePercent = computed(() => toNumber(cpuInfo.value.cpuUsagePercent))
  const cpuUsageDisplay = computed(() => formatPercentValue(cpuUsagePercent.value))
  const cpuPeakDisplay = computed(() => {
    const perCoreRaw = cpuInfo.value.perCoreUsagePercent
    const perCore = Array.isArray(perCoreRaw) ? perCoreRaw.map(toNumber) : []
    if (perCore.length === 0) {
      return '-'
    }
    return `${formatPercentValue(Math.max(...perCore))}%`
  })

  const memoryUsedPercent = computed(() => toNumber(memoryInfo.value.usedPercent))
  const memoryUsedPercentDisplay = computed(() => formatPercentValue(memoryUsedPercent.value))
  const memoryUsedReadable = computed(() => memoryInfo.value.usedReadable ?? '-')

  const diskTotals = computed(() => {
    const partitions = asSectionList(diskInfo.value.partitions)
    const totals = partitions.reduce(
      (acc: { totalBytes: number; usedBytes: number; freeBytes: number }, item: SectionData) => {
        acc.totalBytes += toNumber(item.totalBytes)
        acc.usedBytes += toNumber(item.usedBytes)
        acc.freeBytes += toNumber(item.freeBytes)
        return acc
      },
      { totalBytes: 0, usedBytes: 0, freeBytes: 0 },
    )
    const usedPercent = totals.totalBytes ? (totals.usedBytes / totals.totalBytes) * 100 : 0
    return { ...totals, usedPercent }
  })

  const diskUsedPercent = computed(() => Number(diskTotals.value.usedPercent))
  const diskUsedPercentDisplay = computed(() => formatPercentValue(diskUsedPercent.value))
  const diskTotalReadable = computed(() => formatBytes(diskTotals.value.totalBytes))
  const diskFreeReadable = computed(() => formatBytes(diskTotals.value.freeBytes))

  const jvmHeapUsedPercent = computed(() => toNumber(jvmInfo.value.heapUsedPercentOfMax))
  const jvmHeapUsedPercentDisplay = computed(() => formatPercentValue(jvmHeapUsedPercent.value))
  const jvmHeapUsedReadable = computed(() => jvmInfo.value.heapUsedReadable ?? '-')
  const jvmHeapMaxReadable = computed(() => jvmInfo.value.heapMaxReadable ?? '-')

  const serverOsLabel = computed(() => {
    const family = toStringOr(serverInfo.value.operatingSystemFamily)
    const version = toStringOr(serverInfo.value.operatingSystemVersion, '')
    const build = toStringOr(serverInfo.value.operatingSystemBuildNumber, '')
    const versionLabel = version ? ` ${version}` : ''
    const buildLabel = build ? ` (Build ${build})` : ''
    return `${family}${versionLabel}${buildLabel}`.trim()
  })

  const sampleTimestamp = computed(() => monitorInfo.value?.timestamp)
  const serverBootTimeLabel = computed(() => formatDateTime(toStringOr(serverInfo.value.bootTime, '')))
  const systemTimeLabel = computed(() => formatDateTime(toStringOr(sampleTimestamp.value, '')))
  const lastSampleTimeLabel = computed(() => formatDateTime(toStringOr(sampleTimestamp.value, '')))

  const cpuCurrentFrequencyLabel = computed(() => {
    const rawFrequencies = cpuInfo.value.currentFrequenciesHertz
    const frequencies = Array.isArray(rawFrequencies) ? rawFrequencies.map(toNumber) : []
    if (frequencies.length === 0) {
      const readable = cpuInfo.value.currentFrequenciesReadable
      return Array.isArray(readable) ? toStringOr(readable[0]) : '-'
    }
    const avg = frequencies.reduce((sum: number, value: number) => sum + value, 0) / frequencies.length
    return formatHertz(avg)
  })

  const cpuLoadAverageLabel = computed(() => {
    const loadRaw = cpuInfo.value.loadAverage
    const load = Array.isArray(loadRaw) ? loadRaw.map(toNumber) : []
    if (load.length === 0 || load.every(value => value < 0)) {
      return '-'
    }
    return load.map(value => value.toFixed(2)).join(' / ')
  })

  const jvmUptimeLabel = computed(() => formatDuration(toNumber(jvmInfo.value.uptimeMilliseconds)))

  const cpuStatus = computed(() => statusFromPercent(cpuUsagePercent.value))
  const memoryStatus = computed(() => statusFromPercent(memoryUsedPercent.value))
  const diskStatus = computed(() => statusFromPercent(diskUsedPercent.value))
  const jvmStatus = computed(() => statusFromPercent(jvmHeapUsedPercent.value, 60, 80))

  const diskIoSampleIntervalLabel = computed(() => {
    const interval = diskInfo.value.inputOutputSampleIntervalMilliseconds
    if (!interval && interval !== 0) return '-'
    return `${interval} ms`
  })

  const diskIoTable = computed(() => {
    const items = asSectionList(diskInfo.value.inputOutputStatistics)
    return items.map((item: SectionData) => ({
      name: toStringOr(item.name),
      model: toStringOr(item.model),
      sizeReadable: toStringOr(item.sizeReadable, formatBytes(toNumber(item.sizeBytes))),
      readReadablePerSecond: toStringOr(item.readReadablePerSecond),
      writeReadablePerSecond: toStringOr(item.writeReadablePerSecond),
      readOperationsPerSecond: formatRate(toNumber(item.readOperationsPerSecond)),
      writeOperationsPerSecond: formatRate(toNumber(item.writeOperationsPerSecond)),
      currentQueueLength: item.currentQueueLength ?? '-',
    }))
  })

  const networkSampleIntervalLabel = computed(() => {
    const interval = networkInfo.value.sampleIntervalMilliseconds
    if (!interval && interval !== 0) return '-'
    return `${interval} ms`
  })

  const networkTable = computed(() => {
    const items = asSectionList(networkInfo.value.interfaces)
    return items.map((item: SectionData) => ({
      name: toStringOr(item.name),
      displayName: toStringOr(item.displayName, toStringOr(item.name)),
      ipv4Label: formatIpList(item.ipv4Addresses as string[] | undefined),
      speedReadable: toStringOr(item.speedReadable),
      receiveReadablePerSecond: toStringOr(item.receiveReadablePerSecond),
      transmitReadablePerSecond: toStringOr(item.transmitReadablePerSecond),
      receivePacketsPerSecond: formatRate(toNumber(item.receivePacketsPerSecond)),
      transmitPacketsPerSecond: formatRate(toNumber(item.transmitPacketsPerSecond)),
    }))
  })

  const fetchData = async (fromCache: boolean) => {
    try {
      const res = (await selectSystemMonitorInfo({ fromCache })) as {
        data?: { monitorInfo?: MonitorInfo }
      }
      monitorInfo.value = res.data?.monitorInfo ?? null
    } catch (error) {
      console.error('Failed to fetch data:', error)
      ElMessage.error('数据获取失败')
    }
  }

  const isRefreshing = ref(false)
  const handleForceRefresh = async () => {
    if (isRefreshing.value) return
    isRefreshing.value = true
    try {
      await fetchData(false)
    } finally {
      isRefreshing.value = false
    }
  }

  onMounted(() => fetchData(true))

  return {
    cpuInfo,
    memoryInfo,
    diskInfo,
    jvmInfo,
    serverInfo,
    networkInfo,
    cpuUsagePercent,
    cpuUsageDisplay,
    cpuPeakDisplay,
    memoryUsedPercent,
    memoryUsedPercentDisplay,
    memoryUsedReadable,
    diskUsedPercent,
    diskUsedPercentDisplay,
    diskTotalReadable,
    diskFreeReadable,
    jvmHeapUsedPercent,
    jvmHeapUsedPercentDisplay,
    jvmHeapUsedReadable,
    jvmHeapMaxReadable,
    serverOsLabel,
    serverBootTimeLabel,
    systemTimeLabel,
    lastSampleTimeLabel,
    cpuCurrentFrequencyLabel,
    cpuLoadAverageLabel,
    jvmUptimeLabel,
    cpuStatus,
    memoryStatus,
    diskStatus,
    jvmStatus,
    diskIoSampleIntervalLabel,
    diskIoTable,
    networkSampleIntervalLabel,
    networkTable,
    isRefreshing,
    handleForceRefresh,
  }
}
