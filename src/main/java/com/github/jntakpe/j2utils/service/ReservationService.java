package com.github.jntakpe.j2utils.service;

import com.github.jntakpe.j2utils.domain.Reservation;
import com.github.jntakpe.j2utils.repository.ReservationRepository;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jntakpe
 */
@Service
public class ReservationService extends GenericService<Reservation> {

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * 2-4 ?")
    public void automaticAdd() {
        LocalDate date = new LocalDate().plusWeeks(1);
        save(date);
    }

    @Transactional
    public List<Reservation> findAllAndAddIfNeeded() {
        LocalDate nextTuesday = getNextDate(DateTimeConstants.TUESDAY);
        LocalDate nextThursday = getNextDate(DateTimeConstants.THURSDAY);
        if (reservationRepository.findByJour(nextTuesday) == null) save(nextTuesday);
        if (reservationRepository.findByJour(nextThursday) == null) save(nextThursday);
        return findAll();
    }

    @Transactional
    private Reservation save(LocalDate date) {
        Reservation reservation = new Reservation();
        reservation.setJour(date);
        return save(reservation);
    }

    private LocalDate getNextDate(int dayOfWeek) {
        LocalDate nextReserv = new LocalDate();
        if (nextReserv.getDayOfWeek() > dayOfWeek) nextReserv = nextReserv.plusWeeks(1);
        return nextReserv.withDayOfWeek(dayOfWeek);
    }

}
