# 消息

DROP TABLE if exists jd_message;
create table if not exists jd_message
(
  id bigint not null primary key ,
  title varchar(255) NOT NULL DEFAULT '' COMMENT '消息标题',
  content VARCHAR(1024) NOT NULL DEFAULT '' COMMENT '消息内容',
  start_time datetime not null  comment '消息开始时间',
  end_time datetime not null comment '消息结束时间',
  type tinyint NOT NULL DEFAULT 0 COMMENT '消息类型',
  status TINYINT NOT NULL DEFAULT 0 COMMENT '消息状态（1: 自动，2：显示，3：未显示）',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '创建时间'
)
  COMMENT '新闻';
