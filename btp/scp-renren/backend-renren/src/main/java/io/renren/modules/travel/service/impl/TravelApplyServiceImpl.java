package io.renren.modules.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.modules.travel.dao.TravelApplyDao;
import io.renren.modules.travel.dto.TravelApplyDto;
import io.renren.modules.travel.dto.TravelApplyItemDto;
import io.renren.modules.travel.entity.TravelApplyEntity;
import io.renren.modules.travel.entity.TravelApplyItemEntity;
import io.renren.modules.travel.entity.TravelApplyQueryEntity;
import io.renren.modules.travel.entity.TravelUserEntity;
import io.renren.modules.travel.form.TravelApplyForm;
import io.renren.modules.travel.form.TravelApplyQueryForm;
import io.renren.modules.travel.service.*;
import io.renren.utils.StaticMethod;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * Description: 差旅费用申请基本信息Service
 * @author jianghong.li
 * @Date 2020-04- 09 15:26
 */
@Service("travelApplyService")
public class TravelApplyServiceImpl extends ServiceImpl<TravelApplyDao, TravelApplyEntity> implements TravelApplyService {
    @Autowired
    TravelApplyItemService travelApplyItemService;
    @Autowired
    TravelUserService travelUserService;
    @Autowired
    TravelCompanyService companyService;
    @Autowired
    TravelDepartmentService travelDepartmentService;
    @Autowired
    TravelApplyDao travelApplyDao;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public String save(TravelApplyForm form) {
        TravelApplyEntity entity = new TravelApplyEntity();
        TravelUserEntity userEntity = travelUserService.queryByPernr(form.getPernr());

        String ecnum = Long.toString(System.currentTimeMillis());
        entity.setId(UUID.randomUUID().toString().substring(24));
        entity.setBukrs(userEntity.getBukrs());
        entity.setComment(form.getComment());
        entity.setDepart(userEntity.getDepart());
        entity.setEcnum(ecnum);
        entity.setErdat(new Date());
        entity.setErtim(new Date());
        entity.setPernr(form.getPernr());

        this.save(entity);

        return ecnum;
    }

    @Override
    public void updateById(TravelApplyForm form) {
        TravelApplyEntity entity = baseMapper.selectOne(new QueryWrapper<TravelApplyEntity>().eq("ecnum", form.getEcnum()));
        entity.setComment(form.getComment());

        this.updateById(entity);
    }

    @Override
    public void deleteByEcnum(String ecnum) {
        TravelApplyEntity entity = baseMapper.selectOne(new QueryWrapper<TravelApplyEntity>().eq("ecnum", ecnum));
        entity.setLoevm(-1);

        this.saveOrUpdate(entity);
    }

    @Override
    public PageUtils queryApply(TravelApplyQueryForm form, int page, int rows) {
        Map<String, Object> paramMap = new HashMap<>();
        this.createWhereCondition(paramMap,form);

        paramMap.put("offset",(page-1)*rows);
        paramMap.put("limit",rows);

        /*List<TravelApplyQueryEntity> results = travelApplyDao.queryList(paramMap);
        int total = travelApplyDao.queryTotal(paramMap);*/

        List<TravelApplyQueryEntity> results = this.queryList(paramMap);
        int total = this.queryTotal(paramMap);

        return new PageUtils(results, total, rows, page);
    }

    private int queryTotal(Map<String, Object> paramMap) {
        String sql = "select count(1) "
                + "from travel_apply as a "
                + "left join travel_apply_item as i on i.ecnum=a.ecnum "
                + "left join travel_user as u on u.pernr = a.pernr "
                + paramMap.get("where");

        Integer count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count.intValue();
    }

    private List<TravelApplyQueryEntity> queryList(Map<String, Object> paramMap) {
        String sql = "select a.ecnum,i.erdat,i.loevm,i.dmbtr,i.comment,u.tel,u.bankc "
                + "from travel_apply as a "
                + "left join travel_apply_item as i on i.ecnum=a.ecnum "
                + "left join travel_user as u on u.pernr = a.pernr "
                + paramMap.get("where")
                + " LIMIT " +paramMap.get("limit") + " OFFSET " + paramMap.get("offset");

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        //List<TravelApplyQueryEntity> list = jdbcTemplate.queryForList(sql, TravelApplyQueryEntity.class);

        List<TravelApplyQueryEntity> results = new ArrayList<>();
        if(list !=null) {
            list.forEach(e->{
                TravelApplyQueryEntity entity = new TravelApplyQueryEntity();
                try {
                    entity.setBankc((String) e.get("BANKC"));
                    entity.setComment((String) e.get("COMMENT"));
                    entity.setDmbtr(String.valueOf(e.get("DMBTR")));
                    entity.setEcnum((String) e.get("ECNUM"));
                    entity.setErdat(StaticMethod.getCurrentDateTime((Date) e.get("ERDAT"), "yyyy-MM-dd"));
                    entity.setLoevm((String) e.get("LOEVM"));
                    entity.setTel((String) e.get("TEL"));
                    results.add(entity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        return results;
    }

    private void createWhereCondition(Map<String, Object> paramMap, TravelApplyQueryForm form) {

        StringBuffer sb = new StringBuffer(" where 1=1 ");
        if(StringUtils.isNotEmpty(form.getEcnum())) {
            sb.append("AND a.ecnum LIKE ('%" + form.getEcnum() + "%') ");
        }
        if(form.getLoevm() != null) {
            sb.append("AND i.loevm = " + form.getLoevm() + " ");
        }

        if(StringUtils.isNotEmpty(form.getPernr())) {
            sb.append("AND a.pernr = " + form.getPernr() + " ");
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

    @Override
    public PageUtils list(TravelApplyForm form, int page, int rows) {
        Map<String, Object> paramMap = new HashMap<>();

        StringBuffer where = new StringBuffer();
        where.append(" where 1=1 ");
        if(StringUtils.isNotEmpty(form.getEcnum())) {
            where.append("AND a.ecnum LIKE ('%" + form.getEcnum() + "%') ");
        }

        paramMap.put("where",where.toString());
        paramMap.put("offset",(page-1)*rows);
        paramMap.put("limit",rows);


        List<TravelApplyEntity> results = this.applyList(paramMap);
        int total = this.applyTotal(paramMap);

        return new PageUtils(results, total, rows, page);
    }

    private List<TravelApplyEntity> applyList(Map<String, Object> paramMap) {
        String sql = "select a.* "
                + "from travel_apply as a "
                + paramMap.get("where")
                + " LIMIT " +paramMap.get("limit") + " OFFSET " + paramMap.get("offset");

        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);

        List<TravelApplyEntity> results = new ArrayList<>();
        if(list !=null) {
            list.forEach(e->{
                TravelApplyEntity entity = new TravelApplyEntity();
                try {
                    entity.setId(String.valueOf(e.get("ID")));
                    entity.setEcnum(String.valueOf(e.get("ECNUM")));
                    entity.setBukrs(String.valueOf(e.get("BUKRS")));
                    entity.setDepart(String.valueOf(e.get("DEPART")));
                    entity.setPernr(String.valueOf(e.get("PERNR")));
                    entity.setComment(String.valueOf(e.get("COMMENT")));
                    entity.setErdat((Date)e.get("ERDAT"));
                    entity.setErtim((Date)e.get("ERTIM"));
                    entity.setLoevm(Integer.parseInt((String)e.get("LOEVM")));
                    results.add(entity);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }

        return results;
    }

    private int applyTotal(Map<String, Object> paramMap) {
        String sql = "select count(1) "
                + "from travel_apply as a "
                + paramMap.get("where");

        Integer count = jdbcTemplate.queryForObject(sql,Integer.class);
        return count.intValue();
    }

    @Override
    public TravelApplyDto getApplyByEcnum(String ecnum) {
        TravelApplyEntity entity = baseMapper.selectOne(new QueryWrapper<TravelApplyEntity>().eq("ecnum",ecnum));

        if(entity == null ) {
            return null;
        }

        TravelApplyDto travelApplyDto = convertData(entity);

        return travelApplyDto;
    }

    private TravelApplyDto convertData(TravelApplyEntity entity) {
        TravelApplyDto travelApplyDto = new TravelApplyDto();
        try {
            travelApplyDto.setId(entity.getId());
            travelApplyDto.setEcnum(entity.getEcnum());
            travelApplyDto.setBukrs(companyService.getCompanyByBukrs(entity.getBukrs()));
            travelApplyDto.setDepart(travelDepartmentService.getDepartmentByDeprs(entity.getDepart()));
            travelApplyDto.setPernr(travelUserService.getUserByPernr(entity.getPernr()));
            travelApplyDto.setComment(entity.getComment());
            travelApplyDto.setLovem(entity.getLoevm());

            travelApplyDto.setErdat(StaticMethod.getCurrentDateTime(entity.getErdat(),"yyyy-MM-dd"));
            travelApplyDto.setErtim(StaticMethod.getCurrentDateTime(entity.getErtim(),"HH:mm:ss"));

            if(entity.getLoevm().equals(0)) {
                travelApplyDto.setLovemname("已创建");
            } else if(entity.getLoevm().equals(1)) {
                travelApplyDto.setLovemname("已审批");
            } else if(entity.getLoevm().equals(-1)) {
                travelApplyDto.setLovemname("已删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<TravelApplyItemDto> items = travelApplyItemService.getItemsByEcnum(entity.getEcnum());

        travelApplyDto.setTravelApplyItemDto(items);
        return travelApplyDto;
    }
}
