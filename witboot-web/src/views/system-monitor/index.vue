<template>
  <div class="monitor-page">
    <el-row :gutter="16" class="stat-row">
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>CPU 使用率</span>
            <el-tag size="small" :type="cpuStatus.tagType" effect="plain">{{ cpuStatus.label }}</el-tag>
          </div>
          <el-statistic :value="cpuUsageDisplay" suffix="%" />
          <el-progress :percentage="cpuUsagePercent" :stroke-width="8" :status="cpuStatus.progressStatus" />
          <div class="stat-foot">峰值 {{ cpuPeakDisplay }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>内存使用率</span>
            <el-tag size="small" :type="memoryStatus.tagType" effect="plain">{{ memoryStatus.label }}</el-tag>
          </div>
          <el-statistic :value="memoryUsedPercentDisplay" suffix="%" />
          <el-progress :percentage="memoryUsedPercent" :stroke-width="8" :status="memoryStatus.progressStatus" />
          <div class="stat-foot">已使用 {{ memoryUsedReadable }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>存储使用率</span>
            <el-tag size="small" :type="diskStatus.tagType" effect="plain">{{ diskStatus.label }}</el-tag>
          </div>
          <el-statistic :value="diskUsedPercentDisplay" suffix="%" />
          <el-progress :percentage="diskUsedPercent" :stroke-width="8" :status="diskStatus.progressStatus" />
          <div class="stat-foot">剩余 {{ diskFreeReadable }}</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>JVM 堆使用率</span>
            <el-tag size="small" :type="jvmStatus.tagType" effect="plain">{{ jvmStatus.label }}</el-tag>
          </div>
          <el-statistic :value="jvmHeapUsedPercentDisplay" suffix="%" />
          <el-progress :percentage="jvmHeapUsedPercent" :stroke-width="8" :status="jvmStatus.progressStatus" />
          <div class="stat-foot">{{ jvmHeapUsedReadable }} / {{ jvmHeapMaxReadable }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="info-row">
      <el-col :span="8" class="info-col">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>服务器信息</span>
              <span class="panel-meta">实时</span>
            </div>
          </template>
          <el-descriptions :column="2" size="small" class="desc">
            <el-descriptions-item label="主机名">{{ serverInfo.hostName }}</el-descriptions-item>
            <el-descriptions-item label="操作系统">{{ serverOsLabel }}</el-descriptions-item>
            <el-descriptions-item label="架构">{{ serverInfo.architecture }}</el-descriptions-item>
            <el-descriptions-item label="IP 地址">{{ serverInfo.hostAddress }}</el-descriptions-item>
            <el-descriptions-item label="厂商">{{ serverInfo.systemManufacturer }}</el-descriptions-item>
            <el-descriptions-item label="型号">{{ serverInfo.systemModel }}</el-descriptions-item>
            <el-descriptions-item label="序列号">{{ serverInfo.systemSerialNumber }}</el-descriptions-item>
            <el-descriptions-item label="启动时间">{{ serverBootTimeLabel }}</el-descriptions-item>
            <el-descriptions-item label="系统时间">{{ systemTimeLabel }}</el-descriptions-item>
            <el-descriptions-item label="磁盘总量">{{ diskTotalReadable }}</el-descriptions-item>
            <el-descriptions-item label="磁盘剩余">{{ diskFreeReadable }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="8" class="info-col">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>CPU 信息</span>
              <span class="panel-meta">实时</span>
            </div>
          </template>
          <el-descriptions :column="2" size="small" class="desc">
            <el-descriptions-item label="型号">{{ cpuInfo.name }}</el-descriptions-item>
            <el-descriptions-item label="厂商">{{ cpuInfo.vendor }}</el-descriptions-item>
            <el-descriptions-item label="物理 CPU">{{ cpuInfo.physicalPackageCount }}</el-descriptions-item>
            <el-descriptions-item label="物理核心">{{ cpuInfo.physicalProcessorCount }}</el-descriptions-item>
            <el-descriptions-item label="逻辑核心">{{ cpuInfo.logicalProcessorCount }}</el-descriptions-item>
            <el-descriptions-item label="最大频率">{{ cpuInfo.maximumFrequencyReadable }}</el-descriptions-item>
            <el-descriptions-item label="当前频率">{{ cpuCurrentFrequencyLabel }}</el-descriptions-item>
            <el-descriptions-item label="负载">{{ cpuLoadAverageLabel }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
      <el-col :span="8" class="info-col">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>JVM 信息</span>
              <span class="panel-meta">实时</span>
            </div>
          </template>
          <el-descriptions :column="2" size="small" class="desc">
            <el-descriptions-item label="Java 版本">{{ jvmInfo.javaVersion }}</el-descriptions-item>
            <el-descriptions-item label="JVM 名称">{{ jvmInfo.javaVirtualMachineName }}</el-descriptions-item>
            <el-descriptions-item label="JVM 版本">{{ jvmInfo.javaVirtualMachineVersion }}</el-descriptions-item>
            <el-descriptions-item label="Java Home">{{ jvmInfo.javaHome }}</el-descriptions-item>
            <el-descriptions-item label="堆内存">{{ jvmHeapUsedReadable }} / {{ jvmHeapMaxReadable }}</el-descriptions-item>
            <el-descriptions-item label="堆使用率">{{ jvmHeapUsedPercentDisplay }}%</el-descriptions-item>
            <el-descriptions-item label="线程数">{{ jvmInfo.threadCount }}</el-descriptions-item>
            <el-descriptions-item label="守护线程">{{ jvmInfo.daemonThreadCount }}</el-descriptions-item>
            <el-descriptions-item label="已加载类">{{ jvmInfo.loadedClassCount }}</el-descriptions-item>
            <el-descriptions-item label="运行时长">{{ jvmUptimeLabel }}</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="bottom-row">
      <el-col :span="24">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>磁盘 IO</span>
              <span class="panel-meta">采样 {{ diskIoSampleIntervalLabel }}</span>
            </div>
          </template>
          <el-table :data="diskIoTable" stripe height="100%" class="service-table equal-columns">
            <el-table-column prop="name" label="设备" />
            <el-table-column prop="model" label="型号" />
            <el-table-column prop="sizeReadable" label="容量" />
            <el-table-column prop="readReadablePerSecond" label="读速率" />
            <el-table-column prop="writeReadablePerSecond" label="写速率" />
            <el-table-column prop="readOperationsPerSecond" label="读 IOPS" />
            <el-table-column prop="writeOperationsPerSecond" label="写 IOPS" />
            <el-table-column prop="currentQueueLength" label="队列" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
    <el-row :gutter="16" class="bottom-row">
      <el-col :span="24">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>网络接口</span>
              <span class="panel-meta">采样 {{ networkSampleIntervalLabel }}</span>
            </div>
          </template>
          <el-table :data="networkTable" stripe height="100%" class="service-table equal-columns">
            <el-table-column prop="displayName" label="名称" />
            <el-table-column prop="name" label="标识" />
            <el-table-column prop="ipv4Label" label="IPv4" />
            <el-table-column prop="speedReadable" label="速率" />
            <el-table-column prop="receiveReadablePerSecond" label="下行" />
            <el-table-column prop="transmitReadablePerSecond" label="上行" />
            <el-table-column prop="receivePacketsPerSecond" label="RX PPS" />
            <el-table-column prop="transmitPacketsPerSecond" label="TX PPS" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from 'vue'
import { selectSystemMonitorInfo } from '@/api/systemMonitor.ts'
import { ElMessage } from 'element-plus'

type MonitorInfo = Record<string, any>

const monitorInfo = ref<MonitorInfo | null>(null)

const cpuInfo = computed(() => monitorInfo.value?.cpuInfo ?? {})
const memoryInfo = computed(() => monitorInfo.value?.memoryInfo ?? {})
const diskInfo = computed(() => monitorInfo.value?.diskInfo ?? {})
const jvmInfo = computed(() => monitorInfo.value?.jvmInfo ?? {})
const serverInfo = computed(() => monitorInfo.value?.serverInfo ?? {})
const networkInfo = computed(() => monitorInfo.value?.networkInfo ?? {})

const cpuUsagePercent = computed(() => Number(cpuInfo.value.cpuUsagePercent ?? 0))
const cpuUsageDisplay = computed(() => formatPercentValue(cpuUsagePercent.value))
const cpuPeakDisplay = computed(() => {
  const perCore = cpuInfo.value.perCoreUsagePercent ?? []
  if (!Array.isArray(perCore) || perCore.length === 0) {
    return '-'
  }
  return `${formatPercentValue(Math.max(...perCore))}%`
})

const memoryUsedPercent = computed(() => Number(memoryInfo.value.usedPercent ?? 0))
const memoryUsedPercentDisplay = computed(() => formatPercentValue(memoryUsedPercent.value))
const memoryUsedReadable = computed(() => memoryInfo.value.usedReadable ?? '-')

const diskTotals = computed(() => {
  const partitions = diskInfo.value.partitions ?? []
  const totalBytes = partitions.reduce((sum: number, item: any) => sum + (item.totalBytes || 0), 0)
  const usedBytes = partitions.reduce((sum: number, item: any) => sum + (item.usedBytes || 0), 0)
  const freeBytes = partitions.reduce((sum: number, item: any) => sum + (item.freeBytes || 0), 0)
  const usedPercent = totalBytes ? (usedBytes / totalBytes) * 100 : 0
  return { totalBytes, usedBytes, freeBytes, usedPercent }
})

const diskUsedPercent = computed(() => Number(diskTotals.value.usedPercent))
const diskUsedPercentDisplay = computed(() => formatPercentValue(diskUsedPercent.value))
const diskTotalReadable = computed(() => formatBytes(diskTotals.value.totalBytes))
const diskFreeReadable = computed(() => formatBytes(diskTotals.value.freeBytes))

const jvmHeapUsedPercent = computed(() => Number(jvmInfo.value.heapUsedPercentOfMax ?? 0))
const jvmHeapUsedPercentDisplay = computed(() => formatPercentValue(jvmHeapUsedPercent.value))
const jvmHeapUsedReadable = computed(() => jvmInfo.value.heapUsedReadable ?? '-')
const jvmHeapMaxReadable = computed(() => jvmInfo.value.heapMaxReadable ?? '-')

const serverOsLabel = computed(() => {
  const family = serverInfo.value.operatingSystemFamily ?? '-'
  const version = serverInfo.value.operatingSystemVersion ?? ''
  const build = serverInfo.value.operatingSystemBuildNumber ?? ''
  const versionLabel = version ? ` ${version}` : ''
  const buildLabel = build ? ` (Build ${build})` : ''
  return `${family}${versionLabel}${buildLabel}`.trim()
})

const serverBootTimeLabel = computed(() => formatDateTime(serverInfo.value.bootTime))
const systemTimeLabel = computed(() => formatDateTime(monitorInfo.value?.timestamp))

const cpuCurrentFrequencyLabel = computed(() => {
  const frequencies = cpuInfo.value.currentFrequenciesHertz ?? []
  if (!Array.isArray(frequencies) || frequencies.length === 0) {
    return cpuInfo.value.currentFrequenciesReadable?.[0] ?? '-'
  }
  const avg = frequencies.reduce((sum: number, value: number) => sum + (value || 0), 0) / frequencies.length
  return formatHertz(avg)
})

const cpuLoadAverageLabel = computed(() => {
  const load = cpuInfo.value.loadAverage ?? []
  if (!Array.isArray(load) || load.length === 0 || load.every((value: number) => value < 0)) {
    return '-'
  }
  return load.map((value: number) => value.toFixed(2)).join(' / ')
})

const jvmUptimeLabel = computed(() => formatDuration(jvmInfo.value.uptimeMilliseconds))

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
  const items = diskInfo.value.inputOutputStatistics ?? []
  if (!Array.isArray(items)) return []
  return items.map((item: any) => ({
    name: item.name ?? '-',
    model: item.model ?? '-',
    sizeReadable: item.sizeReadable ?? formatBytes(item.sizeBytes),
    readReadablePerSecond: item.readReadablePerSecond ?? '-',
    writeReadablePerSecond: item.writeReadablePerSecond ?? '-',
    readOperationsPerSecond: formatRate(item.readOperationsPerSecond),
    writeOperationsPerSecond: formatRate(item.writeOperationsPerSecond),
    currentQueueLength: item.currentQueueLength ?? '-',
  }))
})

const networkSampleIntervalLabel = computed(() => {
  const interval = networkInfo.value.sampleIntervalMilliseconds
  if (!interval && interval !== 0) return '-'
  return `${interval} ms`
})

const networkTable = computed(() => {
  const items = networkInfo.value.interfaces ?? []
  if (!Array.isArray(items)) return []
  return items.map((item: any) => ({
    name: item.name ?? '-',
    displayName: item.displayName ?? item.name ?? '-',
    ipv4Label: formatIpList(item.ipv4Addresses),
    speedReadable: item.speedReadable ?? '-',
    receiveReadablePerSecond: item.receiveReadablePerSecond ?? '-',
    transmitReadablePerSecond: item.transmitReadablePerSecond ?? '-',
    receivePacketsPerSecond: formatRate(item.receivePacketsPerSecond),
    transmitPacketsPerSecond: formatRate(item.transmitPacketsPerSecond),
  }))
})

const fetchData = async () => {
  try {
    const res = await selectSystemMonitorInfo({})
    monitorInfo.value = res?.data?.monitorInfo ?? null
  } catch (error) {
    console.error('Failed to fetch data:', error)
    ElMessage.error('数据获取失败')
  }
}

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
  if (!bytes && bytes !== 0) return '-'
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

onMounted(() => fetchData())
</script>

<style scoped lang="scss">
.monitor-page {
  min-height: 100%;
  display: flex;
  flex-direction: column;
  padding: 20px;
  gap: 20px;
  background: radial-gradient(circle at top, rgba(102, 126, 234, 0.08), transparent 55%),
    #f5f7fa;
  min-height: 0;

  --card-radius: 14px;
  --card-shadow: 0 16px 32px rgba(15, 23, 42, 0.08);
  --card-shadow-strong: 0 22px 44px rgba(15, 23, 42, 0.12);
  --primary-gradient: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}


.stat-card,
.panel-card {
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow);
  border: none;
}

.stat-card:hover,
.panel-card:hover {
  box-shadow: var(--card-shadow-strong);
}

.stat-head {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
  font-size: 12px;
  color: #94a3b8;
}

.stat-foot {
  margin-top: 8px;
  font-size: 12px;
  color: #64748b;
}

.panel-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  color: #0f172a;
}

.panel-meta {
  font-size: 12px;
  color: #94a3b8;
}

.info-row {
  align-items: stretch;
}

.info-col :deep(.el-card) {
  height: 100%;
}

.info-col :deep(.el-card__body) {
  display: flex;
  flex-direction: column;
}

.desc :deep(.el-descriptions__label) {
  width: 80px;
  color: #64748b;
}


.service-table :deep(.el-table__cell) {
  padding: 8px 0;
}

.service-table :deep(.el-table__body tr) {
  height: 40px;
}

.equal-columns :deep(table) {
  table-layout: fixed;
}

.equal-columns :deep(colgroup col) {
  width: 12.5% !important;
}

</style>
