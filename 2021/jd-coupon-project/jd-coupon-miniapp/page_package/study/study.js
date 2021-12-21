// page_package/study/study.js
const app = getApp()
Page({

    /**
     * 页面的初始数据
     */
    data: {
        windowHeight: app.globalData.windowHeight,
        CustomBar: app.globalData.CustomBar,
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

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
    onSave(event) {
        wx.getSetting({
            success(res) {
                wx.showLoading({title: '保存中...',})
                wx.downloadFile({
                    url: "http://mss.sankuai.com/v1/mss_51a7233366a4427fa6132a6ce72dbe54/newsPicture/05558951-de60-49fb-b674-dd906c8897a6",
                    success: function(res) {
                        wx.saveImageToPhotosAlbum({
                            filePath: res.tempFilePath,
                            success: function(data) {
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
                            fail: function(err) {
                                wx.hideLoading()
                                console.log(err);
                            }
                        });
                    }
                });
            },
            fail: (err) => {
                wx.showModal({
                    title: '提示',
                    showCancel: false,
                    content: '请到小程序设置中打开相册权限',
                    success(res) {}
                })
            }
        });
    },
    onPreview(event) {
        wx.previewImage({
            urls: ['https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1583131085102&di=3454eccc0e5443f08f24157034dd9220&imgtype=0&src=http%3A%2F%2Fhbimg.huabanimg.com%2F437304a7ca4cf0c9386df81fcfc761299c6a11271ffc5-OwRrXi_fw658'],
        })
    }

})