package com.csbaic.jd.service.order;

public interface SubUnionIdConverter {


    /**
     * 将{@link SubUnionId} 转换成字符串
     * @param subUnionId
     * @return
     */
    String covert(SubUnionId subUnionId);

    /**
     * 将 字符串转换成 {@link SubUnionId}
     * @param subUnionId
     * @return
     */
    SubUnionId covert(String subUnionId);
}
