<template>
  <div class="mod-demo-echarts">

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_chartMonth" class="chart-box"></div>
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
        chartMonth: null
      }
    },
    mounted () {
      this.initChartMonth()
    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartMonth) {
        this.chartMonth.resize()
      }
    },
    methods: {
      // 折线图
      initChartMonth () {
        this.$http({
          url: this.$http.adornUrl('/travel/report/month'),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          let json = data.results
          let title = new Array();
          let values = new Array();
          for (let item in json) {
            title.push(json[item].YEARMONTH)
            values.push(json[item].TOTAL)
          }

          var option = {
            color: ['#3398DB'],
            tooltip: {
              trigger: 'axis',
              axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
              }
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
                data: title,
                axisTick: {
                  alignWithLabel: true
                }
              }
            ],
            yAxis: [
              {
                type: 'value'
              }
            ],
            series: [
              {
                name: '费用',
                type: 'bar',
                barWidth: '60%',
                data: values
              }
            ]
          }

          this.chartMonth = echarts.init(document.getElementById('J_chartMonth'))
          this.chartMonth.setOption(option)
          window.addEventListener('resize', () => {
            this.chartMonth.resize()
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
