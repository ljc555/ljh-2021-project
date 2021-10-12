package io.renren.modules.travel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 09 14:53
 */
@Data
@TableName("travel_user")
public class TravelUserEntity {
    @TableId
    private String id;
    @NotBlank(message="人员信息不能为空")
    private String pernr;
    @NotBlank(message="公司代码不能为空")
    private String bukrs;
    @NotBlank(message="人员姓名不能为空")
    private String name;
    @NotBlank(message="部门编码不能为空")
    private String depart;
    private String name1;
    @NotBlank(message="银行账号不能为空")
    private String bankc;
    @NotBlank(message="银行代码不能为空")
    private String bank;
    private String tel;
}
