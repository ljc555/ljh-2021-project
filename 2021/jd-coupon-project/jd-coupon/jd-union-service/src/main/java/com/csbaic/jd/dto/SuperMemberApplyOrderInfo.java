package com.csbaic.jd.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SuperMemberApplyOrderInfo {

    /**
     * 申请单号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 申请人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long ownerId;

    /**
     * 申请人姓名
     */
    private String ownerName;

    /**
     * 群组id
     */
    private Integer groupId;

    /**
     * 申请图
     */
    private List<String> imageUrls;

    /**
     * 申请状态
     */
    private Integer status;


    /**
     * 申请状态
     */
    private String statusDesc;

    /**
     * 备注
     */
    private String remark;

    /**
     * 申请时间
     */
    private LocalDateTime createTime;


}
