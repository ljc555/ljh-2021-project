// pages/main/home/home.js
const app = getApp()
const {queryProductType, goodsTop} = require("../../../utils/network/services/productImpl.js")
const {newsList, homeBanner} = require("../../../utils/network/services/service.js")
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
        TabCur: 0,
        scrollLeft: 0,
        windowWidth: app.globalData.windowWidth,

        homeTabs: [
            "居家必备", "好评之王", "爆款9块9", "两小时榜"
        ],
        exclusiveList: [
            {
                type: '搜罗好货',
                title: '搜罗全球好货',
                subTitle: '惠享品质生活',
                icon: 'https://img11.360buyimg.com/mobilecms/s140x140_jfs/t5782/134/8417323455/334002/5dce1cbe/597af2a5N85ca792d.jpg',
                navUrl: '',
                tabs: []
            },
            {
                type: '高佣商品',
                title: '专属高佣商品',
                subTitle: '让赚钱更容易',
                icon: 'https://img13.360buyimg.com/mobilecms/s300x300_jfs/t22144/211/466307573/163227/caf5ff10/5b0e1887Na1187ab2.jpg!q70.jpg',
                navUrl: '',
                tabs: []
            },
            {
                title: '新人上手指南',
                subTitle: '新人进阶必看',
                icon: 'https://img11.360buyimg.com/mobilecms/s140x140_jfs/t5782/134/8417323455/334002/5dce1cbe/597af2a5N85ca792d.jpg',
                navUrl: '',
                tabs: []
            },
            // {
            //     title: '爆款额外奖励',
            //     subTitle: '推广得现金奖励',
            //     icon: 'https://img11.360buyimg.com/mobilecms/s140x140_jfs/t5782/134/8417323455/334002/5dce1cbe/597af2a5N85ca792d.jpg',
            //     navUrl: '/page_package/time-reward/time-reward',
            //     tabs: []
            // },
            // {
            //     title: '京东新人福利',
            //     subTitle: '推广成单得奖励',
            //     icon: 'https://img11.360buyimg.com/mobilecms/s140x140_jfs/t5782/134/8417323455/334002/5dce1cbe/597af2a5N85ca792d.jpg',
            //     navUrl: '/page_package/new-welfare/new-welfare',
            //     tabs: []
            // },
            // {
            //     title: '红包现金奖励',
            //     subTitle: '建群拉新可领',
            //     icon: 'https://img11.360buyimg.com/mobilecms/s140x140_jfs/t5782/134/8417323455/334002/5dce1cbe/597af2a5N85ca792d.jpg',
            //     navUrl: '',
            //     tabs: []
            // }
        ],
        activityList: [],//活动banner
        newsList: [],//消息列表
        hotList: [],//爆款热推列表
        page: 1,
        iconList: [
            {
                icon: "../../../images/ic_delivery.png",
                color: 'red',
                badge: 120,
                name: '京东送货',
                sub: '京',
                type: constants.JD_DELIVERY,
                tabs: []
            }, {
                icon: '../../../images/ic_diamond.png',
                color: 'orange',
                badge: 1,
                name: '品牌专区',
                sub: '东',
                type: constants.BRAND_ZONE,
                tabs: []
            }, {
                icon: '尚橙',
                color: 'yellow',
                badge: 0,
                name: '优惠券',
                sub: '优',
                type: constants.COUPON,
                tabs: []
            }, {
                icon: '../../../images/ic_like.png',
                color: 'olive',
                badge: 22,
                name: '发圈必推',
                sub: '惠',
                type: constants.HAIR_RING_PUSH,
                tabs: []
            }, {
                icon: '../../../images/ic_hot.png',
                color: 'cyan',
                badge: 0,
                name: '热销榜单',
                sub: '券',
                type: constants.HOT_LIST,
                tabs: []
            }, {
                icon: '../../../images/ic_skirt.png',
                color: 'blue',
                badge: 0,
                name: '时尚生活',
                sub: '佰',
                type: constants.FASHION_LIFE,
                tabs: [
                    {name: '服饰', type: '1315'},
                    {name: '运动', type: '1318'},
                    {name: '美妆', type: '1316'},
                    {name: '鞋靴', type: '11729'},
                    {name: '箱包', type: '17329'},
                    {name: '珠宝', type: '6144'},
                ]
            }, {
                icon: '../../../images/ic_camera.png',
                color: 'purple',
                badge: 0,
                name: '家电数码',
                sub: '创',
                type: constants.DIGITAL_HOME,
                tabs: [
                    {name: '数码电子', type: '652'},
                    {name: '家用电器', type: '737'},
                ]
            }, {
                icon: '../../../images/ic_fresh.png',
                color: 'mauve',
                badge: 0,
                name: '食品生鲜',
                sub: 'C',
                type: constants.FRESH_FOOD,
                tabs: [
                    {name: '水果生鲜', type: '12218'},
                    {name: '食品饮料', type: '1320'},
                ]
            }, {
                icon: '../../../images/ic_toothpaste.png',
                color: 'purple',
                badge: 0,
                name: '家具日用',
                sub: '科',
                type: constants.FURNITYRE,
                tabs: [
                    {name: '家居', type: '1620'},
                    {name: '清洁', type: '15901'},
                ]
            }, {
                icon: '../../../images/ic_makeups.png',
                color: 'mauve',
                badge: 0,
                name: '美妆个护',
                sub: '技',
                type: constants.BEAUTY_CARE,
                tabs: [
                    {name: '个人护理', type: '16750'},
                    {name: '美妆护肤', type: '1316'},
                ]
            }],
    },

    lifetimes: {
        attached: function () {


        },
    },

    /**
     * 组件的方法列表
     */
    methods: {
        pageEvent(event) {
            this.setData({page: event.detail.page})
            this.triggerEvent('onRefresh1', {})
        },
        onMore() {
            this.selectComponent("#home-product-list").getList('more', this.data.page, this.data.homeTabs[this.data.TabCur]);
        },
        onRefresh() {
            let that = this

            // 在组件实例进入页面节点树时执行
            this.selectComponent("#home-product-list").getList('refresh', 1, '居家必备');
            Promise.all([
                new Promise((resolve, reject) => {

                    newsList().then((res) => {
                        that.setData({newsList: res.data.data})
                        resolve(res)
                    }).catch(err => {
                        reject(err)
                    })

                    goodsTop({pageIndex: 1, pageSize: 12}).then((res) => {
                        res.data.data.records.forEach((item) => {
                            if (item.coupons.length > 0 && item.priceInfo.lowestCouponPrice)
                                item.priceInfo.lowestCouponPrice = item.priceInfo.lowestCouponPrice.toFixed(2)

                            item.comments = util.formatNUmber(item.comments)
                            item.priceInfo.price = item.priceInfo.price.toFixed(2)
                        })
                        let arrData = that.filterWithColumn(res.data.data.records, 3);
                        that.setData({hotList: arrData})
                        resolve(res)
                    }).catch(err => {
                        reject(err)
                    })

                    homeBanner().then(res => {
                        that.setData({activityList: res.data.data})
                        resolve(res)
                    }).catch(err => {
                        reject(err)
                    })
                }),
            ]).then((res) => {
                that.triggerEvent('onRefresh1', {})
            }).catch((err) => {
                that.triggerEvent('onRefresh1', {})
            })
        },
        tabSelect(e) {
            this.setData({
                TabCur: e.currentTarget.dataset.id,
                scrollLeft: (e.currentTarget.dataset.id - 1) * 60
            })
            this.selectComponent("#home-product-list").getList('refresh', 1, e.currentTarget.dataset.item);
        },
        getList(type, currentPage, productType) {
            let that = this

            let data = {
                cid1: productType,
                pageIndex: currentPage,
                pageSize: 20
            }
            that.setData({isLoad: type == "refresh"})

            queryProductType(data).then((res) => {
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
                    if (item.coupons.length > 0 && item.priceInfo.lowestCouponPrice)
                        item.priceInfo.lowestCouponPrice = item.priceInfo.lowestCouponPrice.toFixed(2)

                    item.comments = util.formatNUmber(item.comments)
                    item.priceInfo.price = item.priceInfo.price.toFixed(2)
                })

                that.setData({
                    isLoad: false,
                    page: currentPage + 1,
                    productList: lastData
                })
            }).catch((err) => {
                that.setData({isLoad: false})
                wx.showModal({
                    title: '提示',
                    showCancel: false,
                    content: err.data.message ? err.data.message : '获取信息失败',
                    success(res) {
                    }
                })
            })
        },
        /**
         * 爆款推荐分组
         */
        filterWithColumn(source, column) {
            var count = source.length % column == 0 ? Math.floor(source.length / column) : Math.floor((source.length / column)) + 1;
            var resultmodel = [];
            for (let i = 0; i < count; i++) {
                let middle = {
                    page: i + 1,
                    griddata: []
                };
                for (let j = 0; j < column; j++) {
                    if (i * column + j < source.length) {
                        let target = source[i * column + j];
                        middle.griddata.push(target);
                    }
                }
                resultmodel.push(middle);
            }
            return resultmodel;
        },
        onHotDetails(event) {
            util.setStorage(constants.PRPDUCT_ITEM, event.currentTarget.dataset.item)
            wx.navigateTo({
                url: '/page_package/details/index'
            })
        },
        onProductDetails(event) {
            let item = event.currentTarget.dataset.item
            switch (Number(event.currentTarget.dataset.index)) {
                case 0:
                case 1:
                case 2:
                case 3:
                    wx.navigateTo({
                        url: '/page_package/menu-product/menu-product?type=' + item.type + "&item=" + JSON.stringify(item)
                    })
                    break
                case 4:
                    // wx.navigateTo({
                    //     url: '/page_package/product-type/product-type'
                    // })
                    wx.switchTab({
                        url: '/pages/sort/sort'
                    })
                    break
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    wx.navigateTo({
                        url: '/page_package/menu-product/menu-product?type=' + item.type + "&item=" + JSON.stringify(item)
                    })
                    break
            }
        },
        onCourse(event) {
            let item = event.currentTarget.dataset.item
            switch (event.currentTarget.dataset.id) {
                case 0:
                case 1:
                    wx.navigateTo({
                        url: '/page_package/menu-product/menu-product?type=' + item.type + "&item=" + JSON.stringify(item)
                    })
                    break
                // case 2:
                // case 3:
                //     wx.navigateTo({url: item.navUrl})
                //     break
                case 2:
                    this.triggerEvent('NavChange', {})
                    break
            }
        },
        clickBanner(event) {
            let item = event.currentTarget.dataset.item
            wx.navigateTo({url: item.location + "&title=" + item.title})
            // wx.navigateTo({url: '/page_package/news-details/news-details'})
            // wx.showToast({
            //     title: "活动还未开始",
            //     icon: "none",
            //     duration: 3000
            // })
        },
    }
})