package com.csbaic.jd.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    /**
     * 判断{@code left}是否大于{@code right}
     * @param left
     * @param right
     */
    public static boolean gt(BigDecimal left, BigDecimal right){
        if(left == null || right == null){
            throw new NullPointerException("left or right is null");
        }

        return left.compareTo(right) > 0;
    }

    /**
     * 判断{@code left}是否小于{@code right}
     * @param left
     * @param right
     */
    public static boolean lt(BigDecimal left, BigDecimal right){
        return !gt(left, right);
    }

    /**
     * 判断{@code left}是否等于{@code right}
     * @param left
     * @param right
     */
    public static boolean eq(BigDecimal left, BigDecimal right){
        if(left == null || right == null){
            throw new NullPointerException("left or right is null");
        }

        return left.compareTo(right) == 0;
    }
}
