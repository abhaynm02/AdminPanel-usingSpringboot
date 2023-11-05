package com.SpingLoginApplication.LoginApplication.Service;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var authourities = authentication.getAuthorities();
       var roles=authourities.stream().map(r->r.getAuthority()).findFirst();
        if (roles.orElse("").equals("ADMIN")){
            response.sendRedirect("/admin");
        } else if (roles.orElse("").equals("USER")) {
            response.sendRedirect("/home");
            
        }else {
            response.sendRedirect("/error");
        }
    }
}
