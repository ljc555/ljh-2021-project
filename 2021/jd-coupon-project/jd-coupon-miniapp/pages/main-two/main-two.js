// pages/main/main.js
const app = getApp()
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const {onLogin} = require("../../utils/network/services/user.js")
let pageStart = 0;
Component({
    /**
     * 组件的属性列表
     */
    properties: {},

    /**
     * 组件的初始数据
     */
    data: {
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        Custom: app.globalData.Custom,
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


    lifetimes: {
        attached: function () {
            this.login()
            this.searchContent(this)
        },
    },

    /**
     * 组件的方法列表
     */
    methods: {
        login() {
            let that = this
            // wx.navigateTo({url: '/page_package/login/login?token=1'})
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
        onTransfer(){
            wx.navigateTo({url: '/page_package/transfer/transfer/transfer'})
        },
        searchContent(that){
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
        pageEvent(event) {
            this.setData({pages: event.detail.page})
        },
        onRefresh1() {
            this.setData({
                requesting: false
            })
        },
        NavChange() {
            this.triggerEvent('NavChangen', {})
            util.setStorage(constants.RAIDERS,1)
            wx.switchTab({
                url: '/pages/strategy/strategy'
            })
        },
        /**去搜索 */
        goToSearch(event) {
            wx.navigateTo({
                url: '/page_package/search/search'
            })
        },
        /**商品类型页面 */
        goToType(event) {
            // wx.navigateTo({
            //     url: '/page_package/product-type/product-type'
            // })
            wx.switchTab({
                url: '/pages/sort/sort'
            })
        },
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


// ----------刷新-----------
        itemClick(e) {
            console.log(e);
        },
        hasTopChange(e) {
            this.setData({
                hasTop: e.detail.value
            })
        },
        enableBackToTopChange(e) {
            this.setData({
                enableBackToTop: e.detail.value
            })
        },
        refreshChange(e) {
            this.setData({
                refreshSize: e.detail.value
            })
        },
        bottomChange(e) {
            this.setData({
                bottomSize: e.detail.value
            })
        },
        radioChange: function (e) {
            this.setData({
                color: e.detail.value
            })
        },
        emptyChange(e) {
            if (e.detail.value) {
                this.setData({
                    listData: [],
                    emptyShow: true,
                    end: true
                })
            } else {
                this.setData({
                    emptyShow: false,
                    end: false
                })
            }
        },
        getList(type, currentPage) {
            this.setData({
                requesting: true
            })

            wx.showNavigationBarLoading()

            // 模拟异步获取数据场景
            setTimeout(() => {
                this.setData({
                    requesting: false
                })

                wx.hideNavigationBarLoading()

                if (type === 'refresh') {
                    this.setData({
                        page: currentPage + 1
                    })
                } else {
                    this.setData({
                        page: currentPage + 1,
                        end: false
                    })
                }
            }, 1000);
        },
        onScroll(event){
            this.setData({isShowBack:event.detail.detail.scrollTop > this.data.windowHeight*0.4})
        },
        goTop(){
            this.setData({scrollTop:0,isShowBack:false})
        },
        // 刷新数据
        refresh() {
            this.login()
            this.setData({empty: false, requesting: true})
        },
        // 加载更多
        more() {
            if (this.data.isType) {
                this.selectComponent("#main").onMore()
            } else {
                this.selectComponent("#product-list").getList('more', this.data.pages, this.data.selectTab);
            }

            this.setData({
                requesting: true
            })
            setTimeout(() => {
                this.setData({
                    requesting: false
                })
            }, 1000);
        },
        onLoad() {
            this.getList('refresh', pageStart);
        },
    },
    pageLifetimes: {
        show() {
            if (typeof this.getTabBar === 'function' &&
                this.getTabBar()) {
                this.getTabBar().setData({
                    selected: 0
                })
            }
            const userInfo = util.getStorage(constants.AUTH_INFO)
            if(userInfo)
            this.setData({isSuperVip:userInfo.identify > 1})
        }
    },

})
