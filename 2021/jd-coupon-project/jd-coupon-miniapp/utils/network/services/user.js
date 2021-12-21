/**
 * user api数据处理类
 */

import {Http} from '../http'
import apis from '../apis'
import constants from '../../constants'

/**
 * 检查微信会话是否过期
 * @returns {Promise<any>}
 */
const checkSession = () => {
    return new Promise((resolve, reject) => {
        wx.checkSession({
            success: () => {
                resolve(true)
            },
            fail: () => {
                reject(false);
            }
        });
    })
}

/**
 * 检查用户是否登录
 * @returns {Promise<any>}
 */
const checkLogin = () => {
    return new Promise((resolve, reject) => {
        if (wx.getStorageSync(constants.AUTH_INFO)) {//本地是否存储userInfo
            checkSession().then(() => {
                resolve(true);
            }).catch(() => {
                reject(false)
            });
        } else {
            reject(false)
        }
    })
}

/**
 * 根据wx.login的code登陆系统
 * @param code
 * @returns {Promise<any>}
 */
const onLogin = (code) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.LOGIN(), {code: code},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取邀请人信息
 * @param code
 * @returns {Promise<any>}
 */
const checkCode = (code) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.verCode() + code, {})
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 用户注册
 * @param data
 * @returns {Promise<any>}
 */
const userRegister = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.register(), data)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取会员信息
 * @returns {Promise<any>}
 */
const getVipInfo = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.vipInfo(), {},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取会员成员列表
 * @returns {Promise<any>}
 */
const getMembers = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.members(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}


/**
 * 预估收益
 * @returns {Promise<any>}
 */
const getFeeData = () => {
    return new Promise((resolve, reject) => {
        Http.post(apis.feeData(), {},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取提现信息
 * @returns {Promise<any>}
 */
const getWalleta = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.walleta(), {},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取提现歷史
 * @returns {Promise<any>}
 */
const getHistoryRecords = (data) => {
    return new Promise((resolve, reject) => {
        Http.get(apis.historyRecords(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取结算记录
 * @returns {Promise<any>}
 */
const getBillingsRecords = (data) => {
    return new Promise((resolve, reject) => {
        Http.get(apis.billingsRecords(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取订单记录
 * @returns {Promise<any>}
 */
const orderList = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.orderList(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取我的团队数据
 * @returns {Promise<any>}
 */
const userContacts = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.userContacts(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取我的团队数据
 * @returns {Promise<any>}
 */
const updateUserInfo = (data) => {
    return new Promise((resolve, reject) => {
        Http.put(apis.updateUserInfo(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取个人信息
 * @returns {Promise<unknown>}
 */
const userInfo = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.userInfo(), {},false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 用户反馈
 * @returns {Promise<unknown>}
 */
const feedback = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.feedback(), data,true)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 提现
 * @returns {Promise<unknown>}
 */
const applyWithdraw = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.applyWithdraw(), data,true)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}
/**
 * 提现
 * @returns {Promise<unknown>}
 */
const sendCode = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.sendCode(), data,true)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 升级会员信息
 * @returns {Promise<unknown>}
 */
const superMembers = () => {
    return new Promise((resolve, reject) => {
        Http.get(apis.superMembers(), {},true)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 升级会员信息
 * @returns {Promise<unknown>}
 */
const superMembersUp = (data) => {
    return new Promise((resolve, reject) => {
        Http.post(apis.superMembers(), data,true)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

/**
 * 获取分享图
 * @returns {Promise<unknown>}
 */
const posters = (data) => {
    return new Promise((resolve, reject) => {
        Http.get(apis.posters(), data,false)
            .then((res) => {
                resolve(res);
            })
            .catch((err) => {
                reject(err)
            })
    })
}

module.exports = {
    checkLogin: checkLogin,
    onLogin:onLogin,
    checkCode: checkCode,
    userRegister: userRegister,
    getVipInfo,
    getMembers,
    getFeeData,
    getWalleta,
    getHistoryRecords,
    getBillingsRecords,
    orderList,
    userContacts,
    updateUserInfo,
    userInfo,
    feedback,
    applyWithdraw,
    sendCode,
    superMembers,
    superMembersUp,
    posters,
}