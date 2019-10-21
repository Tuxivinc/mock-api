package org.api.mock.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Swagger config.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Product api docket.
     *
     * @return the docket
     */
    @Bean
    public Docket productApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.api.mock.ctrl"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Api Mock",
                "Mock a APIs for API-Manager tests",
                "1.0",
                "Terms of service",
                new Contact("TODO", "http://TODO.org", "contact@TODO.org").getName(),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }

}