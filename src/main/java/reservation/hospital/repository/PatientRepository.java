package reservation.hospital.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reservation.hospital.domain.Patient;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PatientRepository {

    private final EntityManager em;

    public void save(Patient patient) {
        if(patient.getId() == null) {
            em.persist(patient);
        } else {
            em.merge(patient);
        }
    }

    public Patient findOne(Long id) {
        return em.find(Patient.class, id);
    }

    public List<Patient> findAll() {
        return em.createQuery("select p from Patient p", Patient.class)
                .getResultList();
    }

    public List<Patient> findByName(String name) {
        return em.createQuery("select p from Patient p where p.name = :name", Patient.class)
                .setParameter("name", name)
                .getResultList();
    }

    public void remove(Long patientId) {
        Patient patient = em.find(Patient.class, patientId);
        em.remove(patient);
    }
}
