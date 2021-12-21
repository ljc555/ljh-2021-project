package com.csbaic.jd.service.team;

import com.csbaic.jd.service.team.bean.MemberNode;
import lombok.Data;

@Data
public class MemberInfo {

        private MemberNode root;

        /**
         * {@link #root} 的直属超级会员
         */
        private MemberNode directlySuperMember;

        /**
         * {@link #root} 的直属导师
         */
        private MemberNode directlyTeacher;

        /**
         * {@link #root} 的非直属导师
         */
        private MemberNode indirectlyTeacher;
        /**
         * {@link #directlyTeacher} 或 {@link #indirectlyTeacher} 的直属导师
         */
        private MemberNode directlyTeacherOfTeacher;

}