// page_package/activity/activity.js
const app = getApp()
const {activityByID, goodsConvert} = require('../../utils/network/services/service.js')
const {productsCode} = require('../../utils/network/services/productImpl.js')
const constants = require('../../utils/constants')
const util = require('../../utils/util')
const {onLogin} = require("../../utils/network/services/user.js")
let that = null
Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        activityContent: null,
        copyContent: '',
        copywriting: "1.复制以下文案并微信发送给自己\n2.通过微信访问活动地址购买",
        imgWidth: 0,
        imgHeight: 0,
        userInfo:null,
        ctx:null
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        wx.setNavigationBarTitle({title: options.title})
        that = this
        let width = this.data.windowWidth - 30
        this.setData({imgWidth: width, imgHeight: width + 128})

        if (!app.globalData.loginStatus) {
            wx.showLoading({title: '加载中...'})
            // 登录
            wx.login({
                success: res => {
                    onLogin(res.code)
                        .then(res => {
                            app.globalData.userInfo = res.data.data
                            app.globalData.loginStatus = true
                            util.setStorage(constants.AUTH_INFO, res.data.data)
                            that.setData({userInfo: res.data.data})
                            wx.showLoading({title: '加载中...'})
                            that.getActivity(options)
                        }).catch((err) => {
                        if (err.data.code = 'USER_NOT_REGISTERED' && app.globalData.loginMode == 'true') {
                            that.data.requesting = false
                            that.setData({token: err.data.data.token})
                            wx.navigateTo({url: '/pages/login/login?token=' + err.data.data.token})
                        }
                    })
                }
            })
        } else
            that.getActivity(options)

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
    onCopy() {
        if (this.data.copyContent)
            this.shareContent(this.data.copyContent)
        else
            goodsConvert(this.data.activityContent.content)
                .then(res => {
                    this.setData({copyContent: res.data.data.content})
                    this.shareContent(this.data.copyContent)
                }).catch(err => {
                wx.showToast({title: "获取推广链接失败", icon: "none"})
            })
    },
    shareContent(content) {
        wx.setClipboardData({
            data: content,
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
    },
    goToIcon() {
        wx.previewImage({
            urls: [this.data.activityContent.shareImage],
        })
    },
    getActivity(options){
        activityByID(options.id)
            .then(res => {
                this.setData({
                    activityContent: res.data.data
                })
                // this.imgLoad()
            }).catch(err => {
            // wx.hideLoading()
            wx.showToast({
                title: "活动加载失败",
                icon: "none",
                duration: 3000
            })
        })
    },
    imgLoad(){
        wx.showLoading({title: '加载中...'})
        Promise.all([
            new Promise((resolve, reject) => {
                wx.getImageInfo({
                    src: that.data.userInfo.avatarUrl,
                    success(res) {
                        resolve(res)
                    },
                    fail(err) {
                        reject(err)
                    }
                })
            }),
            new Promise((resolve, reject) => {
                wx.getImageInfo({
                    src: that.data.activityContent.shareImage,
                    success(res) {
                        resolve(res)
                    },
                    fail(err) {
                        reject(err)
                    }
                })
            }),
            // productsCode(data)
            //     .then(res => {
            //         let filePath = `${wx.env.USER_DATA_PATH}/${that.data.productDetails.skuId}${that.data.productDetails.inOrderCount30Days}`;
            //         wx.getFileSystemManager().writeFile({
            //             filePath: filePath,
            //             data: wx.base64ToArrayBuffer(res.data.data.content),
            //             encoding: 'binary',
            //             success: () => {
            //                 resolve(filePath);
            //             },
            //             fail: err => {
            //                 reject(err);
            //             },
            //         });
            //     }).catch((err) => {
            //     reject(err);
            // })
        ]).then(res => {
            const ctx = wx.createCanvasContext('myCanvas', that);
            ctx.fillStyle = "#FFFFFF";
            ctx.fillRect(0, 0, that.data.imgWidth, that.data.imgHeight);
            that.setData({ctx: ctx})
        }).catch(err => {
            wx.showToast({
                title: "分享图加载失败",
                icon: "none",
                duration: 3000
            })
        })
    }
})