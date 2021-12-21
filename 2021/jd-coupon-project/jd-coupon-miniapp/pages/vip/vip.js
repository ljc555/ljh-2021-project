// pages/vip/vip.js
const app = getApp()
const {getVipInfo} = require("../../utils/network/services/user.js")
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const {onLogin} = require("../../utils/network/services/user.js")

Component({
    /**
     * 组件的属性列表
     */
    properties: {},

    /**
     * 组件的初始数据
     */
    data: {
        userInfo: null,
        isShow:true,
        promptMsg:"请填写微信号，让你的伙伴找到你~",
        vipInfo: {
            userInfo: null,
            teacherInfo: null,
            blocks: [],
            memberEstimateFeeInfo: {
                rebateFeeForToday: 0,
                rebateFeeForYesterday: 0,
                awardFeeForToday: 0,
                awardFeeForYesterday: 0
            },
            memberOrderInfo: {
                countForToday: 0,
                countForYesterday: 0
            }
        },
        windowHeight: app.globalData.windowHeight,
        windowWidth: app.globalData.windowWidth,
        CustomBar: app.globalData.CustomBar,
        animationData:{},

        requesting: false,
        end: false,
        emptyShow: false,
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 0,
        color: "#3F82FD",
        empty: false,

        isShowCode: true,
        isShowFens: true,
        isShowIncome: true,
        isShowOrder: true,
        invitationDialog: false,
        isIdentify:false
    },

    lifetimes: {
        attached: function attached() {
            this.setData({
                userInfo: app.globalData.userInfo
            })

        }
    },

    /**
     * 组件的方法列表
     */
    methods: {
        onClose(){
            this.setData({isShow:true})
        },
        onInputCode(){
            wx.navigateTo({
                url: '/page_package/contacts/contacts'
            })
        },
        // 刷新数据
        refresh() {
            this.setData({requesting: true})
            wx.vibrateShort();
            getVipInfo().then(res => {
                this.setData({requesting: false})
                res.data.data.blocks.forEach(item => {
                    item.isCheck = false
                })
                util.setStorage(constants.VIP_INFO_AUTO,res.data.data.userInfo)
                this.setData({
                    vipInfo: res.data.data,
                    isShow:res.data.data.userInfo.wechatId
                })
            }).catch(err => {
                this.setData({requesting: false})
            })
        },
        // 加载更多
        more() {

        },
        isShowData1(){
            this.setData({isShowCode:!this.data.isShowCode})
        },
        isShowData(event) {
            const item = event.currentTarget.dataset.item
            const index = event.currentTarget.dataset.index
            item.isCheck = !item.isCheck
            this.data.vipInfo.blocks.splice(index, 1, item)
            this.setData({vipInfo: this.data.vipInfo})
            // switch (Number(event.currentTarget.dataset.index)) {
            //     case 0:
            //         this.setData({isShowCode: !this.data.isShowCode})
            //         break
            //     case 1:
            //         this.setData({isShowFens: !this.data.isShowFens})
            //         break
            //     case 2:
            //         this.setData({isShowIncome: !this.data.isShowIncome})
            //         break
            //     case 3:
            //         this.setData({isShowOrder: !this.data.isShowOrder})
            //         break
            // }
        },
        onCopy(event) {
            let copyText = ''
            switch (Number(event.currentTarget.dataset.index)) {
                case 0:
                    copyText = this.data.vipInfo.userInfo.invitationCode
                    break
                case 1:
                    break
            }
            wx.setClipboardData({
                data: copyText,
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
        onShowDialog() {
            this.setData({
                invitationDialog: true
            })
        },
        hideModal(event) {
            switch (Number(event.currentTarget.dataset.iscode)) {
                case 0:
                    wx.setClipboardData({
                        data: this.data.vipInfo.teacherInfo.wechatId,
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
                    break
            }
            this.setData({
                invitationDialog: false
            })
        },
        onCash() {
            wx.navigateTo({
                url: '/page_package/money/cash/cash'
            })
        },
        onFans(event) {
            const item = event.currentTarget.dataset.item
            wx.navigateTo({
                url: item.more
            })
        },
        onEquity() {
            wx.navigateTo({
                url: '/page_package/equity/equity'
            })
        },
        onProfit() {
            wx.navigateTo({
                url: '/page_package/profit/profit'
            })
        },
        onOrder() {
            wx.navigateTo({
                url: '/page_package/order/order'
            })
        },
    },
    pageLifetimes: {
        show() {
            if (typeof this.getTabBar === 'function' &&
                this.getTabBar()) {
                this.getTabBar().setData({
                    selected: 2
                })
            }
            let that = this
            if (!app.globalData.loginStatus) {
                wx.login({
                    success: res => {
                        onLogin(res.code)
                            .then(res => {
                                app.globalData.userInfo = res.data.data
                                util.setStorage(constants.VIP_INFO_AUTO, res.data.data.userInfo)
                                that.setData({userInfo: res.data.data})
                                getVipInfo().then(res => {
                                    util.setStorage(constants.VIP_INFO,res.data.data)
                                    that.setData({
                                        vipInfo: res.data.data,
                                        isIdentify: util.identityApp(res.data.data.userInfo.identify)
                                    })
                                })
                            }).catch((err) => {
                            if (err.data.code = 'USER_NOT_REGISTERED') {
                                // that.setData({token: err.data.data.token})
                                wx.reLaunch({url: '/pages/login/login?token=' + err.data.data.token})
                            }
                        })
                    }
                })
            } else {
                getVipInfo().then(res => {

                    util.setStorage(constants.VIP_INFO_AUTO,res.data.data.userInfo)
                    that.setData({
                        vipInfo: res.data.data,
                        isShow:res.data.data.userInfo.wechatId,
                        isIdentify: util.identityApp(res.data.data.userInfo.identify)
                    })
                })
            }
        }
    },
})
