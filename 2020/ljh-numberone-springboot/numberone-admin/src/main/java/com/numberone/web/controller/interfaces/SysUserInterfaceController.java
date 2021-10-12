package com.numberone.web.controller.interfaces;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.StringUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.framework.shiro.service.SysPasswordService;
import com.numberone.framework.util.ShiroUtils;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysPostService;
import com.numberone.system.service.ISysRoleService;
import com.numberone.system.service.ISysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 用户信息
 * 
 * @author numberone
 */
@Controller
@RequestMapping("/interface/user")
public class SysUserInterfaceController extends BaseController
{
    private String prefix = "system/user";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private SysPasswordService passwordService;

    @RequestMapping("/{userId}")
    @ResponseBody
    public SysUser getUser(@PathVariable("userId") Long userId, ModelMap mmap)
    {
        return userService.selectUserById(userId);
    }
}