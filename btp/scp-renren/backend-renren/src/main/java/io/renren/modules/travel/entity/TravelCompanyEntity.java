package io.renren.modules.travel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 09 14:54
 */
@Data
@TableName("travel_company")
public class TravelCompanyEntity {
    @TableId
    private String id;
    @NotBlank(message="公司代码不能为空")
    private String bukrs;
    @NotBlank(message="公司名称不能为空")
    private String butxt;
}
