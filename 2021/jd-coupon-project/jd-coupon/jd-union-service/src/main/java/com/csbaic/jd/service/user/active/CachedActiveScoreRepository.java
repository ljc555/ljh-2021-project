package com.csbaic.jd.service.user.active;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CachedActiveScoreRepository implements ActiveScoreRepository{

    /**
     * 缓存用户的活跃度
     */
    private Cache<Long, ActiveScore> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.HOURS)
            .maximumSize(10000)
            .initialCapacity(1000)
            .build();

    @Override
    public void save(ActiveScoreRequest request, ActiveScore activeScore) {

    }

    @Override
    public void remove(ActiveScoreRequest request) {
        cache.invalidate(request);
    }

    @Override
    public ActiveScore get(ActiveScoreRequest request) {
        return cache.getIfPresent(request);
    }

}
