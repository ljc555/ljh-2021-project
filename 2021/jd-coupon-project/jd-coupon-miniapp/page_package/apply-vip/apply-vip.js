// page_package/apply-vip/apply-vip.js
const app = getApp()
const {superMembers, superMembersUp} = require('../../utils/network/services/user.js')
const {uploadFile} = require('../../utils/network/services/service.js')
const constants = require('../../utils/constants')
const util = require('../../utils/util')

Page({

    /**
     * 页面的初始数据
     */
    data: {
        members: null,
        imgList: [],
        invitationDialog:false
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        superMembers().then(res => {
            this.setData({members: res.data.data})
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
    DelImg(e) {
        this.data.imgList.splice(e.currentTarget.dataset.index, 1);
        this.setData({
            imgList: this.data.imgList
        })
    },
    ChooseImage() {
        wx.chooseImage({
            count: 2, //默认9
            sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album'], //从相册选择
            success: (res) => {
                if (this.data.imgList.length != 0) {
                    this.setData({
                        imgList: this.data.imgList.concat(res.tempFilePaths)
                    })
                } else {
                    this.setData({
                        imgList: res.tempFilePaths
                    })
                }
            }
        });
    },
    onCopy(event) {
        console.log(event)
        wx.setClipboardData({
            data: event.currentTarget.dataset.code,
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
    onExample() {
        wx.navigateTo({
            url: '/page_package/apply-vip/example/example?example=' + JSON.stringify(this.data.members.examples)
        })
    },
    previewImg(event) {
        const index = event.currentTarget.dataset.index
        wx.previewImage({
            current: this.data.imgList[index],
            urls: this.data.imgList,
        })
    },
    onSubmit() {
        let that = this
        if (this.data.imgList.length != 2) {
            wx.showModal({
                title: '提示',
                showCancel: false,
                content: '最少需要提交两张图片',
                success(res) {
                }
            })
            return
        }
        Promise.all([
            new Promise((resolve, reject) => {
                uploadFile({
                    path: that.data.imgList[0],
                    type: 1
                }).then(res1 => {
                    resolve(res1)
                }).catch(err1 => {
                    reject(err1)
                })
            }),
            new Promise((resolve, reject) => {
                uploadFile({
                    path: that.data.imgList[1],
                    type: 1
                }).then(res1 => {
                    resolve(res1)
                }).catch(err1 => {
                    reject(err1)
                })
            })

            // that.data.imgList.forEach(item => {
            //
            // })
        ]).then(res2 => {
            let imgArr = []
            for (let i = 0; i < that.data.imgList.length; i++) imgArr.push(res2[i])
            superMembersUp({imageUrls: imgArr})
                .then((res) => {
                    this.setData({invitationDialog:true})
                }).catch(err => {
                wx.showModal({
                    title: '提示',
                    showCancel: false,
                    content: err.data.message ? err.data.message : '提交失败',
                    success(res) {
                    }
                })
            })
        }).catch(err2 => {
            wx.showModal({
                title: '提示',
                showCancel: false,
                content: err2.data.message ? err2.data.message : '提交失败',
                success(res) {
                }
            })
        })
    },
    hideModal(event){
        this.setData({invitationDialog:false})
        if( event.currentTarget.dataset.iscode == 'x'){
            wx.requestSubscribeMessage({
                tmplIds: ['Ag7cMQ1Sl4dx8jdh_VavgZ1XWXoOPhVO5f66M6sJxq0'],
                success (res) {
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: '订阅成功，审核结果第一时间发送给你',
                        success(res) {
                            wx.navigateBack({//返回
                                delta: 2
                            })
                        }
                    })
                }
            })
        }
    }

})