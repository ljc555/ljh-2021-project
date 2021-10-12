package io.renren.modules.travel.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.travel.entity.*;
import io.renren.modules.travel.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 申请人信息
 */
@RestController
@RequestMapping("/travel")
public class TravelUserController extends AbstractController {
    @Autowired
    private TravelUserService travelUserService;

    /**
     * 查看用户信息
     */
    @SysLog("查看用户信息")
    @GetMapping("/user/{pernr}")
    public R getUser(@PathVariable("pernr") String pernr){
        if(StringUtils.isEmpty(pernr)) {
            return R.error("pernr 为空.");
        }

        TravelUserEntity user = travelUserService.queryByPernr(pernr);

        if(user == null) {
            return R.error("用户:" + pernr + " 不存在.");
        }

        return R.ok().put("user", user);
    }

    /**
     * 所有用户列表
     */
    @SysLog("所有用户列表")
    @GetMapping("/users")
    public R list(@RequestParam Map<String, Object> params){
        List<TravelUserEntity> list = travelUserService.list();

        List<TravelUserEntity> result = new ArrayList<>();
        if(params.get("key")!=null) {
            String key = (String)params.get("key");
            list.forEach(e->{
                if(e.getPernr().indexOf(key)!=-1 || e.getName().indexOf(key)!=-1 || e.getName1().indexOf(key)!=-1){
                    result.add(e);
                }
            });
        }
        PageUtils page = new PageUtils(result,result.size(),result.size(),0);

        return R.ok().put("page", page);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @PostMapping("/user/save")
    public R userSave(@RequestBody TravelUserEntity entity){
        ValidatorUtils.validateEntity(entity);

        entity.setId(Integer.valueOf(travelUserService.list().size()+2).toString());
        travelUserService.save(entity);

        return R.ok();
    }
}
