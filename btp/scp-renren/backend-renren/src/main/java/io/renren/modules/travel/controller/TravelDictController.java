package io.renren.modules.travel.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.sys.entity.SysMenuEntity;
import io.renren.modules.sys.service.ShiroService;
import io.renren.modules.sys.service.SysMenuService;
import io.renren.modules.travel.entity.TravelBankEntity;
import io.renren.modules.travel.entity.TravelCompanyEntity;
import io.renren.modules.travel.entity.TravelCostTypeEntity;
import io.renren.modules.travel.entity.TravelDepartmentEntity;
import io.renren.modules.travel.service.TravelBankService;
import io.renren.modules.travel.service.TravelCompanyService;
import io.renren.modules.travel.service.TravelCostTypeService;
import io.renren.modules.travel.service.TravelDepartmentService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 申请单
 */
@RestController
@RequestMapping("/travel")
public class TravelDictController extends AbstractController {
    @Autowired
    private TravelBankService travelBankService;
    @Autowired
    private TravelCompanyService travelCompanyService;
    @Autowired
    private TravelCostTypeService travelCostTypeService;
    @Autowired
    private TravelDepartmentService travelDepartmentService;

    /**
     * 保存银行
     */
    @SysLog("保存银行")
    @PostMapping("/bank/save")
    public R bankSave(@RequestBody TravelBankEntity entity){
        ValidatorUtils.validateEntity(entity);

        entity.setId(Integer.valueOf(travelBankService.list().size()+2).toString());
        travelBankService.save(entity);

        return R.ok();
    }

    /**
     * 所有银行信息列表
     */
    @SysLog("所有银行信息列表")
    @GetMapping("/bank")
    public List<TravelBankEntity> getBanks(){
        List<TravelBankEntity> list = travelBankService.list();

        return list==null?new ArrayList<>():list;
    }

    /**
     * 所有银行信息列表
     */
    @SysLog("所有银行信息列表分页")
    @GetMapping("/bankpage")
    public R getPageBanks(@RequestParam Map<String, Object> params){
        List<TravelBankEntity> list = travelBankService.list();

        List<TravelBankEntity> result = new ArrayList<>();
        if(params.get("key")!=null) {
            String key = (String)params.get("key");
            list.forEach(e->{
                if(e.getBank().indexOf(key)!=-1 || e.getTxt50().indexOf(key)!=-1 ){
                    result.add(e);
                }
            });
        }
        PageUtils page = new PageUtils(result,result.size(),result.size(),0);

        return R.ok().put("page", page);
    }

    /**
     * 保存费用类别
     */
    @SysLog("保存费用类别")
    @PostMapping("/cost/save")
    public R costSave(@RequestBody TravelCostTypeEntity entity){
        ValidatorUtils.validateEntity(entity);

        entity.setId(Integer.valueOf(travelCostTypeService.list().size()+2).toString());
        travelCostTypeService.save(entity);

        return R.ok();
    }

    /**
     * 所有费用类别列表
     */
    @SysLog("所有费用类别列表")
    @GetMapping("/cost")
    public List<TravelCostTypeEntity> getCosts(){
        List<TravelCostTypeEntity> list = travelCostTypeService.list();

        return list==null?new ArrayList<>():list;
    }

    /**
     * 所有费用类别列表
     */
    @SysLog("所有费用类别列表分页")
    @GetMapping("/costpage")
    public R getPageCosts(@RequestParam Map<String, Object> params){
        List<TravelCostTypeEntity> list = travelCostTypeService.list();

        List<TravelCostTypeEntity> result = new ArrayList<>();
        if(params.get("key")!=null) {
            String key = (String)params.get("key");
            list.forEach(e->{
                if(e.getHkont().indexOf(key)!=-1 || e.getTxt20().indexOf(key)!=-1 ){
                    result.add(e);
                }
            });
        }
        PageUtils page = new PageUtils(result,result.size(),result.size(),0);

        return R.ok().put("page", page);
    }

    /**
     * 保存公司
     */
    @SysLog("保存公司")
    @PostMapping("/company/save")
    public R companySave(@RequestBody TravelCompanyEntity entity){
        ValidatorUtils.validateEntity(entity);

        entity.setId(Integer.valueOf(travelCompanyService.list().size()+2).toString());
        travelCompanyService.save(entity);

        return R.ok();
    }

    /**
     * 所有公司代码列表
     */
    @SysLog("所有公司代码列表")
    @GetMapping("/company")
    public List<TravelCompanyEntity> getCompanys(){
        List<TravelCompanyEntity> list = travelCompanyService.list();

        return list==null?new ArrayList<>():list;
    }

    /**
     * 所有公司代码列表
     */
    @SysLog("所有公司代码列表分页")
    @GetMapping("/companypage")
    public R getPageCompanys(@RequestParam Map<String, Object> params){
        List<TravelCompanyEntity> list = travelCompanyService.list();

        List<TravelCompanyEntity> result = new ArrayList<>();
        if(params.get("key")!=null) {
            String key = (String)params.get("key");
            list.forEach(e->{
                if(e.getBukrs().indexOf(key)!=-1 || e.getButxt().indexOf(key)!=-1 ){
                    result.add(e);
                }
            });
        }
        PageUtils page = new PageUtils(result,result.size(),result.size(),0);

        return R.ok().put("page", page);
    }

    /**
     * 保存公司
     */
    @SysLog("保存部门")
    @PostMapping("/department/save")
    public R departmentSave(@RequestBody TravelDepartmentEntity entity){
        ValidatorUtils.validateEntity(entity);

        entity.setId(Integer.valueOf(travelDepartmentService.list().size()+2).toString());
        travelDepartmentService.save(entity);

        return R.ok();
    }

    /**
     * 所有部门编码列表
     */
    @SysLog("所有部门编码列表")
    @GetMapping("/department")
    public List<TravelDepartmentEntity> getDemartments(){
        List<TravelDepartmentEntity> list = travelDepartmentService.list();

        return list==null?new ArrayList<>():list;
    }

    /**
     * 所有部门编码列表
     */
    @SysLog("所有部门编码列表分页")
    @GetMapping("/departmentpage")
    public R getPageDepartments(@RequestParam Map<String, Object> params){
        List<TravelDepartmentEntity> list = travelDepartmentService.list();

        List<TravelDepartmentEntity> result = new ArrayList<>();
        if(params.get("key")!=null) {
            String key = (String)params.get("key");
            list.forEach(e->{
                if(e.getDeprs().indexOf(key)!=-1 || e.getDepxt().indexOf(key)!=-1 ){
                    result.add(e);
                }
            });
        }
        PageUtils page = new PageUtils(result,result.size(),result.size(),0);

        return R.ok().put("page", page);
    }
}
