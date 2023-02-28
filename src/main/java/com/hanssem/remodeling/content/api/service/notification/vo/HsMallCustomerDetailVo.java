package com.hanssem.remodeling.content.api.service.notification.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hanssem.remodeling.content.api.service.notification.util.NotificationUtil;
import java.time.LocalDate;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class HsMallCustomerDetailVo {

    private String code;
    private String message;

    private CustDetailMap custDetailMap;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class CustDetailMap {

        @JsonProperty("CustNo")
        private long custNo;
        @JsonProperty("CustGradeNo")
        private int custGradeNo;
        @JsonProperty("CustTypCd")
        private String custTypCd;
        @JsonProperty("CustID")
        private String custId;
        @JsonProperty("CustNm")
        private String custNm;
        @JsonProperty("RegYmdt")
        private LocalDate regYmdt;
        @JsonProperty("RegId")
        private int regId;
        @JsonProperty("IntMbsJoinYmdt")
        private LocalDate intMbsJoinYmdt;
        @JsonProperty("IntMbsJoinYN")
        private String intMbsJoinYn;
        @JsonProperty("IntMbsMobileCardNo")
        private String intMbsMobileCardNo;
        @JsonProperty("IntMbsPvcCardNo")
        private String intMbsPvcCardNo;
        @JsonProperty("IntMbsCustNo")
        private String intMbsCustNo;
        @JsonProperty("ChannelCd")
        private String channelCd;
        @JsonProperty("SessionToken")
        private String sessionToken;
        @JsonProperty("CorConfYN")
        private String corConfYn;
        @JsonProperty("MobileNo_01")
        private String mobileNo_01;
        @JsonProperty("MobileNo_02")
        private String mobileNo_02;
        @JsonProperty("MobileNo_03")
        private String mobileNo_03;
        @JsonProperty("ZipNoNew")
        private String zipNoNew;
        @JsonProperty("ZipNoNewAddr")
        private String zipNoNewAddr;
        @JsonProperty("AddrDtlNew")
        private String addrDtlNew;
        @JsonProperty("ZipNoNewBuildingNo")
        private String zipNoNewBuildingNo;

    }

    public long getCustNo() {
        return Optional.ofNullable(this.custDetailMap)
                .map(CustDetailMap::getCustNo)
                .orElse(0L);
    }

    public String getMobileNo() {
        return Optional.ofNullable(this.custDetailMap)
                .map(x -> String.format("%s%s%s",
                        x.getMobileNo_01(),
                        x.getMobileNo_02(),
                        x.getMobileNo_03()))
                .orElse(NotificationUtil.DEFAULT_MOBILE_NO);
    }
}
