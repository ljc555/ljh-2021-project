package com.csbaic.jd.team;

import com.csbaic.jd.JdSocialApplication;
import com.csbaic.jd.commission.CommissionConfiguration;
import com.csbaic.jd.service.ITeamMemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {JdSocialApplication.class})
public class TeamMemberTests {

    @Autowired
    private ITeamMemberService teamMemberService;

    @Test
    public void testTeamMember(){
        teamMemberService.addMember(1L, 2L);
        teamMemberService.addMember(2L, 3L);
        teamMemberService.addMember(3L, 4L);
        teamMemberService.addMember(4L, 5L);
    }
}
