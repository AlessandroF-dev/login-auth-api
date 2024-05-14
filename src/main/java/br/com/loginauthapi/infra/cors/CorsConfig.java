package br.com.loginauthapi.infra.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static br.com.loginauthapi.utils.ConfigurationEnum.*;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(LOCAL_URL.getValue())
                .allowedMethods(
                        GET_METHOD.getValue(),
                        POST_METHOD.getValue(),
                        PUT_METHOD.getValue(),
                        DELETE_METHOD.getValue()
                );
    }
}
