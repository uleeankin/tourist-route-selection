package com.uleeankin.touristrouteselection.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UrlAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        this.handle(request, response, authentication);
        this.clearAuthenticationAttributes(request);
    }



    private void handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Authentication authentication) throws IOException {

        String targetUrl = this.determineTargetUrl(authentication);

        if (!response.isCommitted()) {
            this.redirectStrategy.sendRedirect(request, response, targetUrl);
        }
    }

    private String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("Администратор", "/admin");
        roleTargetUrlMap.put("Модератор", "/moderator");
        roleTargetUrlMap.put("Турист", "/tourist");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if(roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {

        final HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
