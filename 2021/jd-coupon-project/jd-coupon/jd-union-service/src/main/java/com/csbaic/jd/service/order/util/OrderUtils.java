package com.csbaic.jd.service.order.util;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * 订单工具类
 */
public class OrderUtils {

    /**
     * 联盟标签数据（整型的二进制字符串，目前返回16位：0000000000000001。
     * 数据从右向左进行，每一位为1表示符合联盟的标签特征，
     * 第1位：红包，
     * 第2位：组合推广，
     * 第3位：拼购，
     * 第5位：有效首次购（0000000000011XXX表示有效首购，最终奖励活动结算金额会结合订单状态判断，以联盟后台对应活动效果数据报表https://union.jd.com/active为准）,
     * 第8位：复购订单，
     * 第9位：礼金，
     * 第10位：联盟礼金，
     * 第11位：推客礼金。
     * 例如：0000000000000001:红包订单，0000000000000010:组合推广订单，0000000000000100:拼购订单，0000000000011000:有效首购，0000000000000111：红包+组合推广+拼购等）
     */
    public static final List<String> resolveUnionTag(String unionTag){
        List<String> tag = new ArrayList<>();

        if (Strings.isNullOrEmpty(unionTag)) {
            return tag;
        }

        BitSet bitSet = new BitSet(Integer.parseInt(unionTag));
        if(bitSet.get(0)){
            tag.add("红包");
        }

        if(bitSet.get(1)){
            tag.add("组合推广");
        }

        if(bitSet.get(2)){
            tag.add("拼购");
        }

        if(bitSet.get(4)){
            tag.add("首购");
        }

        if(bitSet.get(5)){
            tag.add("复购");
        }


        if(bitSet.get(8)){
            tag.add("礼金");
        }

        if(bitSet.get(9)){
            tag.add("联盟礼金");
        }

        if(bitSet.get(9)){
            tag.add("推客礼金");
        }


        return tag;
    }

}
