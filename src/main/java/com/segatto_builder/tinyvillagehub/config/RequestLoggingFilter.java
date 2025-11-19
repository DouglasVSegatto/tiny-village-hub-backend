package com.segatto_builder.tinyvillagehub.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        // 1. Log the incoming request details (Method and URI)
        logger.info("INCOMING REQUEST: {} {} from client {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteHost());

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            // 2. Log the outgoing response status and duration
            if (response.getStatus() >= 400) {
                logger.warn("FAILED RESPONSE: {} {} resulted in status {} ({}ms)",
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        duration);
            } else {
                logger.debug("SUCCESS RESPONSE: {} {} resulted in status {} ({}ms)",
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        duration);
            }
        }
    }
}