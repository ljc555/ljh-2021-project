package io.renren.modules.travel.controller;

import io.renren.common.exception.RRException;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.sys.controller.AbstractController;
import io.renren.modules.travel.dto.TravelApplyDto;
import io.renren.modules.travel.form.TravelApplyForm;
import io.renren.modules.travel.form.TravelApplyQueryForm;
import io.renren.modules.travel.service.TravelApplyService;
import io.renren.modules.travel.service.TravelReportService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 申请单统计
 */
@RestController
@RequestMapping("/travel")
public class TravelReportController extends AbstractController {
    @Autowired
    private TravelReportService travelReportService;

    /**
     * 酒店费用统计数据
     */
    @ApiOperation("费用类型统计")
    @GetMapping("/report/cost")
    public R cost(){
        List list = travelReportService.queryReportForCost();

        return R.ok().put("results", list);
    }

    /**
     * 部门费用统计数据
     */
    @ApiOperation("部门费用统计")
    @GetMapping("/report/dept")
    public R department(){
        List list = travelReportService.queryReportForDept();

        return R.ok().put("results", list);
    }

    /**
     * 月度费用统计数据
     */
    @ApiOperation("月度费用统计")
    @GetMapping("/report/month")
    public R yearmonth(){
        List list = travelReportService.queryReportForMonth();

        return R.ok().put("results", list);
    }

    /**
     * 按部门按费用类型统计数据
     */
    @ApiOperation("按部门按费用类型费用统计")
    @GetMapping("/report/deptAndCost")
    public R deptAndCost(){
        List list = travelReportService.queryReportForDeptAndCost();

        return R.ok().put("results", list);
    }

    /**
     * 按部门按费用类型统计数据
     */
    @ApiOperation("按部门按费用类型费用统计")
    @GetMapping("/report/deptAndCostMap")
    public R deptAndCostMap(){
        List<Map> result = travelReportService.queryReportForDeptAndCostMap();

        return R.ok().put("results", result);
    }

    /**
     * 汇总
     */
    @ApiOperation("汇总")
    @GetMapping("/report/all")
    public R all(){
        Map<String,List> map = new HashMap<>();
        map.put("cost",travelReportService.queryReportForCost());
        map.put("dept",travelReportService.queryReportForDept());
        map.put("month",travelReportService.queryReportForMonth());
        map.put("deptAndCost",travelReportService.queryReportForDeptAndCost());

        return R.ok().put("results", map);
    }

    /**
     * 汇总
     */
    @ApiOperation("汇总")
    @GetMapping("/report/allMap")
    public R allMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("cost",travelReportService.queryReportForCost());
        map.put("dept",travelReportService.queryReportForDept());
        map.put("month",travelReportService.queryReportForMonth());
        map.put("deptAndCost",travelReportService.queryReportForDeptAndCostMap());

        return R.ok().put("results", map);
    }
}
