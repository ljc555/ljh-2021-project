// components/question/question.js
const app = getApp()
const constants = require("../../utils/constants")
const util = require("../../utils/util")
const {faq} = require("../../utils/network/services/service")
const startPage = 1
Component({
    /**
     * 组件的属性列表
     */
    properties: {
        listData: {
            type: Array,
            value: [
                {
                    open: false,
                    questionList: [
                        {
                            title: '哪些情况会造成订单无效?',
                            content: '1．涉嫌利用本平台实施欺诈或其他违法行为（运营方有权单方对此作出独立认定）；\n' +
                                '\n' +
                                '2．被任何第三方投诉或被相关司法、行政机关处理的；\n' +
                                '\n' +
                                '3．恶意使用本平台功能，使用任何装置、软件或例行程序干预或试图干预京东台或本平台的；\n' +
                                '\n' +
                                '4．利用计算机病毒/程序等手段，非法窃取、删除、修改或增加其他会员的任何信 息，或以其他任何方式危害京东平台/本平台其他功能的正常运行；\n' +
                                '\n' +
                                '5．利用京东CPS系统漏洞，谋取利益；\n' +
                                '\n' +
                                '6．未经运营方书面同意，擅自许可他人使用本平台或擅自将本平台功能全部或部 分转让他人（包括但不限于擅自许可他人使用其帐号利用京本平台进行推广，或擅自将其帐号转让或泄露给他人的）；\n' +
                                '\n' +
                                '7．以不良方法或技术等规避本协议约定和规则要求等；\n' +
                                '\n' +
                                '8．通过程序、脚本模拟或其他形式进行或产生非正常的浏览、点击、交易行为等。通过奖励、诱导点击、弹窗、自动模拟用户点击等行为，产生非正常的浏览、点击、成交的行为；\n' +
                                '\n' +
                                '9．流量劫持。如通过病毒、木马、恶意插件和未经授权软件捆绑安装、强设首页、劫持地址栏或浏览器、劫持京东页面、搜索引擎作弊、篡改用户信息等非常规手段劫持正常流量。或通过链路劫持、DNS劫持、或者ARP攻击等手段劫持正常流量。或者在用户正常浏览过程中，通过修改URL参数或弹窗（浮窗）等的方式劫持京东商城或京东合作伙伴的正常流量（包括京东页面、合作伙伴和其他网站等）；\n' +
                                '\n' +
                                '10．其他京东联盟有合理理由证明的数据异常（如流量来源、分布等）情况。会员承诺并保证：按本协议约定使用本平台并进行推广，遵守平台规则，按约定收取服务费并支付平台费。'
                        },
                        {
                            title: '什么是“上月预估结算佣金”、“本月预估结算佣金”?',
                            content: '上月预估结算佣金：上个自然月产生的已完成订单的预估佣金总额，最终结算金额以账单中的结算数据为准。\n' +
                                '\n' +
                                '本月预估结算佣金：本个自然月（不包含当日）产生的已完成订单的预估佣金总额，最终结算金额以账单中的结算数据为准。  '
                        }
                    ]
                }
            ]
        }
    },

    /**
     * 组件的初始数据
     */
    data: {
        windowWidth: app.globalData.windowWidth,
        questionList:[],
        page:startPage
    },

    /**
     * 组件的方法列表
     */
    methods: {
        onShrink(event) {
            let index = event.currentTarget.dataset.index
            let item = event.currentTarget.dataset.item
            item.open = !item.open
            let listData = this.data.listData
            listData.splice(index, 1, item)
            this.setData({listData: listData})
        },
        itemClick(event) {
            let item = event.currentTarget.dataset.items
            util.setStorage(constants.QUESTION,item)
            wx.navigateTo({
                url: '/page_package/question/question'
            })
        },
        getData(type){
            faq().then(res => {
                this.setData({questionList:res.data.data})
            })
        },
    },
})
