<template>
  <div class="mod-log">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="申请单号" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      style="width: 100%">
      <el-table-column
        prop="id"
        header-align="center"
        align="center"
        width="80"
        label="ID">
      </el-table-column>
      <el-table-column
        prop="ecnum"
        header-align="center"
        align="center"
        label="申请单号">
      </el-table-column>
      <el-table-column
        prop="econr"
        header-align="center"
        align="center"
        label="行号">
      </el-table-column>
      <el-table-column
        prop="bdat"
        header-align="center"
        align="center"
        label="开始日期">
      </el-table-column>
      <el-table-column
        prop="edat"
        header-align="center"
        align="center"
        label="结束日期">
      </el-table-column>
      <el-table-column
        prop="days"
        header-align="center"
        align="center"
        label="天数">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="每天单价">
      </el-table-column>
      <el-table-column
        prop="hkont"
        header-align="center"
        align="center"
        label="费用类型">
      </el-table-column>
      <el-table-column
        prop="dmbtr"
        header-align="center"
        align="center"
        label="金额">
      </el-table-column>
      <el-table-column
        prop="dest"
        header-align="center"
        align="center"
        label="目的地">
      </el-table-column>
      <el-table-column
        prop="hotel"
        header-align="center"
        align="center"
        label="酒店">
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
  </div>
</template>

<script>
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        selectionDataList: []
      }
    },
    created () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/travel/applyItem/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.page.list
            this.totalPage = data.page.totalCount
          } else {
            this.dataList = []
            this.totalPage = 0
          }
          this.dataListLoading = false
        })
      },
      // 每页数
      sizeChangeHandle (val) {
        this.pageSize = val
        this.pageIndex = 1
        this.getDataList()
      },
      // 当前页
      currentChangeHandle (val) {
        this.pageIndex = val
        this.getDataList()
      }
    }
  }
</script>
