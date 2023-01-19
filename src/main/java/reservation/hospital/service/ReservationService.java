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
    public Long join(Long patientId, Long hospitalId, Long departmentId, Long doctorId, LocalDateTime reservationTime) {
        Patient patient = patientRepository.findOne(patientId);
        Hospital hospital = hospitalRepository.findOne(hospitalId);
        Department department = departmentRepository.findOne(departmentId);
        Doctor doctor = doctorRepository.findOne(doctorId);

        Reservation reservation = Reservation.create(patient, hospital, department, doctor, reservationTime, ReservationStatus.RESERVED, 0);
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

    public void cancel(Long id) {
        Reservation reservation = reservationRepository.findOne(id);
        reservation.cancel();
    }

    public void complete(Long id, int fee) {
        Reservation reservation = reservationRepository.findOne(id);
        reservation.complete(fee);
    }

    public List<ReservationDto> getReservationDtoList() {
        List<Reservation> reservationList = findAll();
        List<ReservationDto> reservationDtoList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            ReservationDto reservationDto = new ReservationDto(reservation.getId(), reservation.getPatient().getName(), reservation.getHospital().getName(),
                    reservation.getDepartment().getName(), reservation.getDoctor().getName(),
                    reservation.getReservationTime(), reservation.getStatus(), reservation.getFee());

            reservationDtoList.add(reservationDto);
        }

        return reservationDtoList;
    }
}
