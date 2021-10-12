package io.renren.modules.travel.form;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 13 16:28
 */
@Data
public class TravelApplyQueryForm {
    private String ecnum;
    private Date bdat;
    private Date edat;
    private Integer loevm;
    private String hkont;
    private String pernr;
}
