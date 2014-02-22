package com.github.jntakpe.j2utils.repository;

import com.github.jntakpe.j2utils.domain.Reservation;
import org.joda.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DAO de l'entité {@link com.github.jntakpe.j2utils.domain.Reservation}
 *
 * @author jntakpe
 */
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Reservation findByJour(LocalDate jour);
}
