package com.github.jntakpe.j2utils.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Classe utilitaire pour la gestion de la sécurité
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails springSecurityUser = (UserDetails) securityContext.getAuthentication().getPrincipal();
        return springSecurityUser.getUsername();
    }

    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        final Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities != null) {
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(RoleConstants.ANONYME)) {
                    return false;
                }
            }
        }
        return true;
    }
}
