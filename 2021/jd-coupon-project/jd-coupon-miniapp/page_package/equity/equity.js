// page_package/equity/equity.js
const app = getApp()
Page({

        /**
         * 页面的初始数据
         */
        data: {
            windowHeight: app.globalData.windowHeight,
            CustomBar: app.globalData.CustomBar,

            vipList: [
                {icon: 'moneybagfill', title: '省的更多', sub: '自购有佣金'},
                {icon: 'pay', title: '推广赚钱', sub: '推广赚佣金'},
                {icon: 'baby', title: '平台奖励', sub: '平台额外奖励'},
                {icon: 'friendfill', title: '京东拉新', sub: '高额拉新奖励'},
                {icon: 'friendfill', title: '智能助手', sub: '帮您轻松赚钱'},
                {icon: 'myfill', title: '导师指导', sub: '帮您更快上手'},
                {icon: 'choicenessfill', title: '大咖课程', sub: '免费畅听'},
                {icon: 'goodsfill', title: '精品爆款', sub: '高佣品质商品'},
                {icon: 'servicefill', title: '专属客服', sub: '1V1解决问题'}
            ]
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

        onClick() {
            wx.navigateTo({
                url: '/page_package/apply-vip/apply-vip'
            })
        }
    }
)