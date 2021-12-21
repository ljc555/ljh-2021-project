/**
 * user api数据处理类
 */

import {Http} from '../http'
import apis from '../apis'
import constants from '../../constants'

/**
 * 根据商品类型查询商品列表
 * @returns {Promise<any>}
 */
const queryProductType = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.productType(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 热门商品
 * @returns {Promise<any>}
 */
const hotProduct = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.hotProduct(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 居家必备
 * @returns {Promise<any>}
 */
const ownerShip = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.ownerShip(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 *
 * @returns {Promise<any>}
 */
const goodComment = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.goodComment(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 *
 * @returns {Promise<any>}
 */
const hotLowPrice = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.hotLowPrice(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 *
 * @returns {Promise<any>}
 */
const twoHoursRank = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.twoHoursRank(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 爆款推荐
 * @returns {Promise<any>}
 */
const goodsTop = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.goodsTop(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 商品搜索
 * @returns {Promise<any>}
 */
const searchProduct = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.searchProduct(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}


/**
 * 搜罗好货
 * @returns {Promise<any>}
 */
const global = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.global(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 高佣商品
 * @returns {Promise<any>}
 */
const highCommission = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.highCommission(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 推广链接
 * @returns {Promise<any>}
 */
const goodsUrl = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.goodsUrl(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 推广链接
 * @returns {Promise<any>}
 */
const purchaseUrl = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.purchaseUrl(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 商品推广码
 * @returns {Promise<any>}
 */
const productsCode = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.productCode(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 小程序推广码
 * @returns {Promise<any>}
 */
const appCode = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.appCode(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 京东配送
 * @returns {Promise<any>}
 */
const deliverById = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.deliverById(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}


/**
 * 品牌专区
 * @returns {Promise<any>}
 */
const brand = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.brand(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 商品详情
 * @returns {Promise<any>}
 */
const productDetails = (skuIds) => {
    return new Promise((resolve, reject) => {
        Http.get(apis.productDetails(), {skuIds:skuIds}, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 优惠券
 * @returns {Promise<any>}
 */
const couponList = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.couponList(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}
/**
 * 发圈必推
 * @returns {Promise<any>}
 */
const ringPush = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.ringPush(), data, false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

module.exports = {
    queryProductType,
    hotProduct,
    ownerShip,
    goodComment,
    hotLowPrice,
    twoHoursRank,
    goodsTop,
    searchProduct,
    global,
    highCommission,
    deliverById,
    brand,
    goodsUrl,
    productsCode,
    appCode,
    productDetails,
    couponList,
    purchaseUrl,
    ringPush,
}