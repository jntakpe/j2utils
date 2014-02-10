package com.github.jntakpe.j2utils.security;

import com.github.jntakpe.j2utils.domain.Role;
import com.github.jntakpe.j2utils.domain.Utilisateur;
import com.github.jntakpe.j2utils.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Authentifie un utilisateur
 */
@Component
public class UtilisateurDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authentification de {}", login);
        Utilisateur utilisateur = utilisateurRepository.findByLoginIgnoreCase(login);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("L'utilisateur " + login + " n'a pas été trouvé.");
        }
        Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        for (Role role : utilisateur.getRoles()) {
            roles.add(new SimpleGrantedAuthority(role.getNom()));
        }
        return new org.springframework.security.core.userdetails.User(utilisateur.getLogin(), utilisateur.getPassword(), roles);
    }
}
