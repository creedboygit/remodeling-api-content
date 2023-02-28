package com.hanssem.remodeling.content.api.controller.display.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DisplayMainGnbResponse {

    @Schema(title = "notiYn", description = "GNB강조여부값(방송여부에서 변경됨)", type = "string", example = "N")
    private String notiYn;
    @Schema(title = "rNum", description = "순서", type = "integer", example = "1")
    @JsonProperty("rNum")
    private int rNum;
    @Schema(title = "gnbNm", description = "GNB명", type = "string", example = "추천")
    private String gnbNm;
    @Schema(title = "dispTemplateNo", description = "전시템플릿번호(전시컴포넌트 목록조회 파라미터로 사용)", type = "string", example = "343")
    private String dispTemplateNo;
    @Schema(title = "module", description = "모듈코드(app에서 GNB역할구분)", type = "string", example = "GnbRemodelingSection")
    private String module;
    @Schema(title = "templateApiCd", description = "전시템플릿 API코드", type = "string", example = "templateApiCd")
    private String templateApiCd;
    @Schema(title = "imgTypeUseYn", description = "SNB이미지유형 사용여부", type = "string", example = "N")
    private String imgTypeUseYn;

    @Schema(title = "pageUrl", description = "pageUrl. content 추가컬럼.", type = "string", example = "https://")
    private String pageUrl;

    List<DisplayMallSnbV5> snbList = new ArrayList<>();


    @Getter
    @Setter
    public static class DisplayMallSnbV5 {

        @Schema(title = "rNum", description = "순서", type = "integer", example = "1")
        @JsonProperty("rNum")
        private int rNum;
        @Schema(title = "snbNm", description = "SNB명", type = "string", example = "인테리어매장상세")
        private String snbNm;
        @Schema(title = "snbCd", description = "SNB코드", type = "string", example = "")
        private String snbCd;
        @Schema(title = "dispTemplateNo", description = "SNB전시템플릿번호", type = "string", example = "358")
        private String dispTemplateNo;
        @Schema(title = "bannerUrl", description = "SNB이미지URL", type = "string", example = "")
        private String bannerUrl;
        @Schema(title = "bannerUrl2", description = "SNB이미지URL2", type = "string", example = "")
        private String bannerUrl2;
    }

}
