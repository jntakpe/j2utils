package com.github.jntakpe.j2utils.repository;

import com.github.jntakpe.j2utils.domain.ConnexionToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO de l'entité {@link com.github.jntakpe.j2utils.domain.ConnexionToken}
 *
 * @author jntakpe
 */
public interface ConnexionTokenRepository extends JpaRepository<ConnexionToken, Integer> {

    ConnexionToken findBySeries(String series);
}
