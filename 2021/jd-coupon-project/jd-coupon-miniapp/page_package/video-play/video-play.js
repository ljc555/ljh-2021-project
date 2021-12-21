// page_package/video-play/video-play.js

const app = getApp()

const data = require("../../pages/daren/test-data.js")

const videoList = data.videos



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
    current:0,
    videoList,
    videos:{}
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    let lists = {
      current: Number(options.index),
      videos: videoList
    }

    this.setData({
      current: Number(options.index),
      videos: lists
    })
  },

  onPlay(e) {},

  onPause(e) {
    //  console.log('pause', e.detail.activeId)
  },

  onEnded(e) {},

  onError(e) {},

  onWaiting(e) {},

  onTimeUpdate(e) {},

  onProgress(e) {},

  onLoadedMetaData(e) {
    console.log('LoadedMetaData', e)
  },

  onShare(e){
    wx.showToast({
      title: '分享视频' + e.detail.index,
      icon: "none",
      duration: 3000
    })
  }

})