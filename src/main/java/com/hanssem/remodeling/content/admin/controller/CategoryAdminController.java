package com.hanssem.remodeling.content.admin.controller;

import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailWithChildrenResponse;
import com.hanssem.remodeling.content.api.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "어드민 > 카테고리")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/v1/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryAdminController {

    private final CategoryService categoryService;

    @Value("${content.category.standard-large-category-no}")
    private int standardLargeCategoryNo;

    @Operation(
        summary = "전체 카테고리 리스트 Cascade 조회 (한샘몰)",
        description = "한샘몰 API에서 전체 카테고리 리스트를 조회한다.<br><br>"
            + "하위 카테고리를 파라미터로 전달하고 child로 반복 조회",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = MallCategoryListDetailWithChildrenResponse.class)))})
    @GetMapping(value = "/cascade", produces = MediaType.APPLICATION_JSON_VALUE)
    public MallCategoryListDetailWithChildrenResponse getMallAllCategoryListCascade() {
        return categoryService.getMallAllCategoryListCascade(standardLargeCategoryNo);
    }
}
