// page_package/money/quota/quota.js
const app = getApp()
const util = require("../../../utils/util")
const {applyWithdraw, sendCode} = require("../../../utils/network/services/user.js")
const constants = require("../../../utils/constants")
let interval = null
let that = null
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: app.globalData.userInfo,
        money: 0,
        cashMoney: '',
        inputRemind: "",
        sumInfo: {
            amount: 0,
            code: "",
            payeeCardId: "",
            payeeName: "",
            wechatId: ""
        },
        isDisabled: false,
        currentTime: 61,
        promptMsg: "验证码",
        phone: ''
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        that = this
        let userInfo = util.getStorage(constants.VIP_INFO_AUTO)
        if (userInfo.phone)
            this.setData({
                money: Number(options.balance),
                userInfo: userInfo,
                phone: util.phoneFor(userInfo.phone)
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

    onInput(event) {
        let inputValue = event.detail.value
        switch (Number(event.currentTarget.dataset.index)) {
            case 0:
                let remind = ''
                if (Number(inputValue) <= 0) remind = '最少提现金额为1元'
                else if (Number(inputValue) > this.data.money) remind = '输入的金额超过可转出的进度'
                this.data.sumInfo.amount = inputValue
                this.setData({
                    cashMoney: inputValue,
                    inputRemind: remind,
                    sumInfo: this.data.sumInfo
                })
                break
        }
    },
    onBlur(event) {
        let inputValue = event.detail.value
        switch (Number(event.currentTarget.dataset.index)) {
            case 0:
                this.setData({cashMoney: inputValue})
                break
        }
    },
    onInput1(event) {
        let inputValue = event.detail.value
        let subInfo = this.data.sumInfo
        switch (Number(event.currentTarget.dataset.type)) {
            case 1:
                subInfo.payeeName = inputValue
                break
            case 2:
                subInfo.payeeCardId = inputValue
                break
            case 3:
                subInfo.code = inputValue
                break
        }
        this.setData({sumInfo: subInfo})
    },
    onQuota() {
        this.setData({cashMoney: this.data.money})
    },
    sendCode() {
        sendCode({phone: this.data.userInfo.phone})
            .then(res => {
                that.setData({isDisabled: true})
                util.onToast('发送成功', 'success')
                that.setInt()
            }).catch(err => {
            this.setData({isDisabled: false})
            wx.showModal({
                title: '提示',
                showCancel: false,
                content: err.data?err.data.message:'发送失败',
                success(res) {

                }
            })
        })
    },
    onSubmit() {
        let subInfo = this.data.sumInfo
        subInfo.amount = this.data.cashMoney ? this.data.cashMoney : 0
        if (Number(subInfo.amount) < 1) {
            util.onToast('提现金额必须大于1元')
            return
        } else if (!subInfo.payeeName) {
            util.onToast('请输入姓名')
            return;
        } else if (subInfo.payeeCardId.length < 14) {
            util.onToast('请输入合法证件号码')
            return;
        } else if (!subInfo.code) {
            util.onToast('请输入验证码')
            return;
        }
        subInfo.wechatId = this.data.userInfo.wechatId

        this.setData({sumInfo: subInfo, invitationDialog: true})
    },
    hideModal(event) {
        this.setData({invitationDialog: false})
        if (Number(event.currentTarget.dataset.iscode) == 0) {
            applyWithdraw(this.data.sumInfo)
                .then((res) => {
                    wx.showModal({
                        title: '提示',
                        showCancel: false,
                        content: "提现成功，1-3个工作日到账",
                        success(res) {
                            wx.navigateBack({//返回
                                delta: 1
                            })
                        }
                    })
                }).catch(err => {
                console.log(err)
                wx.showModal({
                    title: '失败提示',
                    showCancel: false,
                    content: err.data?err.data.message:'提现失败',
                    success(res) {

                    }
                })
            })
        }
    },
    setInt() {
        let currentTime = this.data.currentTime
        interval = setInterval(function () {
            currentTime--;
            that.setData({promptMsg: currentTime + '秒',isDisabled: true})
            if (currentTime <= 0) {
                clearInterval(interval)
                that.setData({
                    promptMsg: '重新发送',
                    currentTime: 61,
                    isDisabled: false
                })
            }
        }, 1000)
    }
})