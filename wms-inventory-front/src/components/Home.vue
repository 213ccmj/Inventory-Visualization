<template>
  <div class="home-container">
    <div class="user-center">
      <!-- 用户信息卡片 -->
      <el-card class="user-card-wrapper" :body-style="{ padding: '30px' }">
        <div class="user-info">
          <div class="avatar-wrapper">
            <img width="100px" height="100px" src="../assets/tou.png" class="user-avatar">
          </div>
          <div class="user-details">
            <h2 class="greeting">{{ greeting }}, {{ user.name }}</h2>
            <p class="welcome-text">欢迎使用库存管理系统</p>
            <div class="user-stats">
              <div class="stat-item">
                <i class="el-icon-date"></i>
                <span>{{ new Date().toLocaleDateString() }}</span>
              </div>
              <div class="stat-item">
                <i class="el-icon-time"></i>
                <span>{{ new Date().toLocaleTimeString() }}</span>
              </div>
            </div>
          </div>
        </div>
      </el-card>

      <!-- 统计信息区域 -->
      <div class="statistics-section">
        <div class="section-header">
          <h3 class="section-title">
            <i class="el-icon-data-analysis"></i>
            数据概览
          </h3>
        </div>
        <el-row :gutter="20">
          <el-col :span="6" v-for="(stat, index) in statistics" :key="index">
            <el-card class="stat-card" :class="`stat-card-${index + 1}`" shadow="hover">
              <div class="stat-content">
                <i :class="stat.icon"></i>
                <div class="stat-info">
                  <div class="stat-value">{{ stat.value }}</div>
                  <div class="stat-label">{{ stat.label }}</div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 封面图区域 -->
      <div class="cover-section">
        <img src="../assets/home.png" alt="库存管理系统" class="cover-image">
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Home',
  data() {
    return {
      user: {},
      greeting: '',
      statistics: [ 
        { icon: 'el-icon-house', value: '0', label: '仓库数量', key: 'storageCount' },
        { icon: 'el-icon-s-order', value: '0', label: '商品分类', key: 'goodsTypeCount' },
        { icon: 'el-icon-goods', value: '0', label: '商品总数', key: 'goodsCount' },
        { icon: 'el-icon-user', value: '0', label: '用户数量', key: 'userCount' },
      ]
    };
  },
  computed: {
    getGreeting() {
      const hour = new Date().getHours();
      if (hour < 6) return '凌晨好';
      if (hour < 9) return '早上好';
      if (hour < 12) return '上午好';
      if (hour < 14) return '中午好';
      if (hour < 17) return '下午好';
      if (hour < 19) return '傍晚好';
      return '晚上好';
    }
  },
  methods: {
    init() {
      this.user = JSON.parse(sessionStorage.getItem('CurUser')) || { name: '访客' };
      this.greeting = this.getGreeting;
      this.getStatistics()
    },
    handleQuickAccess(route) {
      this.$router.push(route);
    },
    getStatistics() {
      this.$axios.get(this.$httpUrl+'/home/statistics').then(res=>res.data).then(res=>{
            if(res.code==200){
               this.statistics.forEach(item=>{
                item.value = res.data[item.key]
               })
            }else{
              this.$message.error('获取数据失败');
            } 
        })
    }
  },
  beforeMount() {
    this.init();
  }
};
</script>

<style scoped>
.home-container {
  min-height: 90vh;
  background-color: #f5f7fa;
  padding: 20px;
}

.user-center {
  max-width: 1400px;
  margin: 0 auto;
}

.user-card-wrapper {
  border-radius: 15px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  margin-bottom: 30px;
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 30px;
}

.avatar-wrapper {
  position: relative;
}

.user-avatar {
  border-radius: 50%;
  border: 4px solid #fff;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease;
}

.user-avatar:hover {
  transform: scale(1.05);
}

.user-details {
  flex: 1;
}

.greeting {
  margin: 0;
  color: #2c3e50;
  font-size: 28px;
  font-weight: 600;
}

.welcome-text {
  margin: 10px 0;
  color: #606266;
  font-size: 16px;
}

.user-stats {
  display: flex;
  gap: 20px;
  margin-top: 15px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 8px;
  color: #606266;
  font-size: 14px;
}

.statistics-section {
  margin-bottom: 30px;
}

.section-header {
  margin-bottom: 20px;
  padding: 0 10px;
}

.section-title {
  font-size: 20px;
  color: #303133;
  margin: 0 0 8px 0;
  display: flex;
  align-items: center;
  gap: 8px;
}

.section-title i {
  color: #409EFF;
  font-size: 24px;
}

.section-desc {
  color: #909399;
  font-size: 14px;
  margin: 0;
}

.stat-card {
  height: 120px;
  border-radius: 12px;
  transition: all 0.3s ease;
  border: none;
}

.stat-card:hover {
  transform: translateY(-5px);
}

.stat-content {
  display: flex;
  align-items: center;
  height: 100%;
  padding: 20px;
}

.stat-content i {
  font-size: 40px;
  margin-right: 20px;
  color: rgba(255, 255, 255, 0.9);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #ffffff;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.9);
}

.stat-card-1 {
  background: linear-gradient(135deg, #409EFF 0%, #36D1DC 100%);
}

.stat-card-2 {
  background: linear-gradient(135deg, #67C23A 0%, #95D475 100%);
}

.stat-card-3 {
  background: linear-gradient(135deg, #E6A23C 0%, #F3D19E 100%);
}

.stat-card-4 {
  background: linear-gradient(135deg, #F56C6C 0%, #F89898 100%);
}

.cover-section {
  border-radius: 15px;
  overflow: hidden;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  max-height: 400px;
}

.cover-image {
  width: 100%;
  height: 400px;
  object-fit: cover;
  display: block;
  transition: transform 0.3s ease;
}

.cover-image:hover {
  transform: scale(1.02);
}

/* 响应式设计 */
@media screen and (max-width: 768px) {
  .user-info {
    flex-direction: column;
    text-align: center;
  }
  
  .user-stats {
    justify-content: center;
  }
  
  .statistics-section .el-col {
    margin-bottom: 20px;
  }
}
</style>

