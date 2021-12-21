package com.csbaic.jd.service.order.handle;

import java.util.List;

/**
 * Created by yjwfn on 2020/2/15.
 */
public interface OrderMetadata  {


    /***
     * 订单id
     */
    Long getOrderId();


    /**
     * 订单有效码
     * @return
     */
    Integer getValidCode();


    /**
     * 下单时间
     * @return
     */
    Long getOrderTime();

    /**
     * 完成时间
     * @return
     */
    Long getFinishTime();


    /**
     * 获取订单sku
     * @return
     */
    List<SkuMetadata> getSkuMetadata();

    /**
     * 订单SKU信息
     */
    interface SkuMetadata {

        /**
         * 商品ID
         * @return
         */
        Long getSkuId();

        /**
         * 商品名称
         * @return
         */
        String getSkuName();
        /**
         * 商品数量
         * @return
         */
        Long getSkuNum();

        /**
         *
         商品已退货数量
         * @return
         */
        Long getSkuReturnNum();

        /**
         * 联盟标签数据（整型的二进制字符串，目前返回16位：0000000000000001。数据从右向左进行，每一位为1表示符合联盟的标签特征，第1位：红包，第2位：组合推广，第3位：拼购，第5位：有效首次购（0000000000011XXX表示有效首购，最终奖励活动结算金额会结合订单状态判断，以联盟后台对应活动效果数据报表https://union.jd.com/active为准）,第8位：复购订单，第9位：礼金，第10位：联盟礼金，第11位：推客礼金。例如：0000000000000001:红包订单，0000000000000010:组合推广订单，0000000000000100:拼购订单，0000000000011000:有效首购，0000000000000111：红包+组合推广+拼购等）
         * @return
         */
        String getUnionTag();

        /**
         * sku维度的有效码（-1：未知,2.无效-拆单,3.无效-取消,4.无效-京东帮帮主订单,5.无效-账号异常,6.无效-赠品类目不返佣,7.无效-校园订单,8.无效-企业订单,9.无效-团购订单,10.无效-开增值税专用发票订单,11.无效-乡村推广员下单,12.无效-自己推广自己下单,13.无效-违规订单,14.无效-来源与备案网址不符,15.待付款,16.已付款,17.已完成,18.已结算（5.9号不再支持结算状态回写展示））
         * @return
         */
        Integer getValidCode();

        /**
         * 子联盟ID(需要联系运营开放白名单才能拿到数据)
         */
        String getSubUnionId();

        /**
         * 实际计算佣金的金额。订单完成后，会将误扣除的运费券金额更正。如订单完成后发生退款，此金额会更新。
         * @return
         */
        Double getActualCosPrice() ;

        /**
         * 推客获得的实际佣金（实际计佣金额*佣金比例*最终比例）。如订单完成后发生退款，此金额会更新。
         * @return
         */
        Double getActualFee();

        /**
         * 佣金比例
         * @return
         */
        Double getCommissionRate() ;

        /**
         *
         预估计佣金额，即用户下单的金额(已扣除优惠券、白条、支付优惠、进口税，未扣除红包和京豆)，有时会误扣除运费券金额，完成结算时会在实际计佣金额中更正。如订单完成前发生退款，此金额也会更新。
         * @return
         */
        Double getEstimateCosPrice() ;

        /**
         * 推客的预估佣金（预估计佣金额*佣金比例*最终比例），如订单完成前发生退款，此金额也会更新。
         * @return
         */
        Double getEstimateFee() ;

        /**
         * 最终比例（分成比例+补贴比例）
         * @return
         */
        Double getFinalRate() ;

        /**
         * 商品单价
         * @return
         */
        Double getPrice();

    }
}
