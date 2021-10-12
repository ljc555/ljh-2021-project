package io.renren.modules.travel.dto;

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
public class TravelDepartmentDto {
    private String deprs;
    private String depxt;
}
