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

@Tag(name = "??????")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/display", produces = MediaType.APPLICATION_JSON_VALUE)
public class DisplayController {

    private final InetAddress inetAddress;
    private final AuthClaim authClaim; // Header ????????? ?????? ?????? ???????????????.

    private final DisplayService displayService;
    private final SampleService sampleService; // ??????, ????????? ??? ??????.


    @Operation(summary = "?????? ???????????? ?????? (MashUp)",
            description = "?????? ???????????? ?????? (MashUp)<br>"
                    + "?????? ???????????? ?????? ??? ???????????? ??????.<br><br>"
                    + "?????? 1) API ?????? ????????? 2) API ???????????? ????????? ?????? ??????.<br>"
                    + "1) GET /api/v1/display/components ?????? ???????????? ?????? ??????<br>"
                    + "2) GET /api/v1/display/component-data ???????????? ????????? ??????<br><br>"
                    + "????????????]<br>"
                    + "componentData: ??????????????? ????????? ?????? ?????????. ?????? 2)??? ??????.<br>"
                    + "autoConfigData: ???????????? ?????????????????? ??????.<br><br>"
                    + "* ??????]<br>"
                    + "- ctgNo: ?????? ???????????? ???????????? ????????? ?????? ??????.<br>"
                    + "- custNo, pcId, snbCd, sort: ?????? 2) API??? ???????????? ????????????. ????????? ??????.<br>"
                    + "    ?????? 2)??? ?????? ??????????????? 1)??? ???????????? ?????????.<br>"
                    + "- componentData????????? adType??? ?????? ????????? ??????.<br>"
                    + "  ??????: GET /api/v1/display/component-data-samples ??????] ????????????Data type ?????? ??????.<br><br>"
                    + "* ????????? API ??????:<br>"
                    + "-> https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=1870753154 <br>"
                    + "-> https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=26624133<br>"
                    + "* adType ??????:<br>"
                    + "-> https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=938335744",
            responses = {
                    @ApiResponse(content = @Content(schema = @Schema(implementation = DisplayInfoResponse.class)))
            })
    @GetMapping(value = "/display-info")
    public DisplayInfoResponse findDisplayComponentInfo(
            @Valid DisplayInfoRequest displayInfoRequest) {

        return displayService.findDisplayComponentInfo(displayInfoRequest, true);
    }

    @Operation(summary = "?????? ???????????? ?????? ??????",
            description = "?????? ???????????? ?????? ??????<br><br>"
                    + "* ??????<br>"
                    + "????????? ????????? ??????, dispTemplateNo ?????? templateApiCd ??? ???????????? ?????? ??????????????? ????????? ??? ??????.<br>"
                    + "???????????? ??????????????? templateApiCd??? ???????????? ?????????.<br><br>"
                    + "* ????????? API ??????: https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=1870753154",
            responses = {
                    @ApiResponse(content = @Content(schema = @Schema(implementation = DisplayComponentListResponse.class)))
            })
    @GetMapping(value = "/components")
    public DisplayComponentListResponse findDisplayComponentList(
            @Valid DisplayComponentListRequest displayComponentListRequest) {

        return displayService.findDisplayComponentList(displayComponentListRequest);
    }

    @Operation(summary = "?????? ???????????? ????????? ??????",
            description = "?????? ???????????? ????????? ??????<br>"
                    + "- ???????????? : ?????? ???????????? ?????? ?????? API??? ?????? ??????.<br><br>"
                    + "* ????????? API ??????: https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=26624133<br>"
                    + "* adType ??????: https://docs.google.com/spreadsheets/d/17n_Lz1maEP9w7-9gbjIegrQYOcQgVGdrJpzjY4o-QY8/edit#gid=938335744",
            responses = {
                    @ApiResponse(content = @Content(schema = @Schema(title = "Object", description = "componentLayoutCd??? ?????? ????????? ??????.", example = "componentLayoutCd??? ?????? ????????? ??????.", type = "object")))
            })
    @GetMapping(value = "/component-data")
    public Object findDisplayComponentData(
            @Valid DisplayComponentDataRequest displayComponentDataRequest) {

        return displayService.findDisplayComponentData(displayComponentDataRequest);
    }

    @Operation(summary = "?????? GNB ??????",
            description = "?????? GNB ??????<br><br>"
                    + "GNB ????????? ????????????, ????????? ?????? ????????? ?????? api??? ?????? ??????.<br>"
                    + "GET /api/v1/display/template-info ?????? ????????? ?????? ??????",
            responses = {
                    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisplayMainGnbResponse.class))))
            })
    @GetMapping(value = "/gnb-list")
    public List<DisplayMainGnbResponse> findDisplayGnb(@Valid DisplayGnbRequest displayGnbRequest) {

        return displayService.findDisplayGnb(displayGnbRequest);
    }

    @Operation(summary = "?????? ????????? ?????? ??????",
            description = "?????? ????????? ?????? ?????? - ???????????? ???????????? ????????? ?????? ??????.<br><br>"
                    + "* largeCategoryTemplateNo: ??????????????? templateNo<br>"
                    + "* middleCategoryTemplateNo: ??????????????? templateNo<br>"
                    + "* smallCategoryTemplateNo: ??????????????? templateNo<br>"
                    + "* shopIntAgntDetailTemplateNo: ??????INT??????????????? templateNo<br>"
                    + "* mainMenuTemplateCode: ?????????????????? templateCode<br>",
            responses = {
                    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = DisplayTemplateInfoResponse.class))))
            })
    @GetMapping(value = "/template-info")
    public List<DisplayTemplateInfoResponse> findDisplayCategoryTemplateInfo() {

        return displayService.findDisplayTemplateInfo();
    }

    @Operation(summary = "??????] ????????????Data type ?????? ??????.",
            description = "????????????Data type ????????? ????????????.<br><br>"
                    + "* ???/????????? ?????? ?????? ??????.<br><br>"
                    + "* ?????? ????????? ?????? ???????????? ???????????? ??????.<br>"
                    + "* ??????<br>"
                    + "- ???????????? ????????? ?????? ?????? ??????")
    @GetMapping(value = "/component-data-samples")
    public Map<String, Object> getDisplayDataSamples(
            @RequestParam(required = true) DisplayDataSearchType displayDataSearchType
            , @RequestParam(required = false) String keyword
            , @Schema(type = "boolean", example = "false") Boolean isRefresh) {

        // ??????] redis ????????? ??????, controller??? ?????? ?????????.
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

    @Operation(summary = "?????? Cache clear - ??????",
            description = "?????? ?????? ?????? ??????.")
    @DeleteMapping(value = "/cache-clear-all")
    public void clearDisplayCacheAll() {
        displayService.clearDisplayCacheAll();
    }


    @Operation(summary = "?????? ??????",
            description = "?????? ??????")
    @GetMapping(value = "/menus")
    public DisplayMenuResponse findMenuList(@Valid DisplayMenuRequest displayMenuRequest) {

        return displayService.findMenuList(displayMenuRequest);
    }

}
