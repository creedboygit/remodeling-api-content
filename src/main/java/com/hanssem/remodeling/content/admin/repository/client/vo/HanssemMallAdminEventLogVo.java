package com.hanssem.remodeling.content.admin.repository.client.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HanssemMallAdminEventLogVo {
	private String regId;
    private String pageNm;
    private String srvNm;
    private String srvPort;
    private String wasIP;
    private String wasPort;
    private String pageURL;
    private String method;
    private String queryString;
    private String clientIPAddr;
    private String clientAgent;
}
