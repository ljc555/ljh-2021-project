# 新人上手

DROP TABLE IF EXISTS jd_member_quick_start;
CREATE TABLE IF NOT EXISTS jd_member_quick_start(

  id BIGINT PRIMARY KEY COMMENT '记录id',
  title VARCHAR(32) NOT NULL DEFAULT '' COMMENT '新人上路标题',
  content VARCHAR(512) NOT NULL DEFAULT '' COMMENT '新人上路说明',
  video_url VARCHAR(256) NOT NULL DEFAULT '' COMMENT '视频地址',
  type tinyint NOT NULL DEFAULT 1 COMMENT '跳转页面、复制、显示图片',
  style tinyint NOT NULL DEFAULT 1 COMMENT '显示样式',
  action VARCHAR(16) NOT NULL DEFAULT '' COMMENT '操作提示',
  data VARCHAR(256) NOT NULL DEFAULT '' COMMENT '操作内容',
  sort tinyint NOT NULL DEFAULT 1 COMMENT '显示顺序',
  create_time datetime NOT NULL DEFAULT now() COMMENT '创建时间'


) COMMENT '新人上手';

