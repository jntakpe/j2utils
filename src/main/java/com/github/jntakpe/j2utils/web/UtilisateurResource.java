package com.github.jntakpe.j2utils.web;

import com.codahale.metrics.annotation.Timed;
import com.github.jntakpe.j2utils.domain.ConnexionToken;
import com.github.jntakpe.j2utils.domain.Utilisateur;
import com.github.jntakpe.j2utils.security.SecurityUtils;
import com.github.jntakpe.j2utils.service.ConnexionTokenService;
import com.github.jntakpe.j2utils.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * Gestion des utilisateurs
 *
 * @author jntakpe
 */
@RestController
@RequestMapping("/j2utils")
public class UtilisateurResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private ConnexionTokenService connexionTokenService;

    @Timed
    @RequestMapping(value = "/rest/auth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String isConnected(HttpServletRequest request) {
        logger.debug("Vérification de la connexion de l'utilisateur courant");
        return request.getRemoteUser();
    }

    @Timed
    @RequestMapping(value = "/rest/utilisateur", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Utilisateur> findUtilisateur() {
        Utilisateur utilisateur = utilisateurService.findByLogin(SecurityUtils.getCurrentLogin());
        if (utilisateur == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(utilisateur, HttpStatus.OK);
    }

    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/rest/utilisateur/sessions", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ConnexionToken>> findCurrentSessions() {
        Utilisateur utilisateur = utilisateurService.findByLogin(SecurityUtils.getCurrentLogin());
        if (utilisateur == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(connexionTokenService.findByUtilisateur(utilisateur), HttpStatus.OK);
    }

    @Timed
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/rest/utilisateur/sessions/{series}", method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void invalidateSession(@PathVariable String series) throws UnsupportedEncodingException {
        String decodedSeries = URLDecoder.decode(series, "UTF-8");
        connexionTokenService.delete(decodedSeries);
    }
}
