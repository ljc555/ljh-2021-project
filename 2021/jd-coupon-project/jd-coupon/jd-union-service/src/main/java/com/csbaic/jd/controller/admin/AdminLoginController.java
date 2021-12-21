package com.csbaic.jd.controller.admin;

import com.csbaic.jd.service.LoginService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/login")
@Api(value = "管理员登陆", tags = "管理员登陆")
@ResponseResult
public class AdminLoginController {

    @Autowired
    private LoginService loginService;





}
