// page_package/search/search.js
const app = getApp()
const constants = require("../../utils/constants.js")
const util = require("../../utils/util.js")

Page({

    /**
     *
     */
    data: {
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        Custom: app.globalData.Custom,
        windowHeight: app.globalData.windowHeight,
        windowWidth: app.globalData.windowWidth,
        searchHistory: [],
        searchContent: "",
        scrolltop: '',
        conditionList: [
            {name: '综合排序'},
            {name: '高返利比'},
            {name: '销量'},
            {name: '价格'},
        ],
        numList: [{
            name: '打开京东APP'
        }, {
            name: '长按复制商品标题'
        }, {
            name: '打开惠享小程序'
        }, {
            name: '点击搜索'
        },],

        isCoupon: false,
        isFilter: false,
        isCheck: false,
        curID: 0,
        priceSort: false,
        salesSort: false,
        page:1,
        sortName:''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        if(options.content){
            this.setData({searchContent:options.content})
            let historyList = util.getStorage(constants.SEARCH_HISTORY)
            historyList = historyList ? historyList : []
            let isExis = historyList.filter((elem) => elem == this.data.searchContent)

            if (isExis.length == 0) {
                historyList.push(this.data.searchContent)
                util.setStorage(constants.SEARCH_HISTORY, historyList)
            }
            this.setData({
                isCoupon: true,
                isCheck: false,
                isFilter: true,
                priceSort: false,
                salesSort: false,
                curID: 0,
                page:1,
                sortName:''
            })
            this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, '', '', '');
        }
        let historyList = util.getStorage(constants.SEARCH_HISTORY)
        // 获取本地搜索历史
        this.setData({
            searchHistory: historyList
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

    /**清除记录 */
    onClear() {
        util.removeStorage(constants.SEARCH_HISTORY)
        this.setData({
            searchHistory: util.getStorage(constants.SEARCH_HISTORY)
        })
    },
    historyItem(event) {
        this.setData({
            searchContent: event.currentTarget.dataset.item
        })
        // this.selectComponent("#search-product-list").getList('refresh', 1,
        //     event.currentTarget.dataset.item, 'search');
    },
    /**
     * 搜索框输入监听
     */
    bindinput(event) {
        this.setData({
            searchContent: event.detail.value
        })
    },
    more(){
        this.selectComponent("#search-product-list").getSearchList('more', ++this.data.page, this.data.searchContent, '', this.data.priceSort ? 'desc' : 'asc', this.data.sortName);
    },
    //筛选规则
    onSort(event) {
        this.setData({scrolltop: 0})
        switch (Number(event.currentTarget.dataset.index)) {
            case 0:
                this.setData({
                    isCoupon: true,
                    isCheck: false,
                    isFilter: true,
                    curID: 0,
                    salesSort: false,
                    priceSort: false,
                    page:1,
                    sortName:''
                })
                this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, '', '', '');
                break
            case 1:
                this.setData({
                    isCoupon: true,
                    isCheck: false,
                    isFilter: true,
                    curID: 1,
                    salesSort: false,
                    priceSort: false,
                    page:1,
                    sortName:'commission'
                })
                this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, '', 'desc', 'commission');
                break
            case 2:
                this.setData({
                    isCoupon: true,
                    isCheck: false,
                    isFilter: true,
                    curID: 2,
                    salesSort: !this.data.salesSort,
                    priceSort: false,
                    page:1,
                    sortName:'inOrderCount30Days'
                })
                this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, '', this.data.salesSort ? 'desc' : 'asc', 'inOrderCount30Days');
                break
            case 3:
                this.setData({
                    isCoupon: true,
                    isCheck: false,
                    isFilter: true,
                    curID: 3,
                    salesSort: false,
                    priceSort: !this.data.priceSort,
                    page:1,
                    sortName:'price'
                })
                this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, '', this.data.priceSort ? 'desc' : 'asc', 'price');
                break
        }
    },
    onCheck(event) {
        this.setData({scrolltop: 0})
        if (event.detail.value) {
            this.setData({
                isCoupon: true,
                isCheck: true,
                isFilter: false,
                priceSort: false,
                salesSort: false,
                curID: 0,
                page:1,
                sortName:''
            })
            this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, 1, '', '');
        } else {
            this.setData({
                isCoupon: true,
                isCheck: false,
                isFilter: true,
                priceSort: false,
                salesSort: false,
                curID: 0,
                page:1,
                sortName:''
            })
            this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, '', '', '');
        }
    },
    onSerach(event) {
        let that = this
        if (this.data.searchContent) {
            let historyList = util.getStorage(constants.SEARCH_HISTORY)
            historyList = historyList ? historyList : []
            let isExis = historyList.filter((elem) => elem == that.data.searchContent)

            if (isExis.length == 0) {
                historyList.push(this.data.searchContent)
                util.setStorage(constants.SEARCH_HISTORY, historyList)
            }
            this.setData({scrolltop: 0})
            switch (Number(event.currentTarget.dataset.index)) {
                case 0:
                    this.setData({
                        isCoupon: true,
                        isCheck: true,
                        isFilter: false,
                        priceSort: false,
                        salesSort: false,
                        curID: 0,
                        page:1,
                        sortName:''
                    })
                    this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, 1, '', '');
                    break
                case 1:
                    this.setData({
                        isCoupon: true,
                        isCheck: false,
                        isFilter: true,
                        priceSort: false,
                        salesSort: false,
                        curID: 0,
                        page:1,
                        sortName:''
                    })
                    this.selectComponent("#search-product-list").getSearchList('refresh', 1, this.data.searchContent, '', '', '');
                    break
            }
        }
    }
})