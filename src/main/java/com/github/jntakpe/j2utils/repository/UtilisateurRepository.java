package com.github.jntakpe.j2utils.repository;

import com.github.jntakpe.j2utils.domain.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO de l'entité {@link com.github.jntakpe.j2utils.domain.Utilisateur}
 *
 * @author jntakpe
 */
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {

    Utilisateur findByLoginIgnoreCase(String login);
}
