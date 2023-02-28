package com.hanssem.remodeling.content.admin.controller.goods;

import com.hanssem.remodeling.content.admin.controller.goods.request.AddGoodsRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.GoodsSearchRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsCodeRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsIsYnRequest;
import com.hanssem.remodeling.content.admin.controller.goods.request.ModifyGoodsRequest;
import com.hanssem.remodeling.content.admin.controller.goods.response.ExcelResponse;
import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsListResponse;
import com.hanssem.remodeling.content.admin.controller.goods.response.GoodsResponse;
import com.hanssem.remodeling.content.admin.service.adminlog.AdminLogService;
import com.hanssem.remodeling.content.admin.service.common.CommonAdminService;
import com.hanssem.remodeling.content.admin.service.goods.GoodsAdminService;
import com.hanssem.remodeling.content.common.model.AdminEventInfo;
import com.hanssem.remodeling.content.common.request.UploadRequest;
import com.hanssem.remodeling.content.common.response.UploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민 > 상품")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/v1/goods", produces = MediaType.APPLICATION_JSON_VALUE)
public class GoodsAdminController {

    @Value("${content.goods.upload-path}")
    private String goodsUploadPath;

    private final CommonAdminService commonAdminService;
    private final GoodsAdminService goodsAdminService;
    private final AdminLogService adminLogService;
    private final AdminEventInfo adminEventInfo;

    @Operation(
        summary = "상품 검색" ,
        description = "리모델링 상품 상세 검색 API",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = GoodsListResponse.class)))})
    @GetMapping(value = "/search")
    public Page<GoodsListResponse>  searchGoods(final GoodsSearchRequest request) {
        adminLogService.sendAdminGetEventLog();
        return goodsAdminService.searchGoods(request);
    }

    @Operation(
        summary = "상품 상세",
        description = "상품 상세 정보",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = GoodsResponse.class)))})
    @GetMapping(value = "/{goodsId}")
    public GoodsResponse findGoodsById(
        @PathVariable(name = "goodsId") @Schema(description = "상품번호", example = "618440") Long goodsId) {
        adminLogService.sendAdminGetEventLog();
        return goodsAdminService.findGoodsById(goodsId);
    }

    @Operation(
        summary = "상품 등록", description = "",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Long.class)))})
    @PostMapping(value = "")
    public Long addGoods(@RequestBody @Valid AddGoodsRequest request) {
        adminLogService.sendAdminPostEventLog(request.toString());
        Long result = goodsAdminService.addGoods(request);
        goodsAdminService.clearCategoryCacheAll();
        goodsAdminService.clearDisplayCacheAll();
        goodsAdminService.clearGoodsCacheAll();
        return result;
    }

    @Operation(
        summary = "상품 수정",
        description = "상품 수정",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Long.class)))})
    @PutMapping(value = "/{goodsId}")
    public Long modifyGoods(@RequestBody  @Valid ModifyGoodsRequest request, @PathVariable("goodsId") Long goodsId) {
        adminLogService.sendAdminPostEventLog(request.toString());
        goodsAdminService.modifyGoods(request, goodsId);
        goodsAdminService.clearCategoryCacheAll();
        goodsAdminService.clearDisplayCacheAll();
        goodsAdminService.clearGoodsCacheAll();
        return goodsId;
    }

    @Operation(
        summary = "상품 판매상태 수정",
        description = "상품 판매상태 수정",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Integer.class)))})
    @PutMapping(value = "/state")
    public int modifyGoodsStateCode(@Valid ModifyGoodsCodeRequest request) {
        request.valid();
        adminLogService.sendAdminPostEventLog(request.toString());
        int result = goodsAdminService.modifyGoodsStateCode(request);
        goodsAdminService.clearCategoryCacheAll();
        goodsAdminService.clearDisplayCacheAll();
        goodsAdminService.clearGoodsCacheAll();
        return result;
    }

    @Operation(
        summary = "상품 뱃지 수정", description = "상품 뱃지 수정",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Integer.class)))})
    @PutMapping(value = "/badgeCode")
    public int modifyGoodsBadgeCode(@Valid ModifyGoodsCodeRequest request) {
        request.valid();
        adminLogService.sendAdminPostEventLog(request.toString());
        int result = goodsAdminService.modifyGoodsBadgeCode(request);
        goodsAdminService.clearCategoryCacheAll();
        goodsAdminService.clearDisplayCacheAll();
        goodsAdminService.clearGoodsCacheAll();
        return result;
    }

    @Operation(
        summary = "상품 vr여부 수정", description = "상품 vr여부 수정",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Integer.class)))})
    @PutMapping(value = "/vrYn")
    public int modifyGoodsVrYn(@Valid ModifyGoodsIsYnRequest request) {
        request.valid();
        adminLogService.sendAdminPostEventLog(request.toString());
        int result = goodsAdminService.modifyGoodsVrYn(request);
        goodsAdminService.clearCategoryCacheAll();
        goodsAdminService.clearDisplayCacheAll();
        goodsAdminService.clearGoodsCacheAll();
        return result;
    }

    @Operation(
        summary = "상품 event 여부 수정", description = "상품 event 여부 수정",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = Integer.class)))})
    @PutMapping(value = "/eventYn")
    public Integer modifyGoodsEventYn(@Valid ModifyGoodsIsYnRequest request) {
        request.valid();
        adminLogService.sendAdminPostEventLog(request.toString());
        int result = goodsAdminService.modifyGoodsEventYn(request);
        goodsAdminService.clearCategoryCacheAll();
        goodsAdminService.clearDisplayCacheAll();
        goodsAdminService.clearGoodsCacheAll();
        return result;
    }

    @Operation(summary = "상품 이미지 업로드", description = "파일 업로드",
        responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = UploadResponse.class)))})
    @PostMapping(value = "/images/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadResponse upload(final @ModelAttribute UploadRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        String yearMonth = sdf.format(cal.getTime());
        String path = String.format("%s/%s", goodsUploadPath, yearMonth);
        adminLogService.sendAdminPostEventLog(request.toString());
        return commonAdminService.goodsImageUpload(request, path);
    }

    @Operation(summary = "상품 이미지 업로드 삭제", description = "파일 업로드 삭제",
        responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = UploadResponse.class)))})
    @DeleteMapping(value = "/images/{path}")
    public void upload(@PathVariable(name = "path") @Schema(description = "이미지경로", example = "content/goods/202212/3babfb91-a7cf-4517-b3df-a985f7291e19.jpeg") String path) {
        adminLogService.sendAdminPostEventLog(path);
        commonAdminService.remove(path);
    }

    @Operation(summary = "상품 엑셀 다운로드", description = "상품 엑셀 다운로드",
            responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = FileSystemResource.class)))})
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public FileSystemResource exportGoods(final GoodsSearchRequest request, final HttpServletResponse response) {

        ExcelResponse excelResponse = goodsAdminService.exportGoods(request);

        adminLogService.sendAdminExcelDownloadLog(excelResponse.getRows(), excelResponse.getFile().getName());

        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.CONTENT_DISPOSITION);
        response.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                ContentDisposition.builder("attachment").filename(excelResponse.getFile().getName(), StandardCharsets.UTF_8)
                        .build()
                        .toString());

        return new FileSystemResource(excelResponse.getFile());
    }
}
