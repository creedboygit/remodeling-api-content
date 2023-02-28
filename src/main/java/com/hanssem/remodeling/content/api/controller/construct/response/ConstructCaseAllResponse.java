package com.hanssem.remodeling.content.api.controller.construct.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(title = "ConstructCaseAllResponse", description = "시공사례 응답 객체 (한샘몰 API)")
public class ConstructCaseAllResponse {

    private String code;

    private String message;

    private PagingMapResponse pagingMap;

    private List<ContentsListResponse> contentsList = new ArrayList<>();

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class PagingMapResponse {

        @Schema(title = "curPage", description = "", example = "")
        private String curPage;

        @Schema(title = "totalRecordCount", description = "", example = "")
        private String totalRecordCount;

        @Schema(title = "pageCount", description = "", example = "")
        private String pageCount;

        @Schema(title = "pageRow", description = "", example = "")
        private String pageRow;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ContentsListResponse {

        @Schema(title = "seq", description = "시공사례 번호 (홈아이디어 마스터 번호)", example = "8553")
        private String seq;

        @Schema(title = "imgUrl", description = "홈아이디어 대표 이미지 url", example = "//devimage.hanssem.com/hsimg/upload/homeIdea/2022/12/07/1670413793043_0.jpg")
        private String imgUrl;

        @Schema(title = "area1", description = "면적(m2)", example = "86")
        private int area1;

        @Schema(title = "area2", description = "면적(평형)", example = "26")
        private String area2;

        @Schema(title = "title", description = "제목", example = "아이들과 함께 사는 집")
        private String title;

        @Schema(title = "wishYn", description = "좋아요 여부 (해당 custNo로 좋아요 있을 시 Y 반환)", example = "Y")
        private String wishYn;

        @Schema(title = "newYN", description = "new게시글 여부", example = "Y")
        private String newYN;

        @Schema(title = "shopNm", description = "매장명(or전문가이름)", example = "한샘 에디터 한샘 디자인파크")
        private String shopNm;

        @Schema(title = "shopImgUrl", description = "매장프로필사진(or전문가프로필사진)", example = "//devimage.hanssem.com/hsimg/homeidea/common/store_profile_no_image.png")
        private String shopImgUrl;

        @Schema(title = "addrAptNm", description = "아파트명", example = "한샘빌딩")
        private String addrAptNm;

        @Schema(title = "sidoSggNm", description = "지역명(시도 군구)", example = "서울특별시 마포구")
        private String sidoSggNm;

        @Schema(title = "contentsTypeCd", description = "콘텐츠 유형 (C:시공사례, V:3D제안)", example = "C")
        private String contentsTypeCd;

        @Schema(title = "vrUrl", description = "vr Url", example = "N")
        private String vrUrl;
    }
}
