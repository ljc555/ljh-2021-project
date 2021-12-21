
ALTER TABLE jd_wallet_transaction_flow CHANGE fee_amount amount decimal(9,4) NOT NULL DEFAULT '0.0000' COMMENT '费用数量';
ALTER TABLE jd_wallet_transaction_flow CHANGE transaction_id order_id bigint(20) NOT NULL DEFAULT '0' COMMENT '交易id';


DROP TABLE if exists jd_withdraw_order_payment;
CREATE TABLE IF NOT EXISTS jd_withdraw_order_payment(
  id bigint NOT NULL PRIMARY KEY COMMENT 'id',
  partner_trade_no VARCHAR(32) not null UNIQUE DEFAULT '' COMMENT '商户订单号，需保持历史全局唯一性(只能是字母或者数字，不能包含有其它字符)',
  payment_no VARCHAR(64) NOT NULL UNIQUE DEFAULT '' COMMENT '企业付款成功，返回的微信付款单号',
  payment_time VARCHAR(32) NOT NULL DEFAULT  '' COMMENT '企业付款成功时间',
  amount int NOT NULL COMMENT '支付金额（单位：分）',
  device_info VARCHAR(32) NOT NULL DEFAULT '' COMMENT '微信支付分配的终端设备号',
  payment_desc varchar(100) NOT NULL DEFAULT '' COMMENT '支付备注',
  result_code VARCHAR(16) NOT NULL COMMENT 'SUCCESS/FAIL，注意：当状态为FAIL时，存在业务结果未明确的情况。如果状态为FAIL，请务必关注错误代码（err_code字段），通过查询查询接口确认此次付款的结果。',
  err_code VARCHAR(32) NOT NULL DEFAULT '' COMMENT '错误码信息，注意：出现未明确的错误码时（SYSTEMERROR等），请务必用原商户订单号重试，或通过查询接口确认此次付款的结果。',
  err_code_des VARCHAR(128) NOT  NULL DEFAULT  '' COMMENT '结果信息描述'


) COMMENT '提现支付操作记录'


