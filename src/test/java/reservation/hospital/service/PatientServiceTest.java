package reservation.hospital.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.Patient;
import reservation.hospital.domain.SexType;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PatientServiceTest {

    @Autowired PatientService patientService;

    @Test
    void join() {
        //given
        Patient patient = Patient.create("patientA", 26, SexType.MAN, Address.create("city", "street", "100"), "000");

        //when
        Long patientId = patientService.join(patient);

        //then
        assertThat(patient.getId()).isEqualTo(patientId);
    }

    @Test
    void findOne() {
        //given
        Patient patient = Patient.create("patientA", 26, SexType.MAN, Address.create("city", "street", "100"), "000");
        Long joinId = patientService.join(patient);

        //when
        Patient findPatient = patientService.findOne(joinId);

        //then
        assertThat(findPatient).isSameAs(patient);
    }

    @Test
    void findAll() {
        //given
        Patient patient1 = Patient.create("patientA", 26, SexType.MAN,
                Address.create("city", "street", "100"), "000");

        Patient patient2 = Patient.create("patientB", 26, SexType.MAN,
                Address.create("city", "street", "100"), "000");

        patientService.join(patient1);
        patientService.join(patient2);

        //when
        List<Patient> findAll = patientService.findAll();

        //then
        assertThat(findAll.size()).isEqualTo(2);
    }

    @Test
    void findByName() {
        //given
        Patient patient1 = Patient.create("patientA", 26, SexType.MAN,
                Address.create("city", "street", "100"), "000");

        Patient patient2 = Patient.create("patientA", 26, SexType.MAN,
                Address.create("city", "street", "100"), "000");

        Patient patient3 = Patient.create("patientB", 26, SexType.MAN,
                Address.create("city", "street", "100"), "000");

        patientService.join(patient1);
        patientService.join(patient2);
        patientService.join(patient3);

        //when
        List<Patient> findPatientA = patientService.findByName("patientA");
        List<Patient> findPatientB = patientService.findByName("patientB");

        //then
        assertThat(findPatientA.size()).isEqualTo(2);
        assertThat(findPatientB.size()).isEqualTo(1);
    }
}