package com.example.springbootteste.infraestrutura.config.swagger;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Autowired
	MessageSource internacionalizacaoDeMensagem;

	@Primary
	@Bean
	public LinkDiscoverers discoverers() {
		return new LinkDiscoverers(SimplePluginRegistry.create(Arrays.asList(new CollectionJsonLinkDiscoverer())));
	}

	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.example.springbootteste"))
				.paths(PathSelectors.any())
				.build()
				.globalOperationParameters(Arrays.asList(new ParameterBuilder()
						.name("Authorization")
						.description("Header para token JWT")
						.modelRef(new ModelRef("string"))
						.parameterType("header")
						.required(false)
						.build()))
				.apiInfo(metaInfo());
	}

	private ApiInfo metaInfo() {
    return new ApiInfoBuilder()
    		.title(internacionalizacaoDeMensagem.getMessage("titulo.swagger", null, LocaleContextHolder.getLocale()))
    		.description(internacionalizacaoDeMensagem.getMessage("descricao.swagger", null, LocaleContextHolder.getLocale())).version("1.0")
    		.build();
  }

}
