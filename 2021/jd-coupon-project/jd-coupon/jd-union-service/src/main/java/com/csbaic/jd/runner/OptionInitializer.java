package com.csbaic.jd.runner;

import com.csbaic.jd.dto.Option;
import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.IOptionService;
import com.csbaic.jd.service.option.Options;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class OptionInitializer implements ApplicationRunner {

    @Autowired
    private IOptionService optionService;


    @Override
    public void run(ApplicationArguments args) throws Exception {

        List<Option> options = optionService.getOptions(
                Options.OPT_SYS_INIT_USER_IDENTIFY,
                Options.OPT_SYS_INIT_USER_NAME,
                Options.OPT_SYS_INIT_USER_AVATAR,
                Options.OPT_SYS_ORDER_SYNC_START_TIME,
                Options.OPT_SYS_ORDER_SYNC_DELAY,
                Options.OPT_SYS_SETTLEMENT_START_DAY,
                Options.OPT_SYS_NEXT_GROUP_ID
        );

        Map<String, Option> optionsMap = options.stream()
                .collect(Collectors.toMap(Option::getKey, (o) -> o));

        LocalDateTime now = LocalDateTime.now();

        final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .appendPattern("yyyyMMddHHmmss")
                .toFormatter();


        Map<String, String> initOptions = new HashMap<>();
        /*
            判断是否设置过初始化启动时间
         */
        if(isEmpty(optionsMap, Options.OPT_SYS_ORDER_SYNC_START_TIME)){
            //从系统启动时间开始同步订单
            put(initOptions, Options.OPT_SYS_ORDER_SYNC_START_TIME, dateTimeFormatter.format(now));
            put(initOptions, Options.OPT_SYS_CURRENT_SYNC_TIME, dateTimeFormatter.format(now));

        }

        /*
            设置订单允许的延迟时间，相对京东服务器时间
         */
        if(isEmpty(optionsMap, Options.OPT_SYS_ORDER_SYNC_DELAY)){
            //默认2分钟
            put(initOptions, Options.OPT_SYS_ORDER_SYNC_DELAY, 2);
        }

         /*
            设置订单同步重试次数
         */
        if(isEmpty(optionsMap, Options.OPT_SYS_ORDER_RETRY_COUNT)){
            //默认3次
            put(initOptions, Options.OPT_SYS_ORDER_RETRY_COUNT, 3);
        }

          /*
            设置订单同步重试间隔
         */
        if(isEmpty(optionsMap, Options.OPT_SYS_ORDER_RETRY_INTERVAL)){
            //默认2秒
            put(initOptions, Options.OPT_SYS_ORDER_RETRY_INTERVAL, 2000);
        }

        /*
            APP是否启用强制登陆
         */
        if(isEmpty(optionsMap, Options.OPT_APP_FORCE_LOGIN)){
            //默认2分钟
            put(initOptions, Options.OPT_APP_FORCE_LOGIN, false);
        }

        //系统初始化用户等级
        if(isEmpty(optionsMap, Options.OPT_SYS_INIT_USER_IDENTIFY)){
            //默认超级会员
            put(initOptions, Options.OPT_SYS_INIT_USER_IDENTIFY, UserIdentify.SUPER.getCode());
        }

        //系统初始化用户名称
        if(isEmpty(optionsMap, Options.OPT_SYS_INIT_USER_NAME)){
            //默认超级会员
            put(initOptions, Options.OPT_SYS_INIT_USER_NAME, "系统用户");
        }

        //默认注册用户等级
        if(isEmpty(optionsMap, Options.OPT_SYS_REGISTER_USER_IDENTIFY)){
            //默认超级会员
            put(initOptions, Options.OPT_SYS_REGISTER_USER_IDENTIFY, UserIdentify.REGISTERED.getCode());
        }


        //结算开始日期
        if(isEmpty(optionsMap, Options.OPT_SYS_SETTLEMENT_START_DAY)){
            //结算开始日期，默认每月22号结算帐户
            put(initOptions, Options.OPT_SYS_SETTLEMENT_START_DAY, "0 0 0 25 * ?");
        }

        //默认27~30号可申请提现
        if(isEmpty(optionsMap, Options.OPT_SYS_WITHDRAW_START_DAY)){
            put(initOptions, Options.OPT_SYS_WITHDRAW_START_DAY, "27");
        }

        //默认27~30号可申请提现
        if(isEmpty(optionsMap, Options.OPT_SYS_WITHDRAW_END_DAY)){
            put(initOptions, Options.OPT_SYS_WITHDRAW_END_DAY, "30");
        }

        //默认群编号
        if(isEmpty(optionsMap, Options.OPT_SYS_NEXT_GROUP_ID)){
            put(initOptions, Options.OPT_SYS_NEXT_GROUP_ID, "10000");
        }



        optionService.setOptions(initOptions);
    }

    public static void put(Map<String, String> in, String key, Object value){
        in.put(key,  String.valueOf(value));
        log.debug("Set option {} = {}", key, value);
    }

    public static boolean isEmpty(Map<String, Option> map, String key){
        return !map.containsKey(key) || map.get(key) == null || Strings.isNullOrEmpty(map.get(key).getValue());
    }
}
