package org.serratec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2).select()                                  
          .apis(RequestHandlerSelectors
          .basePackage("org.serratec.resources"))              
          .paths(PathSelectors.any())                          
          .build()
          .apiInfo(apiInfo());                                         
    }

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder()
				.title("API do Ecommerce Serratec")
				.description("Essa é uma API desenvolvida para um Ecommerce")
				.build();
		return apiInfo;
	}
}