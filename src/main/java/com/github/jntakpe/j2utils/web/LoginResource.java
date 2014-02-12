package com.github.jntakpe.j2utils.web;

import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Ressource gérant les connexions des utilisateurs
 *
 * @author jntakpe
 */
@RestController
@RequestMapping("/j2utils")
public class LoginResource {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Timed
    @RequestMapping(value = "/rest/authenticate", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String isConnected(HttpServletRequest request) {
        logger.debug("Vérification de la connexion de l'utilisateur courant");
        return request.getRemoteUser();
    }

}
