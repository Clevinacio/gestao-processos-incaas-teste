package com.incaas.api.gestorprocessos.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI judicialProcessOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("API de Gestão de Processos Judiciais")
                        .description("API RESTful para gerenciar processos judiciais e agendamento de audiências.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))) // Informações de licença
                .externalDocs(new ExternalDocumentation() // Documentação externa (opcional)
                        .description("Repositório do Projeto no GitHub")
                        .url("https://github.com/Clevinacio/gestao-processos-incaas-teste")); // URL do seu repositório GitHub
    }
}
