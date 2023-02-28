package com.hanssem.remodeling.content.api.controller.display.response;

import com.hanssem.remodeling.content.constant.DisplayMenuType;
import com.hanssem.remodeling.content.constant.HsMallType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(name = "DisplayMenuResponse", description = "전시 메뉴 조회")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DisplayMenuResponse {

    @Schema(title = "menuGrpList", description = "메뉴그룹목록.", type = "array")
    @Builder.Default
    private List<MenuGrp> menuGrpList = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuGrp {

        @Schema(title = "mallType", description = "섹션구분(REMODELING/MAIN/MALL/HOMEIDEA)", type = "string", implementation = HsMallType.class, example = "REMODELING")
        private HsMallType mallType;
        @Schema(title = "sort", description = "순서", type = "string", example = "1")
        private String sort;

        @Schema(title = "menuType", description = "메뉴타입: LINK(링크텍스트), CAT(카테고리), TEMP(전시템플릿)", type = "string", implementation = DisplayMenuType.class, example = "LINK")
        private DisplayMenuType menuType;
        @Schema(title = "typeVal1", description = "menuType에 따라 상이.<br>"
                + "menuType=LINK=빈값<br>"
                + "menuType=CAT=cateL/cateM/cateS, <br>"
                + "menuType=TEMP=전시템플릿번호: '/api/v1/display/display-info' API의 파라메터로 사용.", type = "string", example = "")
        private String typeVal1;

        @Schema(title = "typeVal2", description = "menuType=CAT 인 경우만 존재.<br>"
                + "카테고리범위: cateL, cateM, cateS)", type = "string", example = "cateM")
        private String typeVal2;

        @Schema(title = "grpCtgList", description = "menuType=CAT 인 경우만 존재.", type = "array")
        @Builder.Default
        private List<GrpCtg> grpCtgList = new ArrayList<>();

        @Schema(title = "menuDataList", description = "menuType=LINK 인 경우만 존재.", type = "array")
        @Builder.Default
        private List<MenuData> menuDataList = new ArrayList<>();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GrpCtg {

        @Schema(title = "ctgNm", description = "카테고리명. /API/v5/category/navigationList 참고", type = "string", example = "전체리모델링")
        private String ctgNm;
        @Schema(title = "ctgNo", description = "카테고리번호", type = "string", example = "343")
        private String ctgNo;
        @Schema(title = "linkUrl", description = "링크URL", type = "string", example = "https://dev-m.remodeling.hanssem.com/event")
        private String linkUrl;
        @Schema(title = "appLinkTarget", description = "앱링크타켓유형(LINK/GNB/CATE)", type = "string", example = "LINK")
        private String appLinkTarget;
        @Schema(title = "appLinkVal", description = "앱링크값-appLinkTarget에 따라 상이.<br>"
                + "LINK=URL,<br>"
                + "GNB=templateNo,<br>"
                + "CATE=ctgNo", type = "string", example = "")
        private String appLinkVal;
        @Schema(title = "appLinkSubVal", description = "앱링크 세부값<br>"
                + "> SNB 코드 등.", type = "string", example = "")
        private String appLinkSubVal;
        @Schema(title = "appLinkChannel", description = "앱링크채널(REMODELING/MAIN/MALL/HOMEIDEA)", type = "string", example = "REMODELING")
        private String appLinkChannel;
        @Schema(title = "appLinkOption", description = "appLinkTarget=CATE 인 케이스만 추가.", type = "object", implementation = AppLinkOption.class)
        private AppLinkOption appLinkOption;
        @Schema(title = "imgUrl", description = "카테고리 아이콘 이미지URL(통합DT오픈시는 사용안함)", type = "string", example = "https://devimage.hanssem.com/hsimg/upload/display/area/2018/03/08/1520493445778_0.png")
        private String imgUrl;
        @Schema(title = "sort", description = "순서", type = "string", example = "1")
        private String sort;
        @Schema(title = "childCnt", description = "하위 카테고리 갯수", type = "string", example = "1")
        private String childCnt;

        @Schema(title = "ctgList", description = "카테고리 목록", type = "array")
        @Builder.Default
        private List<Ctg> ctgList = new ArrayList<>();
        @Schema(title = "subMenuDataList", description = "카테고리 지정 후 메뉴 추가한 경우 존재.", type = "array")
        @Builder.Default
        private List<SubMenuData> subMenuDataList = new ArrayList<>();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Ctg {

        @Schema(title = "ctgNm", description = "카테고리명. ShowAllYn=1 '전체보기 포함'한 경우 첫번째로 추가", type = "string", example = "전체리모델링")
        private String ctgNm;
        @Schema(title = "ctgNo", description = "카테고리번호", type = "string", example = "343")
        private String ctgNo;
        @Schema(title = "linkUrl", description = "링크URL", type = "string", example = "https://dev-m.remodeling.hanssem.com/event")
        private String linkUrl;
        @Schema(title = "appLinkTarget", description = "앱링크타켓유형(LINK/GNB/CATE)", type = "string", example = "LINK")
        private String appLinkTarget;
        @Schema(title = "appLinkVal", description = "앱링크값-appLinkTarget에 따라 상이.<br>"
                + "LINK=URL,<br>"
                + "GNB=templateNo,<br>"
                + "CATE=ctgNo", type = "string", example = "")
        private String appLinkVal;
        @Schema(title = "appLinkSubVal", description = "앱링크 세부값<br>"
                + "> SNB 코드 등.", type = "string", example = "")
        private String appLinkSubVal;
        @Schema(title = "appLinkChannel", description = "앱링크채널(REMODELING/MAIN/MALL/HOMEIDEA)", type = "string", example = "REMODELING")
        private String appLinkChannel;
        @Schema(title = "appLinkOption", description = "appLinkTarget=CATE 인 케이스만 추가.", type = "object", implementation = AppLinkOption.class)
        private AppLinkOption appLinkOption;
        @Schema(title = "imgUrl", description = "이미지URL", type = "string", example = "https://devimage.hanssem.com/hsimg/upload/display/area/2018/03/08/1520493445778_0.png")
        private String imgUrl;
        @Schema(title = "sort", description = "순서", type = "string", example = "1")
        private String sort;

        @Schema(title = "subMenuDataList", description = "카테고리 지정 후 메뉴 추가한 경우 존재.", type = "array")
        @Builder.Default
        private List<SubMenuData> subMenuDataList = new ArrayList<>();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubMenuData {

        @Schema(title = "menuDataNm", description = "메뉴명", type = "string", example = "메뉴명")
        private String menuDataNm;
        @Schema(title = "sort", description = "순서", type = "string", example = "1")
        private String sort;
        @Schema(title = "imgUrl", description = "이미지URL", type = "string", example = "https://devimage.hanssem.com/hsimg/upload/display/area/2018/03/08/1520493445778_0.png")
        private String imgUrl;
        @Schema(title = "linkUrl", description = "링크URL", type = "string", example = "https://dev-m.remodeling.hanssem.com/event")
        private String linkUrl;
        @Schema(title = "appLinkTarget", description = "앱링크타켓유형(LINK/GNB/CATE)", type = "string", example = "LINK")
        private String appLinkTarget;
        @Schema(title = "appLinkVal", description = "앱링크값-appLinkTarget에 따라 상이.<br>"
                + "LINK=URL,<br>"
                + "GNB=templateNo,<br>"
                + "CATE=ctgNo", type = "string", example = "")
        private String appLinkVal;
        @Schema(title = "appLinkSubVal", description = "앱링크 세부값<br>"
                + "> SNB 코드 등.", type = "string", example = "")
        private String appLinkSubVal;
        @Schema(title = "appLinkChannel", description = "앱링크채널(REMODELING/MAIN/MALL/HOMEIDEA)", type = "string", example = "REMODELING")
        private String appLinkChannel;
        @Schema(title = "appLinkOption", description = "appLinkTarget=CATE 인 케이스만 추가.", type = "object", implementation = AppLinkOption.class)
        private AppLinkOption appLinkOption;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppLinkOption {

        @Schema(title = "ctgNo", description = "카테고리번호", type = "string", example = "343")
        private String ctgNo;
        @Schema(title = "ctgNm", description = "카테고리명", type = "string", example = "전체리모델링")
        private String ctgNm;
        @Schema(title = "ctgDepth", description = "카테고리 레벨", type = "string", example = "2")
        private String ctgDepth;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MenuData {

        @Schema(title = "menuDataNm", description = "메뉴명", type = "string", example = "메뉴명")
        private String menuDataNm;
        @Schema(title = "sort", description = "순서", type = "string", example = "1")
        private String sort;
        @Schema(title = "imgUrl", description = "이미지URL", type = "string", example = "https://devimage.hanssem.com/hsimg/upload/display/area/2018/03/08/1520493445778_0.png")
        private String imgUrl;
        @Schema(title = "linkUrl", description = "링크URL", type = "string", example = "https://dev-m.remodeling.hanssem.com/event")
        private String linkUrl;
        @Schema(title = "appLinkTarget", description = "앱링크타켓유형(LINK/GNB/CATE)", type = "string", example = "LINK")
        private String appLinkTarget;
        @Schema(title = "appLinkVal", description = "앱링크값-appLinkTarget에 따라 상이.<br>"
                + "LINK=URL,<br>"
                + "GNB=templateNo,<br>"
                + "CATE=ctgNo", type = "string", example = "")
        private String appLinkVal;
        @Schema(title = "appLinkSubVal", description = "앱링크 세부값<br>"
                + "> SNB 코드 등.", type = "string", example = "")
        private String appLinkSubVal;
        @Schema(title = "appLinkChannel", description = "앱링크채널(REMODELING/MAIN/MALL/HOEIDEA)", type = "string", example = "REMODELING")
        private String appLinkChannel;
        @Schema(title = "appLinkOption", description = "appLinkTarget=CATE 인 케이스만 추가.", type = "object", implementation = AppLinkOption.class)
        private AppLinkOption appLinkOption;

        @Schema(title = "subMenuDataList", description = "2LEVEL 등록값 존재하는 경우 존재.", type = "array")
        @Builder.Default
        private List<SubMenuData> subMenuDataList = new ArrayList<>();
    }

}
