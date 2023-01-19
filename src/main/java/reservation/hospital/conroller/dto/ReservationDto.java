package reservation.hospital.conroller.dto;

import lombok.Getter;
import reservation.hospital.domain.ReservationStatus;

import java.time.LocalDateTime;

@Getter
public class ReservationDto {

    private Long id;
    private String patientName;
    private String hospitalName;
    private String departmentName;
    private String doctorName;
    private LocalDateTime reservationTime;
    private ReservationStatus status;
    private int fee;

    public ReservationDto() {
    }

    public ReservationDto(Long id, String patientName, String hospitalName, String departmentName, String doctorName,
                          LocalDateTime reservationTime, ReservationStatus status, int fee) {

        this.id = id;
        this.patientName = patientName;
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
        this.doctorName = doctorName;
        this.reservationTime = reservationTime;
        this.status = status;
        this.fee = fee;
    }

}
