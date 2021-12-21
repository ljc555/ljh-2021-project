package com.csbaic.jd.service.goods.impl;

import com.csbaic.jd.dto.CategoryInfo;
import com.csbaic.jd.dto.Goods;
import com.csbaic.jd.service.goods.GoodsFilterStrategy;
import com.csbaic.jd.service.goods.GoodsMetadata;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class JDGoodsFilterStrategy implements GoodsFilterStrategy {



    @Override
    public boolean apply(GoodsMetadata goodsMetadata) {

        /*
            PS:     如用于微信小程序推广，建议根据CID参数过滤掉以下禁投类目：

            OTC商品  cid1=13314 或 cid2=12632

            加油卡 cid2=14409

            游戏充值卡 cid3=4836 或 cid3=4835

            合约机 cid3=6881 或 cid3=6882

            京保养 cid3 = 12405

            烟具：14231
         */

        if (!(goodsMetadata instanceof Goods)) {
            return false;
        }


        Long[] cid1 = new Long[]{13314L, 12632L, 4938L};
        Long[] cid2 = new Long[]{14409L, 14226L};
        Long[] cid3 = new Long[]{4835L, 4836L, 6881L, 6882L, 12405L};
        CategoryInfo categoryInfo = ((Goods) goodsMetadata).getCategoryInfo();

        for(Long cid : cid1){
            if(Objects.equals(cid, categoryInfo.getCid1())){
                return false;
            }
        }

        for(Long cid : cid2){
            if(Objects.equals(cid, categoryInfo.getCid2())){
                return false;
            }
        }

        for(Long cid : cid3){
            if(Objects.equals(cid, categoryInfo.getCid3())){
                return false;
            }
        }

        return true;
    }
}
