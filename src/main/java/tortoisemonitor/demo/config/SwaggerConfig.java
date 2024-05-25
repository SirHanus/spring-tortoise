package tortoisemonitor.demo.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Documentation",
                version = "v1",
                description = "API documentation for the application"
        ),
        security = {@SecurityRequirement(name = "bearer")}
)
@SecuritySchemes(value = {
        @SecurityScheme(
                name = "bearer",
                type = SecuritySchemeType.OAUTH2,
                flows = @OAuthFlows(
                        implicit = @OAuthFlow(authorizationUrl = "${security.authorization.url}")
                )
        )
})
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/**")
                .build();
    }
}


