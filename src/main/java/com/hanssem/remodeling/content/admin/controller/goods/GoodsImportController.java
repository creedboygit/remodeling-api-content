package com.hanssem.remodeling.content.admin.controller.goods;

import com.hanssem.remodeling.content.admin.service.goods.GoodsImportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "어드민 > 상품 마이그레이션")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/v1/goods/import", produces = MediaType.APPLICATION_JSON_VALUE)
public class GoodsImportController {

    private final GoodsImportService goodsImportService;


    @Operation(
        summary = "상품 Excel import" ,
        description = "한샘닷컴 MSSQL로 부터 excel 문서로 생성한 데이터 -> 리모델링상품 MYSQL로 마이그레이션 시키는 API",
        responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = Void.class)))})
    @PostMapping(value = "/data-migration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void readExcel(
        @RequestParam(name = "goods") MultipartFile goodsFile,
        @RequestParam(name = "category") MultipartFile categoryFile) throws IOException {

        String extension = FilenameUtils.getExtension(goodsFile.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        String extension2 = FilenameUtils.getExtension(categoryFile.getOriginalFilename());
        if (!extension2.equals("xlsx") && !extension2.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        goodsImportService.saveGoods(goodsFile, extension);
        goodsImportService.saveCategory(categoryFile, extension);
    }


    @Operation(
        summary = "상품 카테고리 마이그레이션" ,
        description = "한샘닷컴 -> 리모델링 카테고리로 마이그레이션 시키는 API",
        responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))})
    @PostMapping(value = "/category-migration/{password}")
    public String migrateCategory(@PathVariable("password") String password) {
        if (password.isEmpty() || !password.equals("remodeling")) return "Fail";

        goodsImportService.migrateCategory();
        return "OK";
    }

    @Operation(
        summary = "상품 이미지 마이그레이션" ,
        description = "한샘닷컴 -> 리모델링 이미지 마이그레이션 시키는 API",
        responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))})
    @PostMapping(value = "/image-migration/{password}")
    public String migrateImage(@PathVariable("password") String password) throws Exception {
        if (password.isEmpty() || !password.equals("remodeling")) return "Fail";

        goodsImportService.migrateImage();
        return "OK";
    }


    @Operation(
        summary = "상품 신규 항목 마이그레이션",
        description = "리모델링 상품 신규 컬럼 마이그레이션 시키는 API",
        responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))})
    @PostMapping(value = "/renewal-migration", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String migrateRenewal(@RequestParam(name = "goods") MultipartFile file) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("xls")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        goodsImportService.migrateRenewal(file, extension);
        return "OK";
    }

}
