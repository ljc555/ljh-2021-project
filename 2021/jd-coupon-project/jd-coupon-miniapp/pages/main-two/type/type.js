// pages/main/type/type.js
const app = getApp()
const {
    queryProductType, hotProduct, ownerShip, goodComment,
    hotLowPrice, twoHoursRank, searchProduct
} = require("../../../utils/network/services/productImpl.js")
let pageStart = 1;
const constants = require("../../../utils/constants")
const util = require("../../../utils/util")

Component({
    /**
     * 组件的属性列表
     */
    properties: {},

    /**
     * 组件的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        isLoad: false,

        productList: [],
        page: pageStart,
        isReq:false
    },

    /**
     * 组件的方法列表
     */
    methods: {
        onMore(){
            this.getList('more', this.data.page, this.data.homeTabs[this.data.TabCur]);
        },
        itemCLick(event) {
            let item = event.currentTarget.dataset.item
            util.setStorage(constants.PRPDUCT_ITEM, item)
            wx.navigateTo({
                url: '/page_package/details/index'
            })
        },
        getSearchList(type, currentPage, productType, isCoupon, sort, sortName) {
            let that = this
            let searchData = {
                keyword: productType,
                pageIndex: currentPage,
                pageSize: 20,
            }
            if (sort) {
                searchData = {
                    keyword: productType,
                    pageIndex: currentPage,
                    pageSize: 20,
                    sort: sort,//asc升降序,desc默认降序
                    sortName: sortName//排序字段(price：单价, commissionShare：佣金比例, commission：佣金， inOrderCount30Days：30天引单量， inOrderComm30Days：30天支出佣金)
                }
            }
            if (isCoupon) {
                searchData = {
                    keyword: productType,
                    pageIndex: currentPage,
                    pageSize: 20,
                    isCoupon: 1//有优惠券
                }
            }
            that.setData({isLoad: type == "refresh"})
            searchProduct(searchData).then((res) => {
                that.forMaterData(that, res, currentPage, type)
            }).catch(err => {
                that.setData({isLoad: false})
                wx.showModal({
                    title: '提示',
                    showCancel: false,
                    content: '未查到到相关商品',
                    success(res) {
                    }
                })
            })
        },
        /**
         * 根据类型请求数据
         * @param type  刷新类型
         * @param currentPage  分页
         * @param productType  商品类型
         * @param reqType      请求类型
         */
        getList(type, currentPage, productType, reqType = '') {
            let that = this

            let data = {
                cid1: productType,
                pageIndex: currentPage,
                pageSize: 20
            }
            that.setData({isLoad: type == "refresh"})

            if (productType == -110) {
                hotProduct(data).then((res) => {
                    that.forMaterData(that, res, currentPage, type)
                }).catch((err) => {
                    that.errFormat(that, err)
                })
            } else if (productType == '居家必备') {
                ownerShip(data).then((res) => {
                    that.forMaterData(that, res, currentPage, type)
                }).catch((err) => {
                    that.errFormat(that, err)
                })
            } else if (productType == '好评之王') {
                goodComment(data).then((res) => {
                    that.forMaterData(that, res, currentPage, type)
                }).catch((err) => {
                    that.errFormat(that, err)
                })
            } else if (productType == '爆款9块9') {
                hotLowPrice(data).then((res) => {
                    that.forMaterData(that, res, currentPage, type)
                }).catch((err) => {
                    that.errFormat(that, err)
                })
            } else if (reqType == 'search') { //搜索
                let searchData = {
                    keyword: productType,
                    pageIndex: currentPage,
                    pageSize: 20,
                    sort: "",//asc升降序,desc默认降序
                    sortName: ""//排序字段(price：单价, commissionShare：佣金比例, commission：佣金， inOrderCount30Days：30天引单量， inOrderComm30Days：30天支出佣金)
                }
                searchProduct(searchData).then((res) => {
                    that.forMaterData(that, res, currentPage, type)
                }).catch(err => {
                    that.setData({isLoad: false})
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: '未查到到相关商品',
                        success(res) {
                        }
                    })
                })
            } else if (productType == '两小时榜') {
                twoHoursRank(data).then((res) => {
                    that.forMaterData(that, res, currentPage, type)
                }).catch((err) => {
                    that.errFormat(that, err)
                })
            } else
                queryProductType(data).then((res) => {
                    that.forMaterData(that, res, currentPage, type)
                }).catch((err) => {
                    that.errFormat(that, err)
                })
        },
        forMaterData(that, res, currentPage, type) {
            let lastData = that.data.productList
            let reqData = res.data.data.records
            if (type == 'refresh') {
                lastData = reqData
                that.setData({page: 2})
            } else {
                reqData.forEach(item => {
                    lastData.push(item)
                })
                that.setData({page: currentPage + 1})
            }

            lastData.forEach((item) => {
                if (item.coupons.length > 0 && item.priceInfo.lowestCouponPrice)
                    item.priceInfo.lowestCouponPrice = Number(item.priceInfo.lowestCouponPrice).toFixed(2)

                item.comments = util.formatNUmber(item.comments)
                item.priceInfo.price = Number(item.priceInfo.price).toFixed(2)
            })

            that.setData({
                isLoad: false,
                productList: lastData,
                requesting: false,
                isReq:true
            })
            this.triggerEvent('pageEvent', {page: that.data.page} )
        },
        errFormat(that, err) {
            this.triggerEvent('pageEvent', {page: that.data.page - 1} )
            that.setData({isLoad: false, requesting: false})
            wx.showModal({
                title: '提示',
                showCancel: false,
                content: err.data.message ? err.data.message : '获取信息失败',
                success(res) {
                }
            })
        }
    }
})
