// page_package/transfer/chain/chain.js
const {goodsConvert} = require('../../../utils/network/services/service.js')
Page({

  /**
   * 页面的初始数据
   */
  data: {
    content:"",
    chainContent:'',
    isShowDialog:false,
    msg:""
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    this.setData({
      content:decodeURIComponent(options.content)
    })
    this.onChain()
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
  onChain(){
    if(this.data.content){
      goodsConvert(this.data.content)
        .then(res => {
          this.setData({chainContent: res.data.data.content})
        }).catch(err => {
          console.log(err)
          this.setData({
            isShowDialog:true,
            msg:"获取推广链接失败"
          })
        })
    }else {
      this.setData({
        isShowDialog:true,
        msg:"获取推广链接失败"
      })
    }
  },
  textareaBInput(event) {
    this.setData({chainContent: event.detail.value})
  },
  onCopy(){
    if(this.data.chainContent){
      wx.setClipboardData({
        data: this.data.chainContent,
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
  }
})