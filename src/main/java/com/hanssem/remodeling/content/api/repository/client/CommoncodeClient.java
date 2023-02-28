package com.hanssem.remodeling.content.api.repository.client;

import com.hanssem.remodeling.content.api.service.commoncode.vo.CommoncodeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "CommonServiceClient", url = "${feign.baseUrl.CommonServiceClient}")
public interface CommoncodeClient {

    @GetMapping(value = "/api/v1/commoncode/{codeName}")
    CommoncodeVo getCommoncode(@PathVariable(value="codeName") String codeName);

}

