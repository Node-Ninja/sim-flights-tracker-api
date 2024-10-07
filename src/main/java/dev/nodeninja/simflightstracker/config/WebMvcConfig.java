package dev.nodeninja.simflightstracker.config;

import dev.nodeninja.simflightstracker.api.v2.interceptor.AppCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AppCheckInterceptor appCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(appCheckInterceptor)
                .addPathPatterns("/api/**");
    }
}
