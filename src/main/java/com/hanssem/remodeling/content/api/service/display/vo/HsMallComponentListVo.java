package com.hanssem.remodeling.content.api.service.display.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class HsMallComponentListVo {

    private String code;
    private String message;
    private String dispTemplateNo;
    private String templateApiCd;

    private List<MallDisplayComponent> dispComponentList = new ArrayList<>();
    private List<MallDisplayComponentGds> dispComponentListGds = new ArrayList<>();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class MallDisplayComponent {

        @JsonProperty("rNum")
        private int rNum;
        private String adType;
        private String acctTypeOption;
        private String acctRelTypeOption;
        private String dispComponentType;
        private String isAuto;
        private int adNo;
        private int dispTemplateNo;
        private int dispComponentNo;
        private String ctgNo;
        private String cdGrpNo;
        private String componentLayoutCd;
        private String showTitleYn;
        private String useSubTitleYn;
        private String useSeeMoreYn;
        private String changeSeeMoreTitleYn;
        private String useAutoCompTitleYn;
        private String subTitle;
        private String seeMoreTitle;
        private String bgColorR;
        private String bgColorG;
        private String bgColorB;

        private String jspNm;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    public static class MallDisplayComponentGds {

        @JsonProperty("rNum")
        private int rNum;
        private String adType;
        private String dispComponentType;
        private String isAuto;
        private int adNo;
        private int dispTemplateNo;
        private int dispComponentNo;
        private String ctgNo;
        private String cdGrpNo;
        private String componentLayoutCd;
        private String gdsCnt;
    }
}
