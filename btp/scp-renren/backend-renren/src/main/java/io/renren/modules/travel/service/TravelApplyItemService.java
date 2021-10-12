package io.renren.modules.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.travel.dto.TravelApplyItemDto;
import io.renren.modules.travel.entity.TravelApplyItemEntity;
import io.renren.modules.travel.form.TravelApplyItemForm;

import java.util.List;

/**
 * Description:差旅费用申请信息
 *
 * @author jianghong.li
 * @Date 2020-04- 09 15:25
 */
public interface TravelApplyItemService extends IService<TravelApplyItemEntity> {
    List<TravelApplyItemDto> getItemsByEcnum(String ecnum);

    String save(TravelApplyItemForm form);

    void updateById(TravelApplyItemForm form);

    TravelApplyItemDto getApplyItemByEconr(String econr);

    void deleteByeConr(String econr);

    PageUtils queryApplyItem(TravelApplyItemForm form, int page, int limit);
}
