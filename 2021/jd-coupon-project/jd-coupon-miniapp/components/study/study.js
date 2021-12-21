// components/study/study.js
const app = getApp()

Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    windowWidth: app.globalData.windowWidth,
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onClick(event){
      wx.navigateTo({
        url: '/page_package/study/study'
      })
    }
  }
})
