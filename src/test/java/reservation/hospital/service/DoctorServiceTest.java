package reservation.hospital.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.Department;
import reservation.hospital.domain.Doctor;
import reservation.hospital.domain.Hospital;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DoctorServiceTest {

    @Autowired DoctorService doctorService;
    @Autowired DepartmentService departmentService;
    @Autowired HospitalService hospitalService;

    @Test
    void join() {
        //given
        Hospital hospital = Hospital.create("hospital", Address.create("city", "street", "100"), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1234", 3);

        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        // hospital.addDepartment(dept);    // cascade가 동작하지 않음

        //when
        Long joinId = doctorService.join(doctor, hospital.getId(), dept.getId());

        //then
        assertThat(doctor.getId()).isEqualTo(joinId);
        assertThat(doctor.getDepartment()).isSameAs(dept);
        assertThat(doctor.getHospital()).isSameAs(hospital);
    }

    @Test
    void findOne() {
        //given
        Hospital hospital = Hospital.create("hospital", Address.create("city", "street", "100"), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor = Doctor.create("doctor", "1234", 3);

        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        Long joinId = doctorService.join(doctor, hospital.getId(), dept.getId());

        //when
        Doctor findDoctor = doctorService.findOne(joinId);

        //then
        assertThat(findDoctor).isEqualTo(doctor);
        assertThat(findDoctor.getDepartment()).isSameAs(dept);
        assertThat(findDoctor.getHospital()).isSameAs(hospital);
    }

    @Test
    void findAll() {
        //given
        Hospital hospital = Hospital.create("hospital", Address.create("city", "street", "100"), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor1 = Doctor.create("doctor", "100", 3);
        Doctor doctor2 = Doctor.create("doctor", "200", 3);
        Doctor doctor3 = Doctor.create("doctor", "300", 3);

        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor1, hospital.getId(), dept.getId());
        doctorService.join(doctor2, hospital.getId(), dept.getId());
        doctorService.join(doctor3, hospital.getId(), dept.getId());

        //when
        List<Doctor> result = doctorService.findAll();

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(hospital.getDoctors().size()).isEqualTo(3);
        assertThat(dept.getDoctors().size()).isEqualTo(3);
    }

    @Test
    void findByName() {
        //given
        Hospital hospital = Hospital.create("hospital", Address.create("city", "street", "100"), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor1 = Doctor.create("doctorA", "100", 3);
        Doctor doctor2 = Doctor.create("doctorA", "200", 3);
        Doctor doctor3 = Doctor.create("doctorB", "300", 3);

        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor1, hospital.getId(), dept.getId());
        doctorService.join(doctor2, hospital.getId(), dept.getId());
        doctorService.join(doctor3, hospital.getId(), dept.getId());

        //when
        List<Doctor> findDoctorA = doctorService.findByName("doctorA");
        List<Doctor> findDoctorB = doctorService.findByName("doctorB");

        //then
        assertThat(findDoctorA.size()).isEqualTo(2);
        assertThat(findDoctorB.size()).isEqualTo(1);
    }

    @Test
    public void duplicateLicenseId() {
        //given
        Hospital hospital = Hospital.create("hospital", Address.create("city", "street", "100"), "100");
        Department dept = Department.create("dept", "100");
        Doctor doctor1 = Doctor.create("doctorA", "100", 3);
        Doctor doctor2 = Doctor.create("doctorA", "100", 5);

        hospitalService.join(hospital);
        departmentService.join(dept, hospital.getId());
        doctorService.join(doctor1, hospital.getId(), dept.getId());

        //when
        assertThrows(IllegalStateException.class, () -> {
            doctorService.join(doctor2, hospital.getId(), dept.getId());
        });

        //then
    }

}