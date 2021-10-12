package io.renren.modules.travel.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 10 17:42
 */
@Data
public class TravelApplyDto implements Serializable {
    private String id;
    private String ecnum;
    private TravelCompanyDto bukrs;
    private TravelDepartmentDto depart;
    private TravelUserDto pernr;
    private String comment;
    private String erdat;
    private String ertim;
    private Integer lovem;
    private String lovemname;
    private List<TravelApplyItemDto> travelApplyItemDto;
}
