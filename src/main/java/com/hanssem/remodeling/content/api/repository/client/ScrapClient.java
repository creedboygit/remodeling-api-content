package com.hanssem.remodeling.content.api.repository.client;

import com.hanssem.remodeling.content.api.service.scrap.vo.ScrapExistenceVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ScrapClient", url = "${feign.baseUrl.CommonServiceClient}")
public interface ScrapClient {

    @GetMapping(value = "/api/v1/commons/scraps/existence")
    ScrapExistenceVo getScrapExistence(@RequestHeader("Authorization") String token, @RequestHeader("custNo") Long custNo, @RequestParam("targetId") String targetId, @RequestParam("scrapTypeCode") String scrapTypeCode);
}
