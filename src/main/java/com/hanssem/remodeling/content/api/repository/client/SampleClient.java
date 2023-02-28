package com.hanssem.remodeling.content.api.repository.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sample", url = "https://sample")
public interface SampleClient {

    @GetMapping("/")
    void sample();
}
