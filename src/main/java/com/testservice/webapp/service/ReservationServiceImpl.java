package com.testservice.webapp.service;

import com.testservice.webapp.dto.ReservationDto;
import com.testservice.webapp.entity.Reservation;
import com.testservice.webapp.repository.ReservationRep;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReservationServiceImpl implements ReservationService{

    private final ReservationRep reservationRep;

    public ReservationServiceImpl(ReservationRep reservationRep) {
        this.reservationRep = reservationRep;
    }

    @Override
    public List<ReservationDto> getAllReservations() { ;

        return transform(reservationRep.findAll());
    }

    @Override
    public ReservationDto getById(int id) {
        return new ReservationDto(reservationRep.getReservationById(id));
    }

    @Override
    public List<ReservationDto> getByCustomerId(int id) {
        return transform(reservationRep.getByTheCustomerId(id));
    }

    @Override
    @Transactional
    public void deleteReservation(Reservation reservation) {
        this.reservationRep.delete(reservation);
    }

    @Override
    @Transactional
    public void createReservation(Reservation reservation) {
        this.reservationRep.save(reservation);
    }

    private List<ReservationDto> transform(List<Reservation> reservations) {
        List<ReservationDto> reservationDtos = new ArrayList<>();
        for (Reservation res : reservations) {
            reservationDtos.add(new ReservationDto(res));
        }
        return reservationDtos;
    }
}