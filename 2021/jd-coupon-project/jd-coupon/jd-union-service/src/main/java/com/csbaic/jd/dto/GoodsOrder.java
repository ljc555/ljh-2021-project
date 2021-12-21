package com.csbaic.jd.dto;

 import com.csbaic.jd.service.order.handle.OrderMetadata;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class GoodsOrder implements OrderMetadata{

    private Long finishTime;
    private Integer orderEmt;
    private Long orderId;
    private Long orderTime;
    private Long parentId;
    private String payMonth;
    private Integer plus;
    private Long popId;
    private SkuInfo[] skuList;
    private Long unionId;
    private String ext1;
    private Integer validCode;


    @Override
    public List<SkuMetadata> getSkuMetadata() {
        return Arrays.asList(skuList);
    }
}
