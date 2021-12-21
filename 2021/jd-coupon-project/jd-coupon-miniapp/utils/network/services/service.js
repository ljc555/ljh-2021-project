/**
 * user api数据处理类
 */

import {Http} from '../http'
import apis from '../apis'
import constants from '../../constants'
import util from '../../util'

/**
 * 获取快报列表
 * @returns {Promise<any>}
 */
const newsList = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.newsList(), {},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

const homeBanner = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.homeBanner(), {},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取系统配置项
 * @returns {Promise<unknown>}
 */
const options = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.options(), {},false)
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
 * @returns {Promise<unknown>}
 */
const activityByID = (id) => {
    return new Promise((resolve, reject) => {
        Http.get(apis.activityByID(id), {},true)
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
 * @param id
 * @returns {Promise<unknown>}
 */
const goodsConvert = (content) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.goodsConvert(), {content:content},true)
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
 * @param id
 * @returns {Promise<unknown>}
 */
const uploadFile = (data) => {
    let loginInfo = util.getStorage(constants.AUTH_INFO)
    return new Promise((resolve, reject) => {
        wx.showLoading({title: '加载中...'})
        wx.uploadFile({
            url:apis.uploadFile(),
            filePath:data.path,
            name:'file',
            header: {
                'Content-Type': 'multipart/form-data',
                'Authorization': 'Bearer ' + loginInfo.token
            },
            formData:data,
            success: res => {
                let data = JSON.parse(res.data)
                resolve(data.data.uri)
            },
            fail: err => {
                wx.hideLoading()
                reject(err)
            }
        })
    })
}
/**
 *
 * @param id
 * @returns {Promise<unknown>}
 */
const faq = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.faq(), {},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

const messagesDetails = (id) => {
    return new Promise((resolve, reject) => {
        Http.get(apis.messagesDetails(id), {},true)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

const quickStart = (data) => {
    return new Promise((resolve, reject) => {
        Http.get(apis.quickStart(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

module.exports = {
    newsList,
    homeBanner,
    options,
    activityByID,
    goodsConvert,
    faq,
    uploadFile,
    messagesDetails,
    quickStart
}