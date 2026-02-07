<template>
  <div class="monitor-page">
    <el-card class="hero" shadow="never">
      <div class="hero-grid">
        <div class="hero-summary">
          <el-alert title="系统状态：正常" type="success" show-icon :closable="false" />
          <div class="summary-tags">
            <el-tag size="small" effect="plain" type="success">无重大告警</el-tag>
            <el-tag size="small" effect="plain" type="info">最近更新 1 分钟前</el-tag>
          </div>
          <div class="summary-meta">当前负载稳定，资源使用保持在安全阈值内。</div>
        </div>
        <div class="hero-metrics-grid">
          <div class="metric-tile">
            <span class="metric-label">今日告警</span>
            <el-statistic :value="3" />
          </div>
          <div class="metric-tile">
            <span class="metric-label">平均响应</span>
            <el-statistic :value="168" suffix="ms" />
          </div>
          <div class="metric-tile">
            <span class="metric-label">在线服务</span>
            <el-statistic :value="4" suffix="/5" />
          </div>
          <div class="metric-tile">
            <span class="metric-label">异常节点</span>
            <el-statistic :value="1" />
          </div>
        </div>
      </div>
    </el-card>

    <el-row :gutter="16" class="stat-row">
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>CPU 使用率</span>
            <el-tag size="small" type="success" effect="plain">稳定</el-tag>
          </div>
          <el-statistic :value="42" suffix="%" />
          <el-progress :percentage="42" :stroke-width="8" />
          <div class="stat-foot">峰值 58%</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>内存使用率</span>
            <el-tag size="small" type="warning" effect="plain">偏高</el-tag>
          </div>
          <el-statistic :value="63" suffix="%" />
          <el-progress :percentage="63" :stroke-width="8" status="warning" />
          <div class="stat-foot">已使用 12.4GB</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>存储使用率</span>
            <el-tag size="small" type="danger" effect="plain">预警</el-tag>
          </div>
          <el-statistic :value="71" suffix="%" />
          <el-progress :percentage="71" :stroke-width="8" status="exception" />
          <div class="stat-foot">剩余 428GB</div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="never">
          <div class="stat-head">
            <span>JVM 堆使用率</span>
            <el-tag size="small" type="info" effect="plain">平稳</el-tag>
          </div>
          <el-statistic :value="48" suffix="%" />
          <el-progress :percentage="48" :stroke-width="8" status="success" />
          <div class="stat-foot">4.0 GB / 8.0 GB</div>
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
            <el-descriptions-item label="主机名">witboot-prod-01</el-descriptions-item>
            <el-descriptions-item label="操作系统">Ubuntu 22.04 LTS</el-descriptions-item>
            <el-descriptions-item label="内核版本">5.15.0-92</el-descriptions-item>
            <el-descriptions-item label="IP 地址">10.23.12.41</el-descriptions-item>
            <el-descriptions-item label="机房">华东-1</el-descriptions-item>
            <el-descriptions-item label="可用区">AZ-2</el-descriptions-item>
            <el-descriptions-item label="时区">Asia/Shanghai</el-descriptions-item>
            <el-descriptions-item label="系统时间">2026-02-07 10:26</el-descriptions-item>
            <el-descriptions-item label="磁盘总量">1.2 TB</el-descriptions-item>
            <el-descriptions-item label="磁盘剩余">428 GB</el-descriptions-item>
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
            <el-descriptions-item label="型号">Intel Xeon Gold 6230</el-descriptions-item>
            <el-descriptions-item label="架构">x86_64</el-descriptions-item>
            <el-descriptions-item label="核心数">20</el-descriptions-item>
            <el-descriptions-item label="线程数">40</el-descriptions-item>
            <el-descriptions-item label="主频">2.10 GHz</el-descriptions-item>
            <el-descriptions-item label="缓存">27.5 MB</el-descriptions-item>
            <el-descriptions-item label="负载">1.24 / 5.12</el-descriptions-item>
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
            <el-descriptions-item label="JVM 版本">17.0.8</el-descriptions-item>
            <el-descriptions-item label="启动参数">-Xms4g -Xmx8g</el-descriptions-item>
            <el-descriptions-item label="堆内存">4.0 / 8.0 GB</el-descriptions-item>
            <el-descriptions-item label="非堆内存">320 / 512 MB</el-descriptions-item>
            <el-descriptions-item label="GC 次数">1,284</el-descriptions-item>
            <el-descriptions-item label="线程数">238</el-descriptions-item>
            <el-descriptions-item label="运行时长">12 天 6 小时</el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="16" class="bottom-row">
      <el-col :span="8">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>告警概览</span>
              <span class="panel-meta">今日</span>
            </div>
          </template>
          <div class="alert-list">
            <el-alert title="接口响应超时" type="warning" :closable="false">
              <template #default>/api/order/list 平均响应 620ms</template>
            </el-alert>
            <el-alert title="CPU 短时高负载" type="error" :closable="false">
              <template #default>节点 A 负载峰值 92%</template>
            </el-alert>
            <el-alert title="磁盘使用率提醒" type="info" :closable="false">
              <template #default>节点 B 可用空间低于 30%</template>
            </el-alert>
          </div>
        </el-card>
      </el-col>
      <el-col :span="16">
        <el-card class="panel-card" shadow="never">
          <template #header>
            <div class="panel-header">
              <span>关键服务</span>
              <span class="panel-meta">实时</span>
            </div>
          </template>
          <el-table :data="serviceTable" stripe height="100%" class="service-table">
            <el-table-column prop="name" label="服务名称" min-width="160" />
            <el-table-column prop="status" label="状态" width="120">
              <template #default="{ row }">
                <el-tag :type="row.status === '运行中' ? 'success' : 'danger'" effect="plain" size="small">
                  {{ row.status }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="qps" label="QPS" width="100" />
            <el-table-column prop="latency" label="平均响应(ms)" width="140" />
            <el-table-column prop="updatedAt" label="更新时间" width="160" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
const serviceTable = [
  { name: '用户服务', status: '运行中', qps: 1280, latency: 182, updatedAt: '2026-02-07 10:26' },
  { name: '订单服务', status: '运行中', qps: 860, latency: 264, updatedAt: '2026-02-07 10:26' },
  { name: '消息服务', status: '异常', qps: 120, latency: 920, updatedAt: '2026-02-07 10:25' },
  { name: '日志服务', status: '运行中', qps: 360, latency: 140, updatedAt: '2026-02-07 10:26' },
]
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

:deep(.hero.el-card) {
  border-radius: var(--card-radius);
  box-shadow: var(--card-shadow-strong);
  border: none;
  background: radial-gradient(circle at 20% 0%, rgba(102, 126, 234, 0.15), transparent 55%),
    radial-gradient(circle at 80% 0%, rgba(118, 75, 162, 0.18), transparent 50%), #fff;
  width: 100%;
  min-height: 160px;
  flex: 0 0 auto;
}

:deep(.hero .el-card__body) {
  padding: 24px;
  display: block;
}

.hero-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 32px;
  align-items: stretch;
  min-height: 160px;
}

.hero-kicker {
  font-size: 12px;
  letter-spacing: 2px;
  text-transform: uppercase;
  color: #9ca3af;
  margin: 0 0 10px;
}

.hero-summary {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.summary-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.summary-meta {
  font-size: 14px;
  color: #64748b;
  line-height: 1.6;
}

.hero-metrics-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.metric-tile {
  background: linear-gradient(180deg, rgba(248, 250, 252, 0.9), rgba(248, 250, 252, 0.6));
  border-radius: 12px;
  padding: 14px;
  border: 1px solid #eef2f7;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.metric-label {
  font-size: 12px;
  color: #64748b;
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

.alert-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.service-table :deep(.el-table__cell) {
  padding: 8px 0;
}

.service-table :deep(.el-table__body tr) {
  height: 40px;
}

@media (max-width: 1200px) {
  .hero-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 1024px) {
  .hero-metrics-grid {
    grid-template-columns: 1fr;
  }
}
</style>
