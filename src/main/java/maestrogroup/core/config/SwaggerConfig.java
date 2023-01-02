package maestrogroup.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

// config/SwaggerConfig.java
@Configuration
@EnableSwagger2
public class SwaggerConfig {  // Swagger

    private static final String API_NAME = "Maestro API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Maestro API 명세서";
    private static final String REFERENCE = "AccessToken 헤더 값";
    private static final String RefreshToken = "RefreshToken 헤더 값";


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("maestrogroup.core"))  // Swagger를 적용할 클래스의 package명
                .paths(PathSelectors.any())  // 해당 package 하위에 있는 모든 url에 적용
                .build()
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(securityScheme_AccessToken(), securityScheme_RefreshToken()));
    }

    private SecurityContext securityContext() {
        return springfox.documentation
                .spi.service.contexts
                .SecurityContext
                .builder()
                .securityReferences(defaultAuth())
                .operationSelector(operationContext -> true)
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope("global", "accessEverything");
        return List.of(new SecurityReference(REFERENCE, authorizationScopes), new SecurityReference(RefreshToken, authorizationScopes));
    }

    private ApiKey securityScheme_AccessToken() {
        String targetHeader = "Authorization";
        return new ApiKey(REFERENCE, targetHeader, "header");
    }

    private ApiKey securityScheme_RefreshToken(){
        String targetHeader = "RefreshToken";
        return new ApiKey(RefreshToken, targetHeader, "header");
    }

    public ApiInfo apiInfo() {  // API의 이름, 현재 버전, API에 대한 정보
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }
}