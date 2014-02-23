package com.github.j2utils.service;

import com.github.jntakpe.j2utils.domain.Reservation;
import com.github.jntakpe.j2utils.service.ReservationService;
import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author jntakpe
 */
@ContextConfiguration("classpath:/spring/applicationContext-test.xml")
public class ReservationServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private ReservationService reservationService;

    @Test
    public void automaticAddTest() {
        assertThat(reservationService.findAll()).isEmpty();
        reservationService.automaticAdd();
        List<Reservation> reservations = reservationService.findAll();
        assertThat(reservations.size()).isEqualTo(1);
        assertThat(reservations.get(0).getJour()).isEqualTo(new LocalDate().plusWeeks(1));
    }

    @Test
    public void findAllAndAddIfNeededTest() {
        assertThat(reservationService.findAll()).isEmpty();
        List<Reservation> reservations = reservationService.findAllAndAddIfNeeded();
        assertThat(reservations.size()).isEqualTo(2);
        Object[] resaDays = {DateTimeConstants.THURSDAY, DateTimeConstants.TUESDAY};
        assertThat(reservations.get(0).getJour().getDayOfWeek()).isIn(resaDays);
        assertThat(reservations.get(1).getJour().getDayOfWeek()).isIn(resaDays);
        assertThat(reservations.get(0).getJour()).isNotEqualTo(reservations.get(1).getJour());
        reservations = reservationService.findAll();
        assertThat(reservations.size()).isEqualTo(2);
    }

}
