package com.siteoob.spacetrain.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (uri.equals("/login") || uri.equals("/logout") || uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images") || uri.equals("/error") || uri.equals("/favicon.ico") || uri.startsWith("/uploads")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        boolean loggedIn = session != null && session.getAttribute("usuarioId") != null;
        if (loggedIn) {
            return true;
        }

        response.sendRedirect(request.getContextPath() + "/login");
        return false;
    }
}
