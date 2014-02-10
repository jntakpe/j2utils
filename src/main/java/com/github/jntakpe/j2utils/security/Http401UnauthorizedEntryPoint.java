package com.github.jntakpe.j2utils.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Retourne le code HTTP 401 lors d'un accès non autorisé
 */
@Component
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException,
            ServletException {
        log.debug("Tentative d'accès non autorisée. Rejet de la requête.");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied");
    }
}
