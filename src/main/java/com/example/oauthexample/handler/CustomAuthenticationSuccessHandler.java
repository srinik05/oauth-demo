package com.example.oauthexample.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${allowed.emails}")
    private Set<String> whitelist; // Inject the whitelist from properties

    public CustomAuthenticationSuccessHandler(Set<String> whitelist) {
        this.whitelist = whitelist;
        setDefaultTargetUrl("/protected");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Check if the authentication principal is a DefaultOAuth2User
        if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            // Extract the user's email from DefaultOAuth2User
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
            String userEmail = defaultOAuth2User.getAttribute("email");

            // Check if the authenticated user's email is in the whitelist
            if (isUserInWhitelist(userEmail)) {
                super.onAuthenticationSuccess(request, response, authentication);
            } else {
                // Redirect or respond appropriately for unauthorized users
                response.sendRedirect("/unauthorized"); // Redirect to an unauthorized page
            }
        } else {
            throw new OAuth2AuthenticationException("Unsupported authentication principal type");
        }
    }

    private boolean isUserInWhitelist(String userEmail) {
        // Implement your logic to check if the user's email is in the whitelist
        // You might fetch the whitelist from a database or use a predefined set
        // Example:
        return whitelist.contains(userEmail);
    }
}
