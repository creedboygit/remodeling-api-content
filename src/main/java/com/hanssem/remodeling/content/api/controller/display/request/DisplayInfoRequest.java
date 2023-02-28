package com.hanssem.remodeling.content.api.controller.display.request;

import com.hanssem.remodeling.content.constant.DisplayScreenType;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springdoc.api.annotations.ParameterObject;

@Schema(name = "DisplayInfoRequest", description = "전시 컴포넌트 목록 + 데이터 조회")
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ParameterObject
public class DisplayInfoRequest {

    @Schema(title = "dispTemplateNo", description = "목록 조회 Param<br>"
            + "전시템플릿번호(dispTemplateNo 혹은 templateApiCd 하나 필수)", type = "string", example = "343")
    private String dispTemplateNo;

    @Schema(title = "templateApiCd", description = "목록 조회 Param<br>"
            + "전시템플릿API코드(dispTemplateNo 혹은 templateApiCd 하나 필수)", type = "string", example = "M_MAIN_MENU")
    private String templateApiCd;

    @Schema(title = "ctgNo", description = "목록 조회 Param<br>"
            + "카테고리번호", type = "string", example = "")
    private String ctgNo;

    @Schema(title = "custNo", description = "데이터 조회 Param<br>"
            + "회원번호. 스크랩여부 확인 용도", type = "string", example = "")
    private String custNo;

    @Schema(title = "pcId", description = "데이터 조회 Param<br>"
            + "추천 개인화 정보 조회 시 사용되는 pcId(아이겐)<br>"
            + "> RECM_PRD_L_CTG, RECM_PRD_M_CTG", type = "string", example = "")
    private String pcId;

    @Schema(title = "snbCd", description = "데이터 조회 Param<br>"
            + "snb코드. 이벤트 혜택 조회시 사용", type = "string", example = "")
    private String snbCd;

    @Schema(name = "sort", description = "데이터 조회 Param<br>"
            + "정렬값. 이벤트 혜택 조회시 사용<br>"
            + "> 이벤트 헤택: CS(최신), MI(마감임박)", type = "string", example = "")
    private String sort;

    @Deprecated
    @Hidden
    @Schema(title = "spaceTypeCdList", description = "데이터 조회 Param<br>"
            + "홈아이디어(인기사진) 공간 필터값<br>"
            + "공간(CD104)코드<br>"
            + "> 홈아이디어 - 추천(홈) -  홈아이디어(인기사진)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String spaceTypeCdList;

    @Deprecated
    @Hidden
    @Schema(title = "curPage", description = "데이터 조회 Param<br>"
            + "현재 페이지 번호"
            + "> 홈아이디어 - 추천(홈) -  홈아이디어(인기사진)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String curPage;

    @Deprecated
    @Hidden
    @Schema(title = "curRow", description = "데이터 조회 Param<br>"
            + "페이지당 노출 건수. (페이징 지원 컴포넌트일 경우에만 유효함)<br>"
            + "> 홈아이디어 - 추천(홈) -  홈아이디어(인기사진)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String curRow;

    @Deprecated
    @Hidden
    @Schema(title = "gdsNo", description = "데이터 조회 Param<br>"
            + "상품번호"
            + "> 홈아이디어 - 추천(홈) - 홈아이디어 추천(상품기반)컴포넌트 조회시 필요", type = "string", example = "", hidden = true)
    private String gdsNo;

    @Schema(title = "channelCd", description = "진입채널: Mobile / PC(default: Mobile)", type = "string", example = "MOBILE", implementation = DisplayScreenType.class)
    @Builder.Default
    private DisplayScreenType channelCd = DisplayScreenType.MOBILE;

    // 캐시 적용 관련 사용자 파라메터 이슈가 있으므로, AuthClaim.userId 를 사용할수 없음(로그인한 모든 요청이 캐시되므로). 필요시 클라이언트에서 custNo(userId)를 입력하도록 할 것.
    public String toStringWithoutCustNoAndHiddenField() {
        return "DisplayInfoRequest(" +
                "dispTemplateNo=" + dispTemplateNo +
                ", templateApiCd=" + templateApiCd +
                ", ctgNo=" + ctgNo +
                ", pcId=" + pcId +
                ", snbCd=" + snbCd +
                ", sort=" + sort +
                ", channelCd=" + channelCd +
                ')';
    }
}
