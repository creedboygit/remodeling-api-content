package com.hanssem.remodeling.content.admin.controller.favorite;

import com.hanssem.remodeling.content.admin.service.favorite.FavoriteImportService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "어드민 > 인테리어취향테스트")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/v1/favorite-test", produces = MediaType.APPLICATION_JSON_VALUE)
public class FavoriteImportController {

    private final FavoriteImportService favoriteImportService;

    @Operation(
        summary = "인테리어취향 Excel import" ,
        description = "인테리어취향 기본 데이터 Import API",
        responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = Void.class)))})
    @PostMapping(value = "/data-import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importFavoriteStyle(
        @RequestParam(name = "favorite") MultipartFile file) throws IOException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("tsv")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        favoriteImportService.importFavorite(file, extension);
    }

    @Operation(
        summary = "인테리어취향 이미지 upload" ,
        description = "인테리어취향 이미지 upload API",
        responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = Void.class)))})
    @PostMapping(value = "/image-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void importImage(
        @RequestParam(name = "favorite") MultipartFile file) throws Exception {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (!extension.equals("xlsx") && !extension.equals("tsv")) {
            throw new IOException("엑셀파일만 업로드 해주세요.");
        }

        favoriteImportService.importImage(file, extension);
    }
}
