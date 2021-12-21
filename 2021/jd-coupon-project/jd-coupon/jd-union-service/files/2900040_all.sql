SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `jd_activity` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '活动标题',
  `share_image` varchar(255) NOT NULL DEFAULT '' COMMENT '活动分享图',
  `logo_image` varchar(255) NOT NULL DEFAULT '' COMMENT '活动logo图',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '活动分享文本',
  `start_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动开始时间',
  `end_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '活动结束时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（1: 自动，2：显示，3：未显示）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='活动表';

insert into `jd_activity`(`id`,`title`,`share_image`,`logo_image`,`content`,`start_time`,`end_time`,`status`,`create_time`) values
('1242795127613689858','🔥清洁纸品超级品类日','https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/3qSSRdvGAxvwARTmak2oEprLSR7J_share.jpg','https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/3qSSRdvGAxvwARTmak2oEprLSR7J_logo.jpg','🎉清洁纸品超级品类日
☀领券满99减50，满299减150
主会场👉https://u.jd.com/WoWhDy

🔥满99减50直达
👉https://u.jd.com/YGHnSB

🔥满299减150
👉https://u.jd.com/BAAv0C

🔥99元5件
👉https://u.jd.com/pHuf4v

💦斑布 爆品99元5件
👉https://u.jd.com/8p5tu2

💦蓝月亮 抢第2套半价
👉https://u.jd.com/3PXTC8

💦立白 爆款第2件0元抢
👉https://u.jd.com/tGXzHr

💦维达 叠券满129元减20
👉https://u.jd.com/T3QkTL','2020-03-25 00:00:00','2020-03-31 00:00:00',1,'2020-03-25 20:47:03'),
('1249586550744813569','🔥初春潮履 焕新出彩','https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/2srJNTsM1fh2Epnfowoztc1Cuvix_share.png','https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/2srJNTsM1fh2Epnfowoztc1Cuvix_logo.png','[Smile]型格男装闪耀新品
[Smile]春夏潮流新品，爆款直降，超低价！
主会场[Smile]https://u.jd.com/EqPd29

[Smile]【Dickies】 单品低至58元
[Smile]https://u.jd.com/YY4C6u

[Smile]【马克华菲】全场低至69元起
[Smile]https://u.jd.com/Xl8we8

[Smile]【STARTER】全场低至5折
[Smile]https://u.jd.com/U8btgt

[Smile]【dangerouspeople】全场低至4折起
[Smile]https://u.jd.com/HTo25e

[Smile]【速写】 专区2件8.8折
[Smile] https://u.jd.com/ukDode','2020-04-01 00:00:00','2020-04-30 00:00:00',1,'2020-04-13 14:33:44');
CREATE TABLE `jd_apply_withdraw_order` (
  `id` bigint(20) NOT NULL,
  `applier_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '申请人用户id',
  `amount` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '申请提现金额',
  `wechat_id` varchar(255) NOT NULL DEFAULT '' COMMENT '收款人微信号',
  `payee_name` varchar(255) NOT NULL DEFAULT '' COMMENT '收款人姓名',
  `payee_card_id` varchar(255) NOT NULL DEFAULT '' COMMENT '收款人身份证号',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '提现单状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_apply_withdraw_order_operate_record` (
  `id` bigint(20) NOT NULL,
  `withdraw_order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '提现申请单id',
  `operator_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '操作人id',
  `operator_name` varchar(255) NOT NULL DEFAULT '' COMMENT '操作人名称',
  `operate_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `remark` varchar(1014) NOT NULL DEFAULT '' COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_banner` (
  `id` bigint(20) NOT NULL,
  `location` varchar(2048) NOT NULL DEFAULT '' COMMENT 'banner跳转页面',
  `cover_url` varchar(255) NOT NULL DEFAULT '' COMMENT 'banner封面图',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态（1: 自动，2：显示，3：未显示）',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '标题',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='banner表';

insert into `jd_banner`(`id`,`location`,`cover_url`,`end_time`,`start_time`,`status`,`title`) values
('1242798208464195585','/page_package/activity/activity?id=1242795127613689858','https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/3qSSRdvGAxvwARTmak2oEprLSR7J_logo.jpg','2020-03-31 00:00:00','2020-03-25 00:00:00',1,'🔥清洁纸品超级品类日'),
('1249587684121251841','/page_package/activity/activity?id=1249586550744813569','https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/2srJNTsM1fh2Epnfowoztc1Cuvix_logo.png','2020-04-30 00:00:00','2020-04-01 00:00:00',1,'🔥初春潮履 焕新出彩');
CREATE TABLE `jd_feedback` (
  `id` bigint(20) NOT NULL,
  `submitter_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '提交人',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '選項的值',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '反馈状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_member_commission` (
  `id` bigint(20) NOT NULL COMMENT '记录id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `identify` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户等级',
  `level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '记录深度',
  `batch_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '批次Id',
  `sku_id` bigint(20) NOT NULL COMMENT '商品ID',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '京东订单id',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `order_time` datetime NOT NULL COMMENT '下单时间(时间戳，毫秒)',
  `estimate_rebate_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '预估返利（卖货）',
  `actual_rebate_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '实际返利（卖货）',
  `estimate_award_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '预估奖励',
  `actual_award_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '实际奖励',
  `estimate_commission_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '预估佣金',
  `actual_commission_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '实际佣金',
  `sku_name` varchar(1024) NOT NULL DEFAULT '' COMMENT '商品名称',
  `valid_code` tinyint(4) NOT NULL DEFAULT '-1' COMMENT 'sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
  `remark` varchar(256) NOT NULL DEFAULT '' COMMENT '佣金备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  KEY `index_user_id` (`user_id`),
  KEY `index_batch_id` (`batch_id`),
  KEY `index_sku_id` (`sku_id`),
  KEY `index_order_id` (`order_id`),
  KEY `order_finish_time` (`finish_time`),
  KEY `index_order_time` (`order_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_member_commission`(`id`,`user_id`,`identify`,`level`,`batch_id`,`sku_id`,`order_id`,`finish_time`,`order_time`,`estimate_rebate_fee`,`actual_rebate_fee`,`estimate_award_fee`,`actual_award_fee`,`estimate_commission_fee`,`actual_commission_fee`,`sku_name`,`valid_code`,`remark`,`create_time`) values
('1249651547512508418','1249647976792461313',2,0,'1249651547508314114','67324890451','117544770336',null,'2020-04-13 18:50:01',0.7200,0.0000,0.0000,0.0000,0.0000,0.0000,'小狗熊手工小麻花海苔网红小吃小孩零食歪一整箱咪 小麻花x6+小锅巴x4+山药片x2',15,'自购返利: 10.0','2020-04-13 18:52:00'),
('1249651547516702721','1249647976792461313',2,0,'1249651547508314114','67324890451','117544770336',null,'2020-04-13 18:50:01',0.0000,0.0000,0.0000,0.0000,3.6300,0.0000,'小狗熊手工小麻花海苔网红小吃小孩零食歪一整箱咪 小麻花x6+小锅巴x4+山药片x2',15,'推广人获得佣金的：50.0%','2020-04-13 18:52:00'),
('1249651547529285634','1249647766947237890',2,1,'1249651547525091330','67324890451','117544770336',null,'2020-04-13 18:50:01',0.0000,0.0000,0.7200,0.0000,0.0000,0.0000,'小狗熊手工小麻花海苔网红小吃小孩零食歪一整箱咪 小麻花x6+小锅巴x4+山药片x2',15,'直属超级会员获得奖励：20%','2020-04-13 18:52:00'),
('1249651547533479939','1249647377699049474',3,2,'1249651547533479938','67324890451','117544770336',null,'2020-04-13 18:50:01',0.0000,0.0000,0.7200,0.0000,0.0000,0.0000,'小狗熊手工小麻花海苔网红小吃小孩零食歪一整箱咪 小麻花x6+小锅巴x4+山药片x2',15,'非直属导师获取奖励：20%','2020-04-13 18:52:00'),
('1249651547546062850','1249646645646200834',3,4,'1249651547541868545','67324890451','117544770336',null,'2020-04-13 18:50:01',0.0000,0.0000,0.3600,0.0000,0.0000,0.0000,'小狗熊手工小麻花海苔网红小吃小孩零食歪一整箱咪 小麻花x6+小锅巴x4+山药片x2',15,'导师的直属导师获得该导师的 50% 奖励收益','2020-04-13 18:52:00'),
('1249658845240758274','1249647179123920897',2,0,'1249658845236563970','20175593221','112300888440',null,'2020-04-13 19:19:27',1.0700,0.0000,0.0000,0.0000,0.0000,0.0000,'可心柔（COROU） 可心柔V9保湿抽纸便携式外出3层40抽30包宝宝婴儿纸巾柔纸巾整箱量贩',15,'自购返利: 10.0','2020-04-13 19:21:00'),
('1249658845244952578','1249647179123920897',2,0,'1249658845236563970','20175593221','112300888440',null,'2020-04-13 19:19:27',0.0000,0.0000,0.0000,0.0000,5.3900,0.0000,'可心柔（COROU） 可心柔V9保湿抽纸便携式外出3层40抽30包宝宝婴儿纸巾柔纸巾整箱量贩',15,'推广人获得佣金的：50.0%','2020-04-13 19:21:00'),
('1249658845249146883','1249646645646200834',3,1,'1249658845249146882','20175593221','112300888440',null,'2020-04-13 19:19:27',0.0000,0.0000,1.6100,0.0000,0.0000,0.0000,'可心柔（COROU） 可心柔V9保湿抽纸便携式外出3层40抽30包宝宝婴儿纸巾柔纸巾整箱量贩',15,'直属导师获取奖励：30%','2020-04-13 19:21:00'),
('1249658845257535490','1249646154606448642',3,2,'1249658845257535489','20175593221','112300888440',null,'2020-04-13 19:19:27',0.0000,0.0000,0.8000,0.0000,0.0000,0.0000,'可心柔（COROU） 可心柔V9保湿抽纸便携式外出3层40抽30包宝宝婴儿纸巾柔纸巾整箱量贩',15,'导师的直属导师获得该导师的 50% 奖励收益','2020-04-13 19:21:00'),
('1249659851890495491','1249646645646200834',3,0,'1249659851890495490','67813342286','117548817387',null,'2020-04-13 19:23:14',0.5300,0.0000,0.0000,0.0000,0.0000,0.0000,'闪电卫士一次性防护口罩 无纺布带熔喷层 全塑鼻梁条 蓝色三层轻薄透气学生成人男女通用 经典款【20片装】',15,'自购返利: 10.0','2020-04-13 19:25:00'),
('1249659851894689793','1249646645646200834',3,0,'1249659851890495490','67813342286','117548817387',null,'2020-04-13 19:23:14',0.0000,0.0000,0.0000,0.0000,4.3000,0.0000,'闪电卫士一次性防护口罩 无纺布带熔喷层 全塑鼻梁条 蓝色三层轻薄透气学生成人男女通用 经典款【20片装】',15,'推广人获得佣金的：80.0%','2020-04-13 19:25:00'),
('1249659851903078402','1249646154606448642',3,1,'1249659851903078401','67813342286','117548817387',null,'2020-04-13 19:23:14',0.0000,0.0000,0.2500,0.0000,0.0000,0.0000,'闪电卫士一次性防护口罩 无纺布带熔喷层 全塑鼻梁条 蓝色三层轻薄透气学生成人男女通用 经典款【20片装】',15,'导师的直属导师奖励 6%','2020-04-13 19:25:00'),
('1249660858817056770','1249647766947237890',2,0,'1249660858817056769','63733255505','117545220705',null,'2020-04-13 18:57:12',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰 树花晓秀 蜂胶护齿牙膏4支装 天然蜂胶 清新口气 舒敏修护 口腔清洁护理 牙龈修护 480g 蜂胶护齿牙膏 4支装',3,'自购返利: 10.0','2020-04-13 19:29:00'),
('1249660858825445378','1249647766947237890',2,0,'1249660858817056769','63733255505','117545220705',null,'2020-04-13 18:57:12',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰 树花晓秀 蜂胶护齿牙膏4支装 天然蜂胶 清新口气 舒敏修护 口腔清洁护理 牙龈修护 480g 蜂胶护齿牙膏 4支装',3,'推广人获得佣金的：50.0%','2020-04-13 19:29:00'),
('1249660858829639683','1249647377699049474',3,1,'1249660858829639682','63733255505','117545220705',null,'2020-04-13 18:57:12',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰 树花晓秀 蜂胶护齿牙膏4支装 天然蜂胶 清新口气 舒敏修护 口腔清洁护理 牙龈修护 480g 蜂胶护齿牙膏 4支装',3,'直属导师获取奖励：30%','2020-04-13 19:29:00'),
('1249660858842222594','1249646645646200834',3,3,'1249660858838028289','63733255505','117545220705',null,'2020-04-13 18:57:12',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰 树花晓秀 蜂胶护齿牙膏4支装 天然蜂胶 清新口气 舒敏修护 口腔清洁护理 牙龈修护 480g 蜂胶护齿牙膏 4支装',3,'导师的直属导师获得该导师的 50% 奖励收益','2020-04-13 19:29:00'),
('1249664381755592706','1249647377699049474',3,0,'1249664381751398402','67156343434','117545233538',null,'2020-04-13 19:11:01',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰蜂胶祛幽(HP)牙膏4支装 天然蜂胶 远离幽门螺旋杆菌 洁牙固齿 清新口气 口腔清洁 祛除异味 蜂胶祛幽牙膏 4支装',3,'自购返利: 10.0','2020-04-13 19:43:00'),
('1249664381755592707','1249647377699049474',3,0,'1249664381751398402','67156343434','117545233538',null,'2020-04-13 19:11:01',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰蜂胶祛幽(HP)牙膏4支装 天然蜂胶 远离幽门螺旋杆菌 洁牙固齿 清新口气 口腔清洁 祛除异味 蜂胶祛幽牙膏 4支装',3,'推广人获得佣金的：80.0%','2020-04-13 19:43:00'),
('1249664381763981313','1249646645646200834',3,2,'1249664381759787010','67156343434','117545233538',null,'2020-04-13 19:11:01',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰蜂胶祛幽(HP)牙膏4支装 天然蜂胶 远离幽门螺旋杆菌 洁牙固齿 清新口气 口腔清洁 祛除异味 蜂胶祛幽牙膏 4支装',3,'导师的直属导师奖励 6%','2020-04-13 19:43:00'),
('1249664381768175619','1249646154606448642',3,3,'1249664381768175618','67156343434','117545233538',null,'2020-04-13 19:11:01',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'合和泰蜂胶祛幽(HP)牙膏4支装 天然蜂胶 远离幽门螺旋杆菌 洁牙固齿 清新口气 口腔清洁 祛除异味 蜂胶祛幽牙膏 4支装',3,'导师的直属导师获得该导师的 50% 奖励收益','2020-04-13 19:43:00'),
('1249839032611901443','1249660183160819714',1,0,'1249839032611901442','10146821114','112296467773',null,'2020-04-13 19:30:57',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'瑞典Fa-newborn春秋童装儿童上衣外套男童女童风衣 品红 110cm',3,'自购返利: 10.0','2020-04-14 07:17:00'),
('1249839032620290049','1249647976792461313',2,1,'1249839032616095745','10146821114','112296467773',null,'2020-04-13 19:30:57',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'瑞典Fa-newborn春秋童装儿童上衣外套男童女童风衣 品红 110cm',3,'推广人获得佣金的：50.0%','2020-04-14 07:17:00'),
('1249839032624484355','1249647766947237890',2,2,'1249839032624484354','10146821114','112296467773',null,'2020-04-13 19:30:57',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'瑞典Fa-newborn春秋童装儿童上衣外套男童女童风衣 品红 110cm',3,'直属超级会员获得奖励：20%','2020-04-14 07:17:00'),
('1249839032632872962','1249647377699049474',3,3,'1249839032628678657','10146821114','112296467773',null,'2020-04-13 19:30:57',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'瑞典Fa-newborn春秋童装儿童上衣外套男童女童风衣 品红 110cm',3,'非直属导师获取奖励：20%','2020-04-14 07:17:00'),
('1249839032637067267','1249646645646200834',3,5,'1249839032637067266','10146821114','112296467773',null,'2020-04-13 19:30:57',0.0000,0.0000,0.0000,0.0000,0.0000,0.0000,'瑞典Fa-newborn春秋童装儿童上衣外套男童女童风衣 品红 110cm',3,'导师的直属导师获得该导师的 50% 奖励收益','2020-04-14 07:17:00'),
('1249874767977123842','1249646645646200834',3,0,'1249874767977123841','54412358961','117607354307',null,'2020-04-14 09:37:04',11.9000,0.0000,0.0000,0.0000,0.0000,0.0000,'形象美21天精华极光晚安精华液安瓶精华2ml*21包 补水保湿 滋润紧致 温和养护肌肤 2ml*21袋',15,'自购返利: 10.0','2020-04-14 09:39:00'),
('1249874767981318146','1249646645646200834',3,0,'1249874767977123841','54412358961','117607354307',null,'2020-04-14 09:37:04',0.0000,0.0000,0.0000,0.0000,95.2500,0.0000,'形象美21天精华极光晚安精华液安瓶精华2ml*21包 补水保湿 滋润紧致 温和养护肌肤 2ml*21袋',15,'推广人获得佣金的：80.0%','2020-04-14 09:39:00'),
('1249874767989706753','1249646154606448642',3,1,'1249874767985512450','54412358961','117607354307',null,'2020-04-14 09:37:04',0.0000,0.0000,5.7100,0.0000,0.0000,0.0000,'形象美21天精华极光晚安精华液安瓶精华2ml*21包 补水保湿 滋润紧致 温和养护肌肤 2ml*21袋',15,'导师的直属导师奖励 6%','2020-04-14 09:39:00');
CREATE TABLE `jd_member_commission_per_user_for_30_day` (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `estimate_rebate_fee` decimal(31,4) DEFAULT NULL,
  `actual_rebate_fee` decimal(31,4) DEFAULT NULL,
  `estimate_award_fee` decimal(31,4) DEFAULT NULL,
  `actual_award_fee` decimal(31,4) DEFAULT NULL,
  `estimate_commission_fee` decimal(31,4) DEFAULT NULL,
  `actual_commission_fee` decimal(31,4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_message` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '消息标题',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '消息内容',
  `start_time` datetime NOT NULL COMMENT '消息开始时间',
  `end_time` datetime NOT NULL COMMENT '消息结束时间',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息类型',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '消息状态（1: 自动，2：显示，3：未显示）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻';

CREATE TABLE `jd_news` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '活动标题',
  `location` varchar(2048) NOT NULL DEFAULT '' COMMENT '跳转页面',
  `start_time` datetime NOT NULL COMMENT '活动开始时间',
  `end_time` datetime NOT NULL COMMENT '活动结束时间',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '快报状态（1: 自动，2：显示，3：未显示）',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='新闻';

insert into `jd_news`(`id`,`title`,`location`,`start_time`,`end_time`,`status`,`create_time`) values
('1242795456094801922','🔥清洁纸品超级品类日','/page_package/activity/activity?id=1242795127613689858','2020-03-25 00:00:00','2020-03-31 00:00:00',1,'2020-03-25 20:48:21'),
('1242796305235841025','🔥清洁纸品超级品类日','/page_package/activity/activity?id=1242795127613689858','2020-03-25 00:00:00','2020-03-31 00:00:00',1,'2020-03-25 20:51:43'),
('1249587919287488514','🔥初春潮履 焕新出彩','/page_package/activity/activity?id=1249586550744813569','2020-04-01 00:00:00','2020-04-30 00:00:00',1,'2020-04-13 14:39:10');
CREATE TABLE `jd_option` (
  `id` bigint(20) NOT NULL,
  `option_name` varchar(128) NOT NULL COMMENT '選項key',
  `option_value` varchar(255) NOT NULL COMMENT '選項的值',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `option_name` (`option_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_option`(`id`,`option_name`,`option_value`,`create_time`) values
('1242792998689837058','sys_default_user_name','系统用户','2020-03-25 20:38:35'),
('1242792998748557314','opt_sys_next_group_id','10007','2020-03-25 20:38:35'),
('1242792998756945922','sys_withdraw_start_day','27','2020-03-25 20:38:35'),
('1242792998765334529','sys_register_user_identify','1','2020-03-25 20:38:35'),
('1242792998765334530','sys_withdraw_end_day','30','2020-03-25 20:38:35'),
('1242792998773723138','app_force_login','false','2020-03-25 20:38:35'),
('1242792998773723139','sys_current_sync_start_time','20200414094700','2020-03-25 20:38:35'),
('1242792998782111745','sys_order_sync_retry_interval','2000','2020-03-25 20:38:35'),
('1242792998786306049','sys_order_sync_start_time','20200327161300','2020-03-25 20:38:35'),
('1242792998790500353','sys_default_user_identify','1','2020-03-25 20:38:35'),
('1242792998790500354','sys_order_sync_retry_count','3','2020-03-25 20:38:35'),
('1242792998794694657','sys_settlement_start_day','0 0 0 23 * ?','2020-03-25 20:38:35'),
('1242792998798888961','sys_order_sync_delay','2','2020-03-25 20:38:35'),
('1243451545673011201','app_default_inviter_code','PBP1TP','2020-03-27 16:15:25');
CREATE TABLE `jd_order_detail` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `order_id` bigint(20) NOT NULL COMMENT '订单ID',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `order_emt` tinyint(4) NOT NULL DEFAULT '1' COMMENT '下单设备(1:PC,2:无线)',
  `order_time` datetime NOT NULL COMMENT '下单时间(时间戳，毫秒)',
  `owner_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推广人id',
  `buyer_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '购买人id',
  `parent_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '父单的订单ID，仅当发生订单拆分时返回， 0：未拆分，有值则表示此订单为子订单',
  `pay_month` varchar(19) NOT NULL DEFAULT '0' COMMENT '订单维度预估结算时间,不建议使用，可以用订单行sku维度paymonth字段参考，（格式：yyyyMMdd），0：未结算，订单''预估结算时间''仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。',
  `plus` tinyint(4) NOT NULL DEFAULT '0' COMMENT '下单用户是否为PLUS会员 0：否，1：是',
  `pop_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '订单维度商家ID，不建议使用，可以用订单行sku维度popId参考',
  `union_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推客的联盟ID',
  `ext1` varchar(255) NOT NULL DEFAULT '' COMMENT '订单维度的推客生成推广链接时传入的扩展字段，不建议使用，可以用订单行sku维度ext1参考,（需要联系运营开放白名单才能拿到数据）',
  `valid_code` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '订单维度的有效码，不建议使用，可以用订单行sku维度validCode参考,（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '订单更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '订单创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单记录';

insert into `jd_order_detail`(`id`,`order_id`,`finish_time`,`order_emt`,`order_time`,`owner_id`,`buyer_id`,`parent_id`,`pay_month`,`plus`,`pop_id`,`union_id`,`ext1`,`valid_code`,`update_time`,`create_time`) values
('1249651547374096386','117544770336',null,2,'2020-04-13 18:50:01','1249647976792461313','1249647976792461313',0,'0',0,625318,1002274953,'',15,'2020-04-13 18:52:00','2020-04-13 18:52:00'),
('1249653308906278913','117545220705',null,2,'2020-04-13 18:57:12','1249647766947237890','1249647766947237890',0,'0',0,10101608,1002274953,'',3,'2020-04-13 18:59:00','2020-04-13 18:59:00'),
('1249656831983226882','117545233538',null,2,'2020-04-13 19:11:01','1249647377699049474','1249647377699049474',0,'0',0,10101608,1002274953,'',3,'2020-04-13 19:13:00','2020-04-13 19:13:00'),
('1249658845198815233','112300888440',null,2,'2020-04-13 19:19:27','1249647179123920897','1249647179123920897',0,'0',0,154813,1002274953,'',15,'2020-04-13 19:21:00','2020-04-13 19:21:00'),
('1249659851844358145','117548817387',null,2,'2020-04-13 19:23:14','1249646645646200834','1249646645646200834',0,'0',1,10340545,1002274953,'',15,'2020-04-13 19:25:00','2020-04-13 19:25:00'),
('1249661613460426754','112296467773',null,2,'2020-04-13 19:30:57','1249647976792461313','1249660183160819714',0,'0',0,27236,1002274953,'',3,'2020-04-13 19:32:00','2020-04-13 19:32:00'),
('1249874767943569410','117607354307',null,2,'2020-04-14 09:37:04','1249646645646200834','1249646645646200834',0,'0',1,166144,1002274953,'',15,'2020-04-14 09:39:00','2020-04-14 09:39:00');
CREATE TABLE `jd_order_sku` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `sku_id` bigint(20) NOT NULL COMMENT '商品ID',
  `order_id` bigint(20) NOT NULL COMMENT '京东订单id',
  `owner_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推广人id',
  `buyer_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '购买人id',
  `actual_cos_price` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '实际计算佣金的金额。订单完成后，会将误扣除的运费券金额更正。如订单完成后发生退款，此金额会更新。',
  `actual_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '	推客获得的实际佣金（实际计佣金额*佣金比例*最终比例）。如订单完成后发生退款，此金额会更新。',
  `commission_rate` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '佣金比例',
  `estimate_cos_price` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '预估计佣金额，即用户下单的金额(已扣除优惠券、白条、支付优惠、进口税，未扣除红包和京豆)，有时会误扣除运费券金额，完成结算时会在实际计佣金额中更正。如订单完成前发生退款，此金额也会更新。',
  `estimate_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '推客的预估佣金（预估计佣金额*佣金比例*最终比例），如订单完成前发生退款，此金额也会更新。',
  `final_rate` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '最终比例（分成比例+补贴比例）',
  `cid1` bigint(20) NOT NULL DEFAULT '0' COMMENT '一级类目ID',
  `frozen_sku_nnum` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品售后中数量',
  `pid` varchar(128) DEFAULT '' COMMENT '联盟子站长身份标识，格式：子站长ID_子站长网站ID_子站长推广位ID',
  `position_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '推广位ID,0代表无推广位',
  `site_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '网站ID，0：无网站',
  `sku_name` varchar(1024) NOT NULL DEFAULT '' COMMENT '商品名称',
  `sku_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品数量',
  `sku_image` varchar(1024) NOT NULL DEFAULT '' COMMENT '商品主图',
  `sku_return_num` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品已退货数量',
  `sub_side_rate` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '分成比例',
  `subsidy_rate` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '补贴比例',
  `cid3` bigint(20) NOT NULL DEFAULT '0' COMMENT '三级类目ID',
  `union_alias` varchar(255) NOT NULL DEFAULT '' COMMENT '	PID所属母账号平台名称（原第三方服务商来源）',
  `union_tag` varchar(255) NOT NULL DEFAULT '' COMMENT '联盟标签数据（整型的二进制字符串，目前返回16位：0000000000000001。数据从右向左进行，每一位为1表示符合联盟的标签特征，第1位：红包，第2位：组合推广，第3位：拼购，第5位：有效首次购（0000000000011XXX表示有效首购，最终奖励活动结算金额会结合订单状态判断，以联盟后台对应活动效果数据报表https://union.jd.com/active为准）,第8位：复购订单，第9位：礼金，第10位：联盟礼金，第11位：推客礼金。例如：0000000000000001:红包订单，0000000000000010:组合推广订单，0000000000000100:拼购订单，0000000000011000:有效首购，0000000000000111：红包+组合推广+拼购等）',
  `union_traffic_group` tinyint(4) NOT NULL DEFAULT '0' COMMENT '渠道组 1：1号店，其他：京东',
  `valid_code` tinyint(4) NOT NULL DEFAULT '-1' COMMENT 'sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
  `sub_unionId` varchar(255) NOT NULL DEFAULT '' COMMENT '子联盟ID(需要联系运营开放白名单才能拿到数据)',
  `trace_type` tinyint(4) NOT NULL DEFAULT '2' COMMENT '2：同店；3：跨店',
  `pay_month` int(11) NOT NULL DEFAULT '0' COMMENT '订单行维度预估结算时间（格式：yyyyMMdd） ，0：未结算。订单''预估结算时间''仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。',
  `pop_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '	商家ID。''订单行维度''',
  `ext1` varchar(255) NOT NULL DEFAULT '' COMMENT '	推客生成推广链接时传入的扩展字段（需要联系运营开放白名单才能拿到数据）。''订单行维度''',
  `cp_act_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '招商团活动id，正整数，为0时表示无活动',
  `union_role` tinyint(4) NOT NULL DEFAULT '1' COMMENT '站长角色，1： 推客、 2： 团长',
  `gift_coupon_key` varchar(255) NOT NULL DEFAULT '' COMMENT '礼金批次ID',
  `gift_coupon_ocs_amount` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '礼金分摊金额',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'sku更新时间',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'sdu创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_order_id_and_sku_id` (`order_id`,`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='佣金商品记录';

insert into `jd_order_sku`(`id`,`sku_id`,`order_id`,`owner_id`,`buyer_id`,`actual_cos_price`,`actual_fee`,`commission_rate`,`estimate_cos_price`,`estimate_fee`,`final_rate`,`cid1`,`frozen_sku_nnum`,`pid`,`position_id`,`site_id`,`sku_name`,`sku_num`,`sku_image`,`sku_return_num`,`sub_side_rate`,`subsidy_rate`,`cid3`,`union_alias`,`union_tag`,`union_traffic_group`,`valid_code`,`sub_unionId`,`trace_type`,`pay_month`,`pop_id`,`ext1`,`cp_act_id`,`union_role`,`gift_coupon_key`,`gift_coupon_ocs_amount`,`update_time`,`create_time`) values
('1249651547399262209','67324890451','117544770336','1249647976792461313','1249647976792461313',0.0000,0.0000,30.0000,26.9000,7.2600,0.0000,1320,0,'',0,142291,'小狗熊手工小麻花海苔网红小吃小孩零食歪一整箱咪 小麻花x6+小锅巴x4+山药片x2',1,'https://img14.360buyimg.com/ads/jfs/t1/96571/8/7516/187936/5dfc7308Ec21bb900/1e9f93ea11142746.jpg',0,0.0000,0.0000,1590,'','0000000000000000',4,15,'',2,0,625318,'',0,1,'',0.0000,'2020-04-13 18:52:00','2020-04-13 18:52:00'),
('1249653308935639042','63733255505','117545220705','1249647766947237890','1249647766947237890',0.0000,0.0000,71.0000,119.6000,0.0000,0.0000,16750,0,'',0,142291,'合和泰 树花晓秀 蜂胶护齿牙膏4支装 天然蜂胶 清新口气 舒敏修护 口腔清洁护理 牙龈修护 480g 蜂胶护齿牙膏 4支装',1,'https://img14.360buyimg.com/ads/jfs/t1/114949/28/413/113995/5e8c56deE89fb838d/e3757bfa4852c97c.jpg',0,0.0000,0.0000,16806,'','0000000000000000',4,3,'',2,0,10101608,'',0,1,'',0.0000,'2020-04-13 18:59:00','2020-04-13 18:59:00'),
('1249656831995809794','67156343434','117545233538','1249647377699049474','1249647377699049474',0.0000,0.0000,71.0000,159.6000,0.0000,0.0000,16750,0,'',0,142291,'合和泰蜂胶祛幽(HP)牙膏4支装 天然蜂胶 远离幽门螺旋杆菌 洁牙固齿 清新口气 口腔清洁 祛除异味 蜂胶祛幽牙膏 4支装',1,'https://img14.360buyimg.com/ads/jfs/t1/113959/33/99/149977/5e871399Eeb724700/9d01f6ee6100190c.jpg',0,0.0000,0.0000,16806,'','0000000000000000',4,3,'',2,0,10101608,'',0,1,'',0.0000,'2020-04-13 19:13:00','2020-04-13 19:13:00'),
('1249658845207203842','20175593221','112300888440','1249647179123920897','1249647179123920897',0.0000,0.0000,20.0000,59.9000,10.7800,0.0000,15901,0,'',0,142291,'可心柔（COROU） 可心柔V9保湿抽纸便携式外出3层40抽30包宝宝婴儿纸巾柔纸巾整箱量贩',1,'https://img14.360buyimg.com/ads/jfs/t1/3160/29/10391/273015/5bcad3ffE008df512/188a1af611e68eb4.jpg',0,0.0000,0.0000,15908,'','0000000000000000',4,15,'',2,0,154813,'',0,1,'',0.0000,'2020-04-13 19:21:00','2020-04-13 19:21:00'),
('1249659851852746754','67813342286','117548817387','1249646645646200834','1249646645646200834',0.0000,0.0000,20.0000,29.9000,5.3800,0.0000,9192,0,'',0,142291,'闪电卫士一次性防护口罩 无纺布带熔喷层 全塑鼻梁条 蓝色三层轻薄透气学生成人男女通用 经典款【20片装】',1,'https://img14.360buyimg.com/ads/jfs/t1/97798/27/17537/138655/5e8b2b24E01a7e735/c448ca86d413df4d.png',0,0.0000,0.0000,1517,'','0000000000000000',4,15,'',2,0,10340545,'',0,1,'',0.0000,'2020-04-13 19:25:00','2020-04-13 19:25:00'),
('1249661613473009666','10146821114','112296467773','1249647976792461313','1249660183160819714',0.0000,0.0000,30.0000,79.0000,0.0000,0.0000,1319,0,'',0,142291,'瑞典Fa-newborn春秋童装儿童上衣外套男童女童风衣 品红 110cm',1,'https://img14.360buyimg.com/ads/jfs/t1945/310/2304052158/266487/9e24311b/56d1b7b1N9d7c6504.jpg',0,0.0000,0.0000,14936,'','0000000000011000',4,3,'',2,0,27236,'',0,1,'',0.0000,'2020-04-13 19:32:00','2020-04-13 19:32:00'),
('1249874767951958017','54412358961','117607354307','1249646645646200834','1249646645646200834',0.0000,0.0000,70.0000,189.0000,119.0700,0.0000,1316,0,'',0,142291,'形象美21天精华极光晚安精华液安瓶精华2ml*21包 补水保湿 滋润紧致 温和养护肌肤 2ml*21袋',1,'https://img14.360buyimg.com/ads/jfs/t1/55511/34/10177/128239/5d760ab2E3b96e20a/7397ed310ebf39f9.jpg',0,0.0000,0.0000,13546,'','0000000000000000',4,15,'',2,0,166144,'',0,1,'',0.0000,'2020-04-14 09:39:00','2020-04-14 09:39:00');
CREATE TABLE `jd_order_sync_record` (
  `id` bigint(20) NOT NULL COMMENT '记录id',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '京东订单id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否成功（0成功，1失败）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_order_sync_record`(`id`,`order_id`,`status`) values
('1249651547567034370','117544770336',0),
('1249653309074051074','117545220705',0),
('1249656832083890178','117545233538',0),
('1249658845274312705','112300888440',0),
('1249659851919855617','117548817387',0),
('1249661613582061570','112296467773',0),
('1249874768002289666','117607354307',0);
CREATE TABLE `jd_question` (
  `id` bigint(20) NOT NULL,
  `cid` bigint(20) NOT NULL COMMENT '问题分类ID',
  `title` varchar(64) NOT NULL COMMENT '问题标题',
  `answer` varchar(1024) NOT NULL COMMENT '问题回答',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `index_category` (`cid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_question`(`id`,`cid`,`title`,`answer`,`create_time`) values
('1242742075793158145','1242741770884034562','优惠券只能一个号领一次吗？','优惠券数量有限，一般情况一个账号仅能领用一次。','2020-03-25 17:16:14'),
('1242742267862921218','1242741770884034562','部分商品分货较慢是什么原因？','部分商家的产品是第三方POP商家，使用的是第三言主物流，一般送货速度会较慢。另外，在大促活动期间，商家积压的订单比较多，可能发货会较慢。

如果您担心送货速度的问题，可以联系商家或者京东客服，东京客服热线：950618','2020-03-25 17:17:00'),
('1242742614790582273','1242741792610529282','如何申请售后服务？','申请售后常用的2个流程 ：

（1）可以直接到微信->我->支付->东京购物->客户服务中申请售后服务。

（2）也可以在京东APP->我的->客户服务中申请售后。','2020-03-25 17:18:23'),
('1242742683598139394','1242741792610529282','对收到的产品不满意 ？','按照京东7天元理由退货政策，您收到货发现有问题可以与商家协商退货和退款，如协商不成您可以拨打东京客服电话：950618，按2进入按键导航，根据语言提示按7，再按1，然后输入收货人手机号，确认手机号按1，然后再输入订单后四位，等待人工客服接起。','2020-03-25 17:18:39'),
('1242742750933495809','1242741792610529282','为什么通过平台下单的商品售后问题需要联系京东处理？','平台只提供商品链接，分享商品优惠信息，并不提供下单购买等服务，您实际下单仍是在京东内完成，商品也是由京东提供，所以如果产生售后问题，需要您联系京东商场协商处理。','2020-03-25 17:18:55'),
('1242742960657084418','1242741824084586498','如何查看该订单的物流信息？','查看订单的3个途经：

（1）物流信息可以在微信->发现->购物->个人中心查看，如微信的发现页无此选项，可在微信->我->设置->通用->发现页管理中将其打开。

（2）微信->我->支付->京东购物->我的页面查看

通过东京APP订单界面查看订单：

（3）京东APP->我的页面查看','2020-03-25 17:19:45'),
('1242743068148707330','1242741824084586498','如何知道是谁下的单？','东京注重保护客户隐私，您看不到客户姓名、电话等隐私信息。','2020-03-25 17:20:11'),
('1242743117427585025','1242741824084586498','为什么我的订单会显示“无效-取消”？','如果客户下单付款后又取消了此订单，则订单详情内会显示“无效-取消”标识。','2020-03-25 17:20:22'),
('1242743201657597954','1242741824084586498','订单中显示红色“跨店“标志是什么意思？','关于跨店的解析：

推广有效期内，客户通过你A店铺商品的推广主链接进行东京，后来买了B店铺商品。这个订单佣金是你的【跨店收益】。','2020-03-25 17:20:42'),
('1242743268615467009','1242741824084586498','为什么订单会显示”无效-拆单”','拆单是系统根据该订单内的产品类目及仓库中无师进行拆分，已拆单导致父订单无效，但不会影响佣金收益，已拆单的订单佣金是按照拆单后形成的子订单号计算佣金的。 ','2020-03-25 17:20:58'),
('1242743319614009346','1242741824084586498','订单显示“无效-非推广商品”是什么意思？','根据京东推广规则，“无效-非推广商品”是批该商品商家没有设置佣金 或跨店佣金为0或者Plus会员购买为0的商品，此类订单不会获得推广收益。','2020-03-25 17:21:11'),
('1242743382734090241','1242741824084586498','客户订单在东京已经付款，为什么在平台显示待付款','客户在京东20分钟内付款的订单状态会及时更新到小程序中，如果20分钟后付款的订单状态会在次日更新，可在第二天进行查看，不会影响您的收益。','2020-03-25 17:21:26'),
('1242743781218136065','1242741824084586498','客户购买了商品在京东APP中可以看到订单，但您在平台小程序中看不到推广订单？','（1）平台小程序没有这个商品或活动已结束，商品已经在平台为下架状态，所以小程序中不会显示订单；

（2）在下单前，如自己更改默认型号，选择其他规格也有可能不跟单。

（3）在下单后5-10分钟后，订单才可以同步到小程序订单详情中；

（4）如果您是复制其他群中的商品但同有转链，那么订单是不属于您的，不会显示订单。

（5）如果客户通过您的链接领了券没有购买，而是又搜索或者查看很多其他店铺商品之后下单，或者是通过其他途径（如：京东APP）一瞬间导致 。

（6）此购书商品本身没有佣金，这种大部分之前佣金过期的链接。

（7）京东某些商品普通用户买有佣金，京东Plus会员买没佣且买了不跟单。','2020-03-25 17:23:01'),
('1242743928740196354','1242741853012701185','我的收益有哪几种类型？','推广收益：您的直属会员在小程序自购，或者您分享的商品促成别人购买所得到的收益。

平台奖励：您的超级会员获得推广收益，平台额外给您的奖励（此部分为额外奖励，不从您的超级会员收益里扣除）

返利收益：通过小程序购买没有优惠券的商品，会以返利的方式来实现优惠。','2020-03-25 17:23:36'),
('1242743986168606721','1242741853012701185','什么是“预估收益”及“预估收益”会变少的原因是什么？','根据京东的推广规则：预估收益是根据用户一瞬间时间预估统计的推广效果数据，并非实际结算收益，取消已付款的订单或者退换货后对应的收益会相应的扣除，实际收益以订单结算为准。','2020-03-25 17:23:50'),
('1242744363194593282','1242741853012701185','为什么我的订单收益为0？','（1）执照京东的返佣规则 ，乡村推广账号、企业账号、分期付款用户、以开具增值税运用发票的订单是没有收益的。

【乡村推广人账号说明 】：京东乡村推广员账号是东京的一种推广主账号类型，这种账号本身一瞬间是有特殊优惠政策，所以不产生任何佣金收益 。

【企业账号说明】：企业账号是京东的一种客户账号类型， 这种账号因为与企业公司信息绑定，本身一瞬间是有特殊优惠政策，所以下单不产生任务佣金收益。

【京东Plus会员账号说明】京东Plus账号是东京的一种会员账号类型，这种账号可直接通过京东官方购买，本身下单有特殊优惠政策的，所以下单可以会导致订单收益为0。

【开具了增票的下单账号】已申请了开具专业增值税发票的订单和账号，不是计算收益的。

（2）收益计算以客户实际支付金额结算的，如果客户在结算时使用了京券、余额、京豆和E卡等京东虚拟货币，会导致您 的收益减少或为0.

（3）客户点击您发的活动页或优惠券汇总页，进行购买的商品也会呈现在您的订单列表中，大部分产品都是有收益的，但部分的产品比如电脑的收益为0的。

（4）Plus会员ghuj3的订单，收益会比普通客户低。

（5）京东有极少部分商品本身无返利及佣金。','2020-03-25 17:25:19'),
('1242744421667385346','1242741853012701185','订单显示的收益和选品时看到预估“预估收益”不一样？','您选品时看到的收益金额为预估推广收益，由于客户在一瞬间时可能会使用京券、余额、京豆和E卡等京东虚拟货币，导致收益减少，导致订单收益变少的情况发生。','2020-03-25 17:25:33'),
('1242744555805421569','1242741853012701185','每月提现金额和”预估收益“不一致？','所有的收益都是按照系统设计好的规则进行，不存在 恶意扣除，可提现收益跟预估收益不一致原因有以下两点：

（1）客户下单后早申请退货，会导致相应收益减少。

（2）可以提现的部分是订单完成时间 在上个月的订单佣金，每个月结算一次。上个月的推广但订单完成时间在本月的，需要下个月提现。

订单完成时间 就是用户确认收货的时间 ，如果用户不手动点确认收货，一般会在15天，系统会自动变为订单完成，在订单完成 还有一段退换货的时间 ，而且只有订单完成时间 在上个月的，当前这个月可以提现 ，这个是东京的规则，主要原因是东京的退换货周期较长，大家看的预估佣金收益，是按下单时间 算的，所以叫预估，实际提现金额要看这些订单什么时间 完成的。','2020-03-25 17:26:05'),
('1242744635073572866','1242741853012701185','
同一个商品链接，同种商品计佣金额，佣金 怎么算的不同？','（1）同店同种商品也会因为商品规格 不同有不同的商品编码，故设置的佣金不同，可能商家设置的佣金也不同。

（2）不同的时间段下单，在商家预先设置的限定数量优惠券被领取完后，商家重新补券时可能调整了推广佣金。商品商家都是限时推广的，佣金会根据推广效果动态调整。','2020-03-25 17:26:24'),
('1242745099500466178','1242741895224176642','为什么会有违规订单？','平台内推广的商品，有一些是商家通过打造引流爆款产品亏本促销，从而提升商品或店铺在东京内的搜索排名，促销完成后，需要长尾的带动整体销售利润。

所以：

（1）平台内推广的商品，活动期间只能以优惠价格购买一次。

（2）平台内推广主的商品已经是商家让利促销，所以平台内无团购机制。

因推广商品优惠较大，导致灰色产业通过大量代下单等方式 ，进行商品集中采购再转向其他渠道做二次销售，这种行为严重侵害了京东和商家的利益。所以京东风控部门从技术页将一些疑似风险订单和账号定义为”违规订单“。','2020-03-25 17:28:15'),
('1242745257789304834','1242741895224176642','我自己购买的商品被判定为”违规订单“，如何申斥？','如果对订单判断有疑问，我们可以帮您进行早逝处理，您需要提供以下三张照片，通过客服转交进行申斥。

（1）东京上订单详情截图（截图中需要饮食订单号、物品信息、商家名称）

（2）京东上该校是物流签收页面截图（需要包含物流信息、收货人姓名、电话、收货地址）

（3）订单到货后手持商品照片（不需要用户出镜）

提供上述截图后，我们会帮您反馈京东进行申诉，申诉时间大约3到5周左右。申诉成功后佣金 会正常发放。','2020-03-25 17:28:53'),
('1242745347664850946','1242741895224176642','如何避免”违规订单“？','（1）请务必不要代下单，如遇首次购物用户耐心引导即可。

（2）违规订单判定确认后，账号会存在风险状态，风险状态一般会在30天后解除，期间可以用家人的东京岣购物避免再用有“违规订单”','2020-03-25 17:29:14'),
('1242745460017672194','1242741930515050497','一人多微信号怎么提现？','可以绑定同一人的身份证，同一个银行卡完成提现。','2020-03-25 17:29:41'),
('1242745508176670722','1242741930515050497','更换了微信头像和昵称，小程序不同步？','小程序的微信昵称和头像微信不同步，后续会提供相应功能更新昵称头像功能。','2020-03-25 17:29:52'),
('1242745630994280450','1242741930515050497','邀请另人加入后，我们之间都有什么关系？','直属注册会员：通过您直接邀请注册的会员。

我的超级会员：通过您的邀请注册的会员升级成为的超级会员。

全部超级会员：“我的超级会员”以及“我的直属会员”自己邀请的超级会员。

全部粉丝：您直接邀请注册的会员、“我的超级会员”以及“全部超级会同”自己抗议一的注册会员','2020-03-25 17:30:22');
CREATE TABLE `jd_question_category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(64) NOT NULL COMMENT '分类名称',
  `icon` varchar(256) NOT NULL COMMENT '小图标',
  `open` tinyint(4) NOT NULL DEFAULT '1',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_question_category`(`id`,`name`,`icon`,`open`,`create_time`) values
('1242741770884034562','商品类','goodsfill',1,'2020-03-25 17:15:01'),
('1242741792610529282','售后类','servicefill',1,'2020-03-25 17:15:07'),
('1242741824084586498','订单类','shopfill',1,'2020-03-25 17:15:14'),
('1242741853012701185','收益类','moneybagfill',1,'2020-03-25 17:15:21'),
('1242741895224176642','违规订单类','warnfill',1,'2020-03-25 17:15:31'),
('1242741930515050497','其他问题','questionfill',1,'2020-03-25 17:15:39');
CREATE TABLE `jd_settlement_calculate_result` (
  `id` bigint(20) NOT NULL COMMENT '记录id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `start_date` date NOT NULL COMMENT '开始时间',
  `end_date` date NOT NULL COMMENT '开始时间',
  `rebate_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '返利',
  `award_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '奖励',
  `commission_fee` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '佣金',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '成员状态(0成功，1失败)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`start_date`,`end_date`),
  KEY `index_user_id_and_start_date_and_end_date` (`user_id`,`start_date`,`end_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_share_poster` (
  `id` bigint(20) NOT NULL,
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '海报名称',
  `icon_url` varchar(512) NOT NULL DEFAULT '' COMMENT '图标Url',
  `image_url` varchar(512) NOT NULL DEFAULT '' COMMENT '分享图',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '海报文字内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分享海报';

CREATE TABLE `jd_super_member_apply` (
  `id` bigint(20) NOT NULL,
  `owner_id` bigint(20) NOT NULL COMMENT '申请人',
  `group_id` int(11) NOT NULL COMMENT '群组id',
  `image_urls` varchar(260) NOT NULL DEFAULT '' COMMENT '申请图',
  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '申请状态',
  `remark` varchar(64) NOT NULL DEFAULT '' COMMENT '备注信息',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '申请时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='超级会员申请表';

CREATE TABLE `jd_team_member` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `leader_id` bigint(20) unsigned NOT NULL COMMENT '领队id',
  `member_id` bigint(20) unsigned NOT NULL COMMENT '成员id',
  `level` int(11) NOT NULL COMMENT '级别',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_unique_leader_id_and_member_id` (`leader_id`,`member_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_team_member`(`id`,`leader_id`,`member_id`,`level`,`create_time`) values
('1249645277048999938','1249645276885422081','1249645276885422081',0,'2020-04-13 18:27:05'),
('1249646154673557505','1249646154606448642','1249646154606448642',0,'2020-04-13 18:30:35'),
('1249646154698723329','1249645276885422081','1249646154606448642',1,'2020-04-13 18:30:35'),
('1249646645688143873','1249646645646200834','1249646645646200834',0,'2020-04-13 18:32:32'),
('1249646645709115394','1249646154606448642','1249646645646200834',1,'2020-04-13 18:32:32'),
('1249646645713309698','1249645276885422081','1249646645646200834',2,'2020-04-13 18:32:32'),
('1249646801640755202','1249646801590423553','1249646801590423553',0,'2020-04-13 18:33:09'),
('1249646801661726722','1249646154606448642','1249646801590423553',1,'2020-04-13 18:33:09'),
('1249646801665921026','1249645276885422081','1249646801590423553',2,'2020-04-13 18:33:09'),
('1249647179123920898','1249647179123920897','1249647179123920897',0,'2020-04-13 18:34:39'),
('1249647179182641153','1249646645646200834','1249647179123920897',1,'2020-04-13 18:34:39'),
('1249647179186835458','1249646154606448642','1249647179123920897',2,'2020-04-13 18:34:39'),
('1249647179186835459','1249645276885422081','1249647179123920897',3,'2020-04-13 18:34:39'),
('1249647377699049475','1249647377699049474','1249647377699049474',0,'2020-04-13 18:35:26'),
('1249647377770352642','1249647179123920897','1249647377699049474',1,'2020-04-13 18:35:26'),
('1249647377774546945','1249646645646200834','1249647377699049474',2,'2020-04-13 18:35:26'),
('1249647377778741250','1249646154606448642','1249647377699049474',3,'2020-04-13 18:35:26'),
('1249647377782935553','1249645276885422081','1249647377699049474',4,'2020-04-13 18:35:26'),
('1249647766993375234','1249647766947237890','1249647766947237890',0,'2020-04-13 18:36:59'),
('1249647767018541058','1249647377699049474','1249647766947237890',1,'2020-04-13 18:36:59'),
('1249647767018541059','1249647179123920897','1249647766947237890',2,'2020-04-13 18:36:59'),
('1249647767018541060','1249646645646200834','1249647766947237890',3,'2020-04-13 18:36:59'),
('1249647767022735361','1249646154606448642','1249647766947237890',4,'2020-04-13 18:36:59'),
('1249647767022735362','1249645276885422081','1249647766947237890',5,'2020-04-13 18:36:59'),
('1249647976838598658','1249647976792461313','1249647976792461313',0,'2020-04-13 18:37:49'),
('1249647976855375874','1249647766947237890','1249647976792461313',1,'2020-04-13 18:37:49'),
('1249647976859570177','1249647377699049474','1249647976792461313',2,'2020-04-13 18:37:49'),
('1249647976863764482','1249647179123920897','1249647976792461313',3,'2020-04-13 18:37:49'),
('1249647976863764483','1249646645646200834','1249647976792461313',4,'2020-04-13 18:37:49'),
('1249647976867958786','1249646154606448642','1249647976792461313',5,'2020-04-13 18:37:49'),
('1249647976867958787','1249645276885422081','1249647976792461313',6,'2020-04-13 18:37:49'),
('1249648482097041410','1249648482063486977','1249648482063486977',0,'2020-04-13 18:39:50'),
('1249648482113818625','1249645276885422081','1249648482063486977',1,'2020-04-13 18:39:50'),
('1249649658767085570','1249649658737725441','1249649658737725441',0,'2020-04-13 18:44:30'),
('1249649658783862786','1249645276885422081','1249649658737725441',1,'2020-04-13 18:44:30'),
('1249660183160819715','1249660183160819714','1249660183160819714',0,'2020-04-13 19:26:19'),
('1249660183206957058','1249647976792461313','1249660183160819714',1,'2020-04-13 19:26:19'),
('1249660183211151361','1249647766947237890','1249660183160819714',2,'2020-04-13 19:26:19'),
('1249660183211151362','1249647377699049474','1249660183160819714',3,'2020-04-13 19:26:19'),
('1249660183211151363','1249647179123920897','1249660183160819714',4,'2020-04-13 19:26:19'),
('1249660183215345666','1249646645646200834','1249660183160819714',5,'2020-04-13 19:26:19'),
('1249660183215345667','1249646154606448642','1249660183160819714',6,'2020-04-13 19:26:19'),
('1249660183215345668','1249645276885422081','1249660183160819714',7,'2020-04-13 19:26:19');
CREATE TABLE `jd_timeline_goods` (
  `id` bigint(20) NOT NULL,
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '商品名称',
  `sku_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '商品id',
  `publisher_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '发布人id',
  `image_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '分享图',
  `content` varchar(1024) NOT NULL DEFAULT '' COMMENT '发圈内容',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_user` (
  `id` bigint(20) NOT NULL,
  `nick_name` varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  `avatar_url` varchar(1024) NOT NULL DEFAULT '' COMMENT '活动分享图',
  `phone` varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  `identify` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户身份（导师，合伙人）',
  `invitation_code` varchar(10) NOT NULL DEFAULT '' COMMENT '邀请码',
  `inviter_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '邀请人用户id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `role_name` varchar(32) NOT NULL DEFAULT 'MEMBER' COMMENT '成员角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_user`(`id`,`nick_name`,`avatar_url`,`phone`,`identify`,`invitation_code`,`inviter_id`,`create_time`,`role_name`) values
('1249645276885422081','系统用户','','',1,'PBP1TP',0,'2020-04-13 18:27:05','MEMBER'),
('1249646154606448642','惊天霸戈','https://wx.qlogo.cn/mmopen/vi_32/LCEZLO6xdqicB6bB2wQHLdTbDnCmicLqu7ibnAs4yib3iaee2VZXZrszm9ZvicyUPhFy3kxPUKxqZWUh0sPFq5nhEeQg/132','18817096723',3,'AS3PSV','1249645276885422081','2020-04-13 18:44:30','ADMIN'),
('1249646645646200834','NULL NULL bug','https://wx.qlogo.cn/mmopen/vi_32/w9Qqaicic2vnrD4xmQ4VU8licdXqQUlEGjOgdNcO4LWiaaic1fZTys9IiaMsqEwtmfrdajlA4jWFq9GKvkLCuRZMBPYg/132','15700701570',3,'0XPZ6R','1249646154606448642','2020-04-13 18:32:32','MEMBER'),
('1249647179123920897','漫步天空的鱼 ','https://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep1J5W23GsduT7eoxB0VUH12nzYYJicxj3T5Z7Vnf6d8eSaCAhUV5UqgCd3ZdmCWqrK0EzphaU0ewg/132','15674980439',2,'BXW8N2','1249646645646200834','2020-04-13 18:34:39','MEMBER'),
('1249647377699049474','大灰狼᭄ꫛꪝ','https://wx.qlogo.cn/mmopen/vi_32/Cp6oMCj4n8d2icpSMZeuQLa2gwXEgibVH2r5b9vibm8mnSIOTbb38tjgDkTJ9y5GBDX8WMuCgQkZmeibt0Kuia4Q9hA/132','18073692882',3,'GTXY4M','1249647179123920897','2020-04-13 18:35:26','MEMBER'),
('1249647766947237890','伍星','https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKeBn4LFgzpXMBmrAcMRvOl0vibkYDAMibbpIchCBJoaPUT8ePlicEH58RWaiadXJSBtyHF9JubGlPyvg/132','15576622046',2,'CZZ47L','1249647377699049474','2020-04-13 18:36:59','MEMBER'),
('1249647976792461313','🐭','https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJZFvrWqkibKOtTYsBl7Ovq6JVuYHibWwicyibZL1SicIqHOCaGhMAxAEVY8Hg2gpKkelMmxCSricR45wqg/132','15367842658',2,'BVPERE','1249647766947237890','2020-04-13 18:37:49','MEMBER'),
('1249660183160819714','佰创科技，专业软件定制','https://wx.qlogo.cn/mmopen/vi_32/9MjR2XUtd7RibDUm3p2hoJutquAfmPH5rSlWGoa0vsZKhDfIhrZZA4BdiaibAT1yFV8vA8LsicMuDnIgicmHXXIkbxg/132','15399928823',1,'YDYFVZ','1249647976792461313','2020-04-13 19:26:19','MEMBER');
CREATE TABLE `jd_wallet` (
  `id` bigint(20) NOT NULL,
  `balance` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '钱包余额',
  `balance_freeze` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '已被冻结的金额',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '钱包状态',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_wallet_transaction_flow` (
  `id` bigint(20) NOT NULL COMMENT '记录id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `amount` decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '费用数量',
  `order_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '交易id',
  `transaction_time` datetime NOT NULL COMMENT '交易时间',
  `transaction_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '交易类型(0 未知，1收入，2支出） ',
  `transaction_biz` tinyint(4) NOT NULL DEFAULT '0' COMMENT '交易业务类型 ',
  `remark` varchar(255) NOT NULL DEFAULT '' COMMENT '流水备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `jd_wechat_group` (
  `id` bigint(20) NOT NULL,
  `group_id` int(11) NOT NULL COMMENT '群组id',
  `group_name` varchar(64) NOT NULL COMMENT '组名称',
  `owner_id` bigint(20) NOT NULL COMMENT '归属人id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `group_id` (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分享海报';

CREATE TABLE `jd_wechat_user` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',
  `session_key` varchar(255) NOT NULL DEFAULT '' COMMENT '会话密钥',
  `wechat_id` varchar(255) NOT NULL DEFAULT '' COMMENT '微信号',
  `open_id` varchar(255) NOT NULL DEFAULT '' COMMENT '用户唯一标识',
  `union_id` varchar(255) NOT NULL DEFAULT '' COMMENT '用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into `jd_wechat_user`(`id`,`user_id`,`session_key`,`wechat_id`,`open_id`,`union_id`,`create_time`) values
('1249646645721698306','1249646645646200834','GyVq6q2zh5OIcXOh4EbsEA==','wuli_wuxiansen','ostG55SIgJGucXBPW9FF4kXUvQDE','oNCj5w_TGlfVSWPJVgnncYT5BgQI','2020-04-13 18:32:32'),
('1249647179195224066','1249647179123920897','kqqv9YBMAHEij8K6aUjkHQ==','crystal9524','ostG55cqGSeXyqOlcxpDFQz-p76Q','','2020-04-13 18:34:39'),
('1249647377791324162','1249647377699049474','qEzPxkabttdEH7jHXWjUMg==','Shoujijadt','ostG55UrBsInb1B8UQNUSvaNZUBo','','2020-04-13 18:35:26'),
('1249647767035318273','1249647766947237890','0NeMSggOB1s+AQdE4uIZ2Q==','wuxing','ostG55cOduWJjfpmtxikQAS9shcg','oNCj5w2w7m6NyyL4Lq7IyfXyk7ak','2020-04-13 18:36:59'),
('1249647976880541697','1249647976792461313','cTHqNQe5sVw38r22ZgELRw==','laoshuzi-777','ostG55bEOkbmR-EFWDIB7-7mlyzk','oNCj5w1dBjMturL8--k0NK5UcISQ','2020-04-13 18:37:49'),
('1249649658788057090','1249646154606448642','OFfTjOMRyRY/ilzASLDzvQ==','head_main','ostG55bDoaEKkwSn3qgN1Ubncj6U','oNCj5w1xr4Ho1Tj7OFE0-NdiUH6s','2020-04-13 18:44:30'),
('1249660183227928578','1249660183160819714','hlpvEawQ8yAzsUOc5GtllA==','','ostG55b6yIdcTtht2gOv-61tlXjA','oNCj5w8a9qLSiPVDYoixvisRM4dc','2020-04-13 19:26:19');
CREATE TABLE `jd_withdraw_order_payment` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `partner_trade_no` varchar(32) NOT NULL DEFAULT '' COMMENT '商户订单号，需保持历史全局唯一性(只能是字母或者数字，不能包含有其它字符)',
  `payment_no` varchar(64) NOT NULL DEFAULT '' COMMENT '企业付款成功，返回的微信付款单号',
  `payment_time` varchar(32) NOT NULL DEFAULT '' COMMENT '企业付款成功时间',
  `amount` int(11) NOT NULL COMMENT '支付金额（单位：分）',
  `device_info` varchar(32) NOT NULL DEFAULT '' COMMENT '微信支付分配的终端设备号',
  `payment_desc` varchar(100) NOT NULL DEFAULT '' COMMENT '支付备注',
  `result_code` varchar(16) NOT NULL COMMENT 'SUCCESS/FAIL，注意：当状态为FAIL时，存在业务结果未明确的情况。如果状态为FAIL，请务必关注错误代码（err_code字段），通过查询查询接口确认此次付款的结果。',
  `err_code` varchar(32) NOT NULL DEFAULT '' COMMENT '错误码信息，注意：出现未明确的错误码时（SYSTEMERROR等），请务必用原商户订单号重试，或通过查询接口确认此次付款的结果。',
  `err_code_des` varchar(128) NOT NULL DEFAULT '' COMMENT '结果信息描述',
  PRIMARY KEY (`id`),
  UNIQUE KEY `partner_trade_no` (`partner_trade_no`),
  UNIQUE KEY `payment_no` (`payment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='提现支付操作记录';

SET FOREIGN_KEY_CHECKS = 1;

