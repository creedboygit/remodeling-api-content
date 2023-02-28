package com.hanssem.remodeling.content.api.service.display.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HsMallGnbVo {

    private String code;
    private String message;

    private List<DisplayMallGnb> gnbList = new ArrayList<>();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DisplayMallGnb {

        @JsonProperty("rNum")
        private int rNum;
        private String gnbNm;
        private String module;
        private String dispTemplateNo;
        private String notiYn;
        private String templateApiCd;
        private String imgTypeUseYn;

        private List<DisplayMallSnb> snbList = new ArrayList<>();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class DisplayMallSnb {

        @JsonProperty("rNum")
        private int rNum;
        private String snbNm;
        private String snbCd;
        private String dispTemplateNo;
        private String bannerUrl;
        private String bannerUrl2;
    }

}
