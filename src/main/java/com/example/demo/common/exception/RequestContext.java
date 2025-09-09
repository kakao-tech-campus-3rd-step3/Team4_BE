package com.example.demo.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
public class RequestContext {

    private final String userId;
    private final String requestUri;
    private final String httpMethod;
    private final Map<String, String[]> queryParams;

    public RequestContext(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            this.userId = auth.getName();
        } else {
            this.userId = "UNKNOWN";
        }

        this.requestUri = request.getRequestURI();
        this.httpMethod = request.getMethod();
        this.queryParams = request.getParameterMap();
    }
}
