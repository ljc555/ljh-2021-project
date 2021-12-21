// pages/launch/launch.js
let app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    isAuth: false,
    interval: 5,
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    this.setData({
      isAuth: app.globalData.userInfo != null
    })
    if (this.data.isAuth) this.onInterval()
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },

  getNumber: function(event) {
    if (event.detail.userInfo) {
      app.globalData.userInfo = event.detail.userInfo
      console.log("xxx", event.detail.userInfo)
      this.onInterval()
    } else {
      console.log("请授权登录")
    }
  },

  /**
   * 闪屏时间
   */
  onInterval() {
    setInterval(function() {
      if (this.data.interval == 2) wx.redirectTo({
        url: '../index/index'
      })
      this.setData({
        interval: --this.data.interval
      });
    }.bind(this), 1000);
  },
  getInfo() {
    wx.getSetting({
      success(res) {
        console.log("设置")
        if (!res.authSetting['scope.userInfo']) {
          wx.authorize({
            scope: 'scope.userInfo',
            success() {
              // 必须是在用户已经授权的情况下调用
              wx.getUserInfo({
                success: function(res) {
                  app.globalData.userInfo = res.userInfo
                  console.log("11111测试11111")
                }
              })
            },
            fail(){
              console.log("222222测试222222")
            }
          })
        } else {
          console.log("测试")
          wx.getUserInfo({
            success: function(res) {
              app.globalData.userInfo = res.userInfo
              console.log("测试",res.userInfo)
            }
          })
        }
      }
    })
  }
})