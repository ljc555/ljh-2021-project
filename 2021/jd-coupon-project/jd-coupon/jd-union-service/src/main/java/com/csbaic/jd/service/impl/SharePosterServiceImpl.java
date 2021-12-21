package com.csbaic.jd.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.csbaic.common.convert.ObjectConvert;
import com.csbaic.common.exception.BizRuntimeException;
import com.csbaic.common.result.ResultCode;
import com.csbaic.jd.dto.CreateSharePoster;
import com.csbaic.jd.dto.SharePoster;
import com.csbaic.jd.entity.SharePosterEntity;
import com.csbaic.jd.mapper.SharePosterMapper;
import com.csbaic.jd.service.ISharePosterService;
import com.google.common.base.Function;
import com.google.common.base.Strings;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

/**
 * <p>
 * 分享海报 服务实现类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-23
 */
@Service
public class SharePosterServiceImpl extends ServiceImpl<SharePosterMapper, SharePosterEntity> implements ISharePosterService {



    @Override
    public void createPoster(Long userId, CreateSharePoster sharePoster) {
        if(sharePoster == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "参数错误");
        }

        if(Strings.isNullOrEmpty(sharePoster.getTitle())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "分享海报主题不能为空");
        }

        if(Strings.isNullOrEmpty(sharePoster.getContent())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "分享海报文字内容不能为空");
        }


        if(Strings.isNullOrEmpty(sharePoster.getImageUrl())){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "分享海报图片不能为空");
        }


        if(userId == null){
            throw BizRuntimeException.from(ResultCode.BAD_REQUEST, "发布人不能为空");
        }


        SharePosterEntity sharePosterEntity = new SharePosterEntity();
        sharePosterEntity.setContent(sharePoster.getContent());
        sharePosterEntity.setTitle(sharePoster.getTitle());
        sharePosterEntity.setIconUrl(sharePoster.getIconUrl());
        sharePosterEntity.setImageUrl(sharePoster.getImageUrl());
        save(sharePosterEntity);
    }

    @Override
    public IPage<SharePoster> getPosters(int page, int size) {


        return page(new Page<>(page, size)).convert(
                new Function<SharePosterEntity, SharePoster>() {
                    @Nullable
                    @Override
                    public SharePoster apply(@Nullable SharePosterEntity input) {
                        return to(input);
                    }
                }
        );
    }


    public SharePoster to(SharePosterEntity sharePosterEntity){
        return ObjectConvert.spring(sharePosterEntity, SharePoster.class);
    }
}
