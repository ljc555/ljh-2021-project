package com.csbaic.jd.runner;

import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.Option;
import com.csbaic.jd.entity.OptionEntity;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.service.IOptionService;
import com.csbaic.jd.service.ITeamMemberService;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.option.OptionUpdateEvent;
import com.csbaic.jd.service.option.Options;
import com.csbaic.jd.service.order.fetcher.OrderFetcherScheduler;
import com.csbaic.jd.service.settlement.SettlementService;
import com.csbaic.jd.service.team.InvitationCodeGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class SystemInitializer implements ApplicationRunner , ApplicationListener<ApplicationEvent> {


    private final IOptionService optionService;

    private final IUserService userService;

    private final ITeamMemberService teamMemberService;

    private final InvitationCodeGenerator invitationCodeGenerator;

    private final OrderFetcherScheduler orderFetcherScheduler;

    private final SettlementService settlementService;

    private final TaskScheduler scheduler;


    @Autowired
    public SystemInitializer(IOptionService optionService, IUserService userService, ITeamMemberService teamMemberService, InvitationCodeGenerator invitationCodeGenerator, OrderFetcherScheduler orderFetcherScheduler, SettlementService settlementService, TaskScheduler scheduler) {
        this.optionService = optionService;
        this.userService = userService;
        this.teamMemberService = teamMemberService;
        this.invitationCodeGenerator = invitationCodeGenerator;
        this.orderFetcherScheduler = orderFetcherScheduler;
        this.settlementService = settlementService;
        this.scheduler = scheduler;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createSystemUserIfNeed();
        startOrderFetcher();
        startSettlementTask();
    }


    /***
     * 开始结算任务
     */
    private void startSettlementTask(){
        Optional<Option> optional = optionService.getOptions(Options.OPT_SYS_SETTLEMENT_START_DAY)
                .stream()
                .findFirst();

        if(!optional.isPresent()){
            throw BizRuntimeException.from(ResultCode.ERROR, "系统结算时间未设置，启动应用失败");
        }


        String cron =   optional.get().getValue() ;
        log.debug("Start settlement task {}", cron);
        scheduler.schedule(settlementService::settlementForLashMonth, new CronTrigger(cron));
    }

    /**
     * 开始同步订单
     */
    private void startOrderFetcher(){
        orderFetcherScheduler.schedule();
    }

    /**
     * 创建系统初始化用户
     */
    private void createSystemUserIfNeed(){
        int count = userService.count();
        //已经有用户了，不需要插入初始化用户
        if(count > 0){
            return;
        }


        String defaultUserName = optionService.getString(Options.OPT_SYS_INIT_USER_NAME);
        int defaultUserIdentify =  optionService.getInt(Options.OPT_SYS_INIT_USER_IDENTIFY);


        UserEntity userEntity = new UserEntity();
        userEntity.setNickName(defaultUserName);
        userEntity.setIdentify(defaultUserIdentify);
        userService.save(userEntity);


        String invitationCode =  invitationCodeGenerator.generate(userEntity.getId());

        userEntity.setInvitationCode(invitationCode);
        userService.saveOrUpdate(userEntity);
        //添加成员，使用随意的leaderId
        teamMemberService.addMember(Long.MIN_VALUE, userEntity.getId());
        log.info("生成初始化用户：{}", userEntity.getId());

        //更新默认推广码
        optionService.setOption(Options.OPT_APP_DEFAULT_INVITER_CODE, invitationCode);
    }


    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof OptionUpdateEvent){
            OptionUpdateEvent optionUpdateEvent = (OptionUpdateEvent) event;
            List<OptionEntity> options = (List<OptionEntity>) optionUpdateEvent.getSource();

            //todo 动态调整结算
        }
    }
}
