package com.hanssem.remodeling.content.common.config;

import com.hanssem.remodeling.content.common.model.AdminEventInfo;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public InetAddress inetAddress() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }

    /**
     * Request 단위로 Header의 인증정보를 담아줄 빈객체 생성
     * @return @AuthClaim
     */
    @Bean
    @RequestScope
    public AuthClaim authClaim() {
        return new AuthClaim();
    }

    @Bean
    @RequestScope
    public AdminEventInfo adminEventInfo() {
        return new AdminEventInfo();
    }
}
