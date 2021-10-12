package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.travel.dto.TravelApplyDto;
import io.renren.modules.travel.entity.TravelApplyEntity;
import io.renren.modules.travel.form.TravelApplyForm;
import io.renren.modules.travel.form.TravelApplyQueryForm;

/**
 * Description:差旅费用申请基本信息Service
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:25
 */
public interface TravelApplyService extends IService<TravelApplyEntity> {
    String save(TravelApplyForm form);

    void updateById(TravelApplyForm form);

    TravelApplyDto getApplyByEcnum(String ecnum);

    void deleteByEcnum(String ecnum);

    PageUtils queryApply(TravelApplyQueryForm form, int page, int rows);

    PageUtils list(TravelApplyForm form, int page, int rows);
}
