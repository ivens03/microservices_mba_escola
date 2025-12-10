package br.com.mba.spring.colegio.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Back-end - Colegio Documentação")
                        .description("Documentação de APIs do colegio")
                        .version("0.0.1")
                        .contact(new Contact()
                                .name("Ivens Magno Da Costa Lisboa")
                                .email("ivensmagno@gmail.com")));
    }

}
