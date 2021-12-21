package com.csbaic.jd.service.admin;

import com.csbaic.jd.dto.admin.Menu;

import java.util.List;

public interface AdminWebService {


    /**
     * 获取管理系统菜单
     * @param userId
     * @return
     */
    List<Menu> getMenu(Long userId);
}
