// page_package/profit/profit.js
const app = getApp()
const {getFeeData} = require("../../utils/network/services/user.js")
const constants = require("../../utils/constants")
const util = require("../../utils/util")


Page({

    /**
     * 页面的初始数据
     */
    data: {
        tabs: ["本月预估", "上月预估", "近30天预估"],
        TabCur: 0,
        scrollLeft: 0,

        requesting: false,
        end: false,
        emptyShow: false,
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 0,
        color: "#3F82FD",
        empty: false,
        scrollTop: 0,

        thisMonth: util.getEstimateDate(),
        lastMonth: util.getLastMonth(),
        lastDays: util.getLastDays(),

        modelTitle: "",
        modelContent: "",
        modalName: '',

        feeList: null
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.getData()
    },

    getData() {
        this.setData({requesting: true})
        getFeeData().then((res) => {
            this.setData({
                feeList: res.data.data,
                requesting: false
            })
        }).catch(err => {
            this.setData({
                requesting: false
            })
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
    hideModal() {
        this.setData({modalName: ''})
    },
    more() {

    },
    refresh() {
        this.getData()
    },
    tabSelect(event) {
        this.setData({
            TabCur: event.currentTarget.dataset.id,
            scrollLeft: (event.currentTarget.dataset.id - 1) * 60
        })
    },
    onShowModel(event) {
        switch (Number(event.currentTarget.dataset.index)) {
            case 0:
                if (Number(event.currentTarget.dataset.type) == 0) {
                    this.setData({
                        modelTitle: this.data.feeList.thisMonth[0].description.popTitle,
                        modelContent: this.data.feeList.thisMonth[0].description.popText,
                        modalName: 'bottomModal'
                    })
                } else if (Number(event.currentTarget.dataset.type) == 1) {
                    this.setData({
                        modelTitle: this.data.feeList.lastMonth[0].description.popTitle,
                        modelContent: this.data.feeList.lastMonth[0].description.popText,
                        modalName: 'bottomModal'
                    })
                } else {
                    this.setData({
                        modelTitle: this.data.feeList.nearly30Days[0].description.popTitle,
                        modelContent: this.data.feeList.nearly30Days[0].description.popText,
                        modalName: 'bottomModal'
                    })
                }
                break
            case 1:
                this.setData({
                    modelTitle: this.data.feeList.billingInfo.description.popTitle,
                    modelContent: this.data.feeList.billingInfo.description.popText,
                    modalName: 'bottomModal'
                })
                break
        }
    }
})