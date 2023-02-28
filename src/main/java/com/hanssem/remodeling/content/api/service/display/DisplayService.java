package com.hanssem.remodeling.content.api.service.display;


import com.hanssem.remodeling.content.api.controller.display.request.DisplayComponentDataRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayComponentListRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayGnbRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayInfoRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayMenuRequest;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentListResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentWithDataResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayInfoResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMainGnbResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMenuResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayTemplateInfoResponse;
import java.util.List;
import java.util.Map;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;


public interface DisplayService {

    // 캐시 적용 관련 사용자 파라메터 이슈가 있으므로, AuthClaim.userId 를 사용할수 없음(로그인한 모든 요청이 캐시되므로). 필요시 클라이언트에서 custNo(userId)를 입력하도록 할 것.
    // 캐시키에서 custNo 제거 및 캐싱 condition 추가.
    @Cacheable(cacheManager = "displayCacheManager", cacheNames = "findDisplayComponentInfo", key = "#displayInfoRequest.toStringWithoutCustNoAndHiddenField()"
            , condition = "#displayInfoRequest.custNo == null || \"0\".equals(#displayInfoRequest.custNo)")
    DisplayInfoResponse findDisplayComponentInfo(final DisplayInfoRequest displayInfoRequest, boolean is404Error);

    @Cacheable(cacheManager = "displayCacheManager", cacheNames = "findDisplayComponentData", key = "#displayComponentDataRequest.toStringWithoutCustNoAndHiddenField()"
            , condition = "#displayComponentDataRequest.custNo == null || \"0\".equals(#displayComponentDataRequest.custNo)")
    Object findDisplayComponentData(DisplayComponentDataRequest displayComponentDataRequest);

    @Cacheable(cacheManager = "displayCacheManager", cacheNames = "findDisplayComponentList", key = "#displayComponentListRequest")
    DisplayComponentListResponse findDisplayComponentList(final DisplayComponentListRequest displayComponentListRequest);

    @Cacheable(cacheManager = "displayCacheManager", cacheNames = "findDisplayGnb", key = "#displayGnbRequest")
    List<DisplayMainGnbResponse> findDisplayGnb(DisplayGnbRequest displayGnbRequest);

    List<DisplayTemplateInfoResponse> findDisplayTemplateInfo();

    @Cacheable(cacheManager = "displayCacheManager", cacheNames = "getDisplayDataSamples", key = ""
            , unless = "#isRefresh == true")
    List<DisplayComponentWithDataResponse> getDisplayDataSamples(Boolean isRefresh);

    Map<String, Object> processLayoutCd(List<DisplayComponentWithDataResponse> dispComponentList);

    Map<String, Object> processAdType(List<DisplayComponentWithDataResponse> dispComponentList);

    @Cacheable(cacheManager = "displayCacheManager", cacheNames = "findMenuList", key = "#displayMenuRequest")
    DisplayMenuResponse findMenuList(DisplayMenuRequest displayMenuRequest);

    @CacheEvict(cacheManager = "displayCacheManager", cacheNames = "*", allEntries = true)
    void clearDisplayCacheAll();
}
