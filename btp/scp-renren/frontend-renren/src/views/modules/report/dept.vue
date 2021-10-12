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
          url: this.$http.adornUrl('/travel/report/dept'),
          method: 'get',
          params: this.$http.adornParams({
          })
        }).then(({data}) => {
          let json = data.results
          let title = new Array();
          let values = new Array();
          for (let item in json) {
            title.push(json[item].DEPXT)

            values.push({"value": json[item].TOTAL,"name":json[item].DEPXT})
          }
          console.log(title)
          console.log(values)

          var option = {
            title: {
              text: '按部门进行统计',
              subtext: '2020年1月 - 2020年5月',
              left: 'center'
            },
            tooltip: {
              trigger: 'item',
              formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
              orient: 'vertical',
              left: 'left',
              data: title
            },
            series: [
              {
                name: '',
                type: 'pie',
                radius: '55%',
                center: ['50%', '60%'],
                data: values,
                emphasis: {
                  itemStyle: {
                    shadowBlur: 10,
                    shadowOffsetX: 0,
                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                  }
                }
              }
            ]
          };

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
