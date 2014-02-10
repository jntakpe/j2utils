package com.github.jntakpe.j2utils.repository;

import com.github.jntakpe.j2utils.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO de l'entité {@link com.github.jntakpe.j2utils.domain.Role}
 *
 * @author jntakpe
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
