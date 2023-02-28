package com.hanssem.remodeling.content.admin.controller.home;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Hidden
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1")
public class HomeController {

    @Operation(summary = "admin home", description = "Hello world!", tags = { "Home" })
    @ApiResponse(content = @Content(schema = @Schema(implementation = String.class)))
    @GetMapping(value = "/home")
    public String home() {
        return "Admin home";
    }
}