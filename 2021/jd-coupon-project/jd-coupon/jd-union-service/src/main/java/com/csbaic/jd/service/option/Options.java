package com.csbaic.jd.service.option;

/**
 * 配置项的key
 */
public interface Options {

    /**
     * 初始用户的等级
     */
    String OPT_SYS_INIT_USER_IDENTIFY = "sys_default_user_identify";


    /**
     * 初始用户的名称
     */
    String OPT_SYS_INIT_USER_NAME = "sys_default_user_name";


    /**
     * 初始用户头像
     */
    String OPT_SYS_INIT_USER_AVATAR = "sys_default_user_avatar";


    /**
     * 用户注册时的默认等级
     */
    String OPT_SYS_REGISTER_USER_IDENTIFY = "sys_register_user_identify";

    /**
     * 开始同步京东订单的时间
     */
    String OPT_SYS_ORDER_SYNC_START_TIME = "sys_order_sync_start_time";

    /**
     * 开始同步京东订单的时间
     */
    String OPT_SYS_CURRENT_SYNC_TIME = "sys_current_sync_start_time";

    /**
     * 允许同步京东订单的的延时（分钟）
     */
    String OPT_SYS_ORDER_SYNC_DELAY = "sys_order_sync_delay";



    /**
     * 允许同步京东订单的的重试次数
     */
    String OPT_SYS_ORDER_RETRY_COUNT = "sys_order_sync_retry_count";



    /**
     * 允许同步京东订单重试间隔
     */
    String OPT_SYS_ORDER_RETRY_INTERVAL = "sys_order_sync_retry_interval";


    /**
     * 结算开始日期
     */
    String OPT_SYS_SETTLEMENT_START_DAY = "sys_settlement_start_day";

    /**
     * 结算开始日期
     */
    String OPT_SYS_NEXT_GROUP_ID = "opt_sys_next_group_id";

    /**
     * 提现开始日期
     */
    String OPT_SYS_WITHDRAW_START_DAY = "sys_withdraw_start_day";


    /**
     * 提现结束日期
     */
    String OPT_SYS_WITHDRAW_END_DAY = "sys_withdraw_end_day";


    /**
     * 是否需要强制用户登陆App
     */
    String OPT_APP_FORCE_LOGIN = "app_force_login";



    /**
     * 默认推广码（首码）
     */
    String OPT_APP_DEFAULT_INVITER_CODE = "app_default_inviter_code";
}
