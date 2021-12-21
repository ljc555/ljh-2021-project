// pages/strategy/strategy.js
const app = getApp()
const pageStart = 1
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const {faq,quickStart} = require("../../utils/network/services/service")
const {posters} = require("../../utils/network/services/user")

Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowHeight: app.globalData.windowHeight,
        windowWidth: app.globalData.windowWidth,
        CustomBar: app.globalData.CustomBar,
        TabCur: 0,
        scrollLeft: 0,
        tabs: ["新人上手", "进阶学习", "常见问题", "邀请海报"],
        // tabs: ["常见问题", "邀请海报"],


        requesting: false,
        end: false,
        emptyShow: false,
        page: pageStart,
        listData: [],
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 100,
        color: "#3F82FD",
        empty: false,
        scrollTop: -1,

        isHiddenNew: true,
        isStudyNew: true,
        isQuestionNew: false,
        isPosterNew: true,
        questionList:[],
        query:{
            pageIndex:1,
            pageSize:20
        },
        posterList:[],
        quickList:[],
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.refresh()
    },
    refresh() {
        this.setData({requesting: true})
        if(this.data.TabCur == 2){
            faq().then(res => {
                res.data.data.forEach(item => {item.isOpen = false})
                this.setData({requesting: false,questionList:res.data.data})
            }).catch(err =>{
                this.setData({requesting: false})
            })
        }else if(this.data.TabCur == 3) { //海报
            posters(this.data.query)
                .then(res => {
                    wx.stopPullDownRefresh()
                    this.setData({
                        posterList:res.data.data.records,
                        requesting: false})
                    console.log(">>>>",this.data.posterList)
                }).catch(err => {
                this.setData({requesting: false})
                wx.stopPullDownRefresh()
            })
        }else if(this.data.TabCur == 0){ //新手教程
            quickStart(this.data.query)
                .then(res => {
                    wx.stopPullDownRefresh()
                    this.setData({
                        quickList:res.data.data.records,
                        requesting: false})
                }).catch(err => {
                this.setData({requesting: false})
                wx.stopPullDownRefresh()
            })
        }else {
            this.setData({requesting: false})
        }
    },
    tabSelect(event) {
        this.setData({
            TabCur: event.currentTarget.dataset.id,
            scrollTop:0,
            scrollLeft: (event.currentTarget.dataset.id - 1) * 60,
            isHiddenNew: Number(event.currentTarget.dataset.id) != 0,
            isStudyNew: Number(event.currentTarget.dataset.id) != 1,
            isQuestionNew: Number(event.currentTarget.dataset.id) != 2,
            isPosterNew: Number(event.currentTarget.dataset.id) != 3,
        })
        this.refresh()
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
        let raiders = util.getStorage(constants.RAIDERS)
        this.setData({TabCur: raiders ? raiders - 1 : this.data.TabCur})
        util.removeStorage(constants.RAIDERS)
        if (typeof this.getTabBar === 'function' &&
            this.getTabBar()) {
            this.getTabBar().setData({
                selected: 3
            })
            this.refresh()
        }
        this.setData({
            isHiddenNew: Number(this.data.TabCur) != 0,
            isStudyNew: Number(this.data.TabCur) != 1,
            isQuestionNew: Number(this.data.TabCur) != 2,
            isPosterNew: Number(this.data.TabCur) != 3,
        })
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
    onShareAppMessage: function (res) {
        console.log(res)
        let loginInfo = util.getStorage(constants.AUTH_INFO)
        let path = '/pages/index/index?invitationCode='
        if(loginInfo) path = '/pages/index/index?invitationCode=' + loginInfo.invitationCode
        return {
            title: loginInfo.nickName + "邀您使用尚橙优选",
            path:path,
            imageUrl: 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/257051586654096_.pic_hd_default.jpg',
            success: function (res) {
                // 转发成功
                console.log('转发成功')
            },
            fail: function (res) {
                // 转发失败
                console.log('转发失败')
            }
        }
    },
})