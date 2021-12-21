# 问题分组添加是否展开列
ALTER TABLE jd_question_category ADD open tinyint DEFAULT 1 NOT NULL AFTER icon ;


# 添加分享海报功能
DROP table if exists jd_share_poster;
create table if not exists jd_share_poster
(
  id bigint not null primary key ,
  title VARCHAR(64) NOT NULL DEFAULT '' COMMENT '海报名称',
  icon_url varchar(512)  not null default '' COMMENT '图标Url',
  image_url varchar(512) NOT NULL default '' COMMENT '分享图',
  content varchar(256) NOT NULL default '' COMMENT '海报文字内容',
  create_time DATETIME NOT NULL DEFAULT now() COMMENT '发布时间'
)
COMMENT '分享海报';
