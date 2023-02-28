package com.hanssem.remodeling.content.common.model;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AdminEventInfo implements Serializable {

    private String regId;
    private String pageNm;
    private int roleIdx;
    private String pageNo;
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
