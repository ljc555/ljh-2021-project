// components/order-item/order-item.js
const app = getApp()
const {searchProduct} = require('../../utils/network/services/productImpl.js')
const util = require('../../utils/util.js')
const constants = require('../../utils/constants.js')
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        orderItem: {
            type: Object,
            value: {},
        },
        type: {
            type: Number,
            value: 1
        }
    },

    /**
     * 组件的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        isShowDialog: false,
        msg: "",
    },

    /**
     * 组件的方法列表
     */
    methods: {
        onItem(event) {
            const item = event.currentTarget.dataset.item
            const data = {skuIds: [item.skuId]}
            wx.showLoading({title: '加载中...'})
            searchProduct(data)
                .then(res => {
                    wx.hideLoading()
                    if (res.data.data.records.length > 0) {
                        util.setStorage(constants.PRPDUCT_ITEM, res.data.data.records[0])
                        wx.navigateTo({
                            url: '/page_package/details/index'
                        })
                    } else {
                        this.setData({
                            isShowDialog: true,
                            msg: "商品已经失效"
                        })
                    }
                }).catch(err => {
                wx.hideLoading()
                this.setData({
                    isShowDialog: true,
                    msg: "商品已经失效"
                })
            })
        }
    }
})
