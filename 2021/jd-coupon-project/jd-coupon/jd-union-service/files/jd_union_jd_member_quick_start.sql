CREATE TABLE jd_union.jd_member_quick_start
(
    id bigint(20) PRIMARY KEY NOT NULL COMMENT '记录id',
    title varchar(32) DEFAULT '' NOT NULL COMMENT '新人上路标题',
    content varchar(512) DEFAULT '' NOT NULL COMMENT '新人上路说明',
    video_url varchar(256) DEFAULT '' NOT NULL COMMENT '视频地址',
    type tinyint(4) DEFAULT '1' NOT NULL COMMENT '跳转页面、复制、显示图片',
    style tinyint(4) DEFAULT '1' NOT NULL COMMENT '显示样式',
    action varchar(16) DEFAULT '' NOT NULL COMMENT '操作提示',
    data varchar(256) DEFAULT '' NOT NULL COMMENT '操作内容',
    sort tinyint(4) DEFAULT '1' NOT NULL COMMENT '显示顺序',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间'
);
INSERT INTO jd_union.jd_member_quick_start (id, title, content, video_url, type, style, action, data, sort, create_time) VALUES (1249952388481380354, '成长任务1：升级超级会员省得多赚的多', '&lt;p&gt;建立一个大于&lt;span style=&quot;color:#E53333;&quot;&gt;50人的微信群&lt;/span&gt;完成升级，即可省得更多，分享赚钱之路。&lt;/p&gt;', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/video/mda-kddfyxzrasacwkv5.mp4
', 1, 1, '去升级', '/page_package/apply-vip/apply-vip', 1, '2020-04-14 16:04:20');
INSERT INTO jd_union.jd_member_quick_start (id, title, content, video_url, type, style, action, data, sort, create_time) VALUES (1249964797954703361, '成长任务2：掌握选爆品和会卖货的技巧', '&lt;p&gt;了解小程序内重要的选品频道，掌握更多的发品方式和技巧，为亲朋好友带来优惠。&amp;nbsp;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;color:#E53333;&quot;&gt;&lt;br /&gt;想学习更多一定添加专属导师微信！&lt;/span&gt;&lt;/p&gt;&lt;p&gt;&lt;/p&gt;&lt;p&gt;导师微信号：#{#wechat.wechatId}&lt;/p&gt;', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/video/mda-kddfyxzrasacwkv5.mp4
', 2, 1, '复制导师微信', '#{#wechat.wechatId}', 2, '2020-04-14 16:04:20');
INSERT INTO jd_union.jd_member_quick_start (id, title, content, video_url, type, style, action, data, sort, create_time) VALUES (1249967624462594050, '成长任务4: 进官方爆款群选取更多爆单素材', '&lt;p&gt;官方爆款九是重要的选器渠道之一，这里是全网爆款商品。&lt;/p&gt;&lt;p&gt;&lt;br /&gt;&lt;/p&gt;&lt;p&gt;&lt;span style=&quot;color:#E53333;&quot;&gt;注：推广商品需要先转链哦！&lt;/span&gt;&lt;/p&gt;', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/video/mda-kddfyxzrasacwkv5.mp4
', 3, 1, '获取进群二维码', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/257051586654096_.pic_hd_default.jpg', 4, '2020-04-14 16:04:20');
INSERT INTO jd_union.jd_member_quick_start (id, title, content, video_url, type, style, action, data, sort, create_time) VALUES (1249968644873842689, '成长任务3：进培训群实现收益技能双增长', '官方培训群，全方位的学习课程，更有大咖导师独家分享，帮助大家提升收益。', '', 3, 1, '获取进群二维码', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/activity/257051586654096_.pic_hd_default.jpg', 3, '2020-04-14 16:04:20');
INSERT INTO jd_union.jd_member_quick_start (id, title, content, video_url, type, style, action, data, sort, create_time) VALUES (1249974375077449729, '成长任务5：邀请小伙伴加入一起赚钱', '学会邀请小伙伴加入的3种方式，帮助小伙伴少钱赚钱的同时还能获得平台额外奖励，邀请越多赚得越多~', 'https://csbaic-jd-coupon.oss-cn-beijing.aliyuncs.com/video/mda-kddfyxzrasacwkv5.mp4
', 4, 1, '去邀请', '', 5, '2020-04-14 16:14:49');