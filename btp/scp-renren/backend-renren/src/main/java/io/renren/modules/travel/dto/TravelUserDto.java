package io.renren.modules.travel.dto;

import lombok.Data;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 13 14:02
 */
@Data
public class TravelUserDto {
    private String id;
    private String pernr;
    private TravelCompanyDto bukrs;
    private String name;
    private TravelDepartmentDto depart;
    private String name1;
    private TravelBankDto bank;
    private String tel;
}
