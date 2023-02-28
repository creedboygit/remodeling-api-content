package com.hanssem.remodeling.content.admin.controller.budget;

import com.hanssem.remodeling.content.admin.controller.budget.response.SimpleEstimateResponse;
import com.hanssem.remodeling.content.admin.service.budget.BudgetAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "어드민 > 예산감잡기")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/v1/budget", produces = MediaType.APPLICATION_JSON_VALUE)
public class BudgetAdminController {
    private final BudgetAdminService budgetAdminService;

    @Operation(summary = "예산감잡기 스타일 업로드", description = "스타일 데이터 엑셀파일 업로드")
    @PostMapping(value = "/style/excelUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public boolean excelUpload(@RequestParam(name = "file") MultipartFile excel) {
        return budgetAdminService.styleUpload(excel);
    }
    
    @Operation(summary = "특정 예산감잡기견적 요약 조회", description = "예산감잡기견적 요약 조회", responses = {
            @ApiResponse(content = @Content(schema = @Schema(implementation =SimpleEstimateResponse.class)))})
    @GetMapping(value = "/estimate/{estimateMId}/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    public SimpleEstimateResponse getEstimateSimple(@PathVariable(name = "estimateMId") long estimateMId) {
        return budgetAdminService.getEstimateSimple(estimateMId);
    }

    @Operation(summary = "예산감잡기견적 상담신청 완료", description = "빠른상담신청완료 시 상담신청완료로 변경")
    @PutMapping(value = "/estimate/{estimateMId}/complete", produces = MediaType.APPLICATION_JSON_VALUE)
    public void completeEstimateConsult(@PathVariable(name = "estimateMId") long estimateMId) {
        budgetAdminService.completeEstimateConsult(estimateMId);
    }
}
