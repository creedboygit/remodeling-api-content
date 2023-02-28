package com.hanssem.remodeling.content.api.controller.display;

import com.hanssem.remodeling.content.api.controller.display.request.DisplayComponentDataRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayComponentListRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayGnbRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayInfoRequest;
import com.hanssem.remodeling.content.api.controller.display.request.DisplayMenuRequest;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentListResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayComponentWithDataResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayInfoResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMainGnbResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayMenuResponse;
import com.hanssem.remodeling.content.api.controller.display.response.DisplayTemplateInfoResponse;
import com.hanssem.remodeling.content.api.service.display.DisplayService;
import com.hanssem.remodeling.content.api.service.sample.SampleService;
import com.hanssem.remodeling.content.common.error.CustomException;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import com.hanssem.remodeling.content.constant.DisplayDataSearchType;
import com.hanssem.remodeling.content.constant.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.InetAddress;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "전시")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/display", produces = MediaType.APPLICATION_JSON_VALUE)
public class DisplayController {

    private final InetAddress inetAddress;
    private final AuthClaim authClaim; // Header 정보가 담겨 있는 객체입니다.

    private final DisplayService displayService;
    private final SampleService sampleService; // 임시, 테스트 후 제거.


    @Operation(summary = "전시 컴포넌트 조회 (MashUp)",
            description = "전시 컴포넌트 조회 (MashUp)<br>"
                    + "전시 컴포넌트 목록 및 컨포넌트 조회.<br><br>"
                    + "아래 1) API 호출 결과로 2) API 호출하여 데이터 취합 응답.<br>"
                    + "1) GET /api/v1/display/components 전시 컴포넌트 목록 조회<br>"
                    + "2) GET /api/v1/display/component-data 컴포넌트 데이터 조회<br><br>"
                    + "필드설명]<br>"
                    + "componentData: 어드민에서 등록된 정적 데이터. 상기 2)의 응답.<br>"
                    + "autoConfigData: 자동구성 컴포넌트에서 사용.<br><br>"
                    + "* 참고]<br>"
                    + "- ctgNo: 전시 템플릿이 카테고리 기반인 경우 필요.<br>"
                    + "- custNo, pcId, snbCd, sort: 상기 2) API에 입력되는 파라메터. 필요시 입력.<br>"
                    + "    이외 2)의 필수 파라메터는 1)의 응답으로 셋팅됨.<br>"
                    + "- componentData영역은 adType에 따라 응답이 다름.<br>"
                    + "  참고: GET /api/v1/display/component-data-samples 참고] 컴포넌트Data type 샘플 조회.<br><br>"
                    + "* 한샘몰 API 명세:<br>"
                    + "-> https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=1870753154 <br>"
                    + "-> https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=26624133<br>"
                    + "* adType 명세:<br>"
                    + "-> https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=938335744",
            responses = {
                    @ApiResponse(content = @Content(schema = @Schema(implementation = DisplayInfoResponse.class)))
            })
    @GetMapping(value = "/display-info")
    public DisplayInfoResponse findDisplayComponentInfo(
            @Valid DisplayInfoRequest displayInfoRequest) {

        return displayService.findDisplayComponentInfo(displayInfoRequest, true);
    }

    @Operation(summary = "전시 컴포넌트 목록 조회",
            description = "전시 컴포넌트 목록 조회<br><br>"
                    + "* 참고<br>"
                    + "어드민 설정에 따라, dispTemplateNo 혹은 templateApiCd 를 기반으로 전시 컴포넌트를 조회할 수 있다.<br>"
                    + "리모델링 전시에서는 templateApiCd를 사용하지 않는다.<br><br>"
                    + "* 한샘몰 API 명세: https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=1870753154",
            responses = {
                    @ApiResponse(content = @Content(schema = @Schema(implementation = DisplayComponentListResponse.class)))
            })
    @GetMapping(value = "/components")
    public DisplayComponentListResponse findDisplayComponentList(
            @Valid DisplayComponentListRequest displayComponentListRequest) {

        return displayService.findDisplayComponentList(displayComponentListRequest);
    }

    @Operation(summary = "전시 컴포넌트 데이터 조회",
            description = "전시 컴포넌트 데이터 조회<br>"
                    + "- 파라메터 : 전시 컴포넌트 목록 조회 API의 응답 참고.<br><br>"
                    + "* 한샘몰 API 명세: https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=26624133<br>"
                    + "* adType 명세: https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=938335744",
            responses = {
                    @ApiResponse(content = @Content(schema = @Schema(title = "Object", description = "componentLayoutCd에 따라 포맷이 다름.", example = "componentLayoutCd에 따라 포맷이 다름.", type = "object")))
            })
    @GetMapping(value = "/component-data")
    public Object findDisplayComponentData(
            @Valid DisplayComponentDataRequest displayComponentDataRequest) {

        return displayService.findDisplayComponentData(displayComponentDataRequest);
    }

    @Operation(summary = "전시 GNB 조회",
            description = "전시 GNB 조회<br><br>"
                    + "GNB 영역만 조회되며, 이외의 전시 영역은 아래 api를 참고 한다.<br>"
                    + "GET /api/v1/display/template-info 전시 템플릿 정보 조회",
            responses = {
                    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisplayMainGnbResponse.class))))
            })
    @GetMapping(value = "/gnb-list")
    public List<DisplayMainGnbResponse> findDisplayGnb(@Valid DisplayGnbRequest displayGnbRequest) {

        return displayService.findDisplayGnb(displayGnbRequest);
    }

    @Operation(summary = "전시 템플릿 정보 조회",
            description = "전시 템플릿 정보 조회 - 리모델링 전시에서 필요한 정보 조회.<br><br>"
                    + "* largeCategoryTemplateNo: 대카템플릿 templateNo<br>"
                    + "* middleCategoryTemplateNo: 중카템플릿 templateNo<br>"
                    + "* smallCategoryTemplateNo: 소카템플릿 templateNo<br>"
                    + "* shopIntAgntDetailTemplateNo: 매장INT대리점상세 templateNo<br>"
                    + "* mainMenuTemplateCode: 통합메인메뉴 templateCode<br>",
            responses = {
                    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisplayTemplateInfoResponse.class))))
            })
    @GetMapping(value = "/template-info")
    public List<DisplayTemplateInfoResponse> findDisplayCategoryTemplateInfo() {

        return displayService.findDisplayTemplateInfo();
    }

    @Operation(summary = "참고] 컴포넌트Data type 샘플 조회.",
            description = "컴포넌트Data type 샘플를 조회한다.<br><br>"
                    + "* 앱/프론트 개발 참고 목적.<br><br>"
                    + "* 실제 셋팅된 전시 데이터를 기반으로 응답.<br>"
                    + "* 참고<br>"
                    + "- 빈값으로 호출할 경우 전체 응답")
    @GetMapping(value = "/component-data-samples")
    public Map<String, Object> getDisplayDataSamples(
            @RequestParam(required = true) DisplayDataSearchType displayDataSearchType
            , @RequestParam(required = false) String keyword
            , @Schema(type = "boolean", example = "false") Boolean isRefresh) {

        // 예외] redis 적용을 위해, controller에 로직 구현함.
        List<DisplayComponentWithDataResponse> allDataList = displayService.getDisplayDataSamples(isRefresh);

        Map<String, Object> resultMap;

        if (displayDataSearchType == DisplayDataSearchType.COMPONENT_LAYOUT_CD) {
            resultMap = displayService.processLayoutCd(allDataList);
        } else if (displayDataSearchType == DisplayDataSearchType.AD_TYPE) {
            resultMap = displayService.processAdType(allDataList);
        } else {
            throw new CustomException(ResponseCode.BAD_REQUEST);
        }

        if (StringUtils.isNotBlank(keyword)) {
            if (resultMap.containsKey(keyword)) {
                return Map.of(keyword, resultMap.get(keyword));
            }

            throw new CustomException(ResponseCode.NOT_FOUND_DATA);
        }

        return resultMap;
    }

    @Operation(summary = "전시 Cache clear - 전체",
            description = "전시 캐시 전체 삭제.")
    @DeleteMapping(value = "/cache-clear-all")
    public void clearDisplayCacheAll() {
        displayService.clearDisplayCacheAll();
    }


    @Operation(summary = "메뉴 목록",
            description = "메뉴 목록")
    @GetMapping(value = "/menus")
    public DisplayMenuResponse findMenuList(@Valid DisplayMenuRequest displayMenuRequest) {

        return displayService.findMenuList(displayMenuRequest);
    }

}
