package io.renren.modules.travel.service.impl;

import io.renren.modules.travel.entity.TravelCostTypeEntity;
import io.renren.modules.travel.entity.TravelDepartmentEntity;
import io.renren.modules.travel.service.TravelCostTypeService;
import io.renren.modules.travel.service.TravelDepartmentService;
import io.renren.modules.travel.service.TravelReportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 *
 * @author jianghong.li
 * @Date 2020-04- 16 9:48
 */
@Service
public class TravelReportServiceImpl implements TravelReportService {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    TravelDepartmentService travelDepartmentService;
    @Autowired
    TravelCostTypeService travelCostTypeService;

    @Override
    public List queryReportForCost() {
        String sql = "SELECT COALESCE(sum(i.dmbtr),0) AS total,c.hkont,c.txt20 " +
                "FROM travel_cost_type c " +
                "LEFT JOIN travel_apply_item i ON i.hkont=c.hkont " +
                "GROUP BY c.hkont,c.txt20 ";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list==null?new ArrayList():list;
    }

    @Override
    public List queryReportForDept() {
        String sql = "SELECT COALESCE(sum(i.dmbtr),0) AS total,c.deprs,c.depxt " +
                "FROM travel_department c " +
                "LEFT JOIN travel_apply_item i ON i.deprs=c.deprs " +
                "GROUP BY c.deprs,c.depxt ";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list==null?new ArrayList():list;
    }

    @Override
    public List queryReportForMonth() {
        String sql = "SELECT COALESCE(sum(i.dmbtr),0) AS total,i.yearmonth " +
                "FROM travel_apply_item i " +
                "GROUP BY i.yearmonth " +
                "ORDER BY i.yearmonth desc ";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list==null?new ArrayList():list;
    }

    @Override
    public List queryReportForDeptAndCost() {
        String sql = "SELECT COALESCE(sum(i.dmbtr),0) AS total ,d.deprs,d.depxt,i.hkont,c.txt20 " +
                "FROM  travel_apply_item i " +
                "LEFT JOIN travel_department d ON d.deprs=i.deprs " +
                "LEFT JOIN travel_cost_type c ON c.hkont=i.hkont " +
                "GROUP BY d.deprs,d.depxt,i.hkont,c.txt20 " +
                "ORDER BY d.depxt,i.hkont ";

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        return list==null?new ArrayList():list;
    }

    @Override
    public List<Map> queryReportForDeptAndCostMap() {
        List<Map<String,Object>> list = this.queryReportForDeptAndCost();

        List<TravelDepartmentEntity> deptList = travelDepartmentService.list();
        List<TravelCostTypeEntity> costList = travelCostTypeService.list();

        Map<String,String> map = new HashMap();
        list.forEach(e -> {
            map.put(e.get("deprs") + "-" + e.get("hkont"),String.valueOf(e.get("total")));
        });

        List<Map> depts = new ArrayList<>();
        deptList.forEach(d -> {
            Map deptMap = new HashMap();
            deptMap.put("code",d.getDeprs());
            deptMap.put("name",d.getDepxt());

            List<Map> costs = new ArrayList();
            costList.forEach(c -> {
                Map costMap = new HashMap();
                costMap.put("code",c.getHkont());
                costMap.put("name",c.getTxt20());

                if(map.get(d.getDeprs() + "-" + c.getHkont())!=null) {
                    costMap.put("total",map.get(d.getDeprs() + "-" + c.getHkont()));
                } else {
                    costMap.put("total","0");
                }

                costs.add(costMap);
            });

            deptMap.put("values",costs);
            depts.add(deptMap);
        });


        return depts;
    }
}
