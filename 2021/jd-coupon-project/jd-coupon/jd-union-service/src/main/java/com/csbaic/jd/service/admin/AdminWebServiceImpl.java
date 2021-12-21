package com.csbaic.jd.service.admin;

import com.csbaic.jd.dto.admin.Menu;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminWebServiceImpl implements AdminWebService {

    private static Long menuId = 1L;

    @Override
    public List<Menu> getMenu(Long userId) {
        List<Menu> menus = new ArrayList<>();



        getBannerMenu(menus);
        getActivityMenu(menus);
        getNewsMenu(menus);
        getMessageMenu(menus);


        return menus;
    }


    /**
     * 获取Banner菜单
     * @return
     */
    public void getBannerMenu(List<Menu> out){

        Menu root = new Menu();
        root.setIcon("icon-bannner");
        root.setName("轮播图");
        root.setPath("/banners");

        root.addChild(create("/banners/list", "添加轮播图列表", "icon-bannner", null));
        root.addChild(create("/banners/add", "添加轮播图", "icon-bannner", null));

        out.add(root);
    }

    /**
     * 获取活动菜单
     * @return
     */
    public void getActivityMenu(List<Menu> out){

        Menu root = new Menu();
        root.setIcon("icon-activity");
        root.setName("活动");
        root.setPath("/activities");

        root.addChild(create("/activities/list", "活动列表", "icon-activity", null));
        root.addChild(create("/activities/add", "添加活动", "icon-activity", null));

        out.add(root);

    }

    /**
     * 获取快报菜单
     */
    public void getNewsMenu(List<Menu> out){

    }

    /**
     * 获取消息菜单
     * @return
     */
    public void getMessageMenu(List<Menu> out){

    }


    public static Menu create(String path, String name, String icon, Menu.Meta meta){
        return new Menu(menuId++, path, name, icon, meta);
    }
}
