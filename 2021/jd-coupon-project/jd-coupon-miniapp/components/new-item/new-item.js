// components/new-item/new-item.js
const app = getApp()
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        listData: {
            type: Array,
            value: [
                {
                    title: '成长任务1：升级超级会员省更的多赚的多',
                    content: '建立一个大于50人的微信群完成升级，即可开启省得更多同时分享赚钱等9项特权。',
                    subTitle: '',
                    subContent: '',
                    actionBtn: '去升级',
                    video: '查看教学视频',
                    color: ''
                },
                {
                    title: '成长任务2：掌握选爆款和会卖货的技巧',
                    content: '了解小程序内重要的选品频道，并掌握多种发品方式和技巧，为请朋好友带来优惠，自己也能轻松赚取推广费。',
                    subTitle: '想学习更多一定添加专属导师微信！',
                    subContent: 'df33v',
                    actionBtn: '复制微信号',
                    video: '查看教学视频',
                    color: 'color-main'
                },
                {
                    title: '成长任务3：近培训群实现收益技能双增长',
                    content: '官方培训群有全方位阶段课程，更有大咖讲师的独家秘诀分享，帮助您的收益和技能实现快速增长。还可以添加导师微信进群学习。',
                    subTitle: '',
                    subContent: '',
                    actionBtn: '',
                    video: '查看教学视频',
                    color: ''
                },
                {
                    title: '成长任务4：近爆款群选取更多爆弹素材',
                    content: '官方爆款群是重要选品渠道之一，这里都是全网每天的爆款商品，已图片+视频+软文形式展示增强出单！',
                    subTitle: '从爆款群选品要先转链在发出哦~',
                    subContent: '',
                    actionBtn: '获取进群二维码',
                    video: '查看教学视频',
                    color: 'color6'
                },
                {
                    title: '成长任务5：邀请伙伴加入TA赚您更赚',
                    content: '学会邀请伙伴加入的三种方式，帮助伙伴省钱赚钱的同时您还能获得平台的额外奖励，邀请越多赚得就越多哦~',
                    subTitle: '',
                    subContent: '',
                    actionBtn: '去邀请',
                    video: '查看教学视频',
                    color: ''
                },
                // {title:'成长任务6：gu',content:'学会邀请伙伴加入的三种方式，帮助伙伴省钱赚钱的同时您还能获得平台的额外奖励，邀请越多赚得就越多哦~',subTitle:'',subContent:'',actionBtn:'复制',video:'',color:''},
            ]
        }
    },

    lifetimes: {
        attached: function () {

        }
    },

    /**
     * 组件的初始数据
     */
    data: {
        invitationDialog: '',
        item:null,
        mDialog:false
    },

    /**
     * 组件的方法列表
     */
    methods: {
        onClick(event) {
            const item = event.currentTarget.dataset.item
            //type=1:跳转页面   2.复制数据  3.显示一张图 4.分享APP
            switch (item.type) {
                case 1:
                    wx.navigateTo({
                        url: item.data
                    })
                    break
                case 2:
                    this.setData({
                        invitationDialog: true,
                        item:item})
                    break
                case 3:
                    this.setData({mDialog: true,
                        item:item})
                    break
                case 4:
                    // this.setData({invitationDialog: true})
                    break
            }
        },
        onVideoClick(event) {
            const item = event.currentTarget.dataset.item
            console.log(item.videoUrl)
            const url = encodeURIComponent(item.videoUrl)
            console.log(url)
            wx.navigateTo({
                url: '/page_package/video-course/video-course?url=' + url
            })
            // switch (Number(event.currentTarget.dataset.index)) {
            //     case 0:
            //         wx.navigateTo({
            //             url: '/page_package/video-course/video-course'
            //         })
            //         break
            // }
        },
        hideModal(event) {
            switch (Number(event.currentTarget.dataset.iscode)) {
                case 0:
                    wx.setClipboardData({
                        data: this.data.item.data,
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
                    break
            }
            this.setData({
                invitationDialog: false
            })
        },
        onClose() {
            this.setData({
                mDialog:false
            })
        },
        previewImg(event) {
            wx.previewImage({
                urls: [this.data.item.data],
            })
        },
    }
})
