package reservation.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.conroller.dto.ReservationDto;
import reservation.hospital.domain.*;
import reservation.hospital.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    public final ReservationRepository reservationRepository;
    public final PatientRepository patientRepository;
    public final HospitalRepository hospitalRepository;
    public final DepartmentRepository departmentRepository;
    public final DoctorRepository doctorRepository;

    @Transactional
    public Long join(ReservationDto reservationDto) {
        Patient patient = patientRepository.findOne(reservationDto.getPatientId());
        Hospital hospital = hospitalRepository.findOne(reservationDto.getHospitalId());
        Department department = departmentRepository.findOne(reservationDto.getDepartmentId());
        Doctor doctor = doctorRepository.findOne(reservationDto.getDoctorId());

        Reservation reservation = Reservation.create(reservationDto.getId(), patient, hospital, department, doctor,
                reservationDto.getReservationTime(), ReservationStatus.RESERVED, 0);

        reservationRepository.save(reservation);

        return reservation.getId();
    }

    public Reservation findOne(Long id) {
        return reservationRepository.findOne(id);
    }

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public List<Reservation> findByPatientName(String name) {
        return reservationRepository.findByPatientName(name);
    }

    public ReservationDto getReservationDto(Long reservationId) {
        Reservation reservation = reservationRepository.findOne(reservationId);

        ReservationDto reservationDto = new ReservationDto(reservation.getId(),
                reservation.getPatient().getId(), reservation.getPatient().getName(),
                reservation.getHospital().getId(),reservation.getHospital().getName(),
                reservation.getDepartment().getId(),reservation.getDepartment().getName(),
                reservation.getDoctor().getId(), reservation.getDoctor().getName(),
                reservation.getReservationTime(), reservation.getStatus(), reservation.getFee());

        return reservationDto;
    }

    public List<ReservationDto> getReservationDtoList() {
        List<Reservation> reservationList = findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            ReservationDto reservationDto = new ReservationDto(reservation.getId(),
                    reservation.getPatient().getId(), reservation.getPatient().getName(),
                    reservation.getHospital().getId(),reservation.getHospital().getName(),
                    reservation.getDepartment().getId(),reservation.getDepartment().getName(),
                    reservation.getDoctor().getId(), reservation.getDoctor().getName(),
                    reservation.getReservationTime(), reservation.getStatus(), reservation.getFee());

            reservationDtoList.add(reservationDto);
        }

        return reservationDtoList;
    }

    @Transactional
    public void cancel(Long id) {
        Reservation reservation = reservationRepository.findOne(id);

        try {
            if (reservation.getStatus() == ReservationStatus.COMPLETED) {
                throw new IllegalStateException("정산된 예약은 취소할 수 없습니다.");
            } else {
                reservation.cancel();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void remove(Long reservationId) {
        reservationRepository.remove(reservationId);
    }

    @Transactional
    public void complete(Long id, int fee) {
        Reservation reservation = reservationRepository.findOne(id);

        try {
            if(reservation.getStatus() == ReservationStatus.CANCELED) {
                throw new IllegalStateException("취소된 예약은 수납할 수 없습니다.");
            } else if(reservation.getStatus() == ReservationStatus.COMPLETED) {
                throw new IllegalStateException("이미 정산된 예약입니다.");
            } else {
                reservation.complete(fee);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

}
