// pages/sort/sort.js
const data = require("../../utils/data")
const util = require("../../utils/util")
const app = getApp()
const {queryProductType} = require("../../utils/network/services/productImpl.js")
let pageStart = 1;
const constants = require("../../utils/constants")
Page({


  /**
   * 页面的初始数据
   */
  data: {
    CustomBar: app.globalData.CustomBar,
    windowWidth: app.globalData.windowWidth,
    windowHeight: app.globalData.windowHeight,
    VerticalNavTop: 0,
    TabCur: 0,
    MainCur: 0,
    page:pageStart,
    scrollTop:-1,
    requesting:false,
    productList: [],
    productType: data.PRODUCT,
    requestData: {
      cid1: 12218,
      pageIndex: pageStart,
      pageSize: 20
    },
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getList('refresh', pageStart, 12218);
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    if (typeof this.getTabBar === 'function' &&
        this.getTabBar()) {
      this.getTabBar().setData({
        selected: 1
      })
    }
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
    this.refresh()
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
  getList(type, currentPage, productType) {
    let that = this
    this.setData({
      requesting: true
    })

    wx.showNavigationBarLoading()

    let data = {
      cid1: productType,
      pageIndex: currentPage,
      pageSize: 20
    }

    queryProductType(data).then((res) => {
      wx.hideNavigationBarLoading()

      let lastData = that.data.productList
      let reqData = res.data.data.records
      if (type == 'refresh') {
        lastData = reqData
      } else {
        reqData.forEach(item =>{lastData.push(item)})
      }
      lastData.forEach((item) => {
        if(item.coupons.length > 0 && item.priceInfo.lowestCouponPrice)
          item.priceInfo.lowestCouponPrice = Number(item.priceInfo.lowestCouponPrice).toFixed(2)

        item.comments = util.formatNUmber(item.comments)
        item.priceInfo.price = Number(item.priceInfo.price).toFixed(2)
      })

      that.setData({
        page: currentPage + 1,
        requesting: false,
        productList: lastData
      })
    }).catch((err) => {
      wx.hideNavigationBarLoading()
      that.setData({
        requesting: false
      })
      wx.showModal({
        title: '提示',
        showCancel: false,
        content: err.data.message ? err.data.message : '获取信息失败',
        success(res) {
        }
      })
    })
  },

  tabSelect(e) {
    let data = this.data.requestData
    let item = e.currentTarget.dataset.item
    data.cid1 = item.id
    this.setData({
      requestData: data,
      scrollTop:0
    })
    this.getList('refresh', pageStart, item.id);

    this.setData({
      TabCur: e.currentTarget.dataset.id,
      MainCur: e.currentTarget.dataset.id,
      VerticalNavTop: (e.currentTarget.dataset.id - 1) * 50
    })
  },

  onClick(event){
    util.setStorage(constants.PRPDUCT_ITEM,event.currentTarget.dataset.item)
    wx.navigateTo({
      url: '/page_package/details/index'
    })
  },
  // 刷新数据
  refresh() {
    wx.vibrateShort();
    this.getList('refresh', 1, this.data.requestData.cid1);
  },
  // 加载更多
  more() {
    if(!this.data.requesting)
    this.getList('more', this.data.page, this.data.requestData.cid1);
  },

})