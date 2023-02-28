package com.hanssem.remodeling.content.api.service.display.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
@ConfigurationProperties("content.display.gnb")
public class DisplayGnbPageUrlDto {

    private List<GnbPageUrl> pageUrlList;

    @Getter
    @Setter
    @ToString
    public static class GnbPageUrl {

        private String templateNo;
        private String pageUrl;
    }
}
