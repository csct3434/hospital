package reservation.hospital.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private LocalDateTime reservationTime;

    private ReservationStatus status;

    private int fee;

    public static Reservation create(Patient patient, Hospital hospital, Department department,
                                     Doctor doctor, LocalDateTime reservationTime, ReservationStatus status, int fee) {
        Reservation reservation = new Reservation();
        reservation.patient = patient;
        reservation.hospital = hospital;
        reservation.department = department;
        reservation.doctor = doctor;
        reservation.reservationTime = reservationTime;
        reservation.status = status;
        reservation.fee = fee;

        return reservation;
    }

    public static Reservation create(Long id, Patient patient, Hospital hospital, Department department,
                                     Doctor doctor, LocalDateTime reservationTime, ReservationStatus status, int fee) {
        Reservation reservation = new Reservation();
        reservation.id = id;
        reservation.patient = patient;
        reservation.hospital = hospital;
        reservation.department = department;
        reservation.doctor = doctor;
        reservation.reservationTime = reservationTime;
        reservation.status = status;
        reservation.fee = fee;

        return reservation;
    }

    public void cancel() {
        this.status = ReservationStatus.CANCELED;
    }

    public void complete(int fee) {
        this.status = ReservationStatus.COMPLETED;
        this.fee = fee;
    }

}
