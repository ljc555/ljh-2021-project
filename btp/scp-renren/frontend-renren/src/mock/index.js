import Mock from 'mockjs'
import * as common from './modules/common'
import * as jobSchedule from './modules/job-schedule'
import * as oss from './modules/oss'
import * as sysConfig from './modules/sys-config'
import * as sysLog from './modules/sys-log'
import * as sysMenu from './modules/sys-menu'
import * as sysRole from './modules/sys-role'
import * as sysUser from './modules/sys-user'
import * as travelApply from './modules/travel-apply'
import * as travelApplyItem from './modules/travel-apply-item'
import * as travelBank from './modules/travel-bank'
import * as travelCompany from './modules/travel-company'
import * as travelCosttype from './modules/travel-costtype'
import * as travelDept from './modules/travel-department'
import * as travelUser from './modules/travel-user'

// tips
// 1. 开启/关闭[业务模块]拦截, 通过调用fnCreate方法[isOpen参数]设置.
// 2. 开启/关闭[业务模块中某个请求]拦截, 通过函数返回对象中的[isOpen属性]设置.
fnCreate(common, false)
fnCreate(jobSchedule, false)
fnCreate(oss, false)
fnCreate(sysConfig, false)
fnCreate(sysLog, false)
fnCreate(sysMenu, false)
fnCreate(sysRole, false)
fnCreate(sysUser, false)
fnCreate(travelApply, false)
fnCreate(travelApplyItem, false)
fnCreate(travelBank, false)
fnCreate(travelCompany, false)
fnCreate(travelCosttype, false)
fnCreate(travelDept, false)
fnCreate(travelUser, false)

/**
 * 创建mock模拟数据
 * @param {*} mod 模块
 * @param {*} isOpen 是否开启?
 */
function fnCreate (mod, isOpen = true) {
  if (isOpen) {
    for (var key in mod) {
      ((res) => {
        if (res.isOpen !== false) {
          Mock.mock(new RegExp(res.url), res.type, (opts) => {
            opts['data'] = opts.body ? JSON.parse(opts.body) : null
            delete opts.body
            console.log('\n')
            console.log('%cmock拦截, 请求: ', 'color:blue', opts)
            console.log('%cmock拦截, 响应: ', 'color:blue', res.data)
            return res.data
          })
        }
      })(mod[key]() || {})
    }
  }
}
