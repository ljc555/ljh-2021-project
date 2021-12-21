package com.csbaic.jd.service.goods.impl;

import com.csbaic.jd.dto.Goods;
import com.csbaic.jd.entity.UserEntity;
import com.csbaic.jd.service.IUserService;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class HttpsGoodsImageUrlHandler extends AbstractUserGoodsHandler<Goods> {

    protected HttpsGoodsImageUrlHandler(IUserService userService) {
        super(userService);
    }

    @Override
    protected void handleImpl(UserEntity userEntity, Goods metadata) {
        Goods jdGoods = metadata;
        List<String> images = jdGoods.getImages();

        List<String> httpsImages = images.stream()
                .map(HttpsGoodsImageUrlHandler::convertUrl)
                .collect(Collectors.toList());

        jdGoods.setImages(httpsImages);
    }


    private static String convertUrl(String origin){
        return UriComponentsBuilder.fromHttpUrl(origin)
                .scheme("https")
                .toUriString();
    }
}
