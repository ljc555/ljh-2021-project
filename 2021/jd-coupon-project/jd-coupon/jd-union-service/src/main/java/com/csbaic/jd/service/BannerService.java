package com.csbaic.jd.service;

import com.csbaic.jd.dto.Banner;

import java.util.List;

public interface BannerService  {

    /**
     * 获取可用的 BannerEntity 列表，包含以启用、未过期的Banner
     * @return 返回 Banners
     */
    List<Banner> getAvailableBanners();


    /**
     * 添加Banner
     * @param banner
     */
    Banner createBanner(Banner banner);
}
