//index.js
//获取应用实例
const app = getApp()
const userApi = require("../../utils/network/services/user")
const {onLogin} = require("../../utils/network/services/user.js")
const constants = require("../../utils/constants")
const util = require("../../utils/util")
let pageStart = 0;
let that = null;


Page({
    data: {
        isLoad:false,
        language: {
            content: app.i18n._('content'),
        },
        token: "",
        userInfo: {},
        hasUserInfo: false,
        canIUse: wx.canIUse('button.open-type.getUserInfo'),
        scene:"",
        PageCur: 'main',

        test:'',


    //    ----------------------------
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        Custom: app.globalData.Custom,
        windowHeight: app.globalData.windowHeight,
        TabCur: 0,
        scrollLeft: 0,
        requesting: false,
        end: false,
        emptyShow: false,
        page: pageStart,
        listData: [],
        hasTop: false,
        scrollTop:1,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 100,
        color: "#3F82FD",
        empty: false,
        selectTab: -110,
        pages: 1,
        isShowBack:false,
        isSuperVip:false,
        tabs: [
            {
                "id": 12218,
                "name": "推荐",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": -110,
                "name": "热门",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 12218,
                "name": "生鲜",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 1320,
                "name": "食品",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 1620,
                "name": "家居",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 1316,
                "name": "美妆",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 16750,
                "name": "个护",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 6728,
                "name": "汽车",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 9847,
                "name": "家具",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 15901,
                "name": "清洁",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 1713,
                "name": "图书",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 1319,
                "name": "母婴",
                "grade": 0,
                "parentId": 0
            },
            {
                "id": 652,
                "name": "数码",
                "grade": 0,
                "parentId": 0
            }],

        isMain: false,
        isType: true,
        invitationDialog: false,
        searchContent: ''

    },

    NavChange(e) {
        util.setStorage(constants.RAIDERS,1)
        wx.switchTab({
            url: '/pages/strategy/strategy'
        })
    },

    //事件处理函数
    goTo: function () {
        wx.navigateTo({
            url: '../../page_package/details/index'
        })
    },

    onLoad: function (options) {
        that = this
        if(options.scene){//小程序码邀请
            let data = util.parseQueryString(decodeURIComponent(options.scene))
            this.setData({
                scene:data
            })
            console.log("邀请信息",data)
            util.setStorage(constants.INVITE_DATA, {
                type: constants.INVITE_USER_APPS,
                sukId: "",
                invitationCode: this.data.scene.ic
            })
            this.wxLogin()
        }else if(options.invitationCode){ //直接邀请
            util.setStorage(constants.INVITE_DATA, {
                type: constants.INVITE_USER_APPS,
                sukId: "",
                invitationCode: options.invitationCode
            })
           this.wxLogin()
        } else if(!app.globalData.loginStatus){
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

        that.selectComponent("#main").onRefresh();
        that.searchContent() //监测粘贴板内容
    },

    onShow: function () {
        let that = this
        if (that.data.token)
            userApi.checkLogin().then((res => {
                console.log("已经登录了")
            })).catch(err => {
                wx.navigateTo({
                    url: '/pages/login/login?token=' + that.data.token
                })
            })
        if (typeof this.getTabBar === 'function' &&
            this.getTabBar()) {
            this.getTabBar().setData({
                selected: 0
            })
        }
        const userInfo = util.getStorage(constants.AUTH_INFO)
        if(userInfo)
            this.setData({isSuperVip:userInfo.identify > 1})
    },
    wxLogin(){
        wx.login({
            success: res => {
                onLogin(res.code)
                    .then(res => {
                    }).catch((err) => {
                    if (err.data.code == 'USER_NOT_REGISTERED') {
                        this.setData({token: err.data.data.token})
                        wx.navigateTo({url: '/pages/login/login?token=' + err.data.data.token})
                    }
                })
            }
        })
    },
    getUserInfo: function (e) {
        console.log(e)
        app.globalData.userInfo = e.detail.userInfo
        this.setData({
            userInfo: e.detail.userInfo,
            hasUserInfo: true
        })
    },

    onReady: function () {

    },
    onShareAppMessage: function (res) {
        console.log(res)
       let loginInfo = util.getStorage(constants.AUTH_INFO)
        let path = '/pages/index/index?invitationCode='
        if(loginInfo) path = '/pages/index/index?invitationCode=' + loginInfo.invitationCode
        return {
            title: "尚橙优选",
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
    /**去搜索 */
    goToSearch(event) {wx.navigateTo({url: '/page_package/search/search'})},
    /**去转链*/
    onTransfer(){wx.navigateTo({url: '/page_package/transfer/transfer/transfer'})},
    /**商品类型页面 */
    goToType(event) {wx.switchTab({url: '/pages/sort/sort'})},

    tabSelect(e) {
        this.setData({
            TabCur: e.currentTarget.dataset.id,
            scrollLeft: (e.currentTarget.dataset.id - 1) * 60,
            isMain: Number(e.currentTarget.dataset.id) != 0,
            isType: Number(e.currentTarget.dataset.id) == 0
        })
        let item = e.currentTarget.dataset.item
        this.setData({selectTab: item.id})
        if (Number(e.currentTarget.dataset.id) != 0) {
            this.selectComponent("#product-list").getList('refresh', 1, item.id);
        }
    },
    searchContent(){
        wx.getClipboardData({
            success(res) {
                if (res.data){
                    let historyList = util.getStorage(constants.EXTERNAL_SEARCH_HISTORY)
                    historyList = historyList ? historyList : []
                    let isExis = historyList.filter((elem) => elem == res.data)
                    if (isExis.length == 0) {
                        historyList.push(res.data)
                        util.setStorage(constants.EXTERNAL_SEARCH_HISTORY, historyList)
                        that.setData({searchContent: res.data, invitationDialog: true})
                    }
                }
            }
        })
    },
    onPageScroll(event){
        this.setData({isShowBack:event.scrollTop > this.data.windowHeight*0.4})
    },
    goTop(){
        wx.pageScrollTo({scrollTop: 0})
        this.setData({scrollTop:0,isShowBack:false})
    },
    hideModal(event) {
        switch (Number(event.currentTarget.dataset.iscode)) {
            case 0:
                wx.navigateTo({
                    url: '/page_package/search/search?content=' + this.data.searchContent
                })
                break
        }
        this.setData({
            invitationDialog: false
        })
    },
    onPullDownRefresh(){
        wx.vibrateShort();
        that.selectComponent("#main").onRefresh();
    },
    onReachBottom(){
        if(this.data.isLoad) return
        if (this.data.isType) {
            this.selectComponent("#main").onMore()
        } else {
            this.selectComponent("#product-list").getList('more', this.data.pages, this.data.selectTab);
        }
        this.setData({isLoad:true})
    },
    onRefresh1() {
       setTimeout(() => {wx.stopPullDownRefresh()},300)
        this.setData({isLoad:false})
    },
    pageEvent(event) {
        this.setData({pages: event.detail.page,
            isLoad:false})
    },
})