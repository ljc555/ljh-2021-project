package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.csbaic.jd.dto.*;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.jd.service.GoodsService;
import com.csbaic.jd.service.GoodsUrlService;
import com.csbaic.jd.service.IUserService;
import com.csbaic.jd.service.order.SubUnionId;
import com.csbaic.jd.service.order.SubUnionIdConverter;
import com.csbaic.common.result.ResultCode;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class GoodsUrlServiceImpl implements GoodsUrlService {

    private final GoodsService goodsService;

    private final IUserService userService;

    private final SubUnionIdConverter subUnionIdConverter;



    public GoodsUrlServiceImpl(GoodsService goodsService, IUserService userService, SubUnionIdConverter subUnionIdEncoder ) {
        this.goodsService = goodsService;
        this.userService = userService;
        this.subUnionIdConverter = subUnionIdEncoder;
    }


    @Override
    public GoodsRecUrl createPurchaseShortUrl(Long userId, CreatePurchaseShortUrl goodsUrl) {
        if(goodsUrl == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST , "错误的输入参数");
        }

        if(Strings.isNullOrEmpty(goodsUrl.getMaterialId())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST , "商品链接不正确");
        }

        if(userId == null && Strings.isNullOrEmpty(goodsUrl.getInvitationCode())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST , "推广人不正确");
        }


        UserEntity buyer = userService.getById(userId);
        if(buyer == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND , "不正确的用户ID，找不到用户");
        }

        UserEntity owner = buyer;
        if(!Strings.isNullOrEmpty(goodsUrl.getInvitationCode())){
            owner = userService.getOne(
                    Wrappers.<UserEntity>query()
                            .eq(UserEntity.INVITATION_CODE, goodsUrl.getInvitationCode())
            );
        }

        if(owner == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND , "不正确的推广码，找不到用户");
        }

        log.debug("Purchase url : buyer({}), owner({})", buyer.getId(), owner.getId());
        SubUnionId subUnionId = new SubUnionId();
        subUnionId.setOwner(owner.getId());
        subUnionId.setIdentifyOfOwner(owner.getIdentify());
        subUnionId.setBuyer(buyer.getId());
        subUnionId.setIdentifyOfBuyer(buyer.getIdentify());
        subUnionId.setRebate(Objects.isNull(goodsUrl.getCouponUrl())); //沒有優惠商品會有返利

        GetRecUrl getRecUrl = new GetRecUrl();
        getRecUrl.setSubUnionId(subUnionIdConverter.covert(subUnionId));
        getRecUrl.setMaterialId(goodsUrl.getMaterialId());
        getRecUrl.setCouponUrl(goodsUrl.getCouponUrl());
        GoodsRecUrl goodsRecUrl = goodsService.getRecUrlBySubUnionId(getRecUrl);
        return goodsRecUrl;
    }

    @Override
    public GoodsRecUrl createGoodsShortUrl(Long userId, CreateGoodsShortUrl goodsUrl) {

        if(goodsUrl == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST , "错误的输入参数");
        }

        if(Strings.isNullOrEmpty(goodsUrl.getMaterialId())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST , "商品链接不正确");
        }


        UserEntity curUser = userService.getById(userId);

        SubUnionId subUnionId = new SubUnionId();
        subUnionId.setOwner(curUser.getId());
//        subUnionId.setBuyer(curUser.getId());
        subUnionId.setIdentifyOfOwner(curUser.getIdentify());
//        subUnionId.setIdentifyOfBuyer(curUser.getIdentify());
        subUnionId.setRebate(Objects.isNull(goodsUrl.getCouponUrl()));

        GetRecUrl getRecUrl = new GetRecUrl();
        getRecUrl.setSubUnionId(subUnionIdConverter.covert(subUnionId));
        getRecUrl.setMaterialId(goodsUrl.getMaterialId());
        getRecUrl.setCouponUrl(goodsUrl.getCouponUrl());
        GoodsRecUrl goodsRecUrl = goodsService.getRecUrlBySubUnionId(getRecUrl);
        return goodsRecUrl;
    }

    @Override
    public ConvertUrlResult createConvertedUrl(Long userId, ConvertUrlContent convertUrlContent) {
        if(convertUrlContent == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST , "错误的输入参数");
        }

        if(Strings.isNullOrEmpty(convertUrlContent.getContent())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST , "转链内容不能为空");
        }


        UserEntity userEntity  =  userService.getById(userId);

        if(userEntity == null){
            throw BizRuntimeException.from(ResultCode.NOT_FOUND , "未找到用户");
        }


        //https://u.jd.com/lki2pb
        Pattern pattern = Pattern.compile("https://u\\.jd\\.com/[a-zA-Z0-9]+");
        String content = convertUrlContent.getContent();
        Matcher matcher = pattern.matcher(content);
        StringBuilder newContent = new StringBuilder(content);

        while (matcher.find()){
            String url = matcher.group();
            CreateGoodsShortUrl getRecUrl = new CreateGoodsShortUrl();
            getRecUrl.setMaterialId(url);
            GoodsRecUrl  goodsRecUrl = createGoodsShortUrl(userId, getRecUrl);
            newContent.replace(matcher.start(), matcher.end(), goodsRecUrl.getShortURL());
        }

        return new ConvertUrlResult(newContent.toString());
    }
}
