// page_package/menu-product/menu-product.js
const app = getApp()
const {global, highCommission, deliverById, brand, queryProductType,couponList,ringPush} = require("../../utils/network/services/productImpl.js")
let pageStart = 1;
const constants = require("../../utils/constants")
const util = require("../../utils/util")


Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        windowHeight: app.globalData.windowHeight,
        CustomBar: app.globalData.CustomBar,

        requesting: false,
        end: false,
        emptyShow: false,
        page: pageStart,
        listData: [],
        hasTop: false,
        enableBackToTop: false,
        refreshSize: 90,
        bottomSize: 150,
        color: "#3F82FD",
        empty: false,

        TabCur: 0,
        scrollLeft: 0,

        productList: [],
        type: "",
        title: "",//页面标题
        tabs: [],
        item: null,
        cid1: null,
        scrollTop:-1
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let item = JSON.parse(options.item)
        this.setData({
            type: options.type,
            tabs: item.tabs,
            item: item,
            cid1: item.tabs.length > 0 ? item.tabs[0].type : null
        })
        if (!util.isNumber(options.type))
            this.setData({title: options.type})
        else
            switch (Number(options.type)) {
                case constants.JD_DELIVERY:
                    this.setData({title: "京东配送"})
                    break
                case constants.BRAND_ZONE:
                    this.setData({title: "品牌专区"})
                    break
                case constants.COUPON:
                    this.setData({title: "优惠券"})
                    break
                case constants.HAIR_RING_PUSH:
                    this.setData({title: "发圈必推"})
                    break
                case constants.FASHION_LIFE:
                    this.setData({title: "时尚生活"})
                    break
                case constants.DIGITAL_HOME:
                    this.setData({title: "家电数码"})
                    break
                case constants.FRESH_FOOD:
                    this.setData({title: "食品生鲜"})
                    break
                case constants.FURNITYRE:
                    this.setData({title: "家居日用"})
                    break
                case constants.BEAUTY_CARE:
                    this.setData({title: "美妆个护"})
                    break
            }

        this.getList('refresh', pageStart);


        wx.setNavigationBarTitle({
            title:this.data.title
        })
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
    // 刷新数据
    refresh() {
        this.getList('refresh', pageStart);
        this.setData({
            empty: false
        })
    },
    // 加载更多
    more() {
        this.getList('more', this.data.page);
    },
    /**
     * 根据类型请求数据
     * @param type  刷新类型
     * @param currentPage  分页
     * @param productType  商品类型
     * @param reqType      请求类型
     */
    getList(type, currentPage) {
        let that = this

        this.setData({
            requesting: true
        })
        let data = {
            pageIndex: currentPage,
            pageSize: 20
        }
        that.setData({isLoad: type == "refresh"})

        if (this.data.type == '搜罗好货') {
            global(data).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch((err) => {
                that.errFormat(that, err)
            })
        } else if (this.data.type == '高佣商品') {
            highCommission(data).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch((err) => {
                that.errFormat(that, err)
            })
        } else if (this.data.type == constants.JD_DELIVERY) {
            deliverById(data).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch((err) => {
                that.errFormat(that, err)
            })
        } else if (this.data.type == constants.BRAND_ZONE) {
            brand(data).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch((err) => {
                that.errFormat(that, err)
            })
        } else if (this.data.type == constants.COUPON) {
            couponList(data).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch((err) => {
                that.errFormat(that, err)
            })
        } else if(this.data.type == constants.HAIR_RING_PUSH){
            ringPush(data).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch((err) => {
                that.errFormat(that, err)
            })
        }else if (this.data.tabs.length > 0) {
            let data1 = {
                cid1: this.data.cid1,
                pageIndex: currentPage,
                pageSize: 20
            }
            queryProductType(data1).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch((err) => {
                that.errFormat(that, err)
            })
        }
    },

    forMaterData(that, res, currentPage, type) {
        let lastData = that.data.productList
        let reqData = res.data.data.records
        if (type == 'refresh') {
            lastData = reqData
        } else {
            reqData.forEach(item => {
                lastData.push(item)
            })
        }

        lastData.forEach((item) => {

            let couponPrice = item.priceInfo.lowestCouponPrice
            if (item.coupons.length > 0 && couponPrice != 'undefined' && couponPrice != 'NaN') {
                try {
                    item.priceInfo.lowestCouponPrice = item.priceInfo.lowestCouponPrice.toFixed(2)
                } catch (e) {
                    // console.log(">>>", e)
                }
            }

            if (item.comments)
                item.comments = util.formatNUmber(item.comments)
            try {
                item.priceInfo.price = item.priceInfo.price.toFixed(2)
            } catch (e) {
            }

        })

        that.setData({
            page: currentPage + 1,
            productList: lastData,
            requesting: false
        })
    },
    errFormat(that, err) {
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
    },
    itemCLick(event) {
        let item = event.currentTarget.dataset.item
        util.setStorage(constants.PRPDUCT_ITEM, item)
        wx.navigateTo({
            url: '/page_package/details/index'
        })
    },
    tabSelect(e) {
        this.setData({
            TabCur: e.currentTarget.dataset.id,
            scrollLeft: (e.currentTarget.dataset.id - 1) * 60,
            scrollTop:0
        })
        let item = e.currentTarget.dataset.item
        this.setData({cid1:item.type})
        this.getList('refresh', pageStart)
    },
})