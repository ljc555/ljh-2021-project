<template>
  <div class="mod-demo-echarts">

    <el-row :gutter="20">
      <el-col :span="24">
        <el-card>
          <div id="J_chartCost" class="chart-box"></div>
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
        chartCost: null
      }
    },
    mounted () {
      this.initChartCost()
    },
    activated () {
      // 由于给echart添加了resize事件, 在组件激活时需要重新resize绘画一次, 否则出现空白bug
      if (this.chartCost) {
        this.chartCost.resize()
      }
    },
    methods: {
      // 折线图
      initChartCost () {
        this.$http({
          url: this.$http.adornUrl('/travel/report/cost'),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          let json = data.results
          let title = new Array();
          let values = new Array();
          for (let item in json) {
            title.push(json[item].TXT20)
            values.push(json[item].TOTAL)
            //console.log(json[item].TXT20)
          }

          var option = {
            tooltip: {
              show: true
            },
            xAxis: {
              type: 'category',
              data: title
            },
            yAxis: {
              type: 'value'
            },
            series: [{
              data: values,
              type: 'bar',
              showBackground: true,
              backgroundStyle: {
                color: 'rgba(220, 220, 220, 0.8)'
              }
            }]
          }

          this.chartCost = echarts.init(document.getElementById('J_chartCost'))
          this.chartCost.setOption(option)
          window.addEventListener('resize', () => {
            this.chartCost.resize()
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
