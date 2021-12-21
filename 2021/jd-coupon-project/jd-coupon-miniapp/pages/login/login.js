// page_package/login/login.js
const app = getApp()
const {checkCode} = require('../../utils/network/services/user.js')
const constants = require('../../utils/constants')
const util = require('../../utils/util')

Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        windowHeight: app.globalData.windowHeight,
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        Custom: app.globalData.Custom,

        invitationDialog: false,
        isInvitation: false,//是否邀请人
        invitationInfo: {},

        check: true,//默认同意协议
        isBindPhone: false,
        isBindCode: true,
        isUserInfo: false,

        phone: '',
        token: '',
        iv: '',
        loginData: {
            invitationCode: '',//邀请码
            phone: '',//手机号
            token: '',//登录token
            iv: '',//手机号标识
            nickName: '',//昵称
            avatarUrl: '',//头像
        }

    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        const inviteData = util.getStorage(constants.INVITE_DATA)
        const dictionary = util.getStorage(constants.DICTIONARY)
        if (inviteData && inviteData.invitationCode != 'undefined') {
            switch (inviteData.type) {
                case constants.INVITE_USER_APPS://小程序分享邀请
                case constants.INVITE_SHARE_APPS://商品小程序分享邀请
                    this.data.loginData.invitationCode = inviteData.invitationCode
                    this.setData({
                        loginData: this.data.loginData
                    })
                    break
            }
        } else if (dictionary) { //获取系统配置的默认邀请码
            dictionary.forEach(item => {
                if (item.key == 'app_default_inviter_code') {
                    this.data.loginData.invitationCode = item.value
                    this.setData({loginData: this.data.loginData})
                }
            })
        }
        let loginData = this.data.loginData
        loginData.token = options.token
        this.setData({
            loginData: loginData
        })
        if(this.data.loginData.invitationCode == undefined){
            this.data.loginData.invitationCode = ''
            this.setData({loginData:this.data.loginData})
        }
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
    toHome() {
        wx.reLaunch({
            url: '/pages/index/index',
        })
    },
    login() {
        console.log('登录')
        // 登录
        wx.login({
            success: res => {
                // 发送 res.code 到后台换取 openId, sessionKey, unionId
                console.log('用户信息=' + JSON.stringify(res))
                https.Http.request('post', 'http://192.168.3.173:8080/login/wechat', {

                    code: res.code
                })
                    .then(res => {

                    }).catch(err => {

                })
            }
        })
    },

//  ---------------------
    onInput(event) {
        let that = this
        let data = that.data.loginData
        data.invitationCode = event.detail.value
        if (data.invitationCode.length == 6) {
            checkCode(data.invitationCode).then(res => {
                that.setData({
                    invitationDialog: true,
                    invitationInfo: res.data.data
                })
            }).catch(err => {
                let msg = err.data.message
                if (err.data.code == 'NOT_FOUND')
                    msg = '未找到邀请人信息'
                wx.showModal({
                    title: '提示',
                    showCancel: false,
                    content: msg ? msg : '获取信息失败',
                    success(res) {
                    }
                })
            })
        }
        this.setData({
            loginData: data
        })
    },
    hideModal(event) {
        this.setData({
            isInvitation: event.currentTarget.dataset.iscode == '0',
            invitationDialog: false
        })
        if (event.currentTarget.dataset.iscode == '0') {
            this.setData({
                isBindCode: false,
                isBindPhone: true
            })
        }
    },

    /**
     * 选中协议
     * @param event
     */
    onCheckChange(event) {
        this.setData({
            check: !this.data.check
        })
    },

    invitePrompt() {
        wx.showModal({
            title: '提示',
            showCancel: false,
          content: '请联系身边已注册尚橙的朋友索要或网络搜索尚橙邀请码',
            success(res) {
            }
        })
    },

    onNext(event) {
        let that = this
        checkCode(that.data.loginData.invitationCode).then(res => {
            that.setData({
                invitationDialog: true,
                invitationInfo: res.data.data
            })
        }).catch(err => {
            let msg = err.data.message
            if (err.data.code == 'NOT_FOUND')
                msg = '未找到邀请人信息'
            wx.showModal({
                title: '提示',
                showCancel: false,
                content: msg ? msg : '获取信息失败',
                success(res) {
                }
            })
        })
        // 登录
        // wx.login({
        //         //     success: res => {
        //         //         // 发送 res.code 到后台换取 openId, sessionKey, unionId
        //         //         console.log("用户信息=" + JSON.stringify(res))
        //         //
        //         //         https.Http.request('post', apis.LOGIN(), {code: res.code})
        //         //             .then((res) => {
        //         //                 //{"code":"","isSuccess":false,"message":"未注册的微信用户","data":{"token":"oMTE85EX-6wYAQKdhKrQtmOao9Ww"}}
        //         //                 if (res.code == 'USER_NOT_REGISTERED') {
        //         //                     that.setData({
        //         //                         token: res.data.token,
        //         //                         isBindCode: false,
        //         //                         isBindPhone: true
        //         //                     })
        //         //                 }
        //         //                 console.log(res)
        //         //             }).catch((err) => {
        //         //             console.log(err, err.data.code)
        //         //             if (err.data.code == "USER_NOT_REGISTERED") {
        //         //                 console.log("111111111")
        //         //                 that.setData({
        //         //                     token: err.data.data.token,
        //         //                     isBindCode: false,
        //         //                     isBindPhone: true
        //         //                 })
        //         //             }
        //         //             console.log(err)
        //         //         })
        //         //     }
        //         // })
    },
    getPhoneNumber(e) {
        if (e.detail.encryptedData != undefined) {
            let data = this.data.loginData
            data.phone = e.detail.encryptedData
            data.iv = e.detail.iv
            this.setData({
                loginData: data
            })
            util.setStorage(constants.AUTH_INFO_CACHE, data)
            wx.redirectTo({
                url: './phone-login/phone-login?type=1'
            })
        } else {
            wx.reLaunch({
                url: '/pages/index/index',
            })
        }
    }
})