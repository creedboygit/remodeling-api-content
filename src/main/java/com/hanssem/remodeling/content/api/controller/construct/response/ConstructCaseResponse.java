package com.hanssem.remodeling.content.api.controller.construct.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(title = "ConstructCaseResponse", description = "시공사례 응답 객체 (한샘몰 API)")
public class ConstructCaseResponse {

    //고유번호(seq)
    @Schema(title = "id", description = "시공사례 번호 (홈아이디어 마스터 번호)", example = "8553")
    private int id;

    //제목(title)
    @Schema(title = "title", description = "제목", example = "아이들과 함께 사는 집")
    private String title;

    //랜딩URL
    @Schema(title = "detailUrl", description = "랜딩URL", example = "https://pjmall.hanssem.com/m/homeIdea/contents/homeIdeaDetail.do?seq=8563")
    private String detailUrl;

    //이미지(imgUrl)
    @Schema(title = "imageUrl", description = "홈아이디어 대표 이미지 url", example = "//devimage.hanssem.com/hsimg/upload/homeIdea/2022/12/07/1670413793043_0.jpg")
    private String imageUrl;

    //좋아요여부(wishYn)
    @Schema(title = "wishYn", description = "좋아요 여부 (해당 custNo로 좋아요 있을 시 Y 반환)", example = "Y")
    private String wishYn;

    //new게시글 여부(newYn)
    @Schema(title = "newYn", description = "new게시글 여부", example = "Y")
    private String newYn;

    //컨텐츠유형(contentsTypeCd)
    @Schema(title = "contentsType", description = "콘텐츠 유형 (C:시공사례, V:3D제안)", example = "C")
    private String contentsType;

    //VR URL(vrUrl)
    @Schema(title = "vrUrl", description = "vr Url", example = "")
    private String vrUrl;

    //아파트정보
    private Apartment apartment;

    //매장정보
    private Shop shop;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Apartment {

        //아파트명 (addrAptNm)
        @Schema(title = "name", description = "아파트명", example = "한샘빌딩")
        private String name;

        //면적 (area1)
        @Schema(title = "area", description = "면적(m2)", example = "86")
//        private String area;
        private int area;

        //평형 (area2)
        @Schema(title = "ptyp", description = "면적(평형)", example = "26")
        private String ptyp;

        //지역명(시도군구) (sidoSggNm)
        @Schema(title = "sidoGungu", description = "지역명(시도 군구)", example = "서울특별시 마포구")
        private String sidoGungu;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    public static class Shop {

        //매장명(or전문가이름) (shopNm)
        @Schema(title = "name", description = "", example = "")
        private String name;

        //매장프로필사진(or전문가프로필사진) (shopImgUrl)
        @Schema(title = "image", description = "", example = "")
        private String image;
    }
}
