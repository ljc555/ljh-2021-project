// page_package/transfer/transfer/transfer.js

Page({

    /**
     * 页面的初始数据
     */
    data: {
        isShow: true,
        searchContent: "",
      isShowDialog:false,
      msg:""
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

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
    textareaBInput(event) {
        this.setData({searchContent: event.detail.value})
    },
    onClose() {
        this.setData({isShow: false})
    },
    onClear() {
        this.setData({searchContent: ''})
    },
    onTransfer() {
      const content = this.data.searchContent
      if(content.length < 10) return
      const url = encodeURIComponent(content)
      wx.navigateTo({
        url: '/page_package/transfer/chain/chain?content=' + url
      })
    }

})