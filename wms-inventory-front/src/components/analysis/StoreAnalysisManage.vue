<template>
  <div class="analysis-container">
    <div class="stats-grid">
      <div class="stat-card">
        <h3 class="stat-title">今日出库总量</h3>
        <div class="stat-value">{{this.daySUM}}</div>
      </div>
      <div class="stat-card">
        <h3 class="stat-title">今日出库最大量</h3>
        <div class="stat-value">{{this.goodMAX}}</div>
      </div>
      <div class="stat-card">
        <h3 class="stat-title">今日出库最小量</h3>
        <div class="stat-value">{{this.goodMIN}}</div>
      </div>
      <div class="stat-card highlight">
        <h2 class="stat-title">本月出库量TOP1</h2>
        <div class="stat-value">{{this.Top}}</div>
        <div class="stat-metrics">
          <span class="metric">今日占比 {{this.zb}}%</span>
          <span class="metric">同比增长 {{this.tb}}%</span>
        </div>
      </div>
    </div>
    <div class="chart-container" ref="chart"></div>
  </div>
</template>

<script>
export default {
    name:"StoreAnalysisManage",
    data(){
        return{
            daySUM:0,
            goodMAX:0,
            goodMIN:0,
            Top:0,
            zb:0,
            tb:0,
            chart:null,
            option:{
              title: {
                text: '本月总出库记录',
                subtext: '',
                left: 'center'
              },
              xAxis: {
                type: 'category',
                data: []
              },
              yAxis: {
                type: 'value'
              },
              series: [
                {
                  data: [],
                  type: 'bar'
                }
              ]
            },
        }

    },
  mounted(){
        this.$axios.get(this.$httpUrl+'/analysis/getDaySum').then(res=>res.data).then(res=>{
          this.daySUM=res.data
        })
        this.$axios.get(this.$httpUrl+'/analysis/getDayMax').then(res=>res.data).then(res=>{
          this.goodMAX=res.data
        })
        this.$axios.get(this.$httpUrl+'/analysis/getDayMin').then(res=>res.data).then(res=>{
          this.goodMIN=res.data
        })
        this.$axios.get(this.$httpUrl+'/analysis/getTop').then(res=>res.data).then(res=>{
          this.Top=res.data
        })
        this.$axios.get(this.$httpUrl+'/analysis/zb').then(res=>res.data).then(res=>{
          this.zb=res.data
        })
        this.$axios.get(this.$httpUrl+'/analysis/tb').then(res=>res.data).then(res=>{
          this.tb=res.data
        })
        this.$axios.get(this.$httpUrl+'/analysis/getSum').then(res=>res.data).then(res=>{
            for(var i=0;i<res.data.length; i++){
                this.option.xAxis.data[i]=(res.data[i].date);
                this.option.series[0].data[i]=(res.data[i].value)
            }
          this.$nextTick(() => {
            this.getPage()
          });

        })
    },
    methods:{
        getPage() {
            // 引用chart并初始化
            this.chart = this.$echarts.init(this.$refs.chart);
            // 使用刚指定的配置项和数据显示图表。
            this.chart.setOption(this.option);
        },
    }
}
</script>

<style scoped>
.analysis-container {
  padding: 20px;
  background-color: #fff;
  min-height: 90vh;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
  margin-bottom: 30px;
}

.stat-card {
  background: white;
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.stat-card:nth-child(1) {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  color: white;
}

.stat-card:nth-child(2) {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
  color: white;
}

.stat-card:nth-child(3) {
  background: linear-gradient(135deg, #fa709a 0%, #fee140 100%);
  color: white;
}

.stat-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.1);
}

.stat-card.highlight {
  background: linear-gradient(135deg, #1890ff 0%, #36cfc9 100%);
  color: white;
}

.stat-title {
  font-size: 1.1rem;
  color: #666;
  margin-bottom: 15px;
  font-weight: 500;
}

.stat-card:nth-child(1) .stat-title,
.stat-card:nth-child(2) .stat-title,
.stat-card:nth-child(3) .stat-title,
.highlight .stat-title {
  color: white;
}

.stat-value {
  font-size: 2rem;
  font-weight: bold;
  color: #1890ff;
  margin: 10px 0;
}

.stat-card:nth-child(1) .stat-value,
.stat-card:nth-child(2) .stat-value,
.stat-card:nth-child(3) .stat-value,
.highlight .stat-value {
  color: white;
}

.stat-metrics {
  display: flex;
  justify-content: space-between;
  margin-top: 15px;
  font-size: 0.9rem;
}

.metric {
  background: rgba(255, 255, 255, 0.2);
  padding: 5px 10px;
  border-radius: 15px;
}

.chart-container {
  background: white;
  border-radius: 12px;
  padding: 20px;
  /* 四周阴影 */
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
  height: 400px;
  width: 98%;
}


@media (max-width: 768px) {
  .stats-grid {
    grid-template-columns: 1fr;
  }
  
  .stat-card {
    margin-bottom: 15px;
  }
}
</style>