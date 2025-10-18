package com.example.demo.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class AdminSessionFilter extends HttpFilter {

    private static final Set<String> EXCLUDE_PATHS = Set.of(
        "/admin/login.html",
        "/admin/api/login"
    );

    @Override
    protected void doFilter(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {

        String path = request.getRequestURI();
        if (EXCLUDE_PATHS.contains(path)) {
            chain.doFilter(request, response);
            return;
        }

        if (path.startsWith("/admin")) {
            Object adminId = request.getSession(false) != null
                ? request.getSession(false).getAttribute("admin_id")
                : null;

            if (adminId == null) {
                response.sendRedirect("/admin/login.html");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}

