package io.renren.modules.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.travel.dto.TravelApplyDto;
import io.renren.modules.travel.form.TravelApplyForm;
import io.renren.modules.travel.form.TravelApplyQueryForm;
import io.renren.modules.travel.service.TravelApplyService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请单
 */
@RestController
@RequestMapping("/travel")
public class TravelApplyController extends AbstractController {
    @Autowired
    private TravelApplyService travelApplyService;

    /**
     * 保存申请单
     */
    @SysLog("保存申请单")
    @ApiOperation("创建申请单")
    @PostMapping("/apply/save")
    public R save(@RequestBody TravelApplyForm form){
        //数据校验
        verifyForm(form);

        String ecnum = travelApplyService.save(form);

        TravelApplyDto applyDto = travelApplyService.getApplyByEcnum(ecnum);

        if(applyDto == null) {
            return R.error("创建申请单失败.");
        }

        return R.ok().put("apply", applyDto);
    }

    /**
     * 修改申请单
     */
    @SysLog("修改申请单")
    @ApiOperation("修改申请单")
    @PostMapping("/apply/update/{ecnum}")
    public R update(@PathVariable("ecnum") String ecnum,@RequestBody TravelApplyForm form){
        if(StringUtils.isEmpty(ecnum)) {
            return R.error("申请单号ecnum不能为空.");
        }

        //数据校验

        form.setEcnum(ecnum);
        travelApplyService.updateById(form);

        return R.ok();
    }

    /**
     * 获取申请单信息
     * @param ecnum
     * @return
     */
    @SysLog("查看申请单")
    @ApiOperation("查看申请单")
    @GetMapping("/apply/info/{ecnum}")
    public R getApply(@PathVariable("ecnum") String ecnum){
        if(StringUtils.isEmpty(ecnum)) {
            return R.error("申请单号ecnum不能为空.");
        }

        TravelApplyDto applyDto = travelApplyService.getApplyByEcnum(ecnum);

        if(applyDto == null) {
            return R.error("申请单:" + ecnum + " 不存在.");
        }

        return R.ok().put("apply", applyDto);
    }

    /**
     * 修改
     */
    @SysLog("删除申请单")
    @ApiOperation("删除申请单")
    @PostMapping("/apply/delete/{ecnum}")
    public R delete(@PathVariable("ecnum") String ecnum){
        if(StringUtils.isEmpty(ecnum)) {
            return R.error("申请单号ecnum不能为空.");
        }

        travelApplyService.deleteByEcnum(ecnum);

        return R.ok();
    }

    /**
     * 查询申请单列表
     */
    @SysLog("查询申请单")
    @ApiOperation("查询申请单")
    @GetMapping("/apply/query")
    public R query(@RequestBody TravelApplyQueryForm form,
                   @RequestParam(required = false, defaultValue = "0") final int page,
                   @RequestParam(required = false, defaultValue = "10") final int rows){
        PageUtils pageUtil = travelApplyService.queryApply(form,page,rows);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 所有银行信息申请单列表
     */
    @SysLog("获取申请单列表")
    @GetMapping("/apply/list")
    public R list(@RequestParam Map<String, Object> params){
        TravelApplyForm form = new TravelApplyForm();
        int page = 0;
        int limit = 10;

        if(params.get("key") !=null && String.valueOf(params.get("key"))!=""){
            form.setEcnum((String)params.get("key"));
        }

        if(params.get("page") !=null) {
            page = Integer.valueOf((String)params.get("page"));
        }
        if(params.get("limit") !=null) {
            limit = Integer.valueOf((String)params.get("limit"));
        }
        PageUtils pageUtil = travelApplyService.list(form,page,limit);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(TravelApplyForm form) {
        if(form == null) {
            throw new RRException("参数不能为空");
        }

/*
        if (StringUtils.isBlank(form.getBukrs())) {
            throw new RRException("公司代码不能为空");
        }

        if (StringUtils.isBlank(form.getDepart())) {
            throw new RRException("部门编码不能为空");
        }
*/

        if (StringUtils.isBlank(form.getPernr())) {
            throw new RRException("申请人不能为空");
        }
    }
}
