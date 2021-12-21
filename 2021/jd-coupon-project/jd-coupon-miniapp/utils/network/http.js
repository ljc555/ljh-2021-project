const constants = require("../../utils/constants")
const util = require("../../utils/util")

class Http {
    /**
     * 统一请求
     * @param method
     * @param url
     * @param data
     * @returns {Promise<any>}
     */
    static request(method, url, data, isLoading = true) {
        if (method === 'GET') {
            url += this.formatQuery(data)
        }

        console.log('请求链接=' + url, '请求参数' + JSON.stringify(data))

        if (isLoading) wx.showLoading({title: '加载中...'})
        return new Promise((resolve, reject) => {
            let loginInfo = util.getStorage(constants.AUTH_INFO)
            if (loginInfo.token == undefined) {
                wx.request({
                    url: url,
                    data: data,
                    method: method,
                    header: {
                        'Content-Type': 'application/json; charset=utf-8'
                    },
                    success: res => {
                        if (res.data.success) {
                            resolve(res)
                        } else
                            reject(res)
                        wx.hideLoading()
                        console.log('请求结果=', res)

                        if (res.statusCode == 401 && res.data.code != 'USER_NOT_REGISTERED'){
                            util.removeStorage(constants.AUTH_INFO)
                            util.removeStorage(constants.VIP_INFO_AUTO)
                            wx.navigateTo({
                                url: '/pages/login/phone-login/phone-login?type=2'
                            })
                        }

                    },
                    fail: err => {
                        console.log(err)
                        reject(err)
                        wx.hideLoading()
                    }
                });
            } else
                wx.request({
                    url: url,
                    data: data,
                    method: method,
                    header: {
                        'Content-Type': 'application/json; charset=utf-8',
                        'Authorization': url.indexOf("/login/wechat") == -1 ? 'Bearer ' + loginInfo.token : ''
                    },
                    success: res => {
                        if (res.data.success) {
                            resolve(res)
                        } else
                            reject(res)
                        wx.hideLoading()
                        console.log('请求结果=', res)

                        if (res.statusCode == 401 && res.data.code != 'USER_NOT_REGISTERED'){
                            util.removeStorage(constants.AUTH_INFO)
                            util.removeStorage(constants.VIP_INFO_AUTO)
                            wx.navigateTo({
                                url: '/pages/login/phone-login/phone-login?type=2'
                            })
                        }
                    },
                    fail: err => {
                        console.log(err)
                        reject(err)
                        wx.hideLoading()
                    }
                });
        })
    }

    /**
     * 判断请求是否成功
     */
    static isSuccess(res) {
        if (res.status >= 200 && res.status < 300) {
            return res
        } else {
            this.requestException(res)
        }
    }

    /**
     * 异常
     */
    static requestException(res) {
        const error = new Error(res.statusText)

        error.response = res

        throw error
    }


    static get(url, data, isLoading = true) {
        return this.request('GET', url, data, isLoading)
    }

    static put(url, data, isLoading = true) {
        return this.request('PUT', url, data, isLoading)
    }

    static post(url, data, isLoading = true) {
        return this.request('POST', url, data, isLoading)
    }

    static patch(url, data, isLoading = true) {
        return this.request('PATCH', url, data, isLoading)
    }

    static delete(url, data, isLoading = true) {
        return this.request('DELETE', url, data, isLoading)
    }


    /**
     * url处理
     */
    static formatQuery(query) {
        let params = [];

        if (query) {
            for (let item in query) {
                let vals = query[item];
                if (vals !== undefined) {
                    params.push(item + '=' + query[item])
                }
            }
        }
        return params.length ? '?' + params.join('&') : '';
    }
}

export {Http}
