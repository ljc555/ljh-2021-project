drop database if exists `jd_union`;
create DATABASE if not exists `jd_union` DEFAULT CHARSET utf8mb4;
USE `jd_union`;


DROP table if exists jd_question_category ;
CREATE TABLE if not exists jd_question_category (

  id bigint not null primary key,
  name VARCHAR(64) NOT NULL UNIQUE COMMENT '分类名称',
  icon VARCHAR(256) NOT NULL COMMENT '小图标',
  open tinyint NOT NULL DEFAULT 1 COMMENT '菜单是否展开',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间'

);



# 添加分享海报功能
DROP table if exists jd_share_poster;
create table if not exists jd_share_poster
(
  id bigint not null primary key ,
  title VARCHAR(64) NOT NULL DEFAULT '' COMMENT '海报名称',
  icon_url varchar(512)  not null default '' COMMENT '图标Url',
  image_url varchar(512) NOT NULL default '' COMMENT '分享图',
  content varchar(1024) NOT NULL default '' COMMENT '海报文字内容',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '发布时间'
)
  COMMENT '分享海报';




DROP table if exists jd_question ;
CREATE TABLE if not exists jd_question (

  id bigint not null primary key,
  cid bigint NOT NULL  COMMENT '问题分类ID',
  title VARCHAR(64) NOT NULL COMMENT '问题标题',
  answer VARCHAR(1024) NOT NULL COMMENT '问题回答',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间',

  index index_category(cid)
);



DROP table if exists jd_feedback;
CREATE TABLE if not exists jd_feedback (

 id bigint not null primary key,
 submitter_id bigint NOT NULL DEFAULT 0  COMMENT '提交人',
 content VARCHAR (1024) NOT NULL DEFAULT '' COMMENT '選項的值',
 status tinyint NOT NULL default 1 COMMENT '反馈状态'

);

DROP table if exists jd_option;
CREATE TABLE if not exists jd_option (
  id bigint not null primary key,
  option_name VARCHAR(128) NOT NULL UNIQUE COMMENT '選項key',
  option_value VARCHAR(255) NOT NULL COMMENT '選項的值',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间'
);


DROP table if exists jd_wallet ;
create TABLE if not exists jd_wallet (
  id bigint not null primary key,
  balance DECIMAL(9,4) NOT NULL DEFAULT 0 COMMENT '钱包余额',
  balance_freeze DECIMAL(9,4) NOT NULL DEFAULT 0 COMMENT '已被冻结的金额',
  user_id BIGINT NOT NULL DEFAULT 0 COMMENT '用户id',
  status tinyint NOT NULL DEFAULT 0 COMMENT '钱包状态',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间'
);

DROP table if exists jd_apply_withdraw_order;
CREATE table if not exists jd_apply_withdraw_order (
   id bigint not null primary key,
   applier_id BIGINT NOT NULL DEFAULT 0 COMMENT '申请人用户id',
   amount DECIMAL(9,4) NOT NULL DEFAULT 0 COMMENT '申请提现金额',
   wechat_id VARCHAR(255) NOT NULL DEFAULT '' COMMENT '收款人微信号',
   payee_name VARCHAR(255) NOT NULL DEFAULT '' COMMENT '收款人姓名',
   payee_card_id VARCHAR(255) NOT NULL DEFAULT '' COMMENT '收款人身份证号',
   status tinyint NOT NULL DEFAULT 0 COMMENT '提现单状态',
   create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间'
) COMMENT '提现申请单';

DROP table if exists jd_apply_withdraw_order_operate_record;
CREATE table if not exists jd_apply_withdraw_order_operate_record (
    id bigint not null primary key,
    withdraw_order_id bigint not null DEFAULT 0 COMMENT '提现申请单id',
    operator_id bigint not null DEFAULT 0 COMMENT '操作人id',
    operator_name VARCHAR(255) NOT NULL DEFAULT '' COMMENT '操作人名称',
    operate_time DATETIME NOT NULL DEFAULT now() COMMENT '操作时间',
    remark VARCHAR(1014) NOT NULL DEFAULT '' COMMENT '备注'
) COMMENT '提现申请单';


drop table if exists jd_banner;
create table if not exists jd_banner
(
  id bigint not null primary key ,
  location varchar(2048) NOT NULL DEFAULT '' COMMENT 'banner跳转页面',
  cover_url varchar(255) NOT NULL DEFAULT '' COMMENT 'banner封面图',
  end_time DATETIME  NOT NULL  COMMENT '结束时间',
  start_time DATETIME NOT NULL COMMENT '开始时间',
  status tinyint NOT NULL DEFAULT 0 COMMENT '状态（1: 自动，2：显示，3：未显示）',
  title varchar(255) NOT NULL DEFAULT '' COMMENT '标题'
)
  COMMENT 'banner表'
;


drop table if exists jd_activity;
create table if not exists jd_activity
(
  id bigint not null primary key ,
  title varchar(255) NOT NULL DEFAULT '' COMMENT '活动标题',
  share_image varchar(255) NOT NULL DEFAULT '' COMMENT '活动分享图',
  logo_image varchar(255) NOT NULL DEFAULT '' COMMENT '活动logo图',
  content VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '活动分享文本',
  start_time datetime not null DEFAULT now() comment '活动开始时间',
  end_time datetime not null DEFAULT now() comment '活动结束时间',
  status tinyint NOT NULL DEFAULT 0 COMMENT '状态（1: 自动，2：显示，3：未显示）',
 # sku_ids  varchar(512) NOT NULL DEFAULT '' COMMENT '商品活动skuId以,号分隔',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间'
)
COMMENT '活动表';


DROP TABLE if exists jd_news;
create table if not exists jd_news
(
  id bigint not null primary key ,
  title varchar(255) NOT NULL DEFAULT '' COMMENT '活动标题',
  location varchar(2048) NOT NULL DEFAULT '' COMMENT '跳转页面',
  start_time datetime not null  comment '活动开始时间',
  end_time datetime not null comment '活动结束时间',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '快报状态（1: 自动，2：显示，3：未显示）',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间'
)
  COMMENT '新闻';

drop table if exists jd_user;
create table if not exists jd_user
(
  id bigint not null primary key ,
  nick_name varchar(255) NOT NULL DEFAULT '' COMMENT '用户名',
  avatar_url varchar(1024) NOT NULL DEFAULT '' COMMENT '活动分享图',
  phone varchar(20) NOT NULL DEFAULT '' COMMENT '手机号',
  identify tinyint not null default 0 comment '用户身份（导师，合伙人）',
  invitation_code VARCHAR(10) not null DEFAULT '' comment '邀请码',
  inviter_id BIGINT not null DEFAULT 0 comment '邀请人用户id',
  role_name VARCHAR(32) NOT NULL DEFAULT 'MEMBER' COMMENT '角色名称',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '注册时间'
);




drop table if exists jd_wechat_user;
create table if not exists jd_wechat_user
(
  id BIGINT NOT NULL PRIMARY KEY NOT NULL COMMENT 'id',
  user_id BIGINT NOT NULL DEFAULT 0 COMMENT '用户id',
  session_key varchar(255) NOT NULL DEFAULT '' COMMENT '会话密钥',
  wechat_id varchar(255) NOT NULL DEFAULT '' COMMENT '微信号',
  open_id varchar(255) NOT NULL DEFAULT '' COMMENT '用户唯一标识',
  union_id varchar(255) not null default '' comment '用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '注册时间'
)
  COMMENT '微信信息表';





drop table if exists jd_timeline_goods;
create table if not exists jd_timeline_goods
(
  id bigint not null primary key ,
  title VARCHAR(255) NOT NULL DEFAULT '' COMMENT '商品名称',
  sku_id BIGINT not null default 0 COMMENT '商品id',
  publisher_id BIGINT   not null default 0 COMMENT '发布人id',
  image_url varchar(1024) NOT NULL default '' COMMENT '分享图',
  content varchar(1024) NOT NULL default '' COMMENT '发圈内容',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '注册时间'
)
  COMMENT '发圈必备';

DROP TABLE if exists jd_order_detail;
create table if not exists jd_order_detail (
  id BIGINT NOT NULL PRIMARY KEY NOT NULL COMMENT 'id',
  order_id BIGINT not null unique  COMMENT '订单ID',
  finish_time DATETIME  null  comment '完成时间',
  order_emt tinyint not null default 1 comment '下单设备(1:PC,2:无线)',
  order_time DATETIME not null  comment '下单时间(时间戳，毫秒)',
  owner_id BIGINT not null DEFAULT 0 comment '推广人id',
  buyer_id BIGINT not null DEFAULT 0 comment '购买人id',
  parent_id BIGINT not null DEFAULT 0 COMMENT '父单的订单ID，仅当发生订单拆分时返回， 0：未拆分，有值则表示此订单为子订单',
  pay_month VARCHAR(19) not null default '0' COMMENT '订单维度预估结算时间,不建议使用，可以用订单行sku维度paymonth字段参考，（格式：yyyyMMdd），0：未结算，订单''预估结算时间''仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。',
  plus tinyint not null  default '0' comment '下单用户是否为PLUS会员 0：否，1：是',
  pop_id bigint not null default 0 comment '订单维度商家ID，不建议使用，可以用订单行sku维度popId参考',
  union_id bigint not null default 0 comment '推客的联盟ID',
  ext1 varchar(255) not null default '' comment '订单维度的推客生成推广链接时传入的扩展字段，不建议使用，可以用订单行sku维度ext1参考,（需要联系运营开放白名单才能拿到数据）',
  valid_code tinyint not null default -1 comment '订单维度的有效码，不建议使用，可以用订单行sku维度validCode参考,（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
  update_time DATETIME NOT NULL DEFAULT now() on update now() comment '订单更新时间',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '订单创建时间'
) comment '订单记录';


DROP TABLE if exists jd_order_sku;
create table if not exists jd_order_sku (
  id BIGINT NOT NULL PRIMARY KEY NOT NULL COMMENT 'id',
  sku_id bigint not null  comment '商品ID',
  order_id BIGINT not null  comment '京东订单id',
  owner_id BIGINT not null DEFAULT 0 comment '推广人id',
  buyer_id BIGINT not null DEFAULT 0 comment '购买人id',
  actual_cos_price  decimal(9,4)  not null DEFAULT 0 COMMENT '实际计算佣金的金额。订单完成后，会将误扣除的运费券金额更正。如订单完成后发生退款，此金额会更新。',
  actual_fee decimal(9,4)  not null  default 0 comment '	推客获得的实际佣金（实际计佣金额*佣金比例*最终比例）。如订单完成后发生退款，此金额会更新。',
  commission_rate  decimal(9,4)  not null default 0 comment '佣金比例',
  estimate_cos_price  decimal(9,4)  not null  default 0 comment '预估计佣金额，即用户下单的金额(已扣除优惠券、白条、支付优惠、进口税，未扣除红包和京豆)，有时会误扣除运费券金额，完成结算时会在实际计佣金额中更正。如订单完成前发生退款，此金额也会更新。',
  estimate_fee  decimal(9,4)  not null DEFAULT 0 COMMENT '推客的预估佣金（预估计佣金额*佣金比例*最终比例），如订单完成前发生退款，此金额也会更新。',
  final_rate decimal(9,4) not null DEFAULT 0 COMMENT '最终比例（分成比例+补贴比例）',
  cid1 bigint not null default 0 COMMENT '一级类目ID',
  frozen_sku_nnum bigint not null  default 0 comment '商品售后中数量',
  pid varchar(128) default '' comment '联盟子站长身份标识，格式：子站长ID_子站长网站ID_子站长推广位ID',
  position_id bigint not null default 0 comment '推广位ID,0代表无推广位',
  site_id  bigint not null default 0 comment '网站ID，0：无网站',
  sku_name varchar(1024) not null default '' comment '商品名称',
  sku_num bigint not null default 0 comment '商品数量',
  sku_image varchar(1024) not null default '' comment '商品主图',
  sku_return_num bigint not null  default 0 comment '商品已退货数量',
  sub_side_rate  decimal(9,4)  not null default 0 comment '分成比例',
  subsidy_rate decimal(9,4)  not null default 0 comment '补贴比例',
  cid3 bigint not null default 0 comment '三级类目ID',
  union_alias varchar(255) not null default '' comment '	PID所属母账号平台名称（原第三方服务商来源）',
  union_tag varchar(255) not null default '' comment '联盟标签数据（整型的二进制字符串，目前返回16位：0000000000000001。数据从右向左进行，每一位为1表示符合联盟的标签特征，第1位：红包，第2位：组合推广，第3位：拼购，第5位：有效首次购（0000000000011XXX表示有效首购，最终奖励活动结算金额会结合订单状态判断，以联盟后台对应活动效果数据报表https://union.jd.com/active为准）,第8位：复购订单，第9位：礼金，第10位：联盟礼金，第11位：推客礼金。例如：0000000000000001:红包订单，0000000000000010:组合推广订单，0000000000000100:拼购订单，0000000000011000:有效首购，0000000000000111：红包+组合推广+拼购等）',
  union_traffic_group tinyint not null default 0 comment '渠道组 1：1号店，其他：京东',
  valid_code tinyint not null default -1 comment 'sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
  sub_unionId varchar(255) not null default '' comment '子联盟ID(需要联系运营开放白名单才能拿到数据)',
  trace_type tinyint not null default 2 comment '2：同店；3：跨店',
  pay_month int not null default 0 comment '订单行维度预估结算时间（格式：yyyyMMdd） ，0：未结算。订单''预估结算时间''仅供参考。账号未通过资质审核或订单发生售后，会影响订单实际结算时间。',
  pop_id bigint not null default 0 comment '	商家ID。''订单行维度''',
  ext1 varchar(255) not null default '' comment '	推客生成推广链接时传入的扩展字段（需要联系运营开放白名单才能拿到数据）。''订单行维度''',
  cp_act_id bigint not null default 0 comment '招商团活动id，正整数，为0时表示无活动',
  union_role tinyint not null default 1 comment '站长角色，1： 推客、 2： 团长',
  gift_coupon_key varchar(255) not null default '' comment '礼金批次ID',
  gift_coupon_ocs_amount  decimal(9,4)  not null default 0 comment '礼金分摊金额',
  update_time DATETIME NOT NULL DEFAULT now() on update now() comment 'sku更新时间',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT 'sdu创建时间',

  unique unique_order_id_and_sku_id(order_id, sku_id)

) comment '佣金商品记录';

drop table if exists jd_member_commission;
create table if not exists jd_member_commission (
  id BIGINT NOT NULL PRIMARY KEY COMMENT '记录id',
  user_id BIGINT NOT NULL DEFAULT 0 COMMENT '用户id',
  identify tinyint NOT NULL DEFAULT  0 COMMENT '用户等级',
  level tinyint NOT NULL DEFAULT 0 COMMENT '记录深度',
  batch_id BIGINT not null DEFAULT 0 COMMENT '批次Id',
  sku_id bigint not null   comment '商品ID',
  order_id BIGINT not null DEFAULT 0 comment '京东订单id',
  finish_time DATETIME  null  comment '完成时间',
  order_time DATETIME not null  comment '下单时间(时间戳，毫秒)',
  estimate_rebate_fee decimal(9,4) not null default 0 comment '预估返利（卖货）',
  actual_rebate_fee decimal(9,4) not null default 0 comment '实际返利（卖货）',
  estimate_award_fee decimal(9,4) not null default 0 comment '预估奖励',
  actual_award_fee decimal(9,4) not null default 0 comment '实际奖励',
  estimate_commission_fee decimal(9,4) not null default 0 comment '预估佣金',
  actual_commission_fee decimal(9,4) not null default 0 comment '实际佣金',
  sku_name varchar(1024) not null default '' comment '商品名称',
  valid_code tinyint not null default -1 comment 'sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））',
  remark varchar(256) NOT NULL DEFAULT '' COMMENT '佣金备注',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '记录创建时间',

  index index_user_id(user_id),
  index index_batch_id(batch_id),
  index index_sku_id(sku_id),
  index index_order_id(order_id),
  index index_order_time(order_time),
  index order_finish_time(finish_time)

) comment '佣金商品记录';

DROP TABLE if exists jd_settlement_calculate_result;
create table if not exists jd_settlement_calculate_result (

    id BIGINT NOT NULL   KEY COMMENT '记录id',
    user_id BIGINT NOT NULL DEFAULT 0 COMMENT '用户id',
    start_date DATE NOT NULL  COMMENT '开始时间',
    end_date DATE NOT NULL  COMMENT '开始时间',
    rebate_fee decimal(9,4) not null default 0 comment '返利',
    award_fee decimal(9,4) not null default 0 comment '奖励',
    commission_fee decimal(9,4) not null default 0 comment '佣金',
    status tinyint NOT NULL DEFAULT 0 COMMENT '成员状态(0成功，1失败)',
    create_time DATETIME NOT NULL DEFAULT now() COMMENT '记录时间',

    unique (user_id, start_date, end_date),
    index index_user_id_and_start_date_and_end_date(user_id, start_date, end_date)

) COMMENT '用户账单计算状态记录';


# 用户30天单量
DROP VIEW IF EXISTS jd_member_commission_per_user_for_30_day;
CREATE VIEW  jd_member_commission_per_user_for_30_day AS
SELECT
    user_id,
    SUM(estimate_rebate_fee) AS  estimate_rebate_fee, SUM(actual_rebate_fee)  AS  actual_rebate_fee,
    SUM(estimate_award_fee) AS  estimate_award_fee, SUM(actual_award_fee)  AS  actual_award_fee,
    SUM(estimate_commission_fee) AS  estimate_commission_fee, SUM(actual_commission_fee)  AS  actual_commission_fee

FROM
    jd_member_commission
WHERE order_time >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)
GROUP BY user_id;



DROP TABLE if exists jd_order_sync_record;
create table if not exists jd_order_sync_record (
  id BIGINT NOT NULL PRIMARY KEY COMMENT '记录id',
  order_id BIGINT not null DEFAULT 0 comment '京东订单id',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '是否成功（0成功，1失败）'
) comment '佣金商品同步记录';


#  保存商品每天的同步时间
# DROP TABLE if exists jd_order_sync_date;
# create table if not exists jd_order_sync_date(
#     id BIGINT NOT NULL PRIMARY KEY COMMENT '记录id',
#     sync_date date NOT NULL  COMMENT '记录时间，按天为单位',
#     sync_minutes_of_day int NOT NULL  DEFAULT 0 COMMENT '同步分钟',
#     sync_status tinyint NOT NULL DEFAULT 0 COMMENT '同步状态(0,成功，1失败）',
#     create_time DATETIME NOT NULL DEFAULT now() COMMENT '注册时间',
#
#     unique unique_index_sync_date_and_sync_minute_of_day(sync_date,sync_minutes_of_day)
#
# ) COMMENT '保存商品每天的同步时间';

# 用户的交易流水
DROP table if exists jd_wallet_transaction_flow;
create table if not exists jd_wallet_transaction_flow(
    id BIGINT NOT NULL PRIMARY KEY COMMENT '记录id',
    user_id BIGINT NOT NULL DEFAULT 0 COMMENT '用户id',
    fee_amount decimal(9,4) not null default 0 comment '费用数量',
    transaction_id BIGINT NOT NULL DEFAULT 0 COMMENT '交易id',
    transaction_time DATETIME NOT NULL  COMMENT '交易时间',
    transaction_type TINYINT NOT NULL DEFAULT 0 COMMENT '交易类型(0 未知，1收入，2支出） ',
    transaction_biz TINYINT NOT NULL DEFAULT 0 COMMENT '交易业务类型 ',
    remark VARCHAR(255) NOT NULL DEFAULT  '' COMMENT '流水备注',
    create_time DATETIME NOT NULL DEFAULT now() COMMENT '注册时间'
) COMMENT '保存商品每天的同步时间';


#
# create table if not exists category_mapping (
#
#   id bigint NOT NULL primary key auto_increment,
#   name varchar(10) NOT NULL DEFAULT '' comment '分类名称',
#   jid bigint NOT NULL default -1 comment '京东分类名称'
#
#
# ) COMMENT '映射应用类别与京东类型';

drop table if exists jd_team_member;
create table if not exists jd_team_member
(
  id bigint not null comment 'ID'
    primary key ,
  leader_id BIGINT UNSIGNED not null comment '领队id',
  member_id BIGINT UNSIGNED not null comment '成员id',
  level INT not null comment '级别',
  create_time timestamp default CURRENT_TIMESTAMP  not null comment '记录创建时间',
  UNIQUE index_unique_leader_id_and_member_id(leader_id, member_id)
)
  comment '团队人员关系表';


