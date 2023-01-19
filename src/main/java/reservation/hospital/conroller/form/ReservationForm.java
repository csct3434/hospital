package reservation.hospital.conroller.form;

import lombok.Getter;
import lombok.Setter;
import reservation.hospital.domain.Reservation;
import reservation.hospital.domain.ReservationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationForm {

    private Long patientId;
    private Long hospitalId;
    private Long departmentId;
    private Long doctorId;
    private LocalDateTime reservationTime;
}
