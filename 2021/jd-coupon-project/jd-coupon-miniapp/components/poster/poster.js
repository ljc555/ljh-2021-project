// components/poster/poster.js
const app = getApp()
const {appCode} = require("../../utils/network/services/productImpl")

Component({
    /**
     * 组件的属性列表
     */
    properties: {
        listData: {
            type: Array,
            value: [
                {content: '微信功能升级的利好\n中国巨大的人口市场\n惠享社交电商的模式与北京加持\n2020必将是喷井爆发的一年！这是天时！地利！人和！\n加入惠享让您站在时代的风口上！'},
                {content: '微信功能升级的利好\n中国巨大的人口市场\n惠享社交电商的模式与北京加持\n2020必将是喷井爆发的一年！这是天时！地利！人和！\n加入惠享让您站在时代的风口上！'},
            ]
        }
    },

    /**
     * 组件的初始数据
     */
    data: {
        userInfo: app.globalData.userInfo,
        imgWidth: 0,
        imgHeight: 0,
        invitationDialog: false,
        mDialog: false,
        synthesisImg: '',
        isShow: false,
        appCodes:'',
        codeWidth:0,
        codeHeight:0,
        selectIndex:-1
    },

    lifetimes: {
        attached: function () {
            this.setData({
                userInfo: app.globalData.userInfo,
                imgWidth: app.globalData.windowWidth * 0.7,
                imgHeight: app.globalData.windowHeight * 0.7,
                codeWidth: app.globalData.windowWidth * 0.5,
                codeHeight: app.globalData.windowWidth * 0.5,
            })
        },
    },

    /**
     * 组件的方法列表
     */
    methods: {
        onClose() {
            this.setData({
                invitationDialog: false,
                mDialog:false
            })
        },
        onPoster(event) {
            this.imgLoader(event.currentTarget.dataset.item,event.currentTarget.dataset.index)
        },
        saveImg() {
            wx.saveImageToPhotosAlbum({
                filePath: this.data.synthesisImg,
                success: (res) => {
                    this.setData({invitationDialog: false})
                    wx.hideLoading()
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: '图片保存成功，进入系统相册分享吧~',
                        success(res) {
                            if (res.confirm) {

                            }
                        }
                    })
                },
                fail: (err) => {
                    this.setData({invitationDialog: false})
                    wx.hideLoading()
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: '请到小程序设置中打开相册权限',
                        success(res) {
                            if (res.confirm) {
                            }
                        }
                    })
                }
            })
        },
        saveImg1(){
            wx.saveImageToPhotosAlbum({
                filePath: this.data.appCodes,
                success: (res) => {
                    this.setData({mDialog: false})
                    wx.hideLoading()
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: '图片保存成功，进入系统相册分享吧~',
                        success(res) {
                            if (res.confirm) {

                            }
                        }
                    })
                },
                fail: (err) => {
                    this.setData({mDialog: false})
                    wx.hideLoading()
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: '请到小程序设置中打开相册权限',
                        success(res) {
                            if (res.confirm) {
                            }
                        }
                    })
                }
            })
        },
        imgLoader(item,index) {
            if (this.data.synthesisImg && this.data.selectIndex == index) {
                this.setData({invitationDialog: true})
            } else {
                let that = this
                this.setData({
                    // invitationDialog: true,
                    selectIndex:index
                })
                wx.showLoading({title: '合成中...',})
                Promise.all([
                    new Promise((resolve, reject) => {
                        wx.getImageInfo({
                            src: item.imageUrl,
                            success(res) {
                                resolve(res)
                            },
                            fail(err) {
                                err.prompt = '合成分享图失败'
                                reject(err)
                            }
                        })
                    }),
                    new Promise((resolve, reject) => {
                        wx.getImageInfo({
                            src: that.data.userInfo.avatarUrl,
                            success(res) {
                                resolve(res)
                            },
                            fail(err) {
                                err.prompt = '合成头像失败'
                                reject(err)
                            }
                        })
                    }),
                    new Promise((resolve, reject) => {
                        let data = {
                            page: "pages/index/index",
                            scene: {id:that.data.userInfo.userId },
                            size: 72
                        }
                        appCode(data)
                            .then(res => {
                                wx.showLoading({title: '合成中...',})
                                let filePath = `${wx.env.USER_DATA_PATH}/${that.data.userInfo.userId}`;
                                wx.getFileSystemManager().writeFile({
                                    filePath: filePath,
                                    data: wx.base64ToArrayBuffer(res.data.data.content),
                                    encoding: 'binary',
                                    success: () => {
                                        resolve(filePath);
                                    },
                                    fail: err => {
                                        err.prompt = '合成推广码失败'
                                        reject(err);
                                    },
                                });
                            }).catch((err) => {
                            err.prompt = '获取推广码失败'
                            reject(err);
                        })
                    })
                ]).then(res => {
                    const ctx = wx.createCanvasContext('myCanvas', that);
                    ctx.fillStyle = "#FFFFFF";
                    ctx.fillRect(0, 0, that.data.imgWidth, that.data.imgHeight);
                    that.setData({ctx: ctx})
                    const productImg = res[0].path //f分享图图

                    const avatarUrl = res[1].path //用户头像
                    const userName = that.data.userInfo.nickName //用户昵称

                    //绘制商品图
                    ctx.drawImage(productImg, 0, 0, that.data.imgWidth, that.data.imgHeight * 0.75);
                    ctx.save();

                    let imgH = that.data.imgHeight * 0.75

                    //商品标题第一行
                    ctx.setTextAlign('left')
                    ctx.setFillStyle('#000000')
                    ctx.setFontSize(16)
                    ctx.fillText("尚橙优选·分享好物", 10, imgH + 24)
                    ctx.stroke()

                    //用户昵称
                    ctx.setTextAlign('left')
                    ctx.setFillStyle('#333333')
                    ctx.setFontSize(12)
                    ctx.fillText(userName, 68, imgH + 54)
                    ctx.stroke()

                    //推广语
                    ctx.setTextAlign('left')
                    ctx.setFillStyle('#444444')
                    ctx.setFontSize(11)
                    ctx.fillText('邀您一起加入尚橙', 68, imgH + 74)
                    ctx.stroke()
                    //推广语
                    ctx.setTextAlign('left')
                    ctx.setFillStyle('#666666')
                    ctx.setFontSize(9)
                    ctx.fillText('分享京东优惠好货', 68, imgH + 89)
                    ctx.stroke()
                    //
                    // //
                    ctx.setTextAlign('right')
                    ctx.setFillStyle('#444444')
                    ctx.setFontSize(9)
                    ctx.fillText('长按识别', that.data.imgWidth - 24, that.data.imgHeight - 10)
                    ctx.stroke()
                    //
                    //用户头像
                    ctx.arc(34, imgH + 64, 24, 0, Math.PI * 2, false);
                    ctx.clip();
                    ctx.drawImage(avatarUrl, 10, imgH + 40, 48, 48);
                    ctx.restore();

                    // 推广二维码
                    ctx.drawImage(res[2], that.data.imgWidth - 74, that.data.imgHeight - 88, 64, 64);
                    ctx.restore();

                    ctx.draw()
                    setTimeout(() => {
                        that.heIMg(that,1)
                    }, 50)
                }).catch(err => {
                    console.log(">>>", err)
                    wx.hideLoading()
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: "合成图片失败",
                        success(res) {
                        }
                    })
                })
            }

        },
        heIMg(that,type) {
            if(type == 1){
                wx.canvasToTempFilePath({
                    x: 0,
                    y: 0,
                    width: that.data.imgWidth,
                    height: that.data.imgHeight,
                    canvasId: 'myCanvas',
                    success: function (res) {
                        wx.hideLoading()
                        that.setData({
                            synthesisImg: res.tempFilePath,
                            isShow:false,
                            invitationDialog: true
                        })
                    },
                    fail: function (err) {
                        wx.hideLoading()
                    }
                }, that)
            }else {
                wx.canvasToTempFilePath({
                    x: 0,
                    y: 0,
                    width: that.data.codeWidth,
                    height: that.data.codeWidth,
                    canvasId: 'myCanvas1',
                    success: function (res) {
                        wx.hideLoading()
                        that.setData({
                            appCodes: res.tempFilePath,
                            mDialog: true
                        })
                    },
                    fail: function (err) {
                        wx.hideLoading()
                    }
                }, that)
            }

        },
        onCopy(event){
           const item = event.currentTarget.dataset.item
            wx.setClipboardData({
                data: item.content,
                success: function (res) {
                    wx.getClipboardData({
                        success: function (res) {
                            wx.showToast({
                                title: '复制成功'
                            })
                        }
                    })
                }
            })
        },
        appsCode(){
            if (this.data.appCodes) {
                this.setData({mDialog: true})
            } else {
                let that = this
                wx.showLoading({title: '合成中...',})
                Promise.all([
                    new Promise((resolve, reject) => {
                        let data = {
                            page: "pages/index/index",
                            scene: {id:that.data.userInfo.userId },
                            size: that.data.codeWidth
                        }
                        appCode(data)
                            .then(res => {
                                let filePath = `${wx.env.USER_DATA_PATH}/${that.data.userInfo.userId}` + 1;
                                wx.getFileSystemManager().writeFile({
                                    filePath: filePath,
                                    data: wx.base64ToArrayBuffer(res.data.data.content),
                                    encoding: 'binary',
                                    success: () => {
                                        resolve(filePath);
                                    },
                                    fail: err => {
                                        err.prompt = '合成推广码失败'
                                        reject(err);
                                    },
                                });
                            }).catch((err) => {
                            err.prompt = '获取推广码失败'
                            reject(err);
                        })
                    })
                ]).then(res => {
                    const ctx = wx.createCanvasContext('myCanvas1', that);
                    ctx.fillStyle = "#FFFFFF";
                    ctx.fillRect(0, 0, that.data.codeWidth, that.data.codeHeight);
                    const productImg = res[0] //f分享图图

                    console.log("推广码",res,productImg)
                    //绘制商品图
                    ctx.drawImage(productImg, 0, 0, that.data.codeWidth, that.data.codeHeight);
                    ctx.save();

                    ctx.draw()
                    setTimeout(() => {
                        that.heIMg(that,2)
                    }, 50)
                }).catch(err => {
                    console.log(">>>", err)
                    wx.hideLoading()
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: "合成图片失败",
                        success(res) {
                        }
                    })
                })
            }
        }
    }
})
