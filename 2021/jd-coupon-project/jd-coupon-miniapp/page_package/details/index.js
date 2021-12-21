// page_package/details/index.js
const app = getApp()
const {goodsUrl, productsCode, appCode, purchaseUrl} = require("../../utils/network/services/productImpl.js")
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const {onLogin} = require("../../utils/network/services/user.js")
Page({

    /**
     * 页面的初始数据
     */
    data: {
        StatusBar: app.globalData.StatusBar,
        CustomBar: app.globalData.CustomBar,
        Custom: app.globalData.Custom,
        windowWidth: app.globalData.windowWidth,
        windowHeight: app.globalData.windowHeight,
        userInfo: app.globalData.userInfo,
        productDetails: null,
        invitationDialog: false,
        imgWidth: 0,
        imgHeight: 0,
        ctx: null,
        modalName: "",
        shareURl: "",
        shareImgPath: "",
        synthesisImg: '',
        appImg: '',
        userInfo: app.globalData.userInfo,
        shareType: 1
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        this.setData({
            productDetails: util.getStorage(constants.PRPDUCT_ITEM),
            imgWidth: 255,//app.globalData.windowWidth * 0.68
            imgHeight: 407, //app.globalData.windowHeight * 0.61
        })
        let coupons = this.data.productDetails.coupons
        let data = {
            invitationCode: '',
            couponUrl: coupons.length > 0 ? coupons[coupons.length - 1].link : '',
            materialId: this.data.productDetails.materialUrl
        }
        if (!app.globalData.loginStatus) {
            let that = this
            wx.login({
                success: res => {
                    onLogin(res.code)
                        .then(res => {
                            app.globalData.userInfo = res.data.data
                            util.setStorage(constants.AUTH_INFO, res.data.data)
                            that.setData({userInfo: res.data.data})
                            purchaseUrl(data).then(res => {
                                that.setData({shareURl: res.data.data.shortURL})
                            })
                        }).catch((err) => {
                        if (err.data.code = 'USER_NOT_REGISTERED') {
                            // that.setData({token: err.data.data.token})
                            wx.navigateTo({url: '/pages/login/login?token=' + err.data.data.token})
                        }
                    })
                }
            })
        } else {
            purchaseUrl(data).then(res => {
                this.setData({shareURl: res.data.data.shortURL})
            })
        }
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
     * 用户点击右上角
     */
    onShareAppMessage: function (res) {
        this.setData({modalName: ''})
        return {
            title: this.data.productDetails.skuName,
            path: '/pages/product/product?id=' + this.data.productDetails.skuId + '&invitationCode=' + this.data.userInfo.invitationCode,
            imageUrl: this.data.productDetails.images[0],
            success: function (res) {
                // 转发成功
                console.log('转发成功')
            },
            fail: function (res) {
                // 转发失败
                console.log('转发失败')
            }
        }
    },

    goHome(event) {
        wx.reLaunch({
            url: '/pages/index/index',
        })
    },
    onShowModel() {
        this.setData({
            modalName: 'bottomModal'
        })
    },
    onShare(event) {
        let that = this
        let index = event.currentTarget.dataset.index
        that.imgLoader(index)
        that.setData({
            modalName: '',
            shareType: Number(index)
        })
    },
    onClose() {
        let that = this
        this.setData({
            invitationDialog: false,
        })
        this.data.ctx.draw()

        if (this.data.shareImgPath) {
            wx.getFileSystemManager().unlink({
                filePath: this.data.shareImgPath,
                success: res => {
                    that.setData({shareImgPath: ''})
                }
            })
        }
    },


    imgLoader(index) {
        if ((this.data.synthesisImg && index == 1) || (this.data.appImg && index == 0)) {
            this.setData({invitationDialog: true})
        } else {
            let that = this
            wx.showLoading({title: '合成中...',})
            Promise.all([
                new Promise((resolve, reject) => {
                    wx.getImageInfo({
                        src: that.data.productDetails.images[0],
                        success(res) {
                            resolve(res)
                        },
                        fail(err) {
                            err.prompt = '合成商品图失败'
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
                    if (index == 1) {//商品推廣
                        let data = {
                            materialUrl: this.data.productDetails.materialUrl,
                            size: 72
                        }
                        if (this.data.productDetails.coupons.length > 0) {
                            data = {
                                couponUrl: this.data.productDetails.coupons[this.data.productDetails.coupons.length - 1].link,
                                materialUrl: this.data.productDetails.materialUrl,
                                size: 72
                            }
                        }
                        productsCode(data)
                            .then(res => {
                                let filePath = `${wx.env.USER_DATA_PATH}/${that.data.productDetails.skuId}`;
                                wx.getFileSystemManager().writeFile({
                                    filePath: filePath,
                                    data: wx.base64ToArrayBuffer(res.data.data.content),
                                    encoding: 'binary',
                                    success: () => {
                                        resolve(filePath);
                                    },
                                    fail: err => {
                                        err.prompt = '合成商品码失败'
                                        reject(err);
                                    },
                                });
                            }).catch((err) => {
                            err.prompt = '获取商品码失败'
                            reject(err);
                        })
                    } else {
                        let data = {
                            page: "pages/product/product",
                            scene: {id: that.data.productDetails.skuId,},
                            size: 72
                        }
                        appCode(data)
                            .then(res => {
                                let filePath = `${wx.env.USER_DATA_PATH}/${that.data.productDetails.skuId}${that.data.productDetails.inOrderCount30Days}`;
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
                    }
                })

            ]).then(res => {
                let id = index == 1 ? 'myCanvas' : 'myCanvas1'
                const ctx = wx.createCanvasContext(id, that);
                ctx.fillStyle = "#FFFFFF";
                ctx.fillRect(0, 0, that.data.imgWidth, that.data.imgHeight);
                that.setData({ctx: ctx})
                const productImg = res[0].path //商品图
                const title = that.data.productDetails.skuName  //商品名称
                const titleLines = that.skuNameFormat(ctx, title)
                //券后价
                const couponPrice = that.data.productDetails.priceInfo.lowestCouponPrice ? that.data.productDetails.priceInfo.lowestCouponPrice : that.data.productDetails.priceInfo.price
                const comment = util.formatNUmber(that.data.productDetails.comments) //评论条数
                const praise = that.data.productDetails.goodCommentsShare //好评率
                const price = util.formatNUmber(that.data.productDetails.priceInfo.price) //京东价
                //优惠券
                let coupon = 0
                if (that.data.productDetails.coupons.length > 0)
                    coupon = that.data.productDetails.coupons[0].discount ? that.data.productDetails.coupons[0].discount : 0

                const avatarUrl = res[1].path //用户头像
                const userName = that.data.userInfo.nickName //用户昵称
                const code = "DHWKLD"
                const promotionCode = res[2].path //推广码

                //绘制商品图
                ctx.drawImage(productImg, 0, 0, that.data.imgWidth, that.data.imgWidth);
                ctx.save();

                //京东标识矩形
                ctx.rect(10, that.data.imgWidth + 10, 23, 11)
                ctx.setFillStyle('#f43f3b')
                ctx.setStrokeStyle('#f43f3b')
                // ctx.setBorderRadius(5)
                ctx.fill()

                //京东标识文字
                ctx.setTextAlign('left')
                ctx.setFillStyle('#ffffff')
                ctx.setFontSize(9)
                ctx.fillText('京东', 12, that.data.imgWidth + 18)
                ctx.stroke()

                //商品标题第一行
                ctx.setTextAlign('left')
                ctx.setFillStyle('#333333')
                ctx.setFontSize(11)
                ctx.fillText(titleLines[0], 43, that.data.imgWidth + 20)
                ctx.stroke()

                //商品标题第二行
                ctx.setTextAlign('left')
                ctx.setFillStyle('#333333')
                ctx.setFontSize(11)
                ctx.fillText(titleLines.length > 1 ? titleLines[1] : ' ', 10, that.data.imgWidth + 40)
                ctx.stroke()

                //券后价文本
                ctx.setTextAlign('left')
                ctx.setFillStyle('#f43f3b')
                ctx.setFontSize(9)
                ctx.fillText("券后价￥", 10, that.data.imgWidth + 64)
                ctx.stroke()

                //券后价
                ctx.setTextAlign('left')
                ctx.setFillStyle('#f43f3b')
                ctx.setFontSize(15)
                ctx.fillText(util.isNumber(couponPrice) ? Number(couponPrice).toFixed(2) : couponPrice, 45, that.data.imgWidth + 64)
                ctx.stroke()

                //评论 好评
                ctx.setTextAlign('right')
                ctx.setFillStyle('#666666')
                ctx.setFontSize(9)
                ctx.fillText(comment + "条评论 好评率" + praise + "%", that.data.imgWidth - 10, that.data.imgWidth + 64)
                ctx.stroke()

                //京东价
                ctx.setTextAlign('left')
                ctx.setFillStyle('#666666')
                ctx.setFontSize(9)
                ctx.fillText(util.isNumber(price) ? "京东价￥" + Number(price).toFixed(2) : "京东价￥" + price, 10, that.data.imgWidth + 86)
                ctx.stroke()

                //优惠券
                ctx.setTextAlign('left')
                ctx.setFillStyle('#f43f3b')
                ctx.setFontSize(12)
                ctx.fillText("优惠券￥" + coupon, 90, that.data.imgWidth + 86)
                ctx.stroke()

                //用户昵称
                ctx.setTextAlign('left')
                ctx.setFillStyle('#333333')
                ctx.setFontSize(11)
                ctx.fillText('好友' + userName, 64, that.data.imgWidth + 116)
                ctx.stroke()

                //用户昵称
                ctx.setTextAlign('left')
                ctx.setFillStyle('#666666')
                ctx.setFontSize(8)
                ctx.fillText('推荐您享受京东购物优惠', 64, that.data.imgWidth + 134)
                ctx.stroke()

                //
                ctx.setTextAlign('right')
                ctx.setFillStyle('#444444')
                ctx.setFontSize(9)
                ctx.fillText('长按识别', that.data.imgWidth - 28, that.data.imgHeight - 5)
                ctx.stroke()

                //用户头像
                ctx.arc(34, that.data.imgWidth + 120, 24, 0, Math.PI * 2, false);
                ctx.clip();
                ctx.drawImage(avatarUrl, 10, that.data.imgWidth + 96, 48, 48);
                ctx.restore();

                //推广二维码
                ctx.drawImage(res[2], that.data.imgWidth - 74, that.data.imgHeight - 79, 64, 64);
                ctx.restore();

                ctx.draw()

                setTimeout(() => {
                    that.heIMg(that, index, id)
                    that.setData({
                        shareImgPath: res[2]
                    })
                }, 150)
            }).catch(err => {
                console.log(JSON.stringify(err))
                wx.hideLoading()
                wx.showModal({
                    title: '提示',
                    showCancel: false,
                    content: err.prompt?err.prompt:"合成图片失败",
                    success(res) {
                    }
                })
            })
        }
    },

    heIMg(that, index, id) {
        wx.canvasToTempFilePath({
            x: 0,
            y: 0,
            width: that.data.imgWidth,
            height: that.data.imgHeight,
            canvasId: id,
            success: function (res) {
                wx.hideLoading()
                console.log("图片ID=", id)
                if (index == 1)
                    that.setData({
                        synthesisImg: res.tempFilePath,
                        invitationDialog: true
                    })
                else that.setData({
                    appImg: res.tempFilePath,
                    invitationDialog: true
                })
            },
            fail: function (err) {
                wx.hideLoading()
            }
        }, that)
    },

    skuNameFormat(context, title) {
        let ostrtxt = ''
        let olinenum = 0
        let oarrtxt = []
        context.setFontSize(11)
        for (let i = 0; i < title.length; i++) {
            ostrtxt = ostrtxt + title[i]
            let ometricsw = context.measureText(ostrtxt) //计算字符宽度
            if (oarrtxt.length == 0) {
                if (ometricsw.width > this.data.imgWidth - 60) {//设置每行的宽度
                    olinenum++
                    oarrtxt = oarrtxt.concat([ostrtxt])
                    ostrtxt = '';
                }
            } else {
                if (ometricsw.width > this.data.imgWidth - 25) {//设置每行的宽度
                    olinenum++
                    oarrtxt = oarrtxt.concat([ostrtxt])
                    ostrtxt = '';
                }
            }
        }
        oarrtxt = oarrtxt.concat([ostrtxt])
        return oarrtxt
    },
    imgPreview(event) {
        const index = event.currentTarget.dataset.index
        wx.previewImage({
            current: this.data.productDetails.images[index],
            urls: this.data.productDetails.images
        })
    },
    previewImg(event) {
        wx.previewImage({
            urls: [this.data.shareType == 1 ? this.data.synthesisImg : this.data.appImg],
        })
    },
    saveImg: function (event) {
        wx.showLoading({title: '保存中...',})
        wx.saveImageToPhotosAlbum({
            filePath: this.data.shareType == 1 ? this.data.synthesisImg : this.data.appImg,//canvasToTempFilePath返回的tempFilePath
            success: (res) => {
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
    goToJD() {
        if (this.data.shareURl) {
            let materialUrl = encodeURIComponent(this.data.shareURl);
            //pages/item/detail/detail?sku=xxx
            wx.navigateToMiniProgram({
                appId: 'wx91d27dbf599dff74',
                path: 'pages/union/proxy/proxy?spreadUrl=' + materialUrl,
                envVersion: 'release',
                success(res) {
                    // 打开成功
                }
            });
        } else {
            let coupons = this.data.productDetails.coupons
            let data = {
                chainType: 2,
                couponUrl: coupons[coupons.length - 1].link,
                materialId: this.data.productDetails.materialUrl
            }
            if (coupons.length > 0) {
                data = {
                    chainType: 2,
                    couponUrl: coupons[coupons.length - 1].link,
                    materialId: this.data.productDetails.materialUrl
                }
            }
            goodsUrl(data).then(res => {
                this.setData({shareURl: res.data.data.shortURL})
                let materialUrl = encodeURIComponent(this.data.shareURl);
                wx.navigateToMiniProgram({
                    appId: 'wx91d27dbf599dff74',
                    path: 'pages/union/proxy/proxy?spreadUrl=' + materialUrl,
                    envVersion: 'release',
                    success(res) {
                        // 打开成功
                    }
                });
            })
        }
    },

    onCloseModel() {
        this.setData({
            modalName: ''
        })
    },
    copySkuID() {
        wx.setClipboardData({
            data: (this.data.productDetails.skuId).toString(),
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
    copySkuName() {
        wx.setClipboardData({
            data: this.data.productDetails.skuName,
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
    onDetails() {
        wx.navigateToMiniProgram({
            appId: 'wx91d27dbf599dff74',
            path: 'pages/item/detail/detail?sku=' + this.data.productDetails.skuId,
            envVersion: 'release',
            success(res) {
                // 打开成功
            }
        });
    },
})