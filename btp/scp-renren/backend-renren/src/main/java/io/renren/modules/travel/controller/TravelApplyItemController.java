package io.renren.modules.travel.controller;

import io.renren.common.annotation.SysLog;
import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.travel.dto.TravelApplyDto;
import io.renren.modules.travel.dto.TravelApplyItemDto;
import io.renren.modules.travel.form.TravelApplyForm;
import io.renren.modules.travel.form.TravelApplyItemForm;
import io.renren.modules.travel.service.TravelApplyItemService;
import io.renren.modules.travel.service.TravelApplyService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 申请单行
 */
@RestController
@RequestMapping("/travel")
public class TravelApplyItemController extends AbstractController {
    @Autowired
    private TravelApplyItemService travelApplyItemService;
    @Autowired
    private TravelApplyService travelApplyService;

    /**
     * 保存申请单行
     */
    @SysLog("保存申请单行")
    @PostMapping("/applyItem/save")
    public R save(@RequestBody TravelApplyItemForm form){
        if (StringUtils.isBlank(form.getEcnum())) {
            throw new RRException("申请单号不能为空");
        }

        //数据校验
        verifyForm(form);

        TravelApplyDto applyDto = travelApplyService.getApplyByEcnum(form.getEcnum());
        form.setDeprs(applyDto.getPernr().getDepart().getDeprs());
        String econr = travelApplyItemService.save(form);

        TravelApplyItemDto applyItemDto = travelApplyItemService.getApplyItemByEconr(econr);

        if(applyItemDto == null) {
            return R.error("申请单行:" + econr + " 不存在.");
        }

        return R.ok().put("apply", applyItemDto);
    }

    /**
     * 保存申请单行
     */
    @SysLog("保存申请单行多行")
    @PostMapping("/applyItem/saveItems")
    public R saveItems(@RequestBody TravelApplyItemForm[] forms){
        if(forms == null) {
            throw new RRException("参数不能为空");
        }

        for(int i=0;i<forms.length;i++) {

            if (StringUtils.isBlank(forms[i].getEcnum())) {
                throw new RRException("申请单号不能为空");
            }

            //数据校验
            verifyForm(forms[i]);

            TravelApplyDto applyDto = travelApplyService.getApplyByEcnum(forms[i].getEcnum());
            forms[i].setDeprs(applyDto.getPernr().getDepart().getDeprs());
            String econr = travelApplyItemService.save(forms[i]);

            TravelApplyItemDto applyItemDto = travelApplyItemService.getApplyItemByEconr(econr);

            if (applyItemDto == null) {
                return R.error("申请单行:" + econr + " 不存在.");
            }
        }

        //return R.ok().put("apply", applyItemDto);
        return R.ok();
    }

    /**
     * 修改申请单行
     */
    @SysLog("修改申请单行")
    @PostMapping("/applyItem/update/{econr}")
    public R update(@PathVariable("econr") String econr, @RequestBody TravelApplyItemForm form){
        if(StringUtils.isEmpty(econr)) {
            return R.error("申请单行号econr不能为空.");
        }

        //数据校验
        verifyForm(form);

        form.setEconr(econr);
        travelApplyItemService.updateById(form);

        return R.ok();
    }

    @SysLog("查看申请单行")
    @GetMapping("/applyItem/info/{econr}")
    public R getApply(@PathVariable("econr") String econr){
        if(StringUtils.isEmpty(econr)) {
            return R.error("申请单行号econr不能为空.");
        }

        TravelApplyItemDto applyItemDto = travelApplyItemService.getApplyItemByEconr(econr);

        if(applyItemDto == null) {
            return R.error("申请单行:" + econr + " 不存在.");
        }

        return R.ok().put("apply", applyItemDto);
    }

    /**
     * 删除
     */
    @SysLog("删除申请单行")
    @PostMapping("/applyItem/delete/{econr}")
    public R delete(@PathVariable("econr") String econr){
        if(StringUtils.isEmpty(econr)) {
            return R.error("申请单行号econr不能为空.");
        }

        travelApplyItemService.deleteByeConr(econr);

        return R.ok();
    }

    /**
     * 查询申请单列表
     */
    @SysLog("查询申请单行")
    @ApiOperation("查询申请单行")
    @GetMapping("/applyItem/query")
    public R query(@RequestBody TravelApplyItemForm form,
                   @RequestParam(required = false, defaultValue = "0") final int page,
                   @RequestParam(required = false, defaultValue = "10") final int rows){
        PageUtils pageUtil = travelApplyItemService.queryApplyItem(form,page,rows);

        return R.ok().put("page", pageUtil);
    }

    /**
     * 所有银行信息申请单列表
     */
    @SysLog("获取申请单行列表")
    @GetMapping("/applyItem/list")
    public R list(@RequestParam Map<String, Object> params){
        TravelApplyItemForm form = new TravelApplyItemForm();
        int page = 0;
        int limit = 10;

        if(params.get("key") !=null) {
            form.setEcnum((String)params.get("key"));
        }

        if(params.get("page") !=null) {
            page = Integer.valueOf((String)params.get("page"));
        }
        if(params.get("limit") !=null) {
            limit = Integer.valueOf((String)params.get("limit"));
        }
        PageUtils pageUtil = travelApplyItemService.queryApplyItem(form,page,limit);

        return R.ok().put("page", pageUtil);
    }

    @SysLog("获取申请单下行列表")
    @GetMapping("/applyItem/list/{ecnum}")
    public List<TravelApplyItemDto> itemList(@PathVariable("ecnum") String ecnum){
        List<TravelApplyItemDto> list = travelApplyItemService.getItemsByEcnum(ecnum);

        return list==null?new ArrayList<>():list;
    }

    /**
     * 验证参数是否正确
     */
    private void verifyForm(TravelApplyItemForm form) {
        if(form == null) {
            throw new RRException("参数不能为空");
        }

        if (form.getBdat()==null) {
            throw new RRException("'开始日期'不能为空");
        }

        if (form.getEdat()==null) {
            throw new RRException("结束日期不能为空");
        }

        if (StringUtils.isBlank(form.getDays())) {
            throw new RRException("天数不能为空");
        }

        if (StringUtils.isBlank(form.getPrice())) {
            throw new RRException("'每天单价不能为空");
        }

        if (StringUtils.isBlank(form.getHkont())) {
            throw new RRException("费用类型不能为空");
        }

        if (StringUtils.isBlank(form.getDest())) {
            throw new RRException("目的地不能为空");
        }

        if (StringUtils.isBlank(form.getHotel())) {
            throw new RRException("酒店不能为空");
        }

        if (StringUtils.isBlank(form.getDmbtr())) {
            throw new RRException("'金额'不能为空");
        }
    }
}
