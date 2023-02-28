package com.hanssem.remodeling.content.api.controller.budget;

import com.hanssem.remodeling.content.api.controller.budget.request.SaveEstimateRequest;
import com.hanssem.remodeling.content.api.controller.budget.request.StyleEstimateRequest;
import com.hanssem.remodeling.content.api.controller.budget.response.PtypResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.PtypStyleResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.SaveEstimateResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.StyleEstimateResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.StyleResponse;
import com.hanssem.remodeling.content.api.controller.budget.response.UsersEstimateResponse;
import com.hanssem.remodeling.content.api.service.budget.BudgetService;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import com.hanssem.remodeling.content.constant.SpaceDetailType;
import com.hanssem.remodeling.content.constant.SpaceType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예산감잡기")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/budget", produces = MediaType.APPLICATION_JSON_VALUE)
public class BudgetController {

    /**
     * Header 정보가 담겨 있는 객체입니다.
     */
    private final AuthClaim authClaim;

    private final BudgetService budgetService;

    @Operation(summary = "평형, 평형옵션 전체정보", description = "평형별 선택 가능한 방,욕실의 개수 정보 조회", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = PtypResponse.class)))})
    @GetMapping(value = "/ptyp-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public PtypResponse getPtyp() {
        return budgetService.getPtypList();
    }

    @Operation(summary = "스타일 조회", description = "선택한 평형대의 스타일 전체 조회<br><br>" +
            "/api/v1/budget/ptyp-info 에서 조회된 ptypMID값으로 조회<br><br>" +
            "[ptypMId] 조회값<br><br>" +
            "1) 20평형 : 1 조회<br><br>" +
            "2) 30평형 : 2 조회<br><br>" +
            "3) 40평형 : 3 조회", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = PtypStyleResponse.class)))})
    @GetMapping(value = "/style", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PtypStyleResponse> getPtypStyle(@RequestParam(name = "ptypMId") long ptypMId,
            @RequestParam(name = "spaceType", required = false) SpaceType spaceType,
            @RequestParam(name = "spaceDetailType", required = false) SpaceDetailType spaceDetailType) {

        return budgetService.getPtypStyle(ptypMId, spaceType, spaceDetailType);
    }

    @Operation(summary = "스타일 상품(자재) 조회", description = "선택한 스타일의 상품(자재) 조회<br><br>" +
            "[constructionType 설명]<br><br>" +
            "BASIC_COMMON : 공통공사<br><br>" +
            "COMMON : 공통공사<br><br>" +
            "SPACE : 공간공사<br><br>", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = StyleResponse.class)))})
    @GetMapping(value = "/style/{styleMId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StyleResponse getStyleMaterial(@PathVariable(name = "styleMId") long styleMId) {
        return budgetService.getStyleMaterial(styleMId);
    }

    @Operation(summary = "견적 저장 전 스타일 견적상세 조회", description = "공간별 스타일 담는 도중 견적상세보기 조회", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = StyleEstimateResponse.class)))})
    @PostMapping(value = "/style/estimate", produces = MediaType.APPLICATION_JSON_VALUE)
    public StyleEstimateResponse getStyleEstimate(
            @RequestBody @Valid final StyleEstimateRequest request) {
        return budgetService.getStyleEstimate(request);
    }

    @Operation(summary = "사용자 예산감잡기견적 전체 조회", description = "사용자 예산감잡기견적 전체 조회", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = UsersEstimateResponse.class)))})
    @GetMapping(value = "/estimate", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UsersEstimateResponse> getUserEstimate(
            @RequestParam(name = "userId") Long userId) {
        return budgetService.getUserEstimate(userId, authClaim.getUserId());
    }

    @Operation(summary = "특정 예산감잡기견적 상세정보 조회", description = "예산감잡기견적 상세정보 조회<br><br>" +
            "[사용자 예산감잡기견적 전체 조회 시 나온 estimateMId로 조회]", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = StyleEstimateResponse.class)))})
    @GetMapping(value = "/estimate/{estimateMId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StyleEstimateResponse getEstimate(
            @PathVariable(name = "estimateMId") long estimateMId) {
        return budgetService.getEstimate(estimateMId, authClaim.getUserId());
    }

    @Operation(summary = "예산감잡기견적 삭제", description = "담은공간 견적 삭제")
    @DeleteMapping(value = "/estimate/{estimateMId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void removeEstimate(@PathVariable(name = "estimateMId") long estimateMId) {
        budgetService.removeEstimate(estimateMId, authClaim.getUserId());
    }

    @Operation(summary = "[비회원]예산감잡기견적 저장", description = "담은공간 견적 저장(비회원)", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = SaveEstimateResponse.class)))})
    @PostMapping(value = "/no-auth/estimate", produces = MediaType.APPLICATION_JSON_VALUE)
    public SaveEstimateResponse saveEstimateNoAuth(
            @RequestBody @Valid final SaveEstimateRequest request) {
        return budgetService.saveEstimateNoAuth(request);
    }

    @Operation(summary = "[회원]예산감잡기견적 저장", description = "담은공간 견적 저장(회원)", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation = SaveEstimateResponse.class)))})
    @PostMapping(value = "/estimate", produces = MediaType.APPLICATION_JSON_VALUE)
    public SaveEstimateResponse saveEstimate(
            @RequestBody @Valid final SaveEstimateRequest request) {
        return budgetService.saveEstimate(request, authClaim.getUserId());
    }
}
