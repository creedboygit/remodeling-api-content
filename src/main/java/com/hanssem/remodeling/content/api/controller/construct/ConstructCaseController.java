package com.hanssem.remodeling.content.api.controller.construct;

import com.hanssem.remodeling.content.api.controller.construct.request.ConstructCaseRequest;
import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseAllResponse;
import com.hanssem.remodeling.content.api.controller.construct.response.ConstructCaseResponse;
import com.hanssem.remodeling.content.api.service.construct.ConstructCaseService;
import com.hanssem.remodeling.content.common.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "시공사례")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/constructCase", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConstructCaseController {

    private final ConstructCaseService constructCaseService;

    @Operation(
            summary = "시공사례 리스트 (한샘몰 API)",
            description = "시공사례 리스트 (한샘몰 API)",
            responses = {@ApiResponse(content = @Content(schema = @Schema(implementation = ConstructCaseAllResponse.class)))})
    @GetMapping
    public PageResponse<ConstructCaseResponse> getConstructCase(@Valid ConstructCaseRequest request) {

        return constructCaseService.getConstructCase(request);
    }
}
