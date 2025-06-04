package com.incaas.api.gestorprocessos.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.incaas.api.gestorprocessos.filter.AuthTokenFilter;

@Configuration
public class WebConfig {
    @Value("${app.auth.token:fixed-test-token}")
    private String authToken;

    @Bean
    public FilterRegistrationBean<AuthTokenFilter> authTokenFilter() {
        FilterRegistrationBean<AuthTokenFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthTokenFilter(authToken));
        registrationBean.addUrlPatterns("/api/v1/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
