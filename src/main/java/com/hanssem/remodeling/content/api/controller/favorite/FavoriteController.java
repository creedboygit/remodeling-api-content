package com.hanssem.remodeling.content.api.controller.favorite;

import com.hanssem.remodeling.content.api.controller.favorite.request.FavoriteTestRequest;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteQuestionResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteStyleSimpleResponse;
import com.hanssem.remodeling.content.api.controller.favorite.response.FavoriteTestResponse;
import com.hanssem.remodeling.content.api.service.favorite.FavoriteService;
import com.hanssem.remodeling.content.common.model.AuthClaim;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "인테리어취향테스트")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/favorite-test", produces = MediaType.APPLICATION_JSON_VALUE)
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final AuthClaim authClaim;

    @Operation(
        summary = "인테리어취향테스트 질의 항목 조회",
        description = "스타일이미지, 컬러, 키워드, 재질 질의 항목 전달",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FavoriteQuestionResponse.class)))})
    @GetMapping(value = "")
    public FavoriteQuestionResponse questions() {
        return favoriteService.questions();
    }

    @Operation(
        summary = "인테리어취향테스트 결과보기",
        description = "스타일이미지, 컬러, 키워드, 재질 데이터 답변 제출 및 결과",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FavoriteTestResponse.class)))})
    @PostMapping(value = "/feedback")
    public FavoriteTestResponse answer(@RequestBody @Valid FavoriteTestRequest request) {
        request.validated();
        return favoriteService.answer(request, authClaim);
    }

    @Operation(
        summary = "인테리어취향테스트 스타일정보 조회 (빠른상담신청용)",
        description = "빠른상담신청시 스타일정보 조회용으로 사용",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FavoriteStyleSimpleResponse.class)))})
    @GetMapping(value = "/styles/{styleId}/consult")
    public FavoriteStyleSimpleResponse getStyleForConsult(@PathVariable("styleId") final Long styleId) {
        return favoriteService.getStyleForConsult(styleId);
    }

    @Operation(
        summary = "인테리어취향테스트 스타일정보 조회",
        description = "스타일정보 조회용으로 사용",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = FavoriteStyleSimpleResponse.class)))})
    @GetMapping(value = "/styles/{styleId}")
    public FavoriteTestResponse getStyle(@PathVariable("styleId") final Long styleId) {
        return favoriteService.getStyle(styleId);
    }

}
