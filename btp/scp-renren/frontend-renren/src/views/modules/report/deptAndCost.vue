<template>
  <div class="mod-demo-echarts">

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_chartDeptAndCost" class="chart-box"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import echarts from 'echarts'
  export default {
    data () {
      return {
        chartDeptAndCost: null
      }
    },
    mounted () {
      this.initChartDeptAndCost()
    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartDeptAndCost) {
        this.chartDeptAndCost.resize()
      }
    },
    methods: {
      // 折线图
      initChartDeptAndCost () {
        this.$http({
          url: this.$http.adornUrl('/travel/report/deptAndCostMap'),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          let json = data.results
          let xAxisValue = new Array();
          let title = new Array();
          let datas = new Array();
          for (let item in json) {
            xAxisValue.push(json[item].name)
            let costValues = json[item].values
            datas[item] = new Array();
            for (let flag in costValues) {
              if(item==0) {
                title.push(costValues[flag].name)
              }
              datas[item][flag] = costValues[flag].total
            }
          }

          let arr2 = [];
          for(let i=0;i<datas[0].length;i++){
            arr2[i] = [];
          }
          for(let i=0;i<datas.length;i++){
            for(let j=0;j<datas[i].length;j++){
              arr2[j][i] = datas[i][j];
            }
          }

          console.log(arr2)

          let yAxisValue = new Array();
          for(let item in title) {
            yAxisValue.push({ name:title[item], type:'bar',data: arr2[item]});
          }

          console.log(yAxisValue)
          var option = {
            tooltip: {
              trigger: 'axis',
              axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              }
            },
            legend: {
              data: title
            },
            grid: {
              left: '3%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis: [
              {
                type: 'category',
                data: xAxisValue
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: yAxisValue
          }

          this.chartDeptAndCost = echarts.init(document.getElementById('J_chartDeptAndCost'))
          this.chartDeptAndCost.setOption(option)
          window.addEventListener('resize', () => {
            this.chartDeptAndCost.resize()
          })
        })
      }
    }
  }
</script>

<style lang="scss">
  .mod-demo-echarts {
    > .el-alert {
      margin-bottom: 10px;
    }
    > .el-row {
      margin-top: -10px;
      margin-bottom: -10px;
      .el-col {
        padding-top: 10px;
        padding-bottom: 10px;
      }
    }
    .chart-box {
      min-height: 400px;
    }
  }
</style>
