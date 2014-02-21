package com.github.jntakpe.j2utils.repository;

import com.github.jntakpe.j2utils.domain.ConnexionToken;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * DAO de l'entité {@link com.github.jntakpe.j2utils.domain.ConnexionToken}
 *
 * @author jntakpe
 */
public interface ConnexionTokenRepository extends JpaRepository<ConnexionToken, Integer> {

    ConnexionToken findBySeries(String series);

    List<ConnexionToken> findByTokenTsDateBefore(LocalDate localDate);
}
