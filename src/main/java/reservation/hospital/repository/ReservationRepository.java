package reservation.hospital.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reservation.hospital.domain.Reservation;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepository {

    private final EntityManager em;

    public void save(Reservation reservation) {
        if(reservation.getId() == null) {
            em.persist(reservation);
        } else {
            em.merge(reservation);
        }
    }

    public Reservation findOne(Long id) {
        return em.find(Reservation.class, id);
    }

    public List<Reservation> findAll() {
        return em.createQuery("select r from Reservation r", Reservation.class)
                .getResultList();
    }

    public List<Reservation> findByPatientName(String name) {
        return em.createQuery("select r from Reservation r where r.patient.name = :name", Reservation.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void remove(Long reservationId) {
        Reservation reservation = em.find(Reservation.class, reservationId);
        em.remove(reservation);
    }
}
