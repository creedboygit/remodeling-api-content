package com.hanssem.remodeling.content.api.controller.category;

import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListDetailWithChildrenResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryListResponse;
import com.hanssem.remodeling.content.api.controller.category.response.MallCategoryLocationResponse;
import com.hanssem.remodeling.content.api.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

    private final CategoryService categoryService;

    @Value("${content.category.standard-large-category-no}")
    private int standardLargeCategoryNo;

    @Hidden
    @Operation(
            summary = "전체 카테고리 리스트 1차원 조회 (한샘몰)",
            description = "한샘몰 API에서 전체 카테고리 리스트를 조회한다.<br><br>"
                    + "파라미터 없이 전체 카테고리 리스트 한번에 조회",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = MallCategoryListDetailResponse.class)))}
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public MallCategoryListDetailResponse getMallAllCategoryList() {

        return categoryService.getMallAllCategoryList();
    }

    @Operation(
            summary = "전체 카테고리 리스트 Cascade 조회 (한샘몰)",
            description = "한샘몰 API에서 전체 카테고리 리스트를 조회한다.<br><br>"
                    + "하위 카테고리를 파라미터로 전달하고 child로 반복 조회",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = MallCategoryListDetailWithChildrenResponse.class)))}
    )
    @GetMapping(value = "/cascade", produces = MediaType.APPLICATION_JSON_VALUE)
    public MallCategoryListDetailWithChildrenResponse getMallAllCategoryListCascade() {

        return categoryService.getMallAllCategoryListCascade(standardLargeCategoryNo);
    }

    @Operation(
            summary = "카테고리 Cache clear - 전체",
            description = "카테고리 전체 캐시 삭제")
    @DeleteMapping(value = "/cache-clear-all")
    public void clearCategoryCacheAll() {
        categoryService.clearCategoryCacheAll();
        ;
    }

    @Operation(
            summary = "카테고리 현재 위치 조회 (한샘몰)",
            description = "카테고리 현재 위치 조회 (한샘몰)",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = MallCategoryLocationResponse.class)))}
    )
    @GetMapping(value = "/{ctgNo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MallCategoryLocationResponse getMallCategoryLocation(
            @PathVariable(name = "ctgNo") @Schema(description = "카테고리번호", example = "16141") int ctgNo
    ) {

        return categoryService.getMallCategoryLocation(ctgNo);
    }

    @Operation(
            summary = "카테고리 번호, 레벨로 리스트 조회 (한샘몰)",
            description = "한샘몰 API에서 카테고리 번호, 레벨로 리스트를 조회한다.<br><br>"
                    + "level(카테고리레벨) : current / child<br>"
                    + "current : 동일레벨의 카테고리 리스트 조회<br>"
                    + "child : 하위레벨 카테고리 리스트 조회",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = MallCategoryListResponse.class)))}
    )
    @GetMapping(value = "/{ctgNo}/{level}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MallCategoryListResponse getMallCategoryList(
            @PathVariable(name = "ctgNo") @Schema(description = "카테고리번호", example = "16265") int ctgNo,
            @PathVariable(name = "level") @Schema(description = "레벨", example = "child") String level
    ) {

        return categoryService.getMallApiCategoryList(ctgNo, level);
    }

    @Hidden
    @Operation(
            deprecated = true,
            summary = "대카테고리 리스트 조회 (한샘몰)",
            description = "한샘몰 API에서 대카테고리 리스트를 조회한다.<br><br>",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = MallCategoryListResponse.class)))}
    )
    @GetMapping(value = "/large-categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public MallCategoryListResponse getMallLargeCategoryList() {

        return categoryService.getMallLargeCategoryList(standardLargeCategoryNo);
    }

}
