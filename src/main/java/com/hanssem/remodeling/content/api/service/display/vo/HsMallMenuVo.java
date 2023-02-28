package com.hanssem.remodeling.content.api.service.display.vo;

import com.hanssem.remodeling.content.constant.HsMallType;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class HsMallMenuVo {

    private String code;
    private String message;

    private List<MenuGrp> menuGrpList = new ArrayList<>();

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class MenuGrp {

        private HsMallType mallType;
        private String sort;

        // 템플릿타입: TEMP, 카테고리: CATE, 링크텍스트타입: LINK
        private String menuType;
        private String typeVal1;

        // menuType=CAT인 경우. 카테고리범위: cateL, cateM, cateS
        private String typeVal2;

        // menuType=CAT 인 경우.
        private List<GrpCtg> grpCtgList = new ArrayList<>();
        // menuType=LINK 인 경우.
        private List<MenuData> menuDataList = new ArrayList<>();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class GrpCtg {

        private String ctgNm;
        private String ctgNo;
        private String linkUrl;
        private String appLinkTarget;
        private String appLinkChannel;
        private String appLinkVal;
        private String appLinkSubVal;
        private AppLinkOption appLinkOption;
        private String imgUrl;
        private String sort;
        private String childCnt;
        private List<Ctg> ctgList = new ArrayList<>();
        private List<SubMenuData> subMenuDataList = new ArrayList<>();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Ctg {

        private String ctgNm;
        private String ctgNo;
        private String linkUrl;
        private String appLinkTarget;
        private String appLinkChannel;
        private String appLinkVal;
        private String appLinkSubVal;
        private AppLinkOption appLinkOption;
        private String imgUrl;
        private String sort;
        private List<SubMenuData> subMenuDataList = new ArrayList<>();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class SubMenuData {

        private String menuDataNm;
        private String sort;
        private String imgUrl;
        private String linkUrl;
        private String appLinkTarget;
        private String appLinkChannel;
        private String appLinkVal;
        private String appLinkSubVal;
        private AppLinkOption appLinkOption;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class AppLinkOption {

        private String ctgNo;
        private String ctgNm;
        private String ctgDepth;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class MenuData {

        private String menuDataNm;
        private String sort;
        private String imgUrl;
        private String linkUrl;
        private String appLinkTarget;
        private String appLinkChannel;
        private String appLinkVal;
        private String appLinkSubVal;
        private AppLinkOption appLinkOption;
        private List<SubMenuData> subMenuDataList = new ArrayList<>();
    }

}
