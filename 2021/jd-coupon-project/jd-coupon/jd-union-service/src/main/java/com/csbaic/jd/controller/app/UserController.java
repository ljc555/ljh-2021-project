package com.csbaic.jd.controller.app;

import com.csbaic.auth.accesstoken.AccessTokenService;
import com.csbaic.common.result.Result;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.user.MemberService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@ResponseResult
@RequestMapping("/users")
@Api(value = "用户接口", tags = "用户接口")
public class UserController {


    @Autowired
    private IUserService userService;



    @Autowired
    private AccessTokenService accessTokenService;


    @Autowired
    private MemberService memberService;


    /**
     * 更新用户信息
     * @param user 用户信息
     * @return
     */
    @ApiOperation("更新用户信息")
    @PutMapping("/update_user_info")
    public void updateUserInfo(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId, @RequestBody UpdateUserInfo user){
          userService.updateUserInfo(userId, user);
    }


    /**
     * 获取用户信息
     * @return
     */
    @ApiOperation("获取我的用户信息")
    @GetMapping("/mine")
    public MineUserInfo getMyUserInfo(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId){
        return userService.getMyUserInfo(userId);
    }





    /**
     * 注册用户
     * @param user 用户信息
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/wechat")
    public LoginInfo createUserByWechat(@RequestBody CreateWechatUser user){
        return userService.createUserByWechat(user);
    }

    /**
     * 查询用户通讯录
     * @return
     */
    @ApiOperation("查询用户通讯录")
    @PostMapping("/contacts")
    public UserContacts getMembersOf(@ApiParam(hidden = true) @AuthenticationPrincipal(expression = "id") Long userId,@RequestBody QueryContacts contacts){
        return memberService.getUserContacts(userId, contacts);
    }



    /**
     * 使用code获取用户信息
     * @param code 邀请码
     * @return
     */
    @ApiOperation("使用邀请码获取用户信息")
    @PostMapping("/get_by_invitation_code/{code}")
    public SimpleUserInfo createUserByWechat(@PathVariable("code") String code){
        return userService.getUserInfoByInvitationCode(code);
    }

//
//    /**
//     * 查询用户通讯录
//     * @return
//     */
//    @ApiOperation("生成token（测试）")
//    @PostMapping("/{userId}")
//    public Result createToken(@PathVariable("userId") Long userId){
//        Map<String, Object> p = new HashMap<>();
//        p.put("user_id", userId);
//        return Result.ok(accessTokenService.encode(p));
//    }

}
