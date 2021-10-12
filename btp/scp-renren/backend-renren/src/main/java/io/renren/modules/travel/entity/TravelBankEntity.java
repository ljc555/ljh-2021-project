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
@TableName("travel_bank")
public class TravelBankEntity {
    @TableId
    private String id;
    @NotBlank(message="银行不能为空")
    private String bank;
    @NotBlank(message="银行名称不能为空")
    private String txt50;
}
