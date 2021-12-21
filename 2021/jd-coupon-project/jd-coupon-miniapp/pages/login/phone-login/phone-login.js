// page_package/login/phone-login/phone-login.js
const app = getApp()
const {userRegister, updateUserInfo} = require("../../../utils/network/services/user.js")
const constants = require("../../../utils/constants")
const util = require("../../../utils/util")

Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        Custom: app.globalData.Custom,

        loginData: null,
        type: 1,
        invitationDialog:false
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let that= this
        this.setData({
            type: options.type ? Number(options.type) : 1,
            loginData: util.getStorage(constants.AUTH_INFO_CACHE)
        })
        if (this.data.type == 1)
        setTimeout(()=>{that.setData({invitationDialog:true})},50)
        console.log(this.data.loginData)
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

    toHome() {
        wx.reLaunch({
            url: '/pages/index/index',
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
    onShareAppMessage: function () {

    },
    getUserInfo(event) {
        if (event.detail.userInfo) {
            let userInfo = event.detail.userInfo

            // event.detail.userInfo
            // avatarUrl: ""
            // city: "Loudi"
            // country: "China"
            // gender: 1
            // language: "zh_CN"
            // nickName: "伍星"
            // province: "Hunan"
            let data = {
                avatarUrl: userInfo.avatarUrl,
                invitationCode: this.data.loginData.invitationCode,
                nickName: userInfo.nickName,
                phone: this.data.loginData.phone,
                token: this.data.loginData.token,
                iv: this.data.loginData.iv
            }

            let updateInfo = {
                avatarUrl: userInfo.avatarUrl,
                nickName: userInfo.nickName,
            }
            if (this.data.type == 1) {
                userRegister(data).then((res) => {
                    util.setStorage(constants.AUTH_INFO, data)
                    this.loginType()
                }).catch(err => {
                    let msg = err.data.message
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: msg ? msg : '登录失败',
                        success(res) {
                        }
                    })
                })
            } else {
                // wx.showModal({
                //     title: '提示',
                //     showCancel: false,
                //     content: msg ? msg : '登录失败',
                //     success(res) {
                //     }
                // })
                wx.reLaunch({
                    url: '/pages/index/index',
                })
            }
        } else {
            /*  wx.showToast({
                  title: "请授权登录",
                  icon: "none",
                  duration: 3000
              })*/
            wx.reLaunch({
                url: '/pages/index/index',
            })
        }
    },
    loginType() {
        const inviteData = util.getStorage(constants.INVITE_DATA)
        if (inviteData) {
            switch (inviteData.type) {
                case constants.INVITE_SHARE_APPS://商品小程序分享邀请
                    util.removeStorage(constants.INVITE_DATA)
                    wx.reLaunch({
                        url: '/pages/product/product?isLogin=yes&id=' + inviteData.sukId + '&invitationCode=' + inviteData.invitationCode
                    })
                    break
                default:
                    wx.reLaunch({
                        url: '/pages/index/index',
                    })
                    break
            }
        } else
            wx.reLaunch({
                url: '/pages/index/index',
            })
    },
    hideModal(event) {
        this.setData({
            isInvitation: event.currentTarget.dataset.iscode == '0',
            invitationDialog: false
        })
    },
})