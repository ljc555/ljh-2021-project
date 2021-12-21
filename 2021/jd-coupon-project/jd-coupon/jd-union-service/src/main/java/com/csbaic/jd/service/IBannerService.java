package com.csbaic.jd.service;

import com.csbaic.jd.dto.Banner;
import com.csbaic.jd.dto.CreateBanner;
import com.csbaic.jd.dto.CreateBannerByActivity;
import com.csbaic.jd.entity.BannerEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * banner表 服务类
 * </p>
 *
 * @author yjwfn
 * @since 2020-02-14
 */
public interface IBannerService extends IService<BannerEntity> {

    /**
     * 获取可用的 BannerEntity 列表，包含以启用、未过期的Banner
     * @return 返回 Banners
     */
    List<Banner> getAvailableBanners();


    /**
     * 添加Banner
     * @param banner
     */
    Banner createBanner(CreateBanner banner);


    /**
     * 添加Banner
     * @param activity
     */
    List<Banner> createBannerByActivity(CreateBannerByActivity activity);
}
