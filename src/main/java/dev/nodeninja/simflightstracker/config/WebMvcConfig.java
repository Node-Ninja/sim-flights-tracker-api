package dev.nodeninja.simflightstracker.config;

import dev.nodeninja.simflightstracker.api.v2.interceptor.AppCheckInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
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
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        if (!appCheckOff) {
            registry.addInterceptor(appCheckInterceptor)
                    .addPathPatterns("/**")
                    .excludePathPatterns(
                            "/oauth/callback",
                            "/oauth/complete",
                            "/api/v2/stats",
                            "/index.html",
                            "/privacy.html",
                            "/app-ads.txt",
                            "/robots.txt",
                            "/favicon.ico",
                            "/sft_transparent.png",
                            "/airlines/**",
                            "/assets/**",
                            "/_1.jpg",
                            "/_2.jpg",
                            "/_3.jpg",
                            "/_4.png",
                            "/_5.png",
                            "/"
                    );
        }
    }
}
