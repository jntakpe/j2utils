package com.github.jntakpe.j2utils.web;

import com.github.jntakpe.j2utils.domain.Reservation;
import com.github.jntakpe.j2utils.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Gestion des réservation
 *
 * @author jntakpe
 */
@RestController
@RequestMapping("/j2utils/rest/reservation")
public class ReservationResource {

    @Autowired
    private ReservationService reservationService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }
}
