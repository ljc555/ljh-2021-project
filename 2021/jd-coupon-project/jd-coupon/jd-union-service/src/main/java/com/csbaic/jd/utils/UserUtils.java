package com.csbaic.jd.utils;

import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.enums.UserIdentify;

/***
 * 用户工具类
 */
public class UserUtils {

    /**
     * 从{@link org.springframework.security.core.Authentication}中获取用户id
     * @return null if no user id
     */
    public static Long getUserId(){
        Object principal = SecurityUtils.getPrincipal();
        if(principal instanceof UserEntity){
            return ((UserEntity) principal).getId();
        }
        return  null;
    }

    /***
     * 获取用户身份
     * @param identify
     * @return
     */
    public static UserIdentify getUserIdentify(int identify){
        UserIdentify userIdentify  = UserIdentify.of(identify);

        if(userIdentify == null){
            throw BizRuntimeException.from(ResultCode.ERROR, "用户等级异常");
        }

        return userIdentify;
    }

}
