package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;
import reservation.hospital.domain.ReservationStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class ReservationDto {

    private Long id;
    private Long patientId;
    private String patientName;
    private Long hospitalId;
    private String hospitalName;
    private Long departmentId;
    private String departmentName;
    private Long doctorId;
    private String doctorName;
    private LocalDateTime reservationTime;
    private ReservationStatus status;
    private int fee;

    public ReservationDto() {
    }

    public ReservationDto(Long id, Long patientId, String patientName,
                          Long hospitalId, String hospitalName, Long departmentId, String departmentName,
                          Long doctorId, String doctorName, LocalDateTime reservationTime, ReservationStatus status, int fee) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.reservationTime = reservationTime;
        this.status = status;
        this.fee = fee;
    }
}
