-- 个人信息
DROP TABLE travel_user;
CREATE TABLE travel_user (
	id varchar(20) NOT NULL COMMENT '主键ID',
	pernr varchar(10) NOT NULL COMMENT '人员信息',
	bukrs varchar(100) NOT NULL COMMENT '公司代码',
	name varchar(50) NOT NULL COMMENT '人员姓名',
	depart varchar(50) NOT NULL COMMENT '部门编码',
	name1 varchar(50) COMMENT '部门描述',
	bankc varchar(20) NOT NULL COMMENT '银行账号',
	bank varchar(10) NOT NULL COMMENT '银行代码',
	tel varchar(20) COMMENT '电话',
	PRIMARY KEY (id)
);

INSERT INTO travel_user VALUES ('dwf23131','143001','defo','李毅','cloud','cloud','6212264100011222221','ICBC','13759313121');
INSERT INTO travel_user VALUES ('dwf23132','143002','defo','赵丽伟','oracle','oracle','6212264100011222222','ICBC','13759313122');
INSERT INTO travel_user VALUES ('dwf23133','143003','defo','许子文','sap','sap','6212264100011222223','ICBC','13759313123');
INSERT INTO travel_user VALUES ('dwf23134','143004','defo','孔建','digital','digital','6212264100011222224','ICBC','13759313124');

-- 部门编码
DROP TABLE travel_department;
CREATE TABLE travel_department (
	id varchar(20) NOT NULL COMMENT '主键ID',
	deprs varchar(20) NOT NULL COMMENT '部门编码',
	depxt varchar(40) NOT NULL COMMENT '部门名称',
	PRIMARY KEY (id)
);
INSERT INTO travel_department VALUES ('1','cloud','Cloud');
INSERT INTO travel_department VALUES ('2','oracle','Oracle');
INSERT INTO travel_department VALUES ('3','sap','Sap');
INSERT INTO travel_department VALUES ('4','digital','Digital');

-- 公司代码
DROP TABLE travel_company;
CREATE TABLE travel_company (
	id varchar(20) NOT NULL COMMENT '主键ID',
	bukrs varchar(40) NOT NULL COMMENT '公司代码',
	butxt varchar(20) NOT NULL COMMENT '公司名称',
	PRIMARY KEY (id)
);
INSERT INTO travel_company VALUES ('1','defo','defo创新');

-- 银行信息
DROP TABLE travel_bank;
CREATE TABLE travel_bank (
	id varchar(20) NOT NULL COMMENT '主键ID',
	bank varchar(10) NOT NULL COMMENT '银行',
	txt50 varchar(50) NOT NULL COMMENT '银行名称',
	PRIMARY KEY (id)
);

INSERT INTO travel_bank VALUES ('1','ICBC','中国工商银行');
INSERT INTO travel_bank VALUES ('2','CMD','招商银行');
INSERT INTO travel_bank VALUES ('3','BOC','中国银行');

-- 费用类别
DROP TABLE travel_cost_type;
CREATE TABLE travel_cost_type (
	id varchar(20) NOT NULL COMMENT '主键ID',
	hkont varchar(10) NOT NULL COMMENT '科目类型',
	txt20 varchar(20) NOT NULL COMMENT '科目描述',
	PRIMARY KEY (id)
);
INSERT INTO travel_cost_type VALUES ('1','tax','交通费用');
INSERT INTO travel_cost_type VALUES ('2','hotel','酒店费用');
INSERT INTO travel_cost_type VALUES ('3','mobile','电话费用');
INSERT INTO travel_cost_type VALUES ('4','meals','餐费');
INSERT INTO travel_cost_type VALUES ('5','postage','邮寄费');

-- 差旅费用申请基本信息
DROP TABLE travel_apply;
CREATE TABLE travel_apply (
	id varchar(20) NOT NULL COMMENT '主键ID',
	ecnum varchar(15) NOT NULL COMMENT '申请单号',
	bukrs varchar(20) NOT NULL COMMENT '公司代码',
	depart varchar(20) NOT NULL COMMENT '部门编码',
	pernr varchar(10) NOT NULL COMMENT '申请人',
	comment varchar(200) COMMENT '备注',
	erdat date NOT NULL COMMENT '创建日期',
	ertim Datetime NOT NULL COMMENT '创建时间',
	loevm varchar(2) default '0' COMMENT '删除标记',
	PRIMARY KEY (id)
);

-- 差旅费用申请信息
DROP TABLE travel_apply_item;
CREATE TABLE travel_apply_item (
	id varchar(20) NOT NULL COMMENT '主键ID',
	ecnum varchar(15) NOT NULL COMMENT '申请单号',
	econr varchar(15) NOT NULL COMMENT '行号',
	bdat date NOT NULL COMMENT '开始日期',
	edat date NOT NULL COMMENT '结束日期',
	days int NOT NULL COMMENT '天数',
	price varchar(8) NOT NULL COMMENT '每天单价',
	hkont varchar(10) NOT NULL COMMENT '费用类型',
	dest varchar(100) NOT NULL COMMENT '目的地',
	hotel varchar(100) NOT NULL COMMENT '酒店',
	dmbtr double NOT NULL COMMENT '金额',
	comment varchar(200) COMMENT '备注',
	loevm varchar(2) default '0' COMMENT '删除标记',
	erdat date NOT NULL COMMENT '创建日期',
	ertim Datetime NOT NULL COMMENT '创建时间',
	deprs varchar(20) NOT NULL COMMENT '部门编码',
  yearmonth varchar(20) NOT NULL COMMENT 'yyyy-mm',
	PRIMARY KEY (id)
);
CREATE INDEX IDX_ecnum_TRAVEL_APPLY_ITEM ON travel_apply_item(ecnum);
