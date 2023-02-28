package com.hanssem.remodeling.content.api.controller.sample;

import com.hanssem.remodeling.content.api.controller.sample.request.PathTestRequest;
import com.hanssem.remodeling.content.api.controller.sample.request.SampleCacheRequest;
import com.hanssem.remodeling.content.api.controller.sample.response.PathTestResponse;
import com.hanssem.remodeling.content.api.controller.sample.response.SampleCacheResponse;
import com.hanssem.remodeling.content.api.controller.sample.response.SampleCacheResponse2;
import com.hanssem.remodeling.content.api.service.sample.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


//@Profile("local")
@Tag(name = "샘플")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/sample", produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {

    private final SampleService sampleService;


    @GetMapping(value = "/redis/cache-able")
    @Operation(summary = "레디스 캐시 적용 - @RequestParam")
    public SampleCacheResponse testCacheAble(
            @RequestParam(required = false) @Schema(description = "param1") String param1
            , @RequestParam(required = false) @Schema(description = "param2") String param2) {

        log.debug("#### CONTROLLER testCacheAble- cacheKey1: {}, cacheKey2:{}", param1, param2);
        return sampleService.cacheAble(param1, param2);
    }

    @DeleteMapping(value = "/redis/cache-able")
    @Operation(summary = "레디스 캐시 클리어 - @RequestParam")
    public void testCacheAbleClear(
            @RequestParam(required = false) @Schema(description = "param1") String param1
            , @RequestParam(required = false) @Schema(description = "param2") String param2) {

        log.debug("#### CONTROLLER testCacheAbleClear");
        sampleService.cacheAbleClear(param1, param2);
    }

    @GetMapping(value = "/redis/cache-able-request")
    @Operation(summary = "레디스 캐시 적용 - Object")
    public SampleCacheResponse testCacheAbleRequest(@Valid SampleCacheRequest sampleCacheRequest) {

        log.debug("#### CONTROLLER testCacheAbleRequest- SampleCacheRequest: {}", sampleCacheRequest);
        return sampleService.cacheAbleRequest(sampleCacheRequest);
    }

    @DeleteMapping(value = "/redis/cache-able-request")
    @Operation(summary = "레디스 캐시 클리어 - Object")
    public void testCacheAbleRequestClear(@Valid SampleCacheRequest sampleCacheRequest) {

        log.debug("#### CONTROLLER testCacheAbleRequestClear- SampleCacheRequest: {}", sampleCacheRequest);
        sampleService.cacheAbleRequestClear(sampleCacheRequest);
    }

    @GetMapping(value = "/redis/cache-able-request2")
    @Operation(summary = "레디스 캐시 적용2 - Object")
    public SampleCacheResponse2 testCacheAbleRequest2(@Valid SampleCacheRequest sampleCacheRequest) {

        log.debug("#### CONTROLLER testCacheAbleRequest2- SampleCacheRequest: {}", sampleCacheRequest);
        return sampleService.cacheAbleRequest2(sampleCacheRequest);
    }

    @DeleteMapping(value = "/redis/cache-clear-all")
    @Operation(summary = "레디스 캐시 전체 클리어")
    public void testClearSampleCacheAll() {

        log.debug("#### CONTROLLER testClearSampleCacheAll");
        sampleService.clearSampleCacheAll();
    }

    @DeleteMapping(value = "/redis/cache-clear-part")
    @Operation(summary = "레디스 캐시 부분 클리어")
    public void testClearSampleCachePart() {

        log.debug("#### CONTROLLER testClearSampleCachePart");
        sampleService.clearSampleCachePart();
    }

    @GetMapping(value = "/img-path")
    @Operation(summary = "이미지 경로 도메인 샘플")
    public PathTestResponse testImagePath(@Valid PathTestRequest pathTestRequest) {

        log.debug("#### CONTROLLER testImagePath- pathTestRequest: {}", pathTestRequest);
        return sampleService.testImagePath(pathTestRequest);
    }

    /*
    public void testSample(
            @RequestParam @Schema(example = "200") String param1,
            @RequestParam(required = false) String param2) {
    }
     */
}
