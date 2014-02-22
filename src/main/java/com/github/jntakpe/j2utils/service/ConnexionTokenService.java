package com.github.jntakpe.j2utils.service;

import com.github.jntakpe.j2utils.domain.ConnexionToken;
import com.github.jntakpe.j2utils.domain.Utilisateur;
import com.github.jntakpe.j2utils.repository.ConnexionTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Services associés aux tokens de connexion
 *
 * @author jntakpe
 */
@Service
public class ConnexionTokenService {

    @Autowired
    private ConnexionTokenRepository connexionTokenRepository;

    public List<ConnexionToken> findByUtilisateur(Utilisateur utilisateur) {
        return connexionTokenRepository.findByUtilisateur(utilisateur);
    }

    public void delete(String series) {
        ConnexionToken connexionToken = connexionTokenRepository.findBySeries(series);
        connexionTokenRepository.delete(connexionToken);
    }
}