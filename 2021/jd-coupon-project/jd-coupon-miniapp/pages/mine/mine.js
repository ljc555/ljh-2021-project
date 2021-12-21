// pages/mine/mine.js
const app = getApp()
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const {onLogin,userInfo} = require("../../utils/network/services/user.js")
Component({
  /**
   * 组件的属性列表
   */
  properties: {

  },

  lifetimes: {
    attached: function attached() {
      this.setData({
        userInfo: app.globalData.userInfo
      })
      if(this.data.userInfo){
        this.data.userInfo.phone = util.phoneFor(this.data.userInfo.phone)
        this.setData({
          userInfo: this.data.userInfo
        })
      }
    }
  },

  /**
   * 组件的初始数据
   */
  data: {
    userInfo: {
      avatarUrl: "",
      city: "",
      country: "",
      gender: 1,
      language: "",
      nickName: "",
      province: ""
    },
    mineInfo:null,
    menuList: [
        {
      icon: 'addressbook',
      title: "我的团队",
      navUrl: '/page_package/contacts/contacts'
    },
      {
      icon: 'edit',
      title: "意见反馈",
      navUrl: '/page_package/feedback/feedback'
    },
      {
      icon: 'service',
      title: "联系我们",
      navUrl: ''
    },
      // {
    //   icon: 'circlefill',
    //   title: "用户规范",
    //   navUrl: ''
    // },
    //   {
    //   icon: 'read',
    //   title: "关于我们",
    //   navUrl: '/page_package/about/about'
    // }
    ]
  },

  /**
   * 组件的方法列表
   */
  methods: {
    onShare(){
      util.setStorage(constants.RAIDERS,4)
      wx.switchTab({
        url: '/pages/strategy/strategy'
      })
      // wx.navigateTo({
      //   url:  '/page_package/poster/poster'
      // })
    },
    onClick(event){
      wx.navigateTo({
        url:  event.currentTarget.dataset.item.navUrl
      })
    }
  },
  pageLifetimes: {
    show() {
      if (typeof this.getTabBar === 'function' &&
          this.getTabBar()) {
        this.getTabBar().setData({
          selected: 4
        })
      }

      let that = this
      if (!app.globalData.loginStatus) {
        wx.login({
          success: res => {
            onLogin(res.code)
                .then(res => {
                  app.globalData.userInfo = res.data.data
                  util.setStorage(constants.AUTH_INFO, res.data.data)
                  that.setData({userInfo: res.data.data})
                }).catch((err) => {
              if (err.data.code = 'USER_NOT_REGISTERED') {
                // that.setData({token: err.data.data.token})
                wx.navigateTo({url: '/pages/login/login?token=' + err.data.data.token})
              }
            })
          }
        })
      }

      if (!app.globalData.loginStatus) {
        wx.login({
          success: res => {
            onLogin(res.code)
                .then(res => {
                  app.globalData.userInfo = res.data.data
                  util.setStorage(constants.AUTH_INFO, res.data.data)
                  that.setData({userInfo: res.data.data})
                  userInfo().then(res => {
                    res.data.data.createTime = util.formatTime(new Date(res.data.data.createTime),'/')
                    that.setData({
                      mineInfo: res.data.data
                    })
                  })
                }).catch((err) => {
              if (err.data.code = 'USER_NOT_REGISTERED') {
                // that.setData({token: err.data.data.token})
                wx.reLaunch({url: '/pages/login/login?token=' + err.data.data.token})
              }
            })
          }
        })
      } else {
        userInfo().then(res => {
          res.data.data.createTime = util.formatTime(new Date(res.data.data.createTime),'/')
          that.setData({
            mineInfo: res.data.data
          })
        })
      }

    }
  }
})