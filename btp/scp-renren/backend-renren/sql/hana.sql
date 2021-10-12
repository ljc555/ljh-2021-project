-- 菜单
DROP TABLE sys_menu;
CREATE TABLE sys_menu(
  menu_id bigint NOT NULL,
  parent_id bigint COMMENT '父菜单ID，一级菜单为0',
  name varchar(50) COMMENT '菜单名称',
  url varchar(200) COMMENT '菜单URL',
  perms varchar(500) COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  type int COMMENT '类型   0：目录   1：菜单   2：按钮',
  icon varchar(50) COMMENT '菜单图标',
  order_num int COMMENT '排序',
  PRIMARY KEY (menu_id)
);

-- 系统用户
DROP TABLE sys_user;
CREATE TABLE sys_user (
  user_id int NOT NULL,
  username varchar(50) NOT NULL COMMENT '用户名',
  password varchar(100) COMMENT '密码',
  salt varchar(20) COMMENT '盐',
  email varchar(100) COMMENT '邮箱',
  mobile varchar(100) COMMENT '手机号',
  status int COMMENT '状态  0：禁用   1：正常',
  create_user_id int COMMENT '创建者ID',
  create_time Datetime COMMENT '创建时间',
  PRIMARY KEY (user_id)
);

-- 系统用户Token
DROP TABLE sys_user_token;
CREATE TABLE sys_user_token (
  user_id int NOT NULL,
  token varchar(100) NOT NULL COMMENT 'token',
  expire_time Datetime DEFAULT NULL COMMENT '过期时间',
  update_time Datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (user_id)
);

-- 系统验证码
DROP TABLE sys_captcha;
CREATE TABLE sys_captcha (
  uuid char(36) NOT NULL COMMENT 'uuid',
  code varchar(6) NOT NULL COMMENT '验证码',
  expire_time Datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (uuid)
);

-- 角色
DROP TABLE sys_role;
CREATE TABLE sys_role (
  role_id int NOT NULL,
  role_name varchar(100) COMMENT '角色名称',
  remark varchar(100) COMMENT '备注',
  create_user_id int COMMENT '创建者ID',
  create_time Datetime COMMENT '创建时间',
  PRIMARY KEY (role_id)
);

-- 用户与角色对应关系
DROP TABLE sys_user_role;
CREATE TABLE sys_user_role (
  id bigint NOT NULL,
  user_id bigint COMMENT '用户ID',
  role_id bigint COMMENT '角色ID',
  PRIMARY KEY (id)
);

-- 角色与菜单对应关系
DROP TABLE sys_role_menu;
CREATE TABLE sys_role_menu (
  id bigint NOT NULL,
  role_id bigint COMMENT '角色ID',
  menu_id bigint COMMENT '菜单ID',
  PRIMARY KEY (id)
);

-- 系统配置信息
DROP TABLE sys_config;
CREATE TABLE sys_config (
	id bigint NOT NULL,
	param_key varchar(50) COMMENT 'key',
	param_value varchar(2000) COMMENT 'value',
	status tinyint DEFAULT 1 COMMENT '状态   0：隐藏   1：显示',
	remark varchar(500) COMMENT '备注',
	PRIMARY KEY (id)
);

-- 系统日志
DROP TABLE sys_log;
CREATE TABLE sys_log (
  id bigint NOT NULL,
  username varchar(50) COMMENT '用户名',
  operation varchar(50) COMMENT '用户操作',
  method varchar(200) COMMENT '请求方法',
  params varchar(5000) COMMENT '请求参数',
  time bigint NOT NULL COMMENT '执行时长(毫秒)',
  ip varchar(64) COMMENT 'IP地址',
  create_date Datetime COMMENT '创建时间',
  PRIMARY KEY (id)
);

-- 文件上传
DROP TABLE sys_oss;
CREATE TABLE sys_oss (
  id int NOT NULL,
  url varchar(200) COMMENT 'URL地址',
  create_date Datetime COMMENT '创建时间',
  PRIMARY KEY (id)
);

-- 定时任务
DROP TABLE schedule_job;
CREATE TABLE schedule_job (
  job_id int NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  cron_expression varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  status int DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  remark varchar(255) DEFAULT NULL COMMENT '备注',
  create_time Datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (job_id)
);

-- 定时任务日志
DROP TABLE schedule_job_log;
CREATE TABLE schedule_job_log (
  log_id int NOT NULL COMMENT '任务日志id',
  job_id int NOT NULL COMMENT '任务id',
  bean_name varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  params varchar(2000) DEFAULT NULL COMMENT '参数',
  status int NOT NULL COMMENT '任务状态    0：成功    1：失败',
  error varchar(2000) DEFAULT NULL COMMENT '失败信息',
  times int NOT NULL COMMENT '耗时(单位：毫秒)',
  create_time Datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (log_id)
);

-- 用户表
DROP TABLE tb_user;
CREATE TABLE tb_user (
  user_id bigint NOT NULL,
  username varchar(50) NOT NULL COMMENT '用户名',
  mobile varchar(20) NOT NULL COMMENT '手机号',
  password varchar(64) COMMENT '密码',
  create_time Datetime COMMENT '创建时间',
  PRIMARY KEY (user_id)
);

-- 初始数据
INSERT INTO sys_user (user_id, username, password, salt, email, mobile, status, create_user_id, create_time) VALUES ('1', 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', '1', '1', '2016-11-11 11:11:11');

-- 菜单数据
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (1, 0, '系统管理', NULL, NULL, 0, 'system', 5);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (2, 1, '管理员列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (4, 1, '菜单管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (5, 1, 'SQL监控', 'https://backend-renren.cfapps.eu10.hana.ondemand.com/druid/sql.html', NULL, 1, 'sql', 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (6, 1, '定时任务', 'job/schedule', NULL, 1, 'job', 5);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (7, 6, '查看', NULL, 'sys:schedule:list,sys:schedule:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (8, 6, '新增', NULL, 'sys:schedule:save', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (9, 6, '修改', NULL, 'sys:schedule:update', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (10, 6, '删除', NULL, 'sys:schedule:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (11, 6, '暂停', NULL, 'sys:schedule:pause', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (12, 6, '恢复', NULL, 'sys:schedule:resume', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (13, 6, '立即执行', NULL, 'sys:schedule:run', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (14, 6, '日志列表', NULL, 'sys:schedule:log', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (18, 2, '删除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (22, 3, '删除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (26, 4, '删除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (27, 1, '参数管理', 'sys/config', 'sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete', 1, 'config', 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (29, 1, '系统日志', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (30, 1, '文件上传', 'oss/oss', 'sys:oss:all', 1, 'oss', 6);

INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (32, 0, '差旅管理', NULL, NULL, 0, 'system', 0);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (33, 32, '用户列表', 'travel/user', NULL, 1, 'menu', 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (34, 32, '部门列表', 'travel/dept', NULL, 1, 'menu', 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (35, 32, '公司列表', 'travel/company', NULL, 1, 'menu', 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (36, 32, '银行列表', 'travel/bank', NULL, 1, 'menu', 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (37, 32, '费用类型列表', 'travel/cost', NULL, 1, 'menu', 5);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (38, 32, '申请单列表', 'travel/apply', NULL, 1, 'menu', 6);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (39, 32, '申请单行列表', 'travel/applyitem', NULL, 1, 'menu', 7);

INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (40, 0, '差旅报表', NULL, NULL, 0, 'system', 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (41, 40, '费用类型概览', 'report/cost', NULL, 1, 'menu', 1);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (42, 40, '部门费用', 'report/dept', NULL, 1, 'menu', 3);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (43, 40, '部门费用占比', 'report/deptAndCost', NULL, 1, 'menu', 4);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (44, 40, '月度概览', 'report/month', NULL, 1, 'menu', 2);
INSERT INTO sys_menu(menu_id, parent_id, name, url, perms, type, icon, order_num) VALUES (45, 40, '汇总', 'report/all', NULL, 1, 'menu', 5);

INSERT INTO sys_config (id,param_key, param_value, status, remark) VALUES (1,'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息');
INSERT INTO schedule_job (job_id,bean_name, params, cron_expression, status, remark, create_time) VALUES (1,'testTask', 'renren', '0 0/30 * * * ?', '0', '参数测试', now());

-- 账号：13759449321  密码：admin
INSERT INTO tb_user (user_id,username, mobile, password, create_time) VALUES (1,'mark', '13759449321', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');

--  quartz自带表结构
CREATE TABLE QRTZ_JOB_DETAILS(
SCHED_NAME VARCHAR(120) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
JOB_CLASS_NAME VARCHAR(250) NOT NULL,
IS_DURABLE VARCHAR(1) NOT NULL,
IS_NONCONCURRENT VARCHAR(1) NOT NULL,
IS_UPDATE_DATA VARCHAR(1) NOT NULL,
REQUESTS_RECOVERY VARCHAR(1) NOT NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,JOB_NAME,JOB_GROUP));



CREATE TABLE QRTZ_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
JOB_NAME VARCHAR(200) NOT NULL,
JOB_GROUP VARCHAR(200) NOT NULL,
DESCRIPTION VARCHAR(250) NULL,
NEXT_FIRE_TIME int NULL,
PREV_FIRE_TIME int NULL,
PRIORITY INTEGER NULL,
TRIGGER_STATE VARCHAR(16) NOT NULL,
TRIGGER_TYPE VARCHAR(8) NOT NULL,
START_TIME int NOT NULL,
END_TIME int NULL,
CALENDAR_NAME VARCHAR(200) NULL,
MISFIRE_INSTR int NULL,
JOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,JOB_NAME,JOB_GROUP)
REFERENCES QRTZ_JOB_DETAILS(SCHED_NAME,JOB_NAME,JOB_GROUP))
;

CREATE TABLE QRTZ_SIMPLE_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
REPEAT_COUNT BIGINT NOT NULL,
REPEAT_INTERVAL BIGINT NOT NULL,
TIMES_TRIGGERED BIGINT NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
;

CREATE TABLE QRTZ_CRON_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
CRON_EXPRESSION VARCHAR(120) NOT NULL,
TIME_ZONE_ID VARCHAR(80),
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
;


CREATE TABLE QRTZ_SIMPROP_TRIGGERS
  (
    SCHED_NAME VARCHAR(120) NOT NULL,
    TRIGGER_NAME VARCHAR(200) NOT NULL,
    TRIGGER_GROUP VARCHAR(200) NOT NULL,
    STR_PROP_1 VARCHAR(512) NULL,
    STR_PROP_2 VARCHAR(512) NULL,
    STR_PROP_3 VARCHAR(512) NULL,
    INT_PROP_1 INT NULL,
    INT_PROP_2 INT NULL,
    LONG_PROP_1 BIGINT NULL,
    LONG_PROP_2 BIGINT NULL,
    DEC_PROP_1 NUMERIC(13,4) NULL,
    DEC_PROP_2 NUMERIC(13,4) NULL,
    BOOL_PROP_1 VARCHAR(1) NULL,
    BOOL_PROP_2 VARCHAR(1) NULL,
    PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
    REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
;

CREATE TABLE QRTZ_BLOB_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
BLOB_DATA BLOB NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP),
FOREIGN KEY (SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP)
REFERENCES QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP))
;

CREATE TABLE QRTZ_CALENDARS (
SCHED_NAME VARCHAR(120) NOT NULL,
CALENDAR_NAME VARCHAR(200) NOT NULL,
CALENDAR BLOB NOT NULL,
PRIMARY KEY (SCHED_NAME,CALENDAR_NAME))
;

CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS (
SCHED_NAME VARCHAR(120) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
PRIMARY KEY (SCHED_NAME,TRIGGER_GROUP))
;

CREATE TABLE QRTZ_FIRED_TRIGGERS (
SCHED_NAME VARCHAR(120) NOT NULL,
ENTRY_ID VARCHAR(95) NOT NULL,
TRIGGER_NAME VARCHAR(200) NOT NULL,
TRIGGER_GROUP VARCHAR(200) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
FIRED_TIME BIGINT NOT NULL,
SCHED_TIME BIGINT NOT NULL,
PRIORITY INTEGER NOT NULL,
STATE VARCHAR(16) NOT NULL,
JOB_NAME VARCHAR(200) NULL,
JOB_GROUP VARCHAR(200) NULL,
IS_NONCONCURRENT VARCHAR(1) NULL,
REQUESTS_RECOVERY VARCHAR(1) NULL,
PRIMARY KEY (SCHED_NAME,ENTRY_ID))
;

CREATE TABLE QRTZ_SCHEDULER_STATE (
SCHED_NAME VARCHAR(120) NOT NULL,
INSTANCE_NAME VARCHAR(200) NOT NULL,
LAST_CHECKIN_TIME BIGINT NOT NULL,
CHECKIN_INTERVAL BIGINT NOT NULL,
PRIMARY KEY (SCHED_NAME,INSTANCE_NAME))
;

CREATE TABLE QRTZ_LOCKS (
SCHED_NAME VARCHAR(120) NOT NULL,
LOCK_NAME VARCHAR(40) NOT NULL,
PRIMARY KEY (SCHED_NAME,LOCK_NAME))
;

CREATE INDEX IDX_QRTZ_J_REQ_RECOVERY ON QRTZ_JOB_DETAILS(SCHED_NAME,REQUESTS_RECOVERY);

CREATE INDEX IDX_QRTZ_J_GRP ON QRTZ_JOB_DETAILS(SCHED_NAME,JOB_GROUP);

CREATE INDEX IDX_QRTZ_T_J ON QRTZ_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_JG ON QRTZ_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_T_C ON QRTZ_TRIGGERS(SCHED_NAME,CALENDAR_NAME);
CREATE INDEX IDX_QRTZ_T_G ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_T_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_N_G_STATE ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_GROUP,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NEXT_FIRE_TIME ON QRTZ_TRIGGERS(SCHED_NAME,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST ON QRTZ_TRIGGERS(SCHED_NAME,TRIGGER_STATE,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_STATE);
CREATE INDEX IDX_QRTZ_T_NFT_ST_MISFIRE_GRP ON QRTZ_TRIGGERS(SCHED_NAME,MISFIRE_INSTR,NEXT_FIRE_TIME,TRIGGER_GROUP,TRIGGER_STATE);

CREATE INDEX IDX_QRTZ_FT_TRIG_INST_NAME ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME);
CREATE INDEX IDX_QRTZ_FT_INST_JOB_REQ_RCVRY ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,INSTANCE_NAME,REQUESTS_RECOVERY);
CREATE INDEX IDX_QRTZ_FT_J_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_JG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,JOB_GROUP);
CREATE INDEX IDX_QRTZ_FT_T_G ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_NAME,TRIGGER_GROUP);
CREATE INDEX IDX_QRTZ_FT_TG ON QRTZ_FIRED_TRIGGERS(SCHED_NAME,TRIGGER_GROUP);