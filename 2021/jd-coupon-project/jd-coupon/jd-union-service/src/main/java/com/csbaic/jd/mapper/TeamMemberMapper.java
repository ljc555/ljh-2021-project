package com.csbaic.jd.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.csbaic.jd.entity.TeamMemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 团队人员关系表 Mapper 接口
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface TeamMemberMapper extends BaseMapper<TeamMemberEntity> {

    /**
     * 查找最近且身份为{@code identify}的领导人
     * @param memberId
     * @return
     */
    TeamMemberEntity findNearestLeaderOfMemberAndIdentify(@Param("memberId") Long memberId, @Param("identify") int identify);


    /**
     * 查看用户的成员数量（不包括自己）
     * @param leaderId
     * @param identify
     * @param level
     * @return
     */
    int countByLeaderIdAndUserIdentifyAndLevel(@Param("leaderId") Long leaderId,@Param("identify")  Integer identify,@Param("level")  Integer level);

}
