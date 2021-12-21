const app = getApp()
const {userContacts, updateUserInfo} = require('../../utils/network/services/user.js')
const constants = require('../../utils/constants')
const util = require('../../utils/util')

Page({

    /**
     * 页面的初始数据
     */
    data: {
        isEditWeChat: false,
        weChatCode: '',
        windowWidth: app.globalData.windowWidth,
        windowHeight: app.globalData.windowHeight,
        queryData: {
            pageIndex: 1,
            pageSize: 20
        },
        requesting: false,
        infoData: null
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
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
        wx.vibrateShort();
        this.refresh()
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
    onEdit() {
        this.setData({isEditWeChat: true})
    },
    onSave() {
        this.setData({isEditWeChat: false})
        updateUserInfo({wechatId: this.data.weChatCode})
            .then(res => {

            })
    },
    onCopy(event) {
        if (event.currentTarget.dataset.code) {
            wx.setClipboardData({
                data: event.currentTarget.dataset.code,
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
        } else wx.showToast({
            title: '暂无微信号'
        })
    },
    bindInput(event) {
        this.setData({weChatCode: event.detail.value})
    },
    getData(type) {
        userContacts(this.data.queryData)
            .then(res => {
                wx.stopPullDownRefresh()
                let arr = this.data.infoData
                if (arr && type == 'more') {
                    res.data.data.fans.forEach(item => {
                        arr.fans.push(item)
                    })
                }
                this.setData({
                    infoData: arr ? arr : res.data.data,
                    requesting: false,
                    weChatCode: res.data.data.mine.wechatId ? res.data.data.mine.wechatId : ''
                })
            }).catch(err => {
            wx.stopPullDownRefresh()
            this.setData({requesting: false})
        })
    },
    refresh() {
        this.data.queryData.pageIndex = 1
        this.setData({queryData: this.data.queryData, requesting: true})
        this.getData('refresh')
    },
    more() {
        this.data.queryData.pageIndex = ++this.data.queryData.pageIndex
        this.setData({queryData: this.data.queryData})
        this.getData('more')
    },
    onReachBottom(){
       this.more()
    }
})