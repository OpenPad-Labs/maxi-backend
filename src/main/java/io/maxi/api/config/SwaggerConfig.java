package io.maxi.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig   {

    @Value("${spring.profiles.active:dev}")
    private String active;

    @Bean
    public Docket docket() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                //.enable("dev".equals(active))  // 仅在开发环境开启Swagger
                .enable(true)  // 仅在开发环境开启Swagger
                .apiInfo(apiInfo())
                .select()
                //apis： 添加swagger接口提取范围
                .apis(RequestHandlerSelectors.basePackage("io.maxi.api.controller"))
                .paths(PathSelectors.any())
                .build();

        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Maxi")
                .description("Maxi API")
                .contact(new Contact("Maxi", "https://maxi.io","api@maxi.io"))
                .version("v1.0")
                .build();
    }


}
