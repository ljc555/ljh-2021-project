const SEARCH_HISTORY = "SEARCH_HISTORY"//搜索历史

const EXTERNAL_SEARCH_HISTORY = "EXTERNAL_SEARCH_HISTORY"//外部搜索历史

const AUTH_INFO = "AUTH_INFO"//授权信息

const AUTH_INFO_CACHE = "AUTH_INFO_CACHE"//授权信息缓存

const PRPDUCT_ITEM = "PRPDUCT_ITEM" //商品项

const RAIDERS = "RAIDERS" //打开攻略类型

const QUESTION = "QUESTION" //海报标识

const INVITE_DATA = 'INVITE_DATA'//邀请数据

const NEW_USER_PLOY = 'NEW_USER_PLOY'//新用户注册策略

const DICTIONARY = 'dictionary'//系统数据字典

const VIP_INFO_AUTO = 'user_vip_info_auto' //用戶會員信息

const VIP_IDENTITY_REGISTER = 1 //注册会员

const VIP_IDENTITY_SUPER = 2 //超级会员

const VIP_IDENTITY_TUTOR = 3 //导师


//--------------首页菜单标识符-----------------
const JD_DELIVERY = 101 //京东送货
const BRAND_ZONE = 102 //品牌专区
const COUPON = 103 //优惠券
const HAIR_RING_PUSH = 104 //发圈必推
const HOT_LIST = 105 //热销榜单
const FASHION_LIFE = 106 //时尚生活
const DIGITAL_HOME = 107 //家电数码
const FRESH_FOOD = 108 //食品生鲜
const FURNITYRE = 109 //家具日用
const BEAUTY_CARE = 110 //美妆个护

//------------邀请类型-----------------
const INVITE_SHARE_APPS = 0 //商品页分享小程序邀请
const INVITE_USER_APPS = 1 //小程序邀请

module.exports = {
    SEARCH_HISTORY: SEARCH_HISTORY,
    AUTH_INFO: AUTH_INFO,
    AUTH_INFO_CACHE: AUTH_INFO_CACHE,
    PRPDUCT_ITEM,
    JD_DELIVERY,
    BRAND_ZONE,
    COUPON,
    HAIR_RING_PUSH,
    HOT_LIST,
    FASHION_LIFE,
    DIGITAL_HOME,
    FRESH_FOOD,
    FURNITYRE,
    BEAUTY_CARE,
    RAIDERS,
    QUESTION,
    INVITE_DATA,
    INVITE_SHARE_APPS,
    EXTERNAL_SEARCH_HISTORY,
    NEW_USER_PLOY,
    VIP_INFO_AUTO,
    DICTIONARY,
    VIP_IDENTITY_REGISTER,
    VIP_IDENTITY_SUPER,
    VIP_IDENTITY_TUTOR
}