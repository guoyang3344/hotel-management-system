<template>
  <div class="home-container">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-title">房间总数</div>
              <div class="stat-value">{{ statistics.totalRooms || 0 }}</div>
            </div>
            <div class="stat-icon icon-total">
              <el-icon><OfficeBuilding /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-title">空闲房间</div>
              <div class="stat-value stat-success">{{ statistics.availableRooms || 0 }}</div>
            </div>
            <div class="stat-icon icon-available">
              <el-icon><CheckCircle /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-title">已入住</div>
              <div class="stat-value stat-warning">{{ statistics.occupiedRooms || 0 }}</div>
            </div>
            <div class="stat-icon icon-occupied">
              <el-icon><User /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="stat-card" shadow="hover">
          <div class="stat-content">
            <div class="stat-info">
              <div class="stat-title">维护中</div>
              <div class="stat-value stat-danger">{{ statistics.maintenanceRooms || 0 }}</div>
            </div>
            <div class="stat-icon icon-maintenance">
              <el-icon><Tools /></el-icon>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <span>房间状态统计</span>
          </template>
          <div ref="pieChartRef" class="chart-container"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>
            <span>快捷操作</span>
          </template>
          <el-row :gutter="20">
            <el-col :span="8">
              <router-link to="/room/list">
                <el-card class="quick-card" shadow="hover">
                  <div class="quick-item">
                    <el-icon class="quick-icon"><OfficeBuilding /></el-icon>
                    <span>房间管理</span>
                  </div>
                </el-card>
              </router-link>
            </el-col>
            <el-col :span="8">
              <router-link to="/room/type">
                <el-card class="quick-card" shadow="hover">
                  <div class="quick-item">
                    <el-icon class="quick-icon"><Setting /></el-icon>
                    <span>房型管理</span>
                  </div>
                </el-card>
              </router-link>
            </el-col>
            <el-col :span="8">
              <router-link to="/system/user">
                <el-card class="quick-card" shadow="hover">
                  <div class="quick-item">
                    <el-icon class="quick-icon"><User /></el-icon>
                    <span>用户管理</span>
                  </div>
                </el-card>
              </router-link>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getStatistics } from '@/api/home'

const statistics = ref({})
const pieChartRef = ref(null)
let pieChart = null

const fetchStatistics = async () => {
  try {
    const res = await getStatistics()
    statistics.value = res.data
    initPieChart()
  } catch (error) {
    console.error('获取统计数据失败:', error)
  }
}

const initPieChart = () => {
  if (!pieChartRef.value) return
  
  pieChart = echarts.init(pieChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left',
      top: 'center'
    },
    series: [
      {
        name: '房间状态',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 20,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: statistics.value.availableRooms || 0, name: '空闲', itemStyle: { color: '#67C23A' } },
          { value: statistics.value.occupiedRooms || 0, name: '已入住', itemStyle: { color: '#E6A23C' } },
          { value: statistics.value.maintenanceRooms || 0, name: '维护中', itemStyle: { color: '#F56C6C' } }
        ]
      }
    ]
  }
  
  pieChart.setOption(option)
}

const handleResize = () => {
  pieChart?.resize()
}

onMounted(() => {
  fetchStatistics()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  pieChart?.dispose()
  window.removeEventListener('resize', handleResize)
})
</script>

<style scoped>
.home-container {
  padding: 0;
}

.stat-card {
  height: 120px;
}

.stat-content {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100%;
}

.stat-info {
  display: flex;
  flex-direction: column;
}

.stat-title {
  color: #909399;
  font-size: 14px;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.stat-success {
  color: #67C23A;
}

.stat-warning {
  color: #E6A23C;
}

.stat-danger {
  color: #F56C6C;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  color: #fff;
}

.icon-total {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.icon-available {
  background: linear-gradient(135deg, #11998e 0%, #38ef7d 100%);
}

.icon-occupied {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.icon-maintenance {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.chart-container {
  height: 350px;
}

.quick-card {
  cursor: pointer;
  transition: all 0.3s;
}

.quick-card:hover {
  transform: translateY(-5px);
}

.quick-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px 0;
}

.quick-icon {
  font-size: 36px;
  color: #409EFF;
  margin-bottom: 10px;
}

.quick-item span {
  color: #606266;
  font-size: 14px;
}

a {
  text-decoration: none;
}
</style>
