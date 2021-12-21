// page_package/about/about.js
const app = getApp()

Page({

  /**
   * 页面的初始数据
   */
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    windowWidth: app.globalData.windowWidth,
    windowHeight: app.globalData.windowHeight,

  },
  onLoad: function() {

  }

})