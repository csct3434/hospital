package reservation.hospital.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.Hospital;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class HospitalServiceTest {

    @Autowired HospitalService hospitalService;

    @Test
    void join() {
        //given
        Hospital hospital = Hospital.create("HospitalA", Address.create("city", "street", "1000"), "100");

        //when
        Long joinId = hospitalService.join(hospital);

        //then
        assertThat(hospital.getId()).isEqualTo(joinId);
    }

    @Test
    void findOne() {
        //given
        Hospital hospital = Hospital.create("HospitalA", Address.create("city", "street", "1000"), "100");
        Long joinId = hospitalService.join(hospital);

        //when
        Hospital findHospital = hospitalService.findOne(joinId);

        //then
        assertThat(hospital).isSameAs(findHospital);
    }

    @Test
    void findAll() {
        //given
        Hospital hospital1 = Hospital.create("HospitalA", Address.create("city", "street", "1000"), "100");
        Hospital hospital2 = Hospital.create("HospitalB", Address.create("city", "street", "1000"), "100");

        hospitalService.join(hospital1);
        hospitalService.join(hospital2);

        //when
        List<Hospital> findAllHospital = hospitalService.findAll();

        //then
        assertThat(findAllHospital.size()).isEqualTo(2);
    }

    @Test
    void findByName() {
        //given
        Hospital hospital1 = Hospital.create("HospitalA", Address.create("city", "street", "1000"), "100");
        Hospital hospital2 = Hospital.create("HospitalA", Address.create("city", "street", "1000"), "100");
        Hospital hospital3 = Hospital.create("HospitalA", Address.create("city", "street", "1000"), "100");
        Hospital hospital4 = Hospital.create("HospitalB", Address.create("city", "street", "1000"), "100");

        hospitalService.join(hospital1);
        hospitalService.join(hospital2);
        hospitalService.join(hospital3);
        hospitalService.join(hospital4);

        //when
        List<Hospital> findHospitalA = hospitalService.findByName("HospitalA");
        List<Hospital> findHospitalB = hospitalService.findByName("HospitalB");

        //then
        assertThat(findHospitalA.size()).isEqualTo(3);
        assertThat(findHospitalB.size()).isEqualTo(1);
    }
}