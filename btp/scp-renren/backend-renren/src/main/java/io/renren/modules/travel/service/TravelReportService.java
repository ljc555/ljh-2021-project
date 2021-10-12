package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.modules.travel.entity.TravelApplyEntity;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 16 9:43
 */
public interface TravelReportService {
    List queryReportForCost();

    List queryReportForDept();

    List queryReportForMonth();

    List queryReportForDeptAndCost();

    List<Map> queryReportForDeptAndCostMap();
}
