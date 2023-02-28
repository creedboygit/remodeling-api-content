package com.hanssem.remodeling.content.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "amazon")
public final class WebConfig {

    @Setter
    @Getter
    private String bucket;

    @Setter
    @Getter
    private String cdnUrl;

}
