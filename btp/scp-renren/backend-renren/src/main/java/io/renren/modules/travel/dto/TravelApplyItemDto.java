package io.renren.modules.travel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 10 17:54
 */
@Data
public class TravelApplyItemDto implements Serializable {
    private String id;
    private String ecnum;
    private String econr;
    private String bdat;
    private String edat;
    private String days;
    private String price;
    private TravelCostTypeDto hkont;
    private String dest;
    private String hotel;
    private String dmbtr;
    private String comment;
    private String loevm = "0";
    private String lovemname = "已创建";
    private String erdat;
    private String ertim;

}
