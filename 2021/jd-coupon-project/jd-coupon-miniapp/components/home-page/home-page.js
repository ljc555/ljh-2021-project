const app = getApp()
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const {onLogin} = require("../../utils/network/services/user.js")
const {queryProductType, goodsTop} = require("../../../utils/network/services/productImpl.js")
const {newsList, homeBanner} = require("../../../utils/network/services/service.js")
let pageStart = 1;

Component({
    /**
     * 组件的属性列表
     */
    properties: {},

    /**
     * 组件的初始数据
     */
    data: {
        windowHeight: app.globalData.windowHeight,
        TabCur: 0,
        scrollLeft: 0,
        userInfo: '',
        requesting: false,
        end: false,
        emptyShow: false,
        page: pageStart,
        listData: [],
        hasTop: false,
        scrollTop: 1,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 100,
        color: "#3F82FD",
        empty: false,
        selectTab: -110,
        pages: 1,
        isShowBack: false,
        isSuperVip: false,
        invitationDialog: false,
        searchContent: ''
    },

    /**
     * 组件的方法列表
     */
    methods: {
        login() {
            let that = this
            this.selectComponent("#main").onRefresh();
            if (!app.globalData.loginStatus) {
                // 登录
                wx.login({
                    success: res => {
                        onLogin(res.code)
                            .then(res => {
                                app.globalData.userInfo = res.data.data
                                app.globalData.loginStatus = true
                                util.setStorage(constants.AUTH_INFO, res.data.data)
                                that.setData({userInfo: res.data.data})
                            }).catch((err) => {
                            if (err.data.code = 'USER_NOT_REGISTERED' && app.globalData.loginMode == 'true') {
                                that.data.requesting = false
                                that.setData({token: err.data.data.token})
                                wx.navigateTo({url: '/pages/login/login?token=' + err.data.data.token})
                            }
                        })
                    }
                })
            }
        },
        onScroll(event) {
            this.setData({isShowBack: event.detail.detail.scrollTop > this.data.windowHeight * 0.4})
        },
        // 刷新数据
        refresh() {
            this.login()
            this.setData({empty: false, requesting: true})
        },
        // 加载更多
        more() {
            this.selectComponent("#main").onMore()
            this.setData({requesting: true})
            setTimeout(() => {this.setData({requesting: false})}, 1000);
        },
    }
})
