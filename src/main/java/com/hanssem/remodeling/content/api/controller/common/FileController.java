package com.hanssem.remodeling.content.api.controller.common;

import com.hanssem.remodeling.content.api.service.common.CommonService;
import com.hanssem.remodeling.content.common.request.UploadRequest;
import com.hanssem.remodeling.content.common.response.UploadResponse;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@Slf4j
@Tag(name = "Common")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/common/file", produces = MediaType.APPLICATION_JSON_VALUE)
public class FileController {

    private final CommonService commonService;

    @Operation(summary = "파일 업로드", description = "파일 업로드", responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = UploadResponse.class)))})
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UploadResponse upload(final @ModelAttribute UploadRequest request) {
        return commonService.upload(request);
    }


}
