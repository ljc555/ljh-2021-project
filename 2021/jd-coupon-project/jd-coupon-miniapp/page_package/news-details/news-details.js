// page_package/news-details/news-details.js
const app = getApp()
const {messagesDetails} = require('../../utils/network/services/service.js')
const constants = require('../../utils/constants')
const util = require('../../utils/util')
let that = null

Page({

  /**
   * 页面的初始数据
   */
  data: {
    title:'',
    time:'',
    msgContent:null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({title:options.title})
    messagesDetails(options.id)
        .then(res => {
          console.log(">>>>",res.data)
          this.setData({msgContent:res.data.data,
              time:util.formatData(new Date(res.data.data.createTime))})
        }).catch( err => {

    })
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
  onCopy(){
    wx.setClipboardData({
      data: this.data.msgContent.content,
      success: function (res) {
        wx.getClipboardData({
          success: function (res) {
            wx.showToast({
              title: '复制成功'
            })
          }
        })
      }
    })
  }
})