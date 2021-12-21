package com.csbaic.jd.service.order;

import com.csbaic.jd.enums.UserIdentify;
import com.csbaic.jd.service.IUserService;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class DefaultSubUnionIdConverter implements SubUnionIdConverter{


    private static final String SEPARATOR = "_";

    private static final int INDEX_OWNER = 0;
    private static final int INDEX_BUYER = 1;
    private static final int INDEX_OWNER_IDENTIFY = 2;
    private static final int INDEX_BUYER_IDENTIFY = 3;
    private static final int INDEX_REBATE = 4;

    private final IUserService userService;

    public DefaultSubUnionIdConverter(IUserService userService) {
        this.userService = userService;
    }


    @Override
    public String covert(SubUnionId subUnionId) {
        StringBuilder builder = new StringBuilder();
        builder.append(subUnionId.getOwner()  );
        builder.append(SEPARATOR);
        builder.append(subUnionId.getBuyer());
        builder.append(SEPARATOR);
        builder.append(subUnionId.getIdentifyOfOwner());
        builder.append(SEPARATOR);
        builder.append(subUnionId.getIdentifyOfBuyer());
        builder.append(SEPARATOR);
        builder.append(subUnionId.isRebate() ? 1 : 0);
        return builder.toString();
    }

    @Override
    public SubUnionId covert(String subUnionId) {
        if(Strings.isNullOrEmpty(subUnionId)){
            //老数据
            SubUnionId subUnion = new SubUnionId();
            subUnion.setBuyer(1239405702461857794L);
            subUnion.setOwner(1239404324494581762L);
            subUnion.setIdentifyOfBuyer(UserIdentify.SUPER.getCode());
            subUnion.setIdentifyOfOwner(UserIdentify.SUPER.getCode());
            subUnion.setRebate(true);
            return subUnion;
        }

        List<String> stringList = Splitter.on(SEPARATOR)
                .splitToList(subUnionId);

        long ownerId = Long.parseLong(stringList.get(INDEX_OWNER));
        long buyerId = Long.parseLong(stringList.get(INDEX_BUYER));


        SubUnionId subUnion = new SubUnionId();
        subUnion.setOwner(ownerId);
        subUnion.setBuyer(buyerId);


        /*
            由于用户的身份是从1开始的，如何subUnionid中的身份不正确（多为历史数据bug）
            校正一下用户的等级 。
         */
        int identifyOfOwner = Integer.parseInt(stringList.get(INDEX_OWNER_IDENTIFY));
        if(ownerId > 0 && identifyOfOwner < UserIdentify.REGISTERED.getCode()){
            identifyOfOwner = userService.getUserIdentifyById(ownerId).getCode();
        }

        int identifyOfBuyer = Integer.parseInt(stringList.get(INDEX_BUYER_IDENTIFY));
        if(buyerId > 0 && identifyOfBuyer < UserIdentify.REGISTERED.getCode()){
            identifyOfBuyer = userService.getUserIdentifyById(buyerId).getCode();
        }


        subUnion.setIdentifyOfOwner(identifyOfOwner);
        subUnion.setIdentifyOfBuyer(identifyOfBuyer);
        subUnion.setRebate(!Objects.equals(stringList.get(INDEX_REBATE), "0"));

        return subUnion;
    }
}
