package io.renren.modules.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.modules.travel.dao.TravelUserDao;
import io.renren.modules.travel.dto.TravelBankDto;
import io.renren.modules.travel.dto.TravelCompanyDto;
import io.renren.modules.travel.dto.TravelDepartmentDto;
import io.renren.modules.travel.dto.TravelUserDto;
import io.renren.modules.travel.entity.TravelUserEntity;
import io.renren.modules.travel.service.TravelBankService;
import io.renren.modules.travel.service.TravelCompanyService;
import io.renren.modules.travel.service.TravelDepartmentService;
import io.renren.modules.travel.service.TravelUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:个人信息 Service
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:28
 */
@Service("travelUserService")
public class TravelUserServiceImpl extends ServiceImpl<TravelUserDao, TravelUserEntity> implements TravelUserService {
    @Autowired
    TravelBankService travelBankService;
    @Autowired
    TravelCompanyService travelCompanyService;
    @Autowired
    TravelDepartmentService travelDepartmentService;

    @Override
    public TravelUserEntity queryByPernr(String pernr) {
        if(StringUtils.isEmpty(pernr)) {
            return null;
        }
        return baseMapper.selectOne(new QueryWrapper<TravelUserEntity>().eq("pernr", pernr));
    }

    @Override
    public TravelUserDto getUserByPernr(String pernr) {
        TravelUserDto userDto = new TravelUserDto();
        TravelUserEntity entity = this.queryByPernr(pernr);

        if(entity !=null) {
            userDto.setId(entity.getId());
            userDto.setPernr(entity.getPernr());
            userDto.setName(entity.getName());
            userDto.setName1(entity.getName1());
            userDto.setTel(entity.getTel());

            userDto.setBukrs(travelCompanyService.getCompanyByBukrs(entity.getBukrs()));
            userDto.setDepart(travelDepartmentService.getDepartmentByDeprs(entity.getDepart()));
            userDto.setBank(travelBankService.getBankByBank(entity.getBank()));
        }

        return userDto;
    }
}
