import constants from './constants'

const formatTime = (date,type='/') => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()
    const hour = date.getHours()
    const minute = date.getMinutes()
    const second = date.getSeconds()

    return [year, month, day].map(formatNumber).join(type) + ' ' + [hour, minute, second].map(formatNumber).join(':')
}

const formatData = date => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()

    return [year, month, day].map(formatNumber).join('-')
}

const formatData1 = date => {
    const year = date.getFullYear()
    const month = date.getMonth() + 1
    const day = date.getDate()

    return [year, month, day].map(formatNumber).join('')
}


const formatNumber = n => {
    n = n.toString()
    return n[1] ? n : '0' + n
}

const setStorage = (key, data) => {
    try {
        wx.setStorageSync(key, data)
    } catch (e) {
    }
}

const removeStorage = (key) => {
    console.log(key)
    try {
        wx.removeStorageSync(key)
    } catch (e) {
        console.log(">>>>",e)
    }
}

const getStorage = (key) => {
    try {
        return wx.getStorageSync(key)
    } catch (e) {
        return ''
    }
}

const formatNUmber = (value) => {
    if (Number(value) > 9999) {
        value = value.toString()
        let frist_init = value.slice(0, value.length - 4);//取value后四位之外的数值，例如：32100 取3
        let last_four = value.substr(value.length - 4);//取value后四位，例如：32100 取2100
        return `${frist_init}${last_four.slice(0, 2) === "00" ? "" : "." + last_four.slice(0, 2)}万`
    } else {
        return value
    }
}

/**
 * 判断是否是数字
 * @param val
 * @returns {boolean}
 */
const isNumber = (val) => {
    let regPos = /^\d+(\.\d+)?$/; //非负浮点数
    let regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if (regPos.test(val) || regNeg.test(val)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 手机号加星号
 * @param phone
 */
const phoneFor = (phone) => {
    return phone.replace(/^(\d{4})\d{4}(\d+)/, "$1****$2")
}

/**
 * 预估本月收益时间计算
 */
const getEstimateDate = () => {
    let date1 = new Date()
    date1.setDate(1)
    let oneDate = formatData(date1)

    let date = new Date()
    let currentMonth = date.getMonth()
    let nextMonth = ++currentMonth
    let nextMonthFirstDay = new Date(date.getFullYear(), nextMonth, 1)
    let oneDay = 1000 * 60 * 60 * 24
    let lastDate = formatData(new Date(nextMonthFirstDay - oneDay))

    return oneDate + " — " + lastDate
}

/**
 * 获取上月时间
 */
const getLastMonth = () => {
    let nowdays = new Date()
    let year = nowdays.getFullYear()
    let month = nowdays.getMonth()
    if (month == 0) {
        month = 12
        year = year - 1
    }
    if (month < 10) {
        month = "0" + month
    }
    let firstDayOfPreMonth = year + "-" + month + "-" + "01"
    let lastDay = new Date(year, month, 0)
    let lastDayOfPreMonth = year + "-" + month + "-" + lastDay.getDate()
    firstDayOfPreMonth = firstDayOfPreMonth.toString()
    lastDayOfPreMonth = lastDayOfPreMonth.toString()
    return firstDayOfPreMonth + " — " + lastDayOfPreMonth
}

/**
 * 获取最近30天日期
 */
const getLastDays = () => {
    let date = new Date()
    let nowDate = formatData(date)
    let lastDate = formatData(new Date(date - 1000 * 60 * 60 * 24 * 30))
    return lastDate + " — " + nowDate
}

const compareTime = (date1, date2) => {
    let oDate1 = new Date(date1);
    let oDate2 = new Date(date2);
    return oDate1.getTime() > oDate2.getTime()
}

const onToast = (msg,icon = 'none') => {
    wx.showToast({
        title: msg,
        icon: icon,
        duration: 2000
    })
}

const identityApp = (identity) => {
    if(constants.VIP_IDENTITY_REGISTER == identity){
        return  false
    }else if(constants.VIP_IDENTITY_SUPER == identity || constants.VIP_IDENTITY_TUTOR == identity){
        return  true
    }
    return  false
}

/**
 * 解析url参数
 * @param url
 * @returns {{}}
 */
const parseQueryString = (url) => {
    let obj = {};
    let start = url.indexOf("?") + 1;
    let str = url.substr(start);
    let arr = str.split("&");
    for (let i = 0; i < arr.length; i++) {
        let arr2 = arr[i].split("=");
        obj[arr2[0]] = arr2[1];
    }
    return obj;
}

module.exports = {
    formatTime: formatTime,
    setStorage: setStorage,
    getStorage: getStorage,
    removeStorage: removeStorage,
    formatNUmber,
    isNumber,
    formatData,
    getEstimateDate,
    getLastMonth,
    getLastDays,
    compareTime,
    phoneFor,
    formatData1,
    parseQueryString,
    onToast,
    identityApp
}