package io.renren.modules.travel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 09 14:55
 */
@Data
@TableName("travel_cost_type")
public class TravelCostTypeEntity {
    @TableId
    private String id;
    @NotBlank(message="科目类型不能为空")
    private String hkont;
    @NotBlank(message="科目描述不能为空")
    private String txt20;
}
