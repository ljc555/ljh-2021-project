CREATE TABLE jd_union.jd_activity
(
    id bigint(20) PRIMARY KEY NOT NULL,
    title varchar(255) DEFAULT '' NOT NULL COMMENT '活动标题',
    share_image varchar(255) DEFAULT '' NOT NULL COMMENT '活动分享图',
    logo_image varchar(255) DEFAULT '' NOT NULL COMMENT '活动logo图',
    content varchar(1024) DEFAULT '' NOT NULL COMMENT '活动分享文本',
    start_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '活动开始时间',
    end_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '活动结束时间',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
);
INSERT INTO jd_union.jd_activity (id, title, share_image, logo_image, content, start_time, end_time, create_time) VALUES (1239491671735222274, '医疗器械 守护健康', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/3qSSRdvGAxvwARTmak2oEprLSR7J_share.jpg', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/3qSSRdvGAxvwARTmak2oEprLSR7J_logo.jpg', '[Smile]型格男装闪耀新品
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
[Smile] https://u.jd.com/ukDode', '2020-02-20 00:00:00', '2020-03-31 23:59:59', '2020-03-16 18:00:17');
CREATE TABLE jd_union.jd_apply_withdraw_order
(
    id bigint(20) PRIMARY KEY NOT NULL,
    applier_id bigint(20) DEFAULT '0' NOT NULL COMMENT '申请人用户id',
    amount decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '申请提现金额',
    wechat_id varchar(255) DEFAULT '' NOT NULL COMMENT '收款人微信号',
    payee_name varchar(255) DEFAULT '' NOT NULL COMMENT '收款人姓名',
    payee_card_id varchar(255) DEFAULT '' NOT NULL COMMENT '收款人身份证号',
    status tinyint(4) DEFAULT '0' NOT NULL COMMENT '提现单状态',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
);
CREATE TABLE jd_union.jd_apply_withdraw_order_operate_record
(
    id bigint(20) PRIMARY KEY NOT NULL,
    withdraw_order_id bigint(20) DEFAULT '0' NOT NULL COMMENT '提现申请单id',
    operator_id bigint(20) DEFAULT '0' NOT NULL COMMENT '操作人id',
    operator_name varchar(255) DEFAULT '' NOT NULL COMMENT '操作人名称',
    operate_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '操作时间',
    remark varchar(1014) DEFAULT '' NOT NULL COMMENT '备注'
);
CREATE TABLE jd_union.jd_banner
(
    id bigint(20) PRIMARY KEY NOT NULL,
    location varchar(2048) DEFAULT '' NOT NULL COMMENT 'banner跳转页面',
    cover_url varchar(255) DEFAULT '' NOT NULL COMMENT 'banner封面图',
    end_time datetime COMMENT '结束时间',
    start_time datetime NOT NULL COMMENT '开始时间',
    status tinyint(4) DEFAULT '0' NOT NULL COMMENT '状态',
    title varchar(255) DEFAULT '' NOT NULL COMMENT '标题'
);
INSERT INTO jd_union.jd_banner (id, location, cover_url, end_time, start_time, status, title) VALUES (1239492746609483778, '/activities?id=1239491671735222274', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/3qSSRdvGAxvwARTmak2oEprLSR7J_logo.jpg', '2020-03-31 23:59:59', '2020-02-20 00:00:00', 1, '医疗器械 守护健康');
CREATE TABLE jd_union.jd_member_commission
(
    id bigint(20) PRIMARY KEY NOT NULL COMMENT '记录id',
    user_id bigint(20) DEFAULT '0' NOT NULL COMMENT '用户id',
    identify tinyint(4) DEFAULT '0' NOT NULL COMMENT '用户等级',
    level tinyint(4) DEFAULT '0' NOT NULL COMMENT '记录深度',
    batch_id bigint(20) DEFAULT '0' NOT NULL COMMENT '批次Id',
    sku_id bigint(20) NOT NULL COMMENT '商品ID',
    order_id bigint(20) DEFAULT '0' NOT NULL COMMENT '京东订单id',
    finish_time datetime COMMENT '完成时间',
    order_time datetime NOT NULL COMMENT '下单时间(时间戳，毫秒)',
    estimate_rebate_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '预估返利（卖货）',
    actual_rebate_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '实际返利（卖货）',
    estimate_award_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '预估奖励',
    actual_award_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '实际奖励',
    estimate_commission_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '预估佣金',
    actual_commission_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '实际佣金',
    sku_name varchar(1024) DEFAULT '' NOT NULL COMMENT '商品名称',
    valid_code tinyint(4) DEFAULT '-1' NOT NULL COMMENT 'sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
    remark varchar(256) DEFAULT '' NOT NULL COMMENT '佣金备注',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '记录创建时间'
);
CREATE INDEX index_user_id ON jd_union.jd_member_commission (user_id);
CREATE INDEX index_batch_id ON jd_union.jd_member_commission (batch_id);
CREATE INDEX index_sku_id ON jd_union.jd_member_commission (sku_id);
CREATE INDEX index_order_id ON jd_union.jd_member_commission (order_id);
CREATE INDEX order_finish_time ON jd_union.jd_member_commission (finish_time);
CREATE INDEX index_order_time ON jd_union.jd_member_commission (order_time);
CREATE TABLE jd_union.jd_news
(
    id bigint(20) PRIMARY KEY NOT NULL,
    title varchar(255) DEFAULT '' NOT NULL COMMENT '活动标题',
    location varchar(2048) DEFAULT '' NOT NULL COMMENT '跳转页面',
    status tinyint(4) DEFAULT '0' NOT NULL COMMENT '快报状态（0显示，1不显示）',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
);
CREATE TABLE jd_union.jd_option
(
    id bigint(20) PRIMARY KEY NOT NULL,
    option_name varchar(128) NOT NULL COMMENT '選項key',
    option_value varchar(255) NOT NULL COMMENT '選項的值',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
);
CREATE UNIQUE INDEX option_name ON jd_union.jd_option (option_name);
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239385766565834754, 'app_force_login', 'false', '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239385766574223361, 'sys_default_user_name', '系统用户', '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239385766574223362, 'sys_current_sync_start_time', '20200215000000', '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239385766582611970, 'sys_order_sync_start_time', '20200215000000', '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239385766582611971, 'sys_register_user_identify', '1', '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239385766586806274, 'sys_default_user_identify', '1', '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239385766586806275, 'sys_order_sync_delay', '2', '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239413786118815746, 'sys_order_sync_retry_interval', '2000', '2020-03-16 12:50:48');
INSERT INTO jd_union.jd_option (id, option_name, option_value, create_time) VALUES (1239413786123010049, 'sys_order_sync_retry_count', '3', '2020-03-16 12:50:48');
CREATE TABLE jd_union.jd_order_detail
(
    order_id bigint(20) PRIMARY KEY NOT NULL COMMENT '订单ID',
    finish_time datetime COMMENT '完成时间',
    order_emt tinyint(4) DEFAULT '1' NOT NULL COMMENT '下单设备(1:PC,2:无线)',
    order_time datetime NOT NULL COMMENT '下单时间(时间戳，毫秒)',
    owner_id bigint(20) DEFAULT '0' NOT NULL COMMENT '推广人id',
    buyer_id bigint(20) DEFAULT '0' NOT NULL COMMENT '购买人id',
    parent_id bigint(20) DEFAULT '0' NOT NULL COMMENT '父单的订单ID，仅当发生订单拆分时返回， 0：未拆分，有值则表示此订单为子订单',
    pay_month varchar(19) DEFAULT '0' NOT NULL COMMENT '订单维度预估结算时间,不建议使用，可以用订单行sku维度paymonth字段参考，（格式：yyyyMMdd），0：未结算，订单''预估结算时间''仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。',
    plus tinyint(4) DEFAULT '0' NOT NULL COMMENT '下单用户是否为PLUS会员 0：否，1：是',
    pop_id bigint(20) DEFAULT '0' NOT NULL COMMENT '订单维度商家ID，不建议使用，可以用订单行sku维度popId参考',
    union_id bigint(20) DEFAULT '0' NOT NULL COMMENT '推客的联盟ID',
    ext1 varchar(255) DEFAULT '' NOT NULL COMMENT '订单维度的推客生成推广链接时传入的扩展字段，不建议使用，可以用订单行sku维度ext1参考,（需要联系运营开放白名单才能拿到数据）',
    valid_code tinyint(4) DEFAULT '-1' NOT NULL COMMENT '订单维度的有效码，不建议使用，可以用订单行sku维度validCode参考,（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '订单更新时间',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '订单创建时间'
);
CREATE TABLE jd_union.jd_order_sku
(
    sku_id bigint(20) PRIMARY KEY NOT NULL COMMENT '商品ID',
    order_id bigint(20) DEFAULT '0' NOT NULL COMMENT '京东订单id',
    owner_id bigint(20) DEFAULT '0' NOT NULL COMMENT '推广人id',
    buyer_id bigint(20) DEFAULT '0' NOT NULL COMMENT '购买人id',
    actual_cos_price decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '实际计算佣金的金额。订单完成后，会将误扣除的运费券金额更正。如订单完成后发生退款，此金额会更新。',
    actual_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '	推客获得的实际佣金（实际计佣金额*佣金比例*最终比例）。如订单完成后发生退款，此金额会更新。',
    commission_rate decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '佣金比例',
    estimate_cos_price decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '预估计佣金额，即用户下单的金额(已扣除优惠券、白条、支付优惠、进口税，未扣除红包和京豆)，有时会误扣除运费券金额，完成结算时会在实际计佣金额中更正。如订单完成前发生退款，此金额也会更新。',
    estimate_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '推客的预估佣金（预估计佣金额*佣金比例*最终比例），如订单完成前发生退款，此金额也会更新。',
    final_rate decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '最终比例（分成比例+补贴比例）',
    cid1 bigint(20) DEFAULT '0' NOT NULL COMMENT '一级类目ID',
    frozen_sku_nnum bigint(20) DEFAULT '0' NOT NULL COMMENT '商品售后中数量',
    pid varchar(128) DEFAULT '' COMMENT '联盟子站长身份标识，格式：子站长ID_子站长网站ID_子站长推广位ID',
    position_id bigint(20) DEFAULT '0' NOT NULL COMMENT '推广位ID,0代表无推广位',
    site_id bigint(20) DEFAULT '0' NOT NULL COMMENT '网站ID，0：无网站',
    sku_name varchar(1024) DEFAULT '' NOT NULL COMMENT '商品名称',
    sku_num bigint(20) DEFAULT '0' NOT NULL COMMENT '商品数量',
    sku_image varchar(1024) DEFAULT '' NOT NULL COMMENT '商品主图',
    sku_return_num bigint(20) DEFAULT '0' NOT NULL COMMENT '商品已退货数量',
    sub_side_rate decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '分成比例',
    subsidy_rate decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '补贴比例',
    cid3 bigint(20) DEFAULT '0' NOT NULL COMMENT '三级类目ID',
    union_alias varchar(255) DEFAULT '' NOT NULL COMMENT '	PID所属母账号平台名称（原第三方服务商来源）',
    union_tag varchar(255) DEFAULT '' NOT NULL COMMENT '联盟标签数据（整型的二进制字符串，目前返回16位：0000000000000001。数据从右向左进行，每一位为1表示符合联盟的标签特征，第1位：红包，第2位：组合推广，第3位：拼购，第5位：有效首次购（0000000000011XXX表示有效首购，最终奖励活动结算金额会结合订单状态判断，以联盟后台对应活动效果数据报表https://union.jd.com/active为准）,第8位：复购订单，第9位：礼金，第10位：联盟礼金，第11位：推客礼金。例如：0000000000000001:红包订单，0000000000000010:组合推广订单，0000000000000100:拼购订单，0000000000011000:有效首购，0000000000000111：红包+组合推广+拼购等）',
    union_traffic_group tinyint(4) DEFAULT '0' NOT NULL COMMENT '渠道组 1：1号店，其他：京东',
    valid_code tinyint(4) DEFAULT '-1' NOT NULL COMMENT 'sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
    sub_unionId varchar(255) DEFAULT '' NOT NULL COMMENT '子联盟ID(需要联系运营开放白名单才能拿到数据)',
    trace_type tinyint(4) DEFAULT '2' NOT NULL COMMENT '2：同店；3：跨店',
    pay_month int(11) DEFAULT '0' NOT NULL COMMENT '订单行维度预估结算时间（格式：yyyyMMdd） ，0：未结算。订单''预估结算时间''仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。',
    pop_id bigint(20) DEFAULT '0' NOT NULL COMMENT '	商家ID。''订单行维度''',
    ext1 varchar(255) DEFAULT '' NOT NULL COMMENT '	推客生成推广链接时传入的扩展字段（需要联系运营开放白名单才能拿到数据）。''订单行维度''',
    cp_act_id bigint(20) DEFAULT '0' NOT NULL COMMENT '招商团活动id，正整数，为0时表示无活动',
    union_role tinyint(4) DEFAULT '1' NOT NULL COMMENT '站长角色，1： 推客、 2： 团长',
    gift_coupon_key varchar(255) DEFAULT '' NOT NULL COMMENT '礼金批次ID',
    gift_coupon_ocs_amount decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '礼金分摊金额',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'sku更新时间',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT 'sdu创建时间'
);
CREATE TABLE jd_union.jd_order_sync_record
(
    id bigint(20) PRIMARY KEY NOT NULL COMMENT '记录id',
    order_id bigint(20) DEFAULT '0' NOT NULL COMMENT '京东订单id',
    status tinyint(4) DEFAULT '0' NOT NULL COMMENT '是否成功（0成功，1失败）'
);
CREATE TABLE jd_union.jd_settlement_calculate_result
(
    id bigint(20) PRIMARY KEY NOT NULL COMMENT '记录id',
    user_id bigint(20) DEFAULT '0' NOT NULL COMMENT '用户id',
    start_date date NOT NULL COMMENT '开始时间',
    end_date date NOT NULL COMMENT '开始时间',
    rebate_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '返利',
    award_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '奖励',
    commission_fee decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '佣金',
    status tinyint(4) DEFAULT '0' NOT NULL COMMENT '成员状态(0成功，1失败)',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '记录时间'
);
CREATE UNIQUE INDEX user_id ON jd_union.jd_settlement_calculate_result (user_id, start_date, end_date);
CREATE INDEX index_user_id_and_start_date_and_end_date ON jd_union.jd_settlement_calculate_result (user_id, start_date, end_date);
INSERT INTO jd_union.jd_settlement_calculate_result (id, user_id, start_date, end_date, rebate_fee, award_fee, commission_fee, status, create_time) VALUES (1239494021384298497, 1239388993403297793, '2020-02-01', '2020-02-29', 0.0000, 0.8715, 0.0000, 0, '2020-03-16 18:09:38');
CREATE TABLE jd_union.jd_team_member
(
    id bigint(20) PRIMARY KEY NOT NULL COMMENT 'ID',
    leader_id bigint(20) unsigned NOT NULL COMMENT '领队id',
    member_id bigint(20) unsigned NOT NULL COMMENT '成员id',
    level int(11) NOT NULL COMMENT '级别',
    create_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '记录创建时间'
);
CREATE UNIQUE INDEX index_unique_leader_id_and_member_id ON jd_union.jd_team_member (leader_id, member_id);
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239385766737801217, 1239385766674886657, 1239385766674886657, 0, '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239388993445240833, 1239388993403297793, 1239388993403297793, 0, '2020-03-16 11:12:17');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239388993462018049, 1239385766674886657, 1239388993403297793, 1, '2020-03-16 11:12:17');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239404324511358978, 1239404324494581762, 1239404324494581762, 0, '2020-03-16 12:13:12');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239404324523941890, 1239388993403297793, 1239404324494581762, 1, '2020-03-16 12:13:12');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239404324561690626, 1239385766674886657, 1239404324494581762, 2, '2020-03-16 12:13:12');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239405702487023618, 1239405702461857794, 1239405702461857794, 0, '2020-03-16 12:18:41');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239405702495412226, 1239404324494581762, 1239405702461857794, 1, '2020-03-16 12:18:41');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239405702499606529, 1239388993403297793, 1239405702461857794, 2, '2020-03-16 12:18:41');
INSERT INTO jd_union.jd_team_member (id, leader_id, member_id, level, create_time) VALUES (1239405702503800833, 1239385766674886657, 1239405702461857794, 3, '2020-03-16 12:18:41');
CREATE TABLE jd_union.jd_timeline_goods
(
    id bigint(20) PRIMARY KEY NOT NULL,
    title varchar(255) DEFAULT '' NOT NULL COMMENT '商品名称',
    sku_id bigint(20) DEFAULT '0' NOT NULL COMMENT '商品id',
    publisher_id bigint(20) DEFAULT '0' NOT NULL COMMENT '发布人id',
    image_url varchar(1024) DEFAULT '' NOT NULL COMMENT '分享图',
    content varchar(1024) DEFAULT '' NOT NULL COMMENT '发圈内容',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '注册时间'
);
CREATE TABLE jd_union.jd_user
(
    id bigint(20) PRIMARY KEY NOT NULL,
    nick_name varchar(255) DEFAULT '' NOT NULL COMMENT '用户名',
    avatar_url varchar(1024) DEFAULT '' NOT NULL COMMENT '活动分享图',
    phone varchar(20) DEFAULT '' NOT NULL COMMENT '手机号',
    identify tinyint(4) DEFAULT '0' NOT NULL COMMENT '用户身份（导师，合伙人）',
    invitation_code varchar(10) DEFAULT '' NOT NULL COMMENT '邀请码',
    inviter_id bigint(20) DEFAULT '0' NOT NULL COMMENT '邀请人用户id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '注册时间'
);
INSERT INTO jd_union.jd_user (id, nick_name, avatar_url, phone, identify, invitation_code, inviter_id, create_time) VALUES (1239385766674886657, '系统用户', '', '', 1, 'DLNWEF', 0, '2020-03-16 10:59:28');
INSERT INTO jd_union.jd_user (id, nick_name, avatar_url, phone, identify, invitation_code, inviter_id, create_time) VALUES (1239388993403297793, '惊天霸戈', 'https://wx.qlogo.cn/mmopen/vi_32/LCEZLO6xdq9b8PE8tKnRiaRQN5eCosBoOjhPNB3xZETsJ8DODuapKO6OlUAGAbbPSFxjX55ic5kSYp4mB0icXSV4A/132', '18817096723', 2, 'CMSC07', 1239385766674886657, '2020-03-16 11:12:17');
INSERT INTO jd_union.jd_user (id, nick_name, avatar_url, phone, identify, invitation_code, inviter_id, create_time) VALUES (1239404324494581762, '伍星', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKKwXg2w9OV1iavRD0d49BB7N4D35dhQJD5pMSUcfXYlicL7dsPgW9WciaSJHqrFDEyJDs84KeMpdO1w/132', '15576622046', 1, 'VL2Y1X', 1239388993403297793, '2020-03-16 12:13:12');
INSERT INTO jd_union.jd_user (id, nick_name, avatar_url, phone, identify, invitation_code, inviter_id, create_time) VALUES (1239405702461857794, '🐭', 'https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLFiaIPFibGeGHdFiabT54l02yicZIBMbNzuerdx5O3cCmWQia3kiajncjCb2J2bWpYu5FXzkmH6HsXLEvQ/132', '15367842658', 1, 'T1WM0X', 1239404324494581762, '2020-03-16 12:18:41');
CREATE TABLE jd_union.jd_wallet
(
    id bigint(20) PRIMARY KEY NOT NULL,
    balance decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '钱包余额',
    balance_freeze decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '已被冻结的金额',
    user_id bigint(20) DEFAULT '0' NOT NULL COMMENT '用户id',
    status tinyint(4) DEFAULT '0' NOT NULL COMMENT '钱包状态',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
);
INSERT INTO jd_union.jd_wallet (id, balance, balance_freeze, user_id, status, create_time) VALUES (1239494021795340290, 0.8715, 0.0000, 1239388993403297793, 0, '2020-03-16 18:09:38');
CREATE TABLE jd_union.jd_wallet_transaction_flow
(
    id bigint(20) PRIMARY KEY NOT NULL COMMENT '记录id',
    user_id bigint(20) DEFAULT '0' NOT NULL COMMENT '用户id',
    fee_amount decimal(9,4) DEFAULT '0.0000' NOT NULL COMMENT '费用数量',
    transaction_id bigint(20) DEFAULT '0' NOT NULL COMMENT '交易id',
    transaction_time datetime NOT NULL COMMENT '交易时间',
    transaction_type tinyint(4) DEFAULT '0' NOT NULL COMMENT '交易类型(0 未知，1收入，2支出） ',
    transaction_biz tinyint(4) DEFAULT '0' NOT NULL COMMENT '交易业务类型 ',
    remark varchar(255) DEFAULT '' NOT NULL COMMENT '流水备注',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '注册时间'
);
CREATE TABLE jd_union.jd_wechat_user
(
    id bigint(20) PRIMARY KEY NOT NULL COMMENT 'id',
    user_id bigint(20) DEFAULT '0' NOT NULL COMMENT '用户id',
    session_key varchar(255) DEFAULT '' NOT NULL COMMENT '会话密钥',
    wechat_id varchar(255) DEFAULT '' NOT NULL COMMENT '微信号',
    open_id varchar(255) DEFAULT '' NOT NULL COMMENT '用户唯一标识',
    union_id varchar(255) DEFAULT '' NOT NULL COMMENT '用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '注册时间'
);
INSERT INTO jd_union.jd_wechat_user (id, user_id, session_key, wechat_id, open_id, union_id, create_time) VALUES (1239388993470406657, 1239388993403297793, 'OCzACMF/WJOpTT9aRxVCUg==', '', 'oMTE85Bvh30JFB5WXRjBRNdZMv5g', '', '2020-03-16 11:12:17');
INSERT INTO jd_union.jd_wechat_user (id, user_id, session_key, wechat_id, open_id, union_id, create_time) VALUES (1239404324565884930, 1239404324494581762, 'Eq4JKgG1NRPlNH117WT+rQ==', 'wuxing07240011', 'oMTE85EX-6wYAQKdhKrQtmOao9Ww', '', '2020-03-16 12:13:12');
INSERT INTO jd_union.jd_wechat_user (id, user_id, session_key, wechat_id, open_id, union_id, create_time) VALUES (1239405702507995137, 1239405702461857794, 'tkeW/ef1dH6UECnthbXBJg==', '', 'oMTE85KhfuwTaXsfA8Qfql09NHLE', '', '2020-03-16 12:18:41');
CREATE TABLE jd_union.jd_member_commission_per_user_for_30_day
(
    user_id bigint(20) NOT NULL COMMENT '用户id',
    estimate_rebate_fee decimal(31,4),
    actual_rebate_fee decimal(31,4),
    estimate_award_fee decimal(31,4),
    actual_award_fee decimal(31,4),
    estimate_commission_fee decimal(31,4),
    actual_commission_fee decimal(31,4)
);