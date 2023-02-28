package com.hanssem.remodeling.content.api.service.display.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "content.display.template-info")
public class DisplayTemplateInfoDto {

    @Schema(title = "largeCategoryTemplateNo", description = "대카템플릿 templateNo", type = "string", example = "432")
    private String largeCategoryTemplateNo;

    @Schema(title = "middleCategoryTemplateNo", description = "중카템플릿 templateNo", type = "string", example = "433")
    private String middleCategoryTemplateNo;

    @Schema(title = "smallCategoryTemplateNo", description = "소카템플릿 templateNo", type = "string", example = "434")
    private String smallCategoryTemplateNo;

    @Schema(title = "shopIntAgntDetailTemplateNo", description = "매장INT대리점상세 templateNo", type = "string", example = "452")
    private String shopIntAgntDetailTemplateNo;

    @Schema(title = "mainMenuTemplateCode", description = "통합메인메뉴 templateCode", type = "string", example = "M_MAIN_MENU")
    private String mainMenuTemplateCode;
}
