package com.example.tablelingdingdong.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger springdoc-ui 구성 파일
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("BaseEntity API Document")
                .version("v0.0.1")
                .description("이커머스 user api 명세서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}