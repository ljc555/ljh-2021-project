package com.csbaic.jd;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class CodeGenerator {

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("/Users/yjwfn/development/csbaic/jd-social-commerce/jd-union-service/src/main/java");
        gc.setAuthor("yjwfn");
        gc.setOpen(false);
        gc.setEntityName("%sEntity");
        gc.setIdType(IdType.ASSIGN_ID);
        gc.setDateType(DateType.TIME_PACK);

        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/jd_union?serverTimezone=Asia/Shanghai&characterEncoding=utf8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("com.csbaic.jd");

        mpg.setPackageInfo(pc);




        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        //控制 不生成 controller
//        templateConfig.setController("");
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(false);
        strategy.setRestControllerStyle(false);
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setEntityColumnConstant(true);
        //"banner", "activity","user", "wechat_user", "user_profile" , "timeline_goods", "order_detail", "order_sku", "team_member"
//        strategy.setInclude("news");
//        strategy.setInclude("member_commission");
//        strategy.setInclude("order_sync_record");
//         strategy.setInclude("user");
//        strategy.setInclude("member_billing_calculate_result");
//        strategy.setInclude("user_active_score");
//        strategy.setInclude("member_billing_calculate_status");
//        strategy.setInclude("wallet_transaction_flow");
        //"apply_withdraw_order","apply_withdraw_order_operate_record",
//        strategy.setInclude( "apply_withdraw_order_operate_record");

        strategy.setInclude("jd_member_quick_start");
        strategy.setTablePrefix("jd");
        mpg.setStrategy(strategy);
        mpg.execute();
    }

}