package dev.nodeninja.simflightstracker.api.v2.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class HttpLoggerFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = request.getRemoteAddr();
        }

        long startTime = System.currentTimeMillis();

        log.info("Request:: {} | {} {}",
                ipAddress,
                request.getMethod(),
                request.getRequestURI()
        );

        filterChain.doFilter(servletRequest, servletResponse);

        long endTime = System.currentTimeMillis();

        log.info("Response:: {} | {} {} | status: {} | duration: {} ms",
                ipAddress,
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                endTime - startTime
        );
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
