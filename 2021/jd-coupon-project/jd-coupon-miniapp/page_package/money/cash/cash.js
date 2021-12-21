// page_package/money/cash/cash.js
const app = getApp()
const {getWalleta,userInfo} = require("../../../utils/network/services/user.js")
const constants = require("../../../utils/constants")
const util = require("../../../utils/util")
let that = null
Page({

    /**
     * 页面的初始数据
     */
    data: {
        requesting: false,
        end: false,
        emptyShow: false,
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 0,
        color: "#3F82FD",
        empty: false,
        invitationDialog:false,

        rule: "①本月预估推广收益：【下单时间】在本月内的推广商品订单佣金总和。\n" +
            "②本月可提现收益额：【完成时间】在上月内的推广商品订单佣金总和。\n" +
            "注：退货订单会扣除佣金。换货订单会重新计算可提现时间。",
        process: "操作流程\n" +
            "一、小程序-会员-收益提现。\n" +
            "二、填写个人身份信息，确保填写的实名信息和您的微信实名认证信息一致，否则无法正常到账。\n" +
            "三、收益只可在每月27~30全天申请提现，错过需要等下个月申请，单笔提现金额需大于1元。\n" +
            "四、未提现的收益不会清0，会累积到账户余额，可下次合并提现。\n" +
            "五、提现到账后，您会收到微信服务通知提醒，注意查收",

        cashData: null,
        userInfo:null,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        that = this
        let userInfo = util.getStorage(constants.VIP_INFO_AUTO)
        that.setData({userInfo:userInfo})
        that.refresh()
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
        userInfo().then(res => {that.setData({userInfo:res.data.data})})
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
    // 刷新数据
    refresh() {
        that.setData({
            requesting: true
        })
        getWalleta().then((res) => {
            that.setData({
                cashData: res.data.data,
                requesting: false
            })
        }).catch(err => {
            that.setData({
                requesting: false
            })
        })
    },

    itemClick(event) {
        switch (Number(event.currentTarget.dataset.index)) {
            case 0:
                if (that.data.userInfo.wechatId) {
                    wx.navigateTo({
                        url: '/page_package/money/quota/quota?balance=' + that.data.cashData.balance
                    })
                } else this.setData({invitationDialog:true})
                break
            case 1:
                wx.navigateTo({
                    url: '/page_package/money/history/history'
                })
                break
            case 2:
                wx.navigateTo({
                    url: '/page_package/money/bill/bill'
                })
                break
            case 3:
                wx.navigateTo({
                    url: '/page_package/money/explain/explain'
                })
                break
        }
    },
    hideModal(event){
        this.setData({invitationDialog:false})
        if(event.currentTarget.dataset.iscode == 0)
            wx.navigateTo({url: '/page_package/contacts/contacts'})
    },
    more() {

    }
})