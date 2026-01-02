package com.segatto_builder.tinyvillagehub.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        long startTime = System.currentTimeMillis();
        String username = getCurrentUsername();
        String clientIp = getClientIpAddress(request);

        log.info("INCOMING REQUEST: {} {} from user: {} client: {}",
                request.getMethod(), request.getRequestURI(), username, clientIp);

        if ("POST".equals(request.getMethod()) || "PUT".equals(request.getMethod())) {
            log.debug("Request content length: {} bytes", request.getContentLength());
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            if (response.getStatus() >= 400) {
                log.warn("FAILED RESPONSE: {} {} user: {} status: {} ({}ms)",
                        request.getMethod(), request.getRequestURI(), username,
                        response.getStatus(), duration);
            } else {
                log.debug("SUCCESS RESPONSE: {} {} user: {} status: {} ({}ms)",
                        request.getMethod(), request.getRequestURI(), username,
                        response.getStatus(), duration);
            }
        }
    }

    private String getCurrentUsername() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName()))
                    ? auth.getName() : "anonymous";
        } catch (Exception e) {
            return "anonymous";
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
