package io.renren.modules.travel.form;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:51
 */
@Data
public class TravelApplyItemForm {
    private String ecnum;
    private String econr;
    private Date bdat;
    private Date edat;
    private String days;
    private String price;
    private String hkont;
    private String dest;
    private String hotel;
    private String dmbtr;
    private String comment;
    private String deprs;
}
