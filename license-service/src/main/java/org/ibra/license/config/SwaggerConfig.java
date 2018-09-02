package org.ibra.license.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
public class SwaggerConfig extends WebMvcConfigurationSupport {

	/**
	 * Create Docket document which is responsible to render service API on Swagger UI.
	 * 
	 * @return Docket document.
	 */
    @Bean
    public Docket journeyApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("org.ibra.license.controller"))
                .paths(PathSelectors.any())
                .build()
        		.apiInfo(metadata());
             
    }
    
    /**
     * Determine where is the location that Swagger UI depends on
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
 
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * Create default meta information for services documentation.
     * 
     * @return API Meta-Info
     */
    private ApiInfo metadata() {
    	
    	//TODO: read from confsrv
    	return new ApiInfoBuilder()
                .title("Licenses Service API")
                .description("\"Licesnses REST API Docs\"")
                .version("1.0.0")
                .license("GNU General Public License v3.0")
                .licenseUrl("https://github.com/iabughosh/microservices/blob/master/LICENSE")
                .contact(new Contact("Smart worx", "https://github.com/iabughosh", "ibrahim_abu-ghosh@live.com"))
                .build();
    }
}