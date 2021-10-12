package io.renren.modules.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.travel.dao.TravelApplyItemDao;
import io.renren.modules.travel.dto.TravelApplyItemDto;
import io.renren.modules.travel.dto.TravelCostTypeDto;
import io.renren.modules.travel.entity.TravelApplyEntity;
import io.renren.modules.travel.entity.TravelApplyItemEntity;
import io.renren.modules.travel.form.TravelApplyItemForm;
import io.renren.modules.travel.service.TravelApplyItemService;
import io.renren.modules.travel.service.TravelCostTypeService;
import io.renren.modules.travel.service.TravelUserService;
import io.renren.utils.StaticMethod;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:差旅费用申请信息 Service
 * @author jianghong.li
 * @Date 2020-04- 09 15:27
 */
@Service("travelApplyItemService")
public class TravelApplyItemServiceImpl extends ServiceImpl<TravelApplyItemDao, TravelApplyItemEntity> implements TravelApplyItemService {
    @Autowired
    TravelCostTypeService travelCostTypeService;
    @Autowired
    TravelUserService travelUserService;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<TravelApplyItemDto> getItemsByEcnum(String ecnum) {
        List<TravelApplyItemEntity> list = baseMapper.selectList(new QueryWrapper<TravelApplyItemEntity>().eq("ecnum", ecnum));
        List<TravelApplyItemDto> results = new ArrayList<>();

        list.forEach(e->{
            results.add(convertData(e));
        });
        return results;
    }

    @Override
    public String save(TravelApplyItemForm form) {
        String conr = Long.toString(System.currentTimeMillis());
        TravelApplyItemEntity entity = new TravelApplyItemEntity();
        entity.setId(UUID.randomUUID().toString().substring(24));
        entity.setEcnum(form.getEcnum());
        entity.setEconr(conr);
        entity.setBdat(form.getBdat());
        entity.setEdat(form.getEdat());
        entity.setDays(form.getDays());
        entity.setPrice(form.getPrice());
        entity.setHkont(form.getHkont());
        entity.setDest(form.getDest());
        entity.setHotel(form.getHotel());
        entity.setDmbtr(form.getDmbtr());
        entity.setComment(form.getComment());
        entity.setLoevm("0");
        entity.setErdat(new Date());
        entity.setErtim(new Date());

        entity.setDeprs(form.getDeprs());
        entity.setYearmonth(StaticMethod.getCurrentDateTime("yyyy-MM"));

        this.save(entity);

        return conr;
    }

    @Override
    public void updateById(TravelApplyItemForm form) {
        TravelApplyItemEntity entity = baseMapper.selectOne(new QueryWrapper<TravelApplyItemEntity>().eq("econr", form.getEconr()));
        entity.setBdat(form.getBdat());
        entity.setEdat(form.getEdat());
        entity.setDays(form.getDays());
        entity.setPrice(form.getPrice());
        entity.setHkont(form.getHkont());
        entity.setDest(form.getDest());
        entity.setHotel(form.getHotel());
        entity.setDmbtr(form.getDmbtr());
        entity.setComment(form.getComment());

        this.updateById(entity);
    }

    @Override
    public TravelApplyItemDto getApplyItemByEconr(String econr) {
        TravelApplyItemEntity entity = baseMapper.selectOne(new QueryWrapper<TravelApplyItemEntity>().eq("econr",econr));

        if(entity == null ) {
            return null;
        }

        TravelApplyItemDto travelApplyItemDto = convertData(entity);

        return travelApplyItemDto;
    }

    @Override
    public void deleteByeConr(String econr) {
        TravelApplyItemEntity entity = baseMapper.selectOne(new QueryWrapper<TravelApplyItemEntity>().eq("econr",econr));
        entity.setLoevm("-1");

        this.updateById(entity);
    }

    @Override
    public PageUtils queryApplyItem(TravelApplyItemForm form, int page, int rows) {
        Map<String, Object> paramMap = new HashMap<>();
        this.createWhereCondition(paramMap,form);

        paramMap.put("offset",(page-1)*rows);
        paramMap.put("limit",rows);

        List<TravelApplyItemEntity> results = this.queryList(paramMap);
        int total = this.queryTotal(paramMap);

        return new PageUtils(results, total, rows, page);
    }

    private int queryTotal(Map<String, Object> paramMap) {
        String sql = "select count(1) "
                + "from travel_apply_item as a "
                + paramMap.get("where");

        Integer count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count.intValue();
    }

    private List<TravelApplyItemEntity> queryList(Map<String, Object> paramMap) {
        String sql = "select a.* "
                + "from travel_apply_item as a "
                + paramMap.get("where")
                + " LIMIT " +paramMap.get("limit") + " OFFSET " + paramMap.get("offset");
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        List<TravelApplyItemEntity> results = new ArrayList<>();
        if(list !=null) {
            list.forEach(e->{
                try {
                    TravelApplyItemEntity entity = new TravelApplyItemEntity();
                    entity.setId(String.valueOf(e.get("ID")));
                    entity.setEcnum((String) e.get("ECNUM"));
                    entity.setEconr((String) e.get("ECONR"));
                    entity.setBdat((Date) e.get("BDAT"));
                    entity.setEdat((Date) e.get("EDAT"));
                    entity.setDays(String.valueOf(e.get("DAYS")));
                    entity.setPrice(String.valueOf(e.get("PRICE")));
                    entity.setHkont(String.valueOf(e.get("HKONT")));
                    entity.setDest(String.valueOf(e.get("DEST")));
                    entity.setHotel(String.valueOf(e.get("HOTEL")));
                    entity.setComment((String) e.get("COMMENT"));
                    entity.setDmbtr(String.valueOf(e.get("DMBTR")));
                    entity.setErdat((Date) e.get("ERDAT"));
                    entity.setLoevm((String) e.get("LOEVM"));
                    results.add(entity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        return results;
    }

    private void createWhereCondition(Map<String, Object> paramMap, TravelApplyItemForm form) {

        StringBuffer sb = new StringBuffer(" where 1=1 ");
        if(StringUtils.isNotEmpty(form.getEcnum())) {
            sb.append("AND a.ecnum LIKE ('%" + form.getEcnum() + "%') ");
        }
        if(StringUtils.isNotEmpty(form.getEconr())) {
            sb.append("AND a.econr LIKE ('%" + form.getEconr() + "%') ");
        }

        if(StringUtils.isNotEmpty(form.getHkont())) {
            sb.append("AND a.hkont = " + form.getHkont() + " ");
        }


        if(form.getBdat() != null) {
            sb.append("AND i.erdat >= '" + form.getBdat() + " ");
        }

        if(form.getEdat() != null) {
            sb.append("AND i.erdat <= " + form.getBdat() + " ");
        }

        paramMap.put("where",sb.toString());
    }

    private TravelApplyItemDto convertData(TravelApplyItemEntity entity) {
        TravelApplyItemDto travelApplyItemDto = new TravelApplyItemDto();
        try {
            travelApplyItemDto.setId(entity.getId());
            travelApplyItemDto.setEconr(entity.getEconr());
            travelApplyItemDto.setEcnum(entity.getEcnum());
            travelApplyItemDto.setBdat(StaticMethod.getCurrentDateTime(entity.getBdat(),"yyyy-MM-dd"));
            travelApplyItemDto.setEdat(StaticMethod.getCurrentDateTime(entity.getEdat(),"yyyy-MM-dd"));
            travelApplyItemDto.setDays(entity.getDays());
            travelApplyItemDto.setPrice(entity.getPrice());
            travelApplyItemDto.setHkont(travelCostTypeService.getCostTypeByHkont(entity.getHkont()));
            travelApplyItemDto.setDest(entity.getDest());
            travelApplyItemDto.setHotel(entity.getHotel());
            travelApplyItemDto.setDmbtr(entity.getDmbtr());
            travelApplyItemDto.setComment(entity.getComment());

            travelApplyItemDto.setErdat(StaticMethod.getCurrentDateTime(entity.getErdat(),"yyyy-MM-dd"));
            travelApplyItemDto.setErtim(StaticMethod.getCurrentDateTime(entity.getErtim(),"HH:mm:ss"));
            if(entity.getLoevm().equals("0")) {
                travelApplyItemDto.setLovemname("已创建");
            } else if(entity.getLoevm().equals("1")) {
                travelApplyItemDto.setLovemname("已审批");
            } else if(entity.getLoevm().equals("-1")) {
                travelApplyItemDto.setLovemname("已删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return travelApplyItemDto;
    }
}
