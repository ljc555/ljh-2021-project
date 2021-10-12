package io.renren.modules.travel.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 09 14:55
 */
@Data
@TableName("travel_apply")
public class TravelApplyEntity {
    @TableId
    private String id;
    @NotBlank(message="申请单号不能为空")
    private String ecnum;
    @NotBlank(message="公司代码不能为空")
    private String bukrs;
    @NotBlank(message="部门编码不能为空")
    private String depart;
    @NotBlank(message="申请人不能为空")
    private String pernr;
    private String comment;
    @NotBlank(message="创建日期不能为空")
    private Date erdat;
    @NotBlank(message="创建时间不能为空")
    private Date ertim;
    private Integer loevm = 0;
}
