package com.github.jntakpe.j2utils.service;

import com.github.jntakpe.j2utils.domain.ConnexionToken;
import com.github.jntakpe.j2utils.domain.Utilisateur;
import com.github.jntakpe.j2utils.repository.ConnexionTokenRepository;
import com.github.jntakpe.j2utils.repository.UtilisateurRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Services associés aux utilisateurs
 *
 * @author jntakpe
 */
@Service
public class UtilisateurService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ConnexionTokenRepository connexionTokenRepository;

    @Transactional(readOnly = true)
    public Utilisateur findByLogin(String login) {
        return utilisateurRepository.findByLoginIgnoreCase(login);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void automaticRemove() {
        LocalDate now = new LocalDate();
        List<ConnexionToken> tokens = connexionTokenRepository.findByTokenTsBefore(now.minusMonths(1));
        for (ConnexionToken token : tokens) {
            logger.debug("Suppression du token de connexion {}", token.getSeries());
            token.getUtilisateur().getConnexionTokens().remove(token);
            connexionTokenRepository.delete(token);
        }
    }
}
