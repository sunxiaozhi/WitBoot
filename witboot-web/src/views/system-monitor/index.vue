<template>
  <div class="monitor-page">
    <el-row :gutter="16" class="top-row">
      <el-col :span="24">
        <div class="top-strip">
          <span class="top-meta">最后采集：{{ lastSampleTimeLabel }}</span>
          <el-button
            size="small"
            type="primary"
            plain
            :loading="isRefreshing"
            :icon="Refresh"
            circle
            aria-label="强制刷新"
            @click="handleForceRefresh"
          />
        </div>
      </el-col>
    </el-row>
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
import { Refresh } from '@element-plus/icons-vue'
import { useSystemMonitor } from '@/composables/useSystemMonitor'

const {
  cpuInfo,
  jvmInfo,
  serverInfo,
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
} = useSystemMonitor()
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

.top-row {
  align-items: center;
}

.top-strip {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 8px;
  padding: 4px 2px 0;
}

.top-meta {
  font-size: 12px;
  color: #64748b;
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

