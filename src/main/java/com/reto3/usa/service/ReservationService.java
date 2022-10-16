package com.reto3.usa.service;

import com.reto3.usa.daoRepository.ReservationRepository;
import com.reto3.usa.model.Reservation;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    
     public List<Reservation>getAll(){
        return reservationRepository.getAll();               
    }
    public Optional<Reservation> getReservation(int reservationId){
        return reservationRepository.getReservation(reservationId);
    }
    public Reservation save(Reservation reservation){
        if(reservation.getIdReservation()==null){
            return reservationRepository.save(reservation);
        } else {     
            Optional<Reservation>reservationEncontrado=reservationRepository.getReservation(reservation.getIdReservation());
            if(reservationEncontrado.isEmpty()){
                return reservationRepository.save(reservation);
            } else {
                return reservation;
            }
        }
    }
    public Reservation update(Reservation reservation){
        if(reservation.getIdReservation()!=null){
            Optional<Reservation>reservationEncontrado= getReservation(reservation.getIdReservation());
            if(!reservationEncontrado.isEmpty()){
               if(reservation.getStartDate()!=null){
                   reservationEncontrado.get().setStartDate(reservation.getStartDate());
               }
               if(reservation.getDevolutionDate()!=null){
                   reservationEncontrado.get().setDevolutionDate(reservation.getDevolutionDate());
               }
               if(reservation.getStatus()!=null){
                   reservationEncontrado.get().setStatus(reservation.getStatus());
               }               
               return reservationRepository.save(reservationEncontrado.get());
            }
        }
        return reservation;
    }
    public boolean deleteReservation(int reservationId){
        Boolean resultado = getReservation(reservationId).map(reservationPorEliminar ->{
            reservationRepository.delete(reservationPorEliminar);
            return true;
    }).orElse(false);
    return resultado;
    }
}
