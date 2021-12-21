package com.csbaic.jd.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.csbaic.jd.dto.CreateSharePoster;
import com.csbaic.jd.dto.SharePoster;
import com.csbaic.jd.entity.SharePosterEntity;

/**
 * <p>
 * 分享海报 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-03-23
 */
public interface ISharePosterService extends IService<SharePosterEntity> {

    /**
     * 创建分享海报
     * @param sharePoster
     */
    void createPoster(Long userId, CreateSharePoster sharePoster);


    /**
     * 获取分享海报
     * @param page
     * @param size
     * @return
     */
    IPage<SharePoster> getPosters(int page, int size);
}
