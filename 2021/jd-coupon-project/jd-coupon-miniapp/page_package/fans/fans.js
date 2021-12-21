// page_package/fans/fans.js
const app = getApp()
const {getMembers} = require("../../utils/network/services/user.js")
const constants = require("../../utils/constants")
const util = require("../../utils/util")

Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        windowHeight: app.globalData.windowHeight,
        CustomBar: app.globalData.CustomBar,
        userInfo: app.globalData.userInfo,
        requesting: false,
        end: false,
        emptyShow: false,
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 0,
        color: "#3F82FD",
        empty: false,
        isLoad:false,
        scrollLeft: 0,
        scrollTop: -1,
        TabCur: 0,
        tabs: [
            // {name: "我的注册用户"},
            {name: "我的超级会员"}
        ],
        queryData: {
            identify: 1,
            pageIndex: 1,
            pageSize: 20
        },
        fansList: []
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({queryData: this.data.queryData})
        this.getData('refresh')
        if(this.data.userInfo.identify>2){
            this.setData({
                tabs:[
                    // {name: "我的注册用户"},
                    {name: "我的超级会员"},
                    {name: "我的下级导师"},
                ]
            })
        }
    },

    getData(type) {
        this.setData({
            requesting: true
        })
        if (type == 'refresh')
            this.data.queryData.pageIndex = 1
        else
            this.data.queryData.pageIndex = this.data.queryData.pageIndex + 1

        this.data.queryData.identify = Number(this.data.TabCur) + 2
        this.setData({queryData: this.data.queryData})
        getMembers(this.data.queryData).then((res) => {
            res.data.data.records.forEach( item => {
                item.phone = util.phoneFor(item.phone)
                item.createTime = util.isNumber(item.createTime) ? util.formatTime(new Date(item.createTime)) : item.createTime
            })
            let lastList = this.data.fansList
            if(type != 'refresh') res.data.data.records.forEach( item => {lastList.push(item)})
            this.setData({fansList: type == 'refresh' ? res.data.data.records : lastList})
            wx.stopPullDownRefresh()
            setTimeout(() => {this.setData({requesting:false,isLoad:false})},500)
        }).catch(err => {
            wx.stopPullDownRefresh()
            this.setData({
                requesting: false,
                isLoad:false
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
        this.refresh()
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        this.more()
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    },
    more() {
        if(this.data.isLoad) return
        this.setData({isLoad:true})
        this.getData('more')
    },
    refresh() {
        if(this.data.requesting) return
        this.getData('refresh')
    },
    tabSelect(e) {
        this.setData({
            TabCur: e.currentTarget.dataset.id,
            scrollLeft: (e.currentTarget.dataset.id - 1) * 60,
            scrollTop: 0
        })
        this.getData('refresh')
        // let item = e.currentTarget.dataset.item
    },
})