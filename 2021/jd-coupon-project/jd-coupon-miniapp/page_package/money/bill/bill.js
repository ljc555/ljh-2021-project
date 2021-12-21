const app = getApp()
const {getHistoryRecords} = require("../../../utils/network/services/user.js")
const constants = require("../../../utils/constants")
const util = require("../../../utils/util")
Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowHeight: app.globalData.windowHeight,
        windowWidth: app.globalData.windowWidth,
        CustomBar: app.globalData.CustomBar,

        requesting: false,
        end: false,
        emptyShow: false,
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 0,
        color: "#3F82FD",
        empty: false,

        queryData: {
            pageIndex: 1,
            pageSize: 20
        },
        settlementList: []
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

    getData(type) {
        this.setData({
            requesting: true
        })
        if (type == 'refresh')
            this.data.queryData.pageIndex = 1
        else
            this.data.queryData.pageIndex = this.data.queryData.pageIndex + 1

        this.setData({queryData: this.data.queryData})
        getHistoryRecords(this.data.queryData).then((res) => {
            this.setData({
                settlementList: res.data.data.records,
                requesting: false
            })
        }).catch(err => {
            this.setData({
                requesting: false
            })
        })
    },

    refresh() {
        this.getData('refresh')
    },
    more() {
        this.getData('more')
    }
})