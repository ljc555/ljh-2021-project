

# 添加微信群组表
DROP table if exists jd_wechat_group;
create table if not exists jd_wechat_group
(
  id bigint not null primary key ,
  group_id int NOT NULL UNIQUE  COMMENT '群组id',
  group_name varchar(64)  not null  COMMENT '组名称',
  owner_id bigint NOT NULL   COMMENT '归属人id',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '发布时间'
)
COMMENT '分享海报';

# 超级会员申请表
DROP table if exists jd_super_member_apply;
create table if not exists jd_super_member_apply
(
  id bigint not null primary key ,
  owner_id bigint not null COMMENT '申请人',
  group_id int NOT NULL UNIQUE  COMMENT '群组id',
  image_urls VARCHAR(260) NOT NULL  DEFAULT '' COMMENT '申请图',
  status tinyint NOT NULL DEFAULT 1 COMMENT '申请状态',
  remark varchar(64) NOT NULL DEFAULT '' COMMENT '备注信息',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '申请时间'
)
COMMENT '超级会员申请表';
