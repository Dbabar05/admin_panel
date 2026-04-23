package com.gym.config;

import com.gym.util.TenantContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TenantInterceptor implements HandlerInterceptor {

    private static final String TENANT_HEADER = "X-Tenant-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String tenantIdStr = request.getHeader(TENANT_HEADER);
        if (tenantIdStr != null && !tenantIdStr.isEmpty()) {
            try {
                java.util.UUID tenantId = java.util.UUID.fromString(tenantIdStr);
                TenantContext.setCurrentTenant(tenantId);
            } catch (IllegalArgumentException e) {
                // Ignore or log error
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        TenantContext.clear();
    }
}
