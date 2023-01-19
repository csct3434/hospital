package reservation.hospital.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ReservationServiceTest {

    ReservationService reservationService;
    PatientService patientService;
    HospitalService hospitalService;
    DepartmentService departmentService;
    DoctorService doctorService;

    @Autowired
    public ReservationServiceTest(ReservationService reservationService,
                                  PatientService patientService, HospitalService hospitalService,
                                  DepartmentService departmentService, DoctorService doctorService) {

        this.reservationService = reservationService;
        this.patientService = patientService;
        this.hospitalService = hospitalService;
        this.departmentService = departmentService;
        this.doctorService = doctorService;
    }

    @Test
    void join() {
        // given
        Patient patient = Patient.create("patient", 26, SexType.MAN, getAddress(), "000");
        Hospital hospital = Hospital.create("hospital", getAddress(), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1000", 3);
        LocalDateTime reservationTime = LocalDateTime.now();

        patientService.join(patient);
        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor, hospital.getId(), dept.getId());

        // when
        reservationService.join(patient.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);

        // then
    }

    private static Address getAddress() {
        return Address.create("city", "street", "100");
    }

    @Test
    public void find() {
        // given
        Patient patient = Patient.create("patient", 26, SexType.MAN, getAddress(), "000");
        Hospital hospital = Hospital.create("hospital", getAddress(), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1000", 3);
        LocalDateTime reservationTime = LocalDateTime.now();

        patientService.join(patient);
        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor, hospital.getId(), dept.getId());
        Long joinId = reservationService.join(patient.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);

        // when
        Reservation result = reservationService.findOne(joinId);

        // then
        assertThat(result.getPatient()).isSameAs(patient);
        // assertThat(patient.getReservations().get(0)).isSameAs(result);  양방향 동작 안함
    }

    @Test
    void findAll() {
        // given
        Patient patient1 = Patient.create("patientA", 26, SexType.MAN, getAddress(), "000");
        Patient patient2 = Patient.create("patientB", 24, SexType.WOMAN, getAddress(), "000");
        Hospital hospital = Hospital.create("hospital", getAddress(), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1000", 3);
        LocalDateTime reservationTime = LocalDateTime.now();

        patientService.join(patient1);
        patientService.join(patient2);
        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor, hospital.getId(), dept.getId());
        reservationService.join(patient1.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);
        reservationService.join(patient2.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);

        // when
        List<Reservation> result = reservationService.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void findByPatientName() {
        // given
        Patient patient1 = Patient.create("patientA", 26, SexType.MAN, getAddress(), "000");
        Patient patient2 = Patient.create("patientA", 26, SexType.MAN, getAddress(), "000");
        Patient patient3 = Patient.create("patientB", 24, SexType.WOMAN, getAddress(), "000");
        Hospital hospital = Hospital.create("hospital", getAddress(), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1000", 3);
        LocalDateTime reservationTime = LocalDateTime.now();

        patientService.join(patient1);
        patientService.join(patient2);
        patientService.join(patient3);
        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor, hospital.getId(), dept.getId());
        reservationService.join(patient1.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);
        reservationService.join(patient2.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);
        reservationService.join(patient3.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);

        // when
        List<Reservation> findPatientA = reservationService.findByPatientName("patientA");
        List<Reservation> findPatientB = reservationService.findByPatientName("patientB");

        // then
        assertThat(findPatientA.size()).isEqualTo(2);
        assertThat(findPatientB.size()).isEqualTo(1);
    }

    @Test
    public void cancel() {
        // given
        Patient patient = Patient.create("patient", 26, SexType.MAN, getAddress(), "000");
        Hospital hospital = Hospital.create("hospital", getAddress(), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1000", 3);
        LocalDateTime reservationTime = LocalDateTime.now();

        patientService.join(patient);
        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor, hospital.getId(), dept.getId());
        Long joinId = reservationService.join(patient.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);

        // when
        reservationService.cancel(joinId);

        // then
        Reservation reservation = reservationService.findOne(joinId);
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.CANCELED);
    }

    @Test
    public void complete() {
        // given
        Patient patient = Patient.create("patient", 26, SexType.MAN, getAddress(), "000");
        Hospital hospital = Hospital.create("hospital", getAddress(), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1000", 3);
        LocalDateTime reservationTime = LocalDateTime.now();

        patientService.join(patient);
        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor, hospital.getId(), dept.getId());
        Long joinId = reservationService.join(patient.getId(), hospital.getId(), dept.getId(), doctor.getId(), reservationTime);

        // when
        reservationService.complete(joinId, 5000);

        // then
        Reservation reservation = reservationService.findOne(joinId);
        assertThat(reservation.getStatus()).isEqualTo(ReservationStatus.COMPLETED);
        assertThat(reservation.getFee()).isEqualTo(5000);
    }
}