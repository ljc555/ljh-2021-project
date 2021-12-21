//app.js
const i18n = require('./utils/language/i18n');
const en = require('./utils/language/en');
const zh_cn = require('./utils/language/zhCN');
const {options} = require('./utils/network/services/service.js')
const constants = require('./utils/constants')
const util = require('./utils/util')

App({
    onLaunch: function () {

        //国际化
        i18n.registerLocale({en, zh_cn});

        //获取系统信息
        var sysinfo = wx.getSystemInfoSync();
        var language = sysinfo.language;
        if (language.toLowerCase() == "zh_cn" || language.toLowerCase() == "zh") language = "zh_cn";
        this.globalData.sys_info = sysinfo;
        this.globalData.sys_platform = sysinfo.platform;
        this.globalData.sys_language = language;
        this.i18n.setLocale(language);

        // 获取用户信息
        wx.getSetting({
            success: res => {
                if (res.authSetting['scope.userInfo']) {
                    // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
                    wx.getUserInfo({
                        success: res => {
                            // 可以将 res 发送给后台解码出 unionId
                            this.globalData.userInfo = res.userInfo
                            // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
                            // 所以此处加入 callback 以防止这种情况
                            if (this.userInfoReadyCallback) {
                                this.userInfoReadyCallback(res)
                            }
                        }
                    })
                }
            }
        })


        wx.getSystemInfo({
            success: e => {
                this.globalData.StatusBar = e.statusBarHeight;
                let custom = wx.getMenuButtonBoundingClientRect();
                this.globalData.Custom = custom;
                this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
                this.globalData.windowWidth = e.windowWidth
                this.globalData.windowHeight = e.windowHeight
            }
        })
        this.globalData.loginMode = util.getStorage(constants.NEW_USER_PLOY)
        //获取系统配置信息
        options().then(res => {
            let loginModel = false
            res.data.data.forEach(it => {
                if (it.key == 'app_force_login') loginModel = it.value
            })
            util.setStorage(constants.NEW_USER_PLOY, loginModel)
            util.setStorage(constants.DICTIONARY, res.data.data)
            this.globalData.loginMode = util.getStorage(constants.NEW_USER_PLOY)
        })

    },

    i18n,
    globalData: {
        userInfo: null,
        StatusBar: null,
        Custom: null,
        CustomBar: null,
        sys_language: "zh_cn",
        sys_platform: "ios",
        sys_info: null,
        windowHeight: 0,//窗口高度
        windowWidth: 0,//窗口宽度
        loginStatus: false,//是否已经登录
        loginMode: false,//新用户是否强制注册
        shareUrl:''
    }
})