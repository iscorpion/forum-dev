package com.pagseguro.forum.dev.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun apiInfo(): OpenAPI? {
        val apiName = "Forum Dev"
        return OpenAPI()
            .info(
                Info()
                    .title(apiName)
                    .description("API do Forum de dev")
                    .version("1.0.0")
                    .contact(Contact().name("#eden"))
            )
    }
}