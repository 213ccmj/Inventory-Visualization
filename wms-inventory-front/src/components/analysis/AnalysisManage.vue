<template>
  <div class="analysis-container">
    <div class="analysis-header">
      <div class="search-section">
        <el-select 
          v-model="goodstype" 
          placeholder="请选择分类" 
          class="type-select"
          @change="load">
          <el-option
            v-for="item in goodstypeData"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
        </el-select>
        
        <div class="search-box">
          <el-input 
            v-model="nameText" 
            placeholder="请输入商品名称" 
            class="search-input"
            clearable
            @keyup.enter.native="getThe('')">
          </el-input>
          <el-button 
            @click="getThe('')" 
            type="primary" 
            icon="el-icon-search"
            class="search-btn">
            查询
          </el-button>
          <el-button 
            @click="reset" 
            type="danger" 
            icon="el-icon-delete"
            class="reset-btn">
            重置
          </el-button>
        </div>
      </div>
    </div>

    <div class="charts-container">
      <div class="chart-row">
        <div class="chart-item" ref="chart"></div>
        <div class="chart-item" ref="chart2"></div>
      </div>
      <div class="chart-row">
        <div class="chart-item" ref="chart3"></div>
        <div class="chart-item" ref="chart4"></div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
    name: "AnalysisManage",
    data(){
    return {
      chart:null,
      chart2:null,
      chart3:null,
      nameText:null,
      goodstypeData:[],
      goodstype:1,
      munber:0,
      // 配置可视化图形
      option1:  {
        title: {
          text: '出库量占比',
          subtext: '',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            type: 'pie',
            radius: '50%',
            data:[],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      },
      option2:{
        title: {
          text: '',
          subtext: '30天内商品出库数据(请先选择商品查询)',
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
            type: 'line'
          }
        ]
      },
      option3:{
        title: {
          text: '七日增长率'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: []
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['1', '2', '3', '4', '5', '6', '7']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          {
            name: '',
            type: 'line',
            stack: 'Total',
            data: []
          },
          {
            name: '',
            type: 'line',
            stack: 'Total',
            data: []
          },
          {
            name: '',
            type: 'line',
            stack: 'Total',
            data: []
          },
          {
            name: '',
            type: 'line',
            stack: 'Total',
            data: []
          },
          {
            name: '',
            type: 'line',
            stack: 'Total',
            data: []
          },
          {
            name: '',
            type: 'line',
            stack: 'Total',
            data: []
          }
        ]
      },
      option4:{
              title: {
                text: '明天预计出货数量',
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
                  type: 'bar',
                  label: {
                    show: true,
                    position: 'top',//在上方显示
                    textStyle: {//数值样式
                      fontSize: '12px',
                      color: '#999'
                    },
                  }
                }
              ]
            },
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.load();
      this.loadGoodsType();
      this.getPage1();
      this.getPage2();
      this.getPage3();
      this.getPage4();
      this.eConsole();
    });
  },
  beforeDestroy() {
    // 销毁图表实例
    if (this.chart) {
      this.chart.dispose();
    }
    if (this.chart2) {
      this.chart2.dispose();
    }
    if (this.chart3) {
      this.chart3.dispose();
    }
    if (this.chart4) {
      this.chart4.dispose();
    }
  },
  methods:{
    getThe(){
      if(!this.nameText){
        this.$message.error('请输入商品名称');
        return;
      }
      
      this.$axios.get(this.$httpUrl+'/analysis/getOutThe?goodsname='+(this.nameText)).then(res=>res.data).then(res=>{
        if(res.code==400){
          this.$message.error('该商品不存在');
        }else{
          for(var i=0;i<res.data.length; i++){
                console.log(res.data)
                this.option2.xAxis.data[i]=(res.data[i].date);
                this.option2.series[0].data[i]=(res.data[i].value)
            }
            if(res.data.length<this.option2.series[0].data.length){
              for(var y=res.data.length;y<this.option2.series[0].data.length;y++){
                  this.option2.xAxis.data[y]=null;
                  this.option2.series[0].data[y]=null
              }
            }
            this.option2.title.text=res.data[0].name
            this.getPage2()

        //修改七日增长率
          this.$axios.get(this.$httpUrl+'/analysis/getOne?goodsname='+this.nameText).then(res=>res.data).then(res=>{           
            //清空
              console.log("接受到"+res.data)
              if(res.code!=400){
                for(var i=0;i<this.option3.series.length;i++){
                console.log("进入了清空")
                this.option3.series[i].name=""
                for(var j=0;j<7;j++){
                    this.option3.series[i].data[j]=0.00
                }
              }
              //设置
              for(var k=0;k<7;k++){
                    this.option3.series[0].data[k]=res.data[k].value[0]
              }
              this.getPage3();
              }  
          })
        }
        
        
      })
    },
    reset(){
      this.nameText="";
      for(var i=0;i<this.option2.xAxis.data.length; i++){
                this.option2.xAxis.data[i]=("");
                this.option2.series[0].data[i]=("res.data[i].value")
            }
      this.option2.title.text=null;
      this.getPage2()
      //七日同比
      this.$axios.get(this.$httpUrl+'/analysis/getFive').then(res=>res.data).then(res=>{
            for(var i=0;i<res.data.length; i++){
                for(var j=0;j<res.data[i].value.length; j++){
                     this.option3.series[i].data[j]=res.data[i].value[j]
                 }
                 this.option3.series[i].name=res.data[i].name
                 // this.option3.xAxis.data[i]=res.data[i].date
                 this.option3.legend.data[i]=res.data[i].name
             }
             this.getPage3();
         })
      this.$axios.get(this.$httpUrl+'/analysis/getFive1').then(res=>res.data).then(res=>{
            for(var i=0;i<res.data.length; i++){
                for(var j=0;j<res.data[i].value.length; j++){
                    this.option3.series[i].data[j]=res.data[i].value[j]
                }
                this.option3.series[i].name=res.data[i].name
                // this.option3.xAxis.data[i]=res.data[i].date
                this.option3.legend.data[i]=res.data[i].name
            }
            this.getPage3();
        })
    },
    ccc(param) { //添加点击事件
        console.log("点到了");
        console.log(param);
        this.chart.dispatchAction({ type: 'highlight', dataIndex: param.dataIndex }); //激活点击区域高亮
        if (param.dataIndex !== this.number) { // 当鼠标点击的时候 消除上一个扇区的高亮
            this.chart.dispatchAction({ type: 'downplay', dataIndex: this.number });
        }
        this.number = param.dataIndex //接住当前扇区的dataIndex
        //获取goodsid
        this.$axios.get(this.$httpUrl+'/analysis/getOut?goodsid='+param.data.goodsid).then(res=>res.data).then(res=>{
            console.log(this.option2.series[0].data.length)
            for(var i=0;i<res.data.length; i++){
                  this.option2.xAxis.data[i]=(res.data[i].date);
                  this.option2.series[0].data[i]=(res.data[i].value) 
            }
            if(res.data.length<this.option2.series[0].data.length){
              for(var y=res.data.length;y<this.option2.series[0].data.length;y++){
                  console.log("进来了")
                  this.option2.xAxis.data[y]=null;
                  this.option2.series[0].data[y]=null
              }
            }
            console.log(this.option2.series[0].data.length)
            this.option2.title.text=param.data.name
            this.getPage2()
        })
    },
    getPage1() {
      if (this.$refs.chart) {
        this.chart = this.$echarts.init(this.$refs.chart);
        this.chart.setOption(this.option1);
      }
    },
    getPage2() {
      if (this.$refs.chart2) {
        this.chart2 = this.$echarts.init(this.$refs.chart2);
        this.chart2.setOption(this.option2);
      }
    },
    getPage3() {
      if (this.$refs.chart3) {
        this.chart3 = this.$echarts.init(this.$refs.chart3);
        this.chart3.setOption(this.option3);
      }
    },
    getPage4() {
      if (this.$refs.chart4) {
        this.chart4 = this.$echarts.init(this.$refs.chart4);
        this.chart4.setOption(this.option4);
      }
    },
    eConsole() {
        this.chart.on('click',this.ccc)
    },
    loadGoodsType(){
        this.$axios.get(this.$httpUrl+'/goodstype/list').then(res=>res.data).then(res=>{
         
          if(res.code==200){
            this.goodstypeData=res.data;
          }else{
            alert('获取数据失败')
          }
        })
      },
    load(){
      this.$axios.get(this.$httpUrl+'/analysis/getsix?goodstype='+this.goodstype).then(res=>res.data).then(res=>{
            for(var i=0;i<res.data.length; i++){
                this.option1.series[0].data[i]=({"value":res.data[i].value,"name":res.data[i].name,"goodsid":res.data[i].goodsid});
            }
            if(res.data.length<this.option1.series[0].data.length){
              console.log("进入饼图清空")
              for(var j=res.data.length;j<this.option1.series[0].data.length; j++){
                this.option1.series[0].data[j]=(null);
                console.log("清空了"+j)
            }
            }
            this.getPage1();
            this.getPage2();
            this.eConsole();  
        }),
          this.$axios.get(this.$httpUrl+'/analysis/getFive1?goodstype='+this.goodstype).then(res=>res.data).then(res=>{
            for(var i=0;i<res.data.length; i++){
                for(var j=0;j<res.data[i].value.length; j++){
                    this.option3.series[i].data[j]=res.data[i].value[j]
                }
                this.option3.series[i].name=res.data[i].name
                // this.option3.xAxis.data[i]=res.data[i].date
                this.option3.legend.data[i]=res.data[i].name
            }
            if(res.data.length<this.option3.series.length){
              for(var k=res.data.length;k<this.option3.series.length;k++){
                console.log("进入了清空")
                this.option3.series[k].name=""
                for(var z=0;z<7;z++){
                    this.option3.series[k].data[z]=0.00
                }
              }
            }
            this.getPage3();
        }),
        this.$axios.get(this.$httpUrl+'/analysis/getTomorrow?goodstype='+this.goodstype).then(res=>res.data).then(res=>{
            for(var i=0;i<res.data.length; i++){
                this.option4.xAxis.data[i]=(res.data[i].name);
                this.option4.series[0].data[i]=(res.data[i].value)
            }
            if(res.data.length<this.option4.xAxis.data.length){
              for(var j=res.data.length;j<this.option4.xAxis.data.length; j++){
                this.option4.xAxis.data[j]=("");
                this.option4.series[0].data[j]=(null)
            }
            }
            this.getPage4()
        })  
    }  
  }

};
</script>

<style scoped>
.analysis-container {
  padding: 20px;
  background-color: #fff;
  min-height: 90vh;
  display: flex;
  flex-direction: column;
}

.analysis-header {
  margin-bottom: 15px;
}

.search-section {
  display: flex;
  align-items: center;
  gap: 20px;
  margin-bottom: 15px;
  padding: 15px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
}

.type-select {
  width: 200px;
}

.search-box {
  display: flex;
  gap: 10px;
  flex: 1;
}

.search-input {
  width: 300px;
}

.search-btn, .reset-btn {
  transition: all 0.3s;
}

.search-btn:hover, .reset-btn:hover {
  transform: translateY(-2px);
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
}

.charts-container {
  display: flex;
  flex-direction: column;
  gap: 15px;
  flex: 1;
}

.chart-row {
  display: flex;
  gap: 15px;
  flex: 1;
}

.chart-item {
  flex: 1;
  height: calc(50vh - 120px);
  min-height: 280px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0,0,0,0.1);
  transition: all 0.3s;
  padding: 10px;
}

.chart-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 4px 16px rgba(0,0,0,0.15);
}
</style>
