/**
 * api工具
 * @type {string}
 */
let BASEURL = 'http://192.168.0.103:8080';
// let BASEURL = 'http://192.168.3.173:8080';
// let BASEURL = 'https://jd-api.csbaic.com';

const getBaseUrl = (url) => {
    BASEURL = url
}

const LOGIN = () => {
    return BASEURL + '/login/wechat'
}

const register = () => {
    return BASEURL + '/users/wechat'
}

//验证邀请码
const verCode = () => {
    return BASEURL + '/users/get_by_invitation_code/'
}

//发圈必推
const ringPush = () => {
    return BASEURL + '/goods/today'
}

/**
 * 获取会员信息
 */
const vipInfo = () => {
    return BASEURL + '/members/info'
}

//商品类型列表
const productType = () => {
    return BASEURL + '/goods/by_category'
}

const hotProduct = () => {
    return BASEURL + '/goods/hot'
}

//居家必备
const ownerShip = () => {
    return BASEURL + '/goods/home_ownership'
}

//好评之王
const goodComment = () => {
    return BASEURL + '/goods/good_comment'
}

//9块9
const hotLowPrice = () => {
    return BASEURL + '/goods/hot_low_price'
}

//两小时热榜
const twoHoursRank = () => {
    return BASEURL + '/goods/two_hours_rank'
}

//快报
const newsList = () => {
    return BASEURL + '/news'
}

//爆款推荐列表
const goodsTop = () => {
    return BASEURL + '/goods/top'
}

//商品搜索
const searchProduct = () => {
    return BASEURL + '/goods/'
}

//首頁banner
const homeBanner = () => {
    return BASEURL + '/banners'
}

//搜罗好货
const global = () => {
    return BASEURL + '/goods/global'
}

//高佣商品
const highCommission = () => {
    return BASEURL + '/goods/high_commission'
}

//京东配送
const deliverById = () => {
    return BASEURL + '/goods/deliver_by_jd'
}

//品牌专区
const brand = () => {
    return BASEURL + '/goods/brand'
}

//生成商品购买链接（短链接）
const goodsUrl = () => {
    return BASEURL + '/goods_url/short_url'
}

//获取商品推广连接（短链接）
const purchaseUrl = () => {
    return BASEURL + '/goods_url/purchase_url'
}

//推广码
const productCode = () => {
    return BASEURL + '/qrcode/goods'
}

//小程序推广码
const appCode = () => {
    return BASEURL + '/qrcode/mini_app'
}

//成員列表
const members = () => {
    return BASEURL + '/members/'
}

//预估收益
const feeData = () => {
    return BASEURL + '/members/fee_data'
}

//提现信息
const walleta = () => {
    return BASEURL + '/wallet'
}

//提现记录
const historyRecords = () => {
    return BASEURL + '/wallet/withdraw_records'
}

//结算记录
const billingsRecords = () => {
    return BASEURL + '/wallet/billings'
}

//商品详情
const productDetails = () => {
    return BASEURL + '/goods/query_goods_detail'
}

//優惠券
const couponList = () => {
    return BASEURL + '/goods/coupons'
}

//订单列表
const orderList = () => {
    return BASEURL + '/order/by_time'
}

//我的聯係人
const userContacts = () => {
    return BASEURL + '/users/contacts'
}

//系统配置项
const options = () => {
    return BASEURL + '/options/app'
}

//更新用戶信息
const updateUserInfo = () => {
    return BASEURL + '/users/update_user_info'
}

//获取个人信息
const userInfo = () => {
    return BASEURL + '/users/mine'
}

//活动详情
const activityByID = (id) => {
    return BASEURL + '/activities/' + id
}

//商品转连
const goodsConvert = () => {
    return BASEURL + '/goods_url/convert'
}

//用户反馈
const feedback = () => {
    return BASEURL + '/feedback/'
}

//提现
const applyWithdraw = () => {
    return BASEURL + '/wallet/apply_withdraw'
}

//发送短信
const sendCode = () => {
    return BASEURL + '/wallet/send_apply_withdraw_code'
}

//常见问题
const faq = () => {
    return BASEURL + '/faq'
}
//升级会员信息
const superMembers = () => {
    return BASEURL + '/super_members'
}
//上传文件
const uploadFile = () => {
    return BASEURL + '/upload/app'
}
//上传文件
const posters = () => {
    return BASEURL + '/posters'
}

//快报详情
const messagesDetails = (id) => {
    return BASEURL + '/messages/' + id
}
//新手上路
const quickStart = () => {
    return BASEURL + '/quick_start'
}

module.exports = {
    getBaseUrl, //动态获取url
    LOGIN, //登录
    register, //注册
    verCode,//根据邀请人信息
    vipInfo,//获取会员信息
    productType,//商品类型
    hotProduct,//热门商品
    ownerShip,//居家必备
    goodComment,//好评之王
    hotLowPrice,//9块9
    twoHoursRank,//
    newsList,
    goodsTop,//爆款推荐
    searchProduct,//商品搜索
    homeBanner,//首頁banner
    global,//搜罗好货
    highCommission,//高佣商品
    deliverById,//京东配送
    brand,//品牌专区
    goodsUrl,//推广链接
    productCode,//推广码
    members,//成员列表
    feeData,//预估收益
    walleta,//提现信息
    historyRecords,//提现记录
    billingsRecords,//结算记录,
    appCode,//小程序推廣碼
    productDetails,//商品详情
    couponList,//優惠券
    purchaseUrl,
    orderList,//订单列表
    ringPush,//发圈必推
    userContacts,//我的团队
    options,//获取系统配置项,
    updateUserInfo,//更新用戶信息
    userInfo,//个人信息
    activityByID,//活动详情
    goodsConvert,//商品转链
    feedback,//用户反馈
    applyWithdraw,//提现
    sendCode,//发送提现短信
    faq,//常见问题
    superMembers,//升级会员信息
    uploadFile,//上传文件
    posters,//获取邀请图
    messagesDetails,//快报详情
    quickStart,//新手上路

};