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
 * @Date 2020-04- 09 14:56
 */
@Data
@TableName("travel_apply_item")
public class TravelApplyItemEntity {
    @TableId
    private String id;
    @NotBlank(message="申请单号不能为空")
    private String ecnum;
    @NotBlank(message="行号不能为空")
    private String econr;
    @NotBlank(message="开始日期不能为空")
    private Date bdat;
    @NotBlank(message="结束日期不能为空")
    private Date edat;
    @NotBlank(message="天数不能为空")
    private String days;
    @NotBlank(message="每天单价不能为空")
    private String price;
    @NotBlank(message="费用类型不能为空")
    private String hkont;
    @NotBlank(message="目的地不能为空")
    private String dest;
    @NotBlank(message="酒店不能为空")
    private String hotel;
    @NotBlank(message="金额不能为空")
    private String dmbtr;
    private String comment;
    private String loevm = "0";
    @NotBlank(message="创建日期不能为空")
    private Date erdat;
    @NotBlank(message="创建时间不能为空")
    private Date ertim;
    private String deprs;
    private String yearmonth;
}
