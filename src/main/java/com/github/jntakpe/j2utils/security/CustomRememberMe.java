package com.github.jntakpe.j2utils.security;

import com.github.jntakpe.j2utils.domain.ConnexionToken;
import com.github.jntakpe.j2utils.domain.Utilisateur;
import com.github.jntakpe.j2utils.repository.ConnexionTokenRepository;
import com.github.jntakpe.j2utils.repository.UtilisateurRepository;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.CookieTheftException;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Implémentation personnalisé du RememberMe de Spring Security
 *
 * @author jntakpe
 */
@Service
public class CustomRememberMe extends AbstractRememberMeServices {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final int TOKEN_VALIDITY_DAYS = 31;

    private static final int TOKEN_VALIDITY_SECONDS = 60 * 60 * 24 * TOKEN_VALIDITY_DAYS;

    private static final int DEFAULT_SERIES_LENGTH = 16;

    private static final int DEFAULT_TOKEN_LENGTH = 16;

    private SecureRandom random;

    @Autowired
    private ConnexionTokenRepository connexionTokenRepository;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    public CustomRememberMe(UserDetailsService userDetailsService) {
        super("j2utils super secure key or not", userDetailsService);
        random = new SecureRandom();
    }

    @Override
    @Transactional
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
        ConnexionToken token = getPersistentToken(cookieTokens);
        String login = token.getUtilisateur().getLogin();
        log.debug("Mise à jour du token de l'utilisateur '{}', series '{}'", login, token.getSeries());
        token.setTokenTs(new LocalDate());
        token.setToken(generateTokenData());
        token.setIp(request.getRemoteAddr());
        token.setUserAgent(request.getHeader("User-Agent"));
        try {
            connexionTokenRepository.saveAndFlush(token);
            addCookie(token, request, response);
        } catch (DataAccessException e) {
            log.error("Impossible de mettre à jour le token : ", e);
            throw new RememberMeAuthenticationException("Échec de la connexion automatique: " + e.getMessage());
        }
        return getUserDetailsService().loadUserByUsername(login);
    }

    @Override
    protected void onLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String login = successfulAuthentication.getName();
        log.debug("Creation d'un nouveau token pour l'utilisateur {}", login);
        Utilisateur user = utilisateurRepository.findByLoginIgnoreCase(login);
        ConnexionToken token = new ConnexionToken();
        token.setSeries(generateSeriesData());
        token.setUtilisateur(user);
        token.setToken(generateTokenData());
        token.setTokenTs(new LocalDate());
        token.setIp(request.getRemoteAddr());
        token.setUserAgent(request.getHeader("User-Agent"));
        try {
            connexionTokenRepository.saveAndFlush(token);
            addCookie(token, request, response);
        } catch (DataAccessException e) {
            log.error("Échec de la connexion du token", e);
        }
    }

    @Override
    @Transactional
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String rememberMeCookie = extractRememberMeCookie(request);
        if (rememberMeCookie != null && rememberMeCookie.length() != 0) {
            try {
                String[] cookieTokens = decodeCookie(rememberMeCookie);
                ConnexionToken token = getPersistentToken(cookieTokens);
                connexionTokenRepository.delete(token);
            } catch (InvalidCookieException ice) {
                log.info("Cookie invalide. Echec de la suppression du token.");
            } catch (RememberMeAuthenticationException rmae) {
                log.debug("Impossible de récupérer le token.");
            }
        }
        super.logout(request, response, authentication);
    }

    private ConnexionToken getPersistentToken(String[] cookieTokens) {
        if (cookieTokens.length != 2) {
            throw new InvalidCookieException("Le cookie ne contient pas " + 2 + " tokens mais '" + Arrays.asList(cookieTokens) + "'");
        }
        String presentedSeries = cookieTokens[0];
        String presentedToken = cookieTokens[1];
        ConnexionToken token = connexionTokenRepository.findBySeries(presentedSeries);
        if (token == null) {
            throw new RememberMeAuthenticationException("Aucun token trouvé pour la série : " + presentedSeries);
        }
        log.info("presentedToken={} / tokenValue={}", presentedToken, token.getToken());
        if (!presentedToken.equals(token.getToken())) {
            connexionTokenRepository.delete(token);
            throw new CookieTheftException("Le remember-me token est invalide (vol de cookie).");
        }
        if (token.getTokenTs().plusDays(TOKEN_VALIDITY_DAYS).isBefore(LocalDate.now())) {
            connexionTokenRepository.delete(token);
            throw new RememberMeAuthenticationException("Le remember-me token a expiré.");
        }
        return token;
    }

    private String generateSeriesData() {
        byte[] newSeries = new byte[DEFAULT_SERIES_LENGTH];
        random.nextBytes(newSeries);
        return new String(Base64.encode(newSeries));
    }

    private String generateTokenData() {
        byte[] newToken = new byte[DEFAULT_TOKEN_LENGTH];
        random.nextBytes(newToken);
        return new String(Base64.encode(newToken));
    }

    private void addCookie(ConnexionToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[]{token.getSeries(), token.getToken()}, TOKEN_VALIDITY_SECONDS, request, response);
    }
}
