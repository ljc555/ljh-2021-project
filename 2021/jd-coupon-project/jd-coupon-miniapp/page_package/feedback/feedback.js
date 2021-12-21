// page_package/feedback/feedback.js
const app = getApp()
const {feedback} = require('../../utils/network/services/user.js')
const constants = require('../../utils/constants')
const util = require('../../utils/util')
Page({

    /**
     * 页面的初始数据
     */
    data: {
        feedbackContent: ""
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
        this.setData({feedbackContent: event.detail.value})
    },
    onSubmit() {
        if (this.data.feedbackContent.length < 15) {
            wx.showModal({
                title: '提示',
                showCancel: false,
                content: "请最少输入15个字符反馈信息",
                success(res) {
                }
            })
            return
        }
        feedback({content:this.data.feedbackContent}).then(res => {
          wx.showModal({
            title: '提示',
            showCancel: false,
            content: "我们收到你的反馈啦~如有必要客服将会联系你",
            success(res) {
              if (res.confirm) {
                wx.navigateBack({//返回
                  delta: 1
                })
              }
            }
          })
        }).catch(err => {
          wx.showModal({
            title: '提示',
            showCancel: false,
            content: "提交失败~可以去联系客服",
            success(res) {
            }
          })
        })
    }
})