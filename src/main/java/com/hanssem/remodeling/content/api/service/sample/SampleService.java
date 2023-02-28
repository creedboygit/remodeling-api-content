package com.hanssem.remodeling.content.api.service.sample;

import com.hanssem.remodeling.content.api.controller.sample.request.PathTestRequest;
import com.hanssem.remodeling.content.api.controller.sample.request.SampleCacheRequest;
import com.hanssem.remodeling.content.api.controller.sample.response.PathTestResponse;
import com.hanssem.remodeling.content.api.controller.sample.response.SampleCacheResponse;
import com.hanssem.remodeling.content.api.controller.sample.response.SampleCacheResponse2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


public interface SampleService {

    @Cacheable(cacheManager = "baseCacheManager", cacheNames = "SampleCacheResponse", key = "{#param1, #param2}")
    SampleCacheResponse cacheAble(String param1, String param2);

    @CacheEvict(cacheManager = "baseCacheManager", cacheNames = "SampleCacheResponse", key = "{#param1, #param2}")
    void cacheAbleClear(String param1, String param2);

    @Cacheable(cacheManager = "baseCacheManager", cacheNames = "SampleCacheResponse", key = "#sampleCacheRequest")
    SampleCacheResponse cacheAbleRequest(SampleCacheRequest sampleCacheRequest);

    @CacheEvict(cacheManager = "baseCacheManager", cacheNames = "SampleCacheResponse", key = "#sampleCacheRequest")
    void cacheAbleRequestClear(SampleCacheRequest sampleCacheRequest);

    @Cacheable(cacheManager = "baseCacheManager", cacheNames = "SampleCacheResponse2", key = "#sampleCacheRequest")
    SampleCacheResponse2 cacheAbleRequest2(SampleCacheRequest sampleCacheRequest);

    @CacheEvict(cacheManager = "baseCacheManager", cacheNames = {"*"}, allEntries = true)
    void clearSampleCacheAll();

    @CacheEvict(cacheManager = "baseCacheManager", cacheNames = {"SampleCacheResponse2"}, allEntries = true)
    void clearSampleCachePart();

    PathTestResponse testImagePath(PathTestRequest pathTestRequest);
}