package com.csbaic.jd.web;

import com.csbaic.common.result.ResultCode;

public interface ResultCodes {


    /**
     * 未注册用户
     */
    ResultCode USER_NOT_REGISTERED = ResultCode.error("USER_NOT_REGISTERED", "未注册用户");


    /**
     * 没有找到这个用户
     */
    ResultCode USER_NOT_FOUND = ResultCode.error("USER_NOT_FOUND", "没有找到用户");


    /**
     * 非法邀请码
     */
    ResultCode INVALID_INVITATION_CODE = ResultCode.error("INVALID_INVITATION_CODE", "非法邀请码");
    


}
