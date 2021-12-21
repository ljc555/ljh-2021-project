package com.csbaic.jd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 团队人员关系表
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
@TableName("jd_team_member")
public class TeamMemberEntity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 领队id
     */
    private Long leaderId;

    /**
     * 成员id
     */
    private Long memberId;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Long leaderId) {
        this.leaderId = leaderId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public static final String ID = "id";

    public static final String LEADER_ID = "leader_id";

    public static final String MEMBER_ID = "member_id";

    public static final String LEVEL = "level";

    public static final String CREATE_TIME = "create_time";

    @Override
    public String toString() {
        return "TeamMemberEntity{" +
        "id=" + id +
        ", leaderId=" + leaderId +
        ", memberId=" + memberId +
        ", level=" + level +
        ", createTime=" + createTime +
        "}";
    }
}
