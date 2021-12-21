// page_package/money/history/history.js
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
        historyList: [
            {
                "id":1241218946633691138,
                "wechatId":"wuxing07240011",
                "amount":9.99,
                "payeeName":"伍星",
                "status":0,
                "statusDesc":"已提交",
                "submitTime":[
                    2020,
                    3,
                    21,
                    12,
                    23,
                    52
                ],
                "operations":[
                    {
                        "operatorName":"伍星",
                        "operateTime":[
                            2020,
                            3,
                            21,
                            12,
                            23,
                            52
                        ],
                        "remark":"伍星 发起了提现申请"
                    }
                ]
            }
        ]
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
            let list = res.data.data.records
            list.forEach(item => {
                item.isOpen = false
                item.submitTime = util.formatTime(new Date(item.submitTime))
                item.operations.forEach(im => { im.operateTime = util.formatTime(new Date(im.operateTime))})
            })

            if (type != 'refresh') list.forEach(im => {this.data.historyList.push(im)})

            this.setData({
                historyList: type == 'refresh' ? list:this.data.historyList,
                requesting: false
            })
        }).catch(err => {
            this.setData({
                requesting: false
            })
        })
    },
    openDetails(event){
       let item = event.currentTarget.dataset.item
       let index = event.currentTarget.dataset.index
        this.data.historyList.forEach(im => {
            im.isOpen = false
            if(item.id == im.id) im.isOpen = !item.isOpen
        })
        this.setData({historyList:this.data.historyList})
    },
    refresh() {
        this.getData('refresh')
    },
    more() {
        this.getData('more')
    },
    copy(event){
        let item = event.currentTarget.dataset.item
        wx.setClipboardData({
            data: item.id.toString(),
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
})