// page_package/product-type/product-type.js
const app = getApp()
const {queryProductType} = require("../../utils/network/services/productImpl.js")
let pageStart = 1;
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const data = require("../../utils/data")

Page({
    data: {
        TabCur: 0,
        MainCur: 0,
        VerticalNavTop: 0,
        list: [],
        load: true,
        scrollTop:0,
        CustomBar: app.globalData.CustomBar,
        windowWidth: app.globalData.windowWidth,
        windowHeight: app.globalData.windowHeight,
        requesting: false,
        end: false,
        emptyShow: false,
        page: pageStart,
        listData: [],
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 0,
        color: "#3F82FD",
        empty: false,

        requestData: {
            cid1: 12218,
            pageIndex: pageStart,
            pageSize: 20
        },
        productList: [],
        productType: data.PRODUCT
    },
    onLoad() {

        this.getList('refresh', pageStart, 12218);

        wx.showLoading({
            title: '加载中...',
            mask: true
        });
        let list = [{}];
        for (let i = 0; i < 26; i++) {
            list[i] = {};
            list[i].name = String.fromCharCode(65 + i);
            list[i].id = i;
        }
        this.setData({
            list: list,
            listCur: list[0]
        })
    },
    onReady() {
        wx.hideLoading()
    },
    tabSelect(e) {
        let data = this.data.requestData
        let item = e.currentTarget.dataset.item
        data.cid1 = item.id
        this.setData({
            requestData: data,
            scrollTop:1
        })
        this.getList('refresh', pageStart, item.id);

        // if (wx.pageScrollTo) {
        //     wx.pageScrollTo({
        //         scrollTop: 0,
        //         selector:'#type-scroll',
        //         duration: 300,
        //         success:function () {
        //             console.log("成功")
        //         },
        //         fail:function () {
        //             console.log("失败")
        //         }
        //     })
        // }

        this.setData({
            TabCur: e.currentTarget.dataset.id,
            MainCur: e.currentTarget.dataset.id,
            VerticalNavTop: (e.currentTarget.dataset.id - 1) * 50
        })
    },
    VerticalMain(e) {
        let that = this;
        let list = this.data.list;
        let tabHeight = 0;
        if (this.data.load) {
            for (let i = 0; i < list.length; i++) {
                let view = wx.createSelectorQuery().select("#main-" + list[i].id);
                view.fields({
                    size: true
                }, data => {
                    list[i].top = tabHeight;
                    tabHeight = tabHeight + data.height;
                    list[i].bottom = tabHeight;
                }).exec();
            }
            that.setData({
                load: false,
                list: list
            })
        }
        let scrollTop = e.detail.scrollTop + 20;
        for (let i = 0; i < list.length; i++) {
            if (scrollTop > list[i].top && scrollTop < list[i].bottom) {
                that.setData({
                    VerticalNavTop: (list[i].id - 1) * 50,
                    TabCur: list[i].id
                })
                return false
            }
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
    getList(type, currentPage, productType) {
        let that = this
        this.setData({
            requesting: true
        })

        wx.showNavigationBarLoading()

        let data = {
            cid1: productType,
            pageIndex: currentPage,
            pageSize: 20
        }

        queryProductType(data).then((res) => {
            wx.hideNavigationBarLoading()

            let lastData = that.data.productList
            let reqData = res.data.data.records
            if (type == 'refresh') {
                lastData = reqData
            } else {
                reqData.forEach(item =>{lastData.push(item)})
            }
            lastData.forEach((item) => {
                if(item.coupons.length > 0 && item.priceInfo.lowestCouponPrice)
                    item.priceInfo.lowestCouponPrice = item.priceInfo.lowestCouponPrice.toFixed(2)

                item.comments = util.formatNUmber(item.comments)
                item.priceInfo.price = item.priceInfo.price.toFixed(2)
            })

            that.setData({
                page: currentPage + 1,
                requesting: false,
                productList: lastData
            })
        }).catch((err) => {
            wx.hideNavigationBarLoading()
            that.setData({
                requesting: false
            })
            wx.showModal({
                title: '提示',
                showCancel: false,
                content: err.data.message ? err.data.message : '获取信息失败',
                success(res) {
                }
            })
        })
    },
    // 刷新数据
    refresh() {
        this.getList('refresh', 1, this.data.requestData.cid1);
    },
    // 加载更多
    more() {
        this.getList('more', this.data.page, this.data.requestData.cid1);
    },

    onClick(event){
        util.setStorage(constants.PRPDUCT_ITEM,event.currentTarget.dataset.item)
        wx.navigateTo({
            url: '/page_package/details/index'
        })
    }

})