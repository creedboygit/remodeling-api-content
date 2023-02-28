package com.hanssem.remodeling.content.api.controller.goods;

import com.hanssem.remodeling.content.api.controller.goods.request.GoodsDisplayMashUpRequest;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsDisplayRequest;
import com.hanssem.remodeling.content.api.controller.goods.request.GoodsRequest;
import com.hanssem.remodeling.content.api.controller.goods.response.*;
import com.hanssem.remodeling.content.api.service.goods.GoodsService;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Tag(name = "상품")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/goods", produces = MediaType.APPLICATION_JSON_VALUE)
public final class GoodsController {

    private final AuthClaim authClaim;

    private final GoodsService goodsService;

    @Operation(
            summary = "상품 상세",
            description = "상품 상세 정보",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = GoodsDetailResponse.class)))})
    @GetMapping(value = "/{goodsId}")
    public GoodsDetailResponse getGoodsDetailInfo(
            @PathVariable(name = "goodsId") @NotNull(message = "상품번호를 입력해주세요.") @Schema(description = "상품번호", example = "613664") Long goodsId) {

        return goodsService.getGoodsDetailInfo(authClaim, goodsId);
    }

    @Operation(
            summary = "상품 상세 (스크랩 여부)",
            description = "상품 상세 정보 (스크랩 여부)",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = GoodsDetailResponse.class)))})
    @GetMapping(value = "/scraps/{goodsId}")
    public GoodsScrapResponse getScrapExistence(
            @PathVariable(name = "goodsId") @NotNull(message = "상품번호를 입력해주세요.") @Schema(description = "상품번호", example = "613664") Long goodsId) {

        return goodsService.getGoodsScrapInfo(authClaim, goodsId);
    }

    @Operation(
            summary = "상품 상세 (상담신청용)",
            description = "상품 상세 정보 (상담신청용)",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = GoodsDetailInfoSimpleResponse.class)))})
    @GetMapping(value = "/{goodsId}/simple")
    public GoodsDetailInfoSimpleResponse getGoodsDetailSimpleInfo(
            @PathVariable(name = "goodsId") @NotNull(message = "상품번호를 입력해주세요.") @Schema(description = "상품번호", example = "613664") Long goodsId) {

        return goodsService.getGoodsDetailSimpleInfo(goodsId);
    }

    @Operation(
            summary = "상품 리스트",
            description = "상품 리스트",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = GoodsListResponse.class)))})
    @GetMapping
    public Page<GoodsListResponse> getGoodsList(GoodsRequest request) {

        request.initRequestParam();
        return goodsService.getGoodsList(request);
    }

    @Operation(
            summary = "상품 리스트 (전시용)",
            description = "상품 리스트 (전시용)<br><br>" +
            "전시 컴포넌트 내 들어갈 URL 참조<br><br>",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = GoodsListResponse.class)))})
    @GetMapping(value = "/displays")
    public Page<GoodsListResponse> getGoodsListDisplay(@Valid GoodsDisplayRequest request) {

        GoodsRequest listRequest = goodsService.makeGoodsDisplayCondition(request);
        return goodsService.getGoodsList(listRequest);
    }

    @Operation(
            summary = "상품 리스트 (전시용 Mash Up)",
            description = "상품 리스트 (전시용 Mash Up)<br><br>" +
            "전시 컴포넌트 내 들어갈 URL 참조<br><br>" +
            "categoryNo를 입력 후 조회하면 하위 카테고리의 상품들을 5개씩 조회해서 리스트 생성",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = CategoryGoodsResponse.class)))})
    @GetMapping(value = "/mashup")
    public List<CategoryGoodsResponse> getGoodsListDisplayMashUp(@Valid GoodsDisplayMashUpRequest request) {

        return goodsService.getGoodsListDisplayMashUp(request);
    }

    @Operation(
            summary = "상품 Cache clear - 전체",
            description = "상품 전체 캐시 삭제")
    @DeleteMapping(value = "/cache-clear-all")
    public void clearGoodsCacheAll() {
        goodsService.clearGoodsCacheAll();
    }
}

