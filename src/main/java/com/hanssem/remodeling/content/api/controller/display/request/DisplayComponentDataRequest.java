package com.hanssem.remodeling.content.api.controller.display.request;

import com.hanssem.remodeling.content.constant.DisplayScreenType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "DisplayComponentDataRequest", description = "전시 컴포넌트 데이터 조회")
@Getter
@Setter
@ToString
@ParameterObject
public class DisplayComponentDataRequest {

    @NotBlank(message = "dispComponentType 은(는) 필수 입니다.")
    @Schema(title = "dispComponentType", description = "전시컴포넌트타입", type = "string", example = "DP343_1", requiredMode = RequiredMode.REQUIRED)
    private String dispComponentType;

    @NotBlank(message = "dispComponentNo 은(는) 필수 입니다.")
    @Schema(title = "dispComponentNo", description = "전시컴포넌트번호", type = "string", example = "93841", requiredMode = RequiredMode.REQUIRED)
    private String dispComponentNo;

    @Schema(title = "ctgNo", description = "카테고리번호", type = "string", example = "")
    private String ctgNo;

    @NotBlank(message = "componentLayoutCd 은(는) 필수 입니다.")
    @Schema(title = "componentLayoutCd", description = "컴포넌트 레이아웃 코드", type = "string", example = "remodeling_banner_rolling", requiredMode = RequiredMode.REQUIRED)
    private String componentLayoutCd;

    @Schema(title = "pcId", description = "추천 개인화 정보 조회 시 사용되는 pcId(아이겐)<br>"
            + "> RECM_PRD_L_CTG, RECM_PRD_M_CTG", type = "string", example = "")
    private String pcId;

    @Schema(title = "custNo", description = "회원번호. 스크랩여부 확인 용도", type = "string", example = "")
    private String custNo;

    @Schema(title = "snbCd", description = "snb코드. 이벤트 혜택 조회시 사용", type = "string", example = "")
    private String snbCd;

    @Schema(title = "sort", description = "정렬값. 이벤트 혜택 조회시 사용<br>"
            + "> 이벤트 헤택: CS(최신), MI(마감임박)", type = "string", example = "")
    private String sort;

    @Deprecated
    @Schema(title = "spaceTypeCdList", description = "홈아이디어(인기사진) 공간 필터값<br>"
            + "공간(CD104)코드<br>"
            + "> 홈아이디어 - 추천(홈) -  홈아이디어(인기사진)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String spaceTypeCdList;

    @Deprecated
    @Schema(title = "curPage", description = "현재 페이지 번호. (페이징 지원 컴포넌트일 경우에만 유효함)<br>"
            + "> 홈아이디어 - 추천(홈) -  홈아이디어(인기사진)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String curPage;

    @Deprecated
    @Schema(title = "curRow", description = "페이지당 노출 건수. (페이징 지원 컴포넌트일 경우에만 유효함)<br>"
            + "> 홈아이디어 - 추천(홈) -  홈아이디어(인기사진)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String curRow;

    @Deprecated
    @Schema(title = "gdsNo", description = "상품번호"
            + "> 홈아이디어 - 추천(홈) - 홈아이디어 추천(상품기반)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String gdsNo;

    @Schema(title = "channelCd", description = "진입채널: Mobile / PC(default: Mobile)", type = "string", example = "MOBILE", implementation = DisplayScreenType.class)
    private DisplayScreenType channelCd = DisplayScreenType.MOBILE;

    public String toStringWithoutCustNoAndHiddenField() {
        return "DisplayComponentDataRequest(" +
                "dispComponentType=" + dispComponentType +
                ", dispComponentNo=" + dispComponentNo +
                ", ctgNo=" + ctgNo +
                ", componentLayoutCd=" + componentLayoutCd +
                ", pcId=" + pcId +
                ", snbCd=" + snbCd +
                ", sort=" + sort +
                ", channelCd=" + channelCd +
                ')';
    }
}
