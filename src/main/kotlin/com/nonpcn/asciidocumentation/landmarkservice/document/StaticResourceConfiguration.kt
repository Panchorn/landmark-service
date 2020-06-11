package com.nonpcn.asciidocumentation.landmarkservice.document

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.resource.PathResourceResolver

@Configuration
internal class StaticResourceConfiguration : WebMvcConfigurer {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/document/**")
            .addResourceLocations("classpath:/restdocs/")
            .resourceChain(true)
            .addResolver(PathResourceResolver())
    }

}