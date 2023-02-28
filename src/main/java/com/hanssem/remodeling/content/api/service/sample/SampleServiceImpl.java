package com.hanssem.remodeling.content.api.service.sample;

import com.hanssem.remodeling.content.api.controller.sample.request.PathTestRequest;
import com.hanssem.remodeling.content.api.controller.sample.request.SampleCacheRequest;
import com.hanssem.remodeling.content.api.controller.sample.response.PathTestResponse;
import com.hanssem.remodeling.content.api.controller.sample.response.SampleCacheResponse;
import com.hanssem.remodeling.content.api.controller.sample.response.SampleCacheResponse2;
import com.hanssem.remodeling.content.api.service.sample.mapper.SampleMapper;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SampleServiceImpl implements SampleService {

    @Override
    public SampleCacheResponse cacheAble(String param1, String param2) {
        log.debug("## SERVICE - cacheAble");
        log.debug("############ SERVICE CACHE_TEST param1: {}, param2: {}", param1, param2);

        return SampleCacheResponse
                .builder()
                .filedString("Test1")
                .filedInt(1)
                .filedStringList(Arrays.asList("test1", "test2"))
                .filedObject(SampleCacheResponse.SampleRedisObjectParam.builder()
                        .intMember(11)
                        .strMember("S_11")
                        .listMember(Arrays.asList("l11", "L_22"))
                        .build())
                .build();
    }

    @Override
    public void cacheAbleClear(String param1, String param2) {

    }

    @Override
    public SampleCacheResponse cacheAbleRequest(SampleCacheRequest sampleCacheRequest) {

        log.debug("## SERVICE - cacheAbleRequest");
        log.debug("############ SERVICE CACHE_TEST_REQ sampleCacheRequest: {}", sampleCacheRequest);

        return SampleCacheResponse
                .builder()
                .filedString("Test1")
                .filedInt(1)
                .filedStringList(Arrays.asList("test1", "test2"))
                .filedObject(SampleCacheResponse.SampleRedisObjectParam.builder()
                        .intMember(11)
                        .strMember("S_11")
                        .listMember(Arrays.asList("l11", "L_22"))
                        .build())
                .build();
    }

    @Override
    public void cacheAbleRequestClear(SampleCacheRequest sampleCacheRequest) {

    }

    @Override
    public SampleCacheResponse2 cacheAbleRequest2(SampleCacheRequest sampleCacheRequest) {

        log.debug("## SERVICE - cacheAbleRequest2");
        log.debug("############ SERVICE CACHE_TEST_REQ2 sampleCacheRequest: {}", sampleCacheRequest);

        return SampleCacheResponse2
                .builder()
                .filedString("Test2")
                .filedInt(2)
                .build();
    }

    @Override
    public void clearSampleCacheAll() {

    }

    @Override
    public void clearSampleCachePart() {

    }

    @Override
    public PathTestResponse testImagePath(PathTestRequest pathTestRequest) {

        return SampleMapper.INSTANCE.pathTestRequestToResponse2(pathTestRequest);
    }
}