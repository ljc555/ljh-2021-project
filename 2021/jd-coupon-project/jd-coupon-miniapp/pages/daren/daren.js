// pages/daren/daren.js
const app = getApp()
const data = require("./test-data.js")
let pageStart = 0

Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  /**
   * 组件的初始数据
   */
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    TabCur: 0,
    scrollLeft: 0,
    windowWidth: app.globalData.windowWidth,
    windowHeight: app.globalData.windowHeight,

    tabs: ["推荐", "关注"],
    videoList: data.videos,

    requesting: false,
    end: false,
    emptyShow: false,
    page: pageStart,
    listData: [],
    hasTop: false,
    enableBackToTop: false,
    refreshSize: 90,
    bottomSize: 100,
    color: "#3F82FD",
    empty: false
  },

  lifetimes: {
    attached: function () {
      // 在组件实例进入页面节点树时执行
      this.getList('refresh', pageStart);
    },
  },

  /**
   * 组件的方法列表
   */
  methods: {
    tabSelect(e) {
      this.setData({
        TabCur: e.currentTarget.dataset.id,
        scrollLeft: (e.currentTarget.dataset.id - 1) * 60
      })
    },
    togo(e) {
      let index = e.currentTarget.dataset.index
      wx.navigateTo({
        url: '/page_package/video-play/video-play?index=' + index 
        // url:'/page_package/about/about'
      })
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
    }
  },
  pageLifetimes: {
    show() {
      if (typeof this.getTabBar === 'function' &&
          this.getTabBar()) {
        this.getTabBar().setData({
          selected: 1
        })
      }
    }
  }
})