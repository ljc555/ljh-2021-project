// page_package/order/order.js
const { orderList } = require('../../utils/network/services/user.js')
const constants = require('../../utils/constants')
const util = require('../../utils/util')
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    windowHeight: app.globalData.windowHeight,
    windowWidth: app.globalData.windowWidth,
    CustomBar: app.globalData.CustomBar,
    StatusBar: app.globalData.StatusBar,
    tabs: ['全部', '待付款', '已付款', '已完成', '无效'],
    titles: ['我购买的','我推广的'],
    TabCur: 0,
    TabCur1: 0,
    scrollLeft: 0,
    scrollLeft1: 0,
    searchHeight: 90,
    typeHeight: 0,
    selectData: {
      startTime: '',
      endTime: ''
    },
    isLoad: false,

    queryData: {
      begin: '',
      end: '',
      pageIndex: 1,
      pageSize: 10,
      type:1,
      status:1
    },
    isShowDialog:false,
    orderList:[],
    identify:1,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let TimeNow = new Date();
    let startDay = new Date(TimeNow.getFullYear(), TimeNow.getMonth(), 1);
    this.data.selectData.startTime = util.formatData(startDay)
    this.data.selectData.endTime = util.formatData(TimeNow)
    this.setData({
      identify:util.identityApp(Number(options.identify)),
      selectData:this.data.selectData
    })
    const that = this
    const query = wx.createSelectorQuery()
    query.select('#search').boundingClientRect()
    query.exec(function (res) {that.setData({ searchHeight: res[0].height })})
    if(this.data.identify){
      const query1 = wx.createSelectorQuery()
      query1.select('#type').boundingClientRect()
      query1.exec(function (res) {that.setData({ typeHeight: res[0].height })})
    }

    this.getList('refresh')
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  onLoads(){
    this.getList('refresh')
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  },
  tabSelect (event) {
    console.log(Number(event.currentTarget.dataset.id),">>>>",Number(event.currentTarget.dataset.id) + 1)
    this.data.queryData.status = Number(event.currentTarget.dataset.id) + 1
    this.setData({
      TabCur: Number(event.currentTarget.dataset.id),
      scrollLeft: (event.currentTarget.dataset.id - 1) * 60,
      queryData:this.data.queryData
    })
    this.getList('refresh')
  },

  titleSelect (event) {
    this.data.queryData.type = Number(event.currentTarget.dataset.id) + 1
    this.setData({
      TabCur1: event.currentTarget.dataset.id,
      scrollLeft1: (event.currentTarget.dataset.id - 1) * 60,
      TabCur:0,
      queryData:this.data.queryData
    })
    this.getList('refresh')
  },
  DateChange (event) {
    let selectTime = event.detail.value
    console.log(selectTime)
    switch (Number(event.currentTarget.dataset.index)) {
      case 0:
        if (!this.data.selectData.endTime ||
          (this.data.selectData.endTime &&
            util.compareTime(this.data.selectData.endTime, selectTime))) {
          this.data.selectData.startTime = selectTime
          this.setData({ selectData: this.data.selectData })
        } else {
          this.data.selectData.startTime = selectTime
          this.data.selectData.endTime = selectTime
          this.setData({ selectData: this.data.selectData })
        }
        break
      case 1:
        if (!this.data.selectData.startTime ||
          (this.data.selectData.startTime &&
            util.compareTime(selectTime, this.data.selectData.startTime))) {
          this.data.selectData.endTime = selectTime
          this.setData({ selectData: this.data.selectData })
        } else {
          this.data.selectData.startTime = selectTime
          this.data.selectData.endTime = selectTime
          this.setData({ selectData: this.data.selectData })
        }
        break
    }
  },
  getList (type) {
    this.setData({
      isLoad: true
    })
    if (type == 'refresh')
      this.data.queryData.pageIndex = 1
    else
      this.data.queryData.pageIndex = this.data.queryData.pageIndex + 1

    this.data.queryData.begin = this.data.selectData.startTime
    this.data.queryData.end = this.data.selectData.endTime
    if (this.data.selectData.startTime && !this.data.selectData.endTime)
      this.data.queryData.end = this.data.selectData.startTime

    if (!this.data.selectData.startTime && this.data.selectData.endTime)
      this.data.queryData.begin = this.data.selectData.endTime

    if(this.data.selectData.startTime){
      this.data.queryData.begin = this.data.queryData.begin.split("-").join("")
      this.data.queryData.end = this.data.queryData.end.split("-").join("")
    }
    if(this.data.TabCur != 0)
    this.data.queryData.status = Number(this.data.TabCur) + 1
    this.setData({ queryData: this.data.queryData })

    orderList(this.data.queryData)
      .then(res => {

        res.data.data.records.forEach( item => {
          if(util.isNumber(item.orderTime)){
            item.orderTime = util.formatTime(new Date(item.orderTime))
          }
        })

        let lastList = this.data.orderList
        if(type != 'refresh') res.data.data.records.forEach( item => {lastList.push(item)})


        this.setData({
          orderList: type == 'refresh' ? res.data.data.records : lastList,
          isLoad: false
        })
      }).catch(err => {
        this.setData({isLoad:false})
    })
  }
})