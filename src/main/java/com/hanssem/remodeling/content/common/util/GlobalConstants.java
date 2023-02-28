package com.hanssem.remodeling.content.common.util;

import com.hanssem.remodeling.content.constant.DisplayScreenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GlobalConstants {

    private static String cdnUrl;
    private static String mobileDomain;
    private static String pcDomain;
    private static String gateWayDomain;

    @Value("${amazon.cdn-url}")
    public void setCdnUrl(String url) {
        cdnUrl = url;
    }

    @Value("${domain.mobile}")
    public void setMobileDomain(String domain) {
        mobileDomain = domain;
    }

    @Value("${domain.pc}")
    public void setPcDomain(String domain) {
        pcDomain = domain;
    }

    @Value("${domain.gateway}")
    public void setGateWayDomain(String domain) {
        gateWayDomain = domain;
    }

    public static String makeCdnUrl(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }

        StringBuffer url = new StringBuffer();

        if (path.startsWith("http")) {
            return path;
        } else {
            url.append(cdnUrl);
        }

        if (path.startsWith("/")) {
            url.append(path);
        } else {
            url.append("/").append(path);
        }

        return url.toString();
    }

    public static String makePageFullUrl(String path, DisplayScreenType displayScreenType) {
        if (path == null || path.isEmpty()) {
            return null;
        }

        if (displayScreenType == DisplayScreenType.PC) {
            return String.format("%s%s", pcDomain, path);
        }
        return String.format("%s%s", mobileDomain, path);

    }

    public static String makeGateWayFullUrl(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        return String.format("%s%s", gateWayDomain, path);
    }
}

