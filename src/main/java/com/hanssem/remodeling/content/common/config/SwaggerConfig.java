package com.hanssem.remodeling.content.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.ArrayList;
import java.util.List;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${spring.profiles.active}")
    private String profile;

    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String moudleName;

    @Value("${spring.gateway-url}")
    private String gwUrl;

    @Bean
    public GroupedOpenApi adminOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/admin/**" };
        String[] packages = {"com.hanssem.remodeling.content.admin"};
        return GroupedOpenApi.builder().group("admin")
            .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Remodeling Admin API").version(appVersion)))
            .pathsToMatch(paths)
            .packagesToScan(packages)
            .build();
    }

    @Bean
    public GroupedOpenApi usersOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = { "/api/**" };
        String[] packages = {"com.hanssem.remodeling.content.api"};
        return GroupedOpenApi.builder().group("users")
            .addOpenApiCustomiser(openApi -> openApi.info(new Info().title("Remodeling Content API").version(appVersion)))
            .pathsToMatch(paths)
            .packagesToScan(packages)
            .build();
    }

    @Bean
    public OpenAPI openAPI() {

        OpenAPI apiDoc =  new OpenAPI()
            .info(new Info()
                .title("Remodeling API Content")
                .version("v1")
                .description("Remodeling REST API"))
            .components(new Components()
                .addSecuritySchemes("Authorization",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
            ;
        List<Server> servers = new ArrayList<>();
        String serverName = gwUrl + moudleName;
        if(profile.equals("local")) {
            servers.add(0, new Server().url(String.format("http://localhost:%s/", port)));
            servers.add(1, new Server().url(serverName));
        }else{
            servers.add(0, new Server().url(serverName));
        }

        apiDoc.setServers(servers);
        return apiDoc;
    }



    //private final TypeResolver typeResolver;

    /*
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(info())
                .alternateTypeRules(
                        AlternateTypeRules.newRule(typeResolver.resolve(new GenericType<List<Sort.Order>>() {
                        }), String.class),
                        AlternateTypeRules.newRule(typeResolver.resolve(OrderSpecifier[].class), String.class)
                )
                .consumes(consumes())
                .produces(produces())
                .forCodeGeneration(true)
                .genericModelSubstitutes(ResponseEntity.class)
                .ignoredParameterTypes(Pageable.class)
                .securityContexts(List.of(securityContext()))
                .securitySchemes(apiHeaders())
                .select()
                .apis(RequestHandlerSelectors.basePackage("co.edithome.appname.controller"))
                .paths(PathSelectors.ant("/api/v1/**"))
                .build().pathMapping(profile.equals("local")? "" : "feed-service");
    }

    private ApiInfo info() {
        return new ApiInfoBuilder()
                .title("Edithome API Template")
                .description("Edithome REST API")
                .version("v1")
                .build();
    }

    private Set<String> consumes() {
        final Set<String> consumes = new HashSet<>();
        consumes.add(MediaType.APPLICATION_JSON_VALUE);
        consumes.add(MediaType.MULTIPART_FORM_DATA_VALUE);
        consumes.add(MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        return consumes;
    }

    private Set<String> produces() {
        final Set<String> produces = new HashSet<>();
        produces.add(MediaType.APPLICATION_JSON_VALUE);
        return produces;
    }

    private List<SecurityScheme> apiHeaders() {
        return List.of(
                new ApiKey("Authorization", "bearer", "header")
        );
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }
    */
}
