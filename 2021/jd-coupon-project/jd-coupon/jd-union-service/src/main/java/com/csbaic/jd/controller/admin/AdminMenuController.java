package com.csbaic.jd.controller.admin;

import com.csbaic.jd.dto.admin.Menu;
import com.csbaic.jd.service.admin.AdminWebService;
import com.csbaic.web.annotation.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/routes")
@Api(value = "后台菜单", tags = "后台菜单")
@ResponseResult
public class AdminMenuController {


    @Autowired
    private AdminWebService menuService;


    /**
     * 获取管理平台菜单
     */
    @ApiOperation("添加快报")
    @PostMapping()
    public List<Menu> getMenu(@AuthenticationPrincipal(expression = "id") Long userId){
        return menuService.getMenu(userId);
    }


}
