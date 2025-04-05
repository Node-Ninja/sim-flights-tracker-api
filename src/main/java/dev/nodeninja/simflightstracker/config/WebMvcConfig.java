package dev.nodeninja.simflightstracker.config;

import dev.nodeninja.simflightstracker.api.v2.interceptor.AppCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${sft.app-check-off}")
    private Boolean appCheckOff;

    private final AppCheckInterceptor appCheckInterceptor;

    @SuppressWarnings("null")
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if (!appCheckOff) {
            registry.addInterceptor(appCheckInterceptor)
                    .addPathPatterns("/**")
                    .excludePathPatterns(
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/index.html",
                            "/privacy.html",
                            "/play.png",
                            "/app-ads.txt",
                            "/sft_transparent.png",
                            "/airlines/**",
                            "/apple.svg",
                            "/"
                    );
        }
    }
}
