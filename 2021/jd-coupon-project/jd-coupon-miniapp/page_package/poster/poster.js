const app = getApp()
const {posters} = require("../../utils/network/services/user.js")
const constants = require("../../utils/constants")
const util = require("../../utils/util")


Page({

  /**
   * 页面的初始数据
   */
  data: {
    requesting: false,
    posterList:[],
    query:{
      pageIndex:1,
      pageSize:10
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.getData('refresh')
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
    wx.vibrateShort();
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
  getData(type){
    if(type == 'refresh'){

    }
    posters(this.data.query)
        .then(res => {
          wx.stopPullDownRefresh()
          console.log(">>>>>",res.data.data)
          this.setData({posterList:res.data.data.records})
        }).catch(err => {
      wx.stopPullDownRefresh()
    })
  },
  more() {

  },
  refresh() {
    this.getData('refresh')
  },
})