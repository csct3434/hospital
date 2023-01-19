package reservation.hospital.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.Department;
import reservation.hospital.domain.Hospital;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class DepartmentServiceTest {

    @Autowired HospitalService hospitalService;
    @Autowired DepartmentService departmentService;

    @Test
    void join() {
        //given
        Hospital hospital = Hospital.create("hospitalA", Address.create("city", "street", "100"), "100");
        Department dept = Department.create("deptA", "100");

        Long hospitalId = hospitalService.join(hospital);

        //when
        Long joinId = departmentService.join(dept, hospitalId);

        //then
        assertThat(dept.getId()).isEqualTo(joinId);
        assertThat(dept.getHospital()).isSameAs(hospital);
    }

    @Test
    void findOne() {
        //given
        Hospital hospital = Hospital.create("hospitalA", Address.create("city", "street", "100"), "100");
        Department dept = Department.create("deptA", "100");

        Long hospitalId = hospitalService.join(hospital);
        Long deptId = departmentService.join(dept, hospitalId);

        //when
        Department findDept = departmentService.findOne(deptId);

        //then
        assertThat(dept).isSameAs(findDept);
        assertThat(findDept.getHospital()).isSameAs(hospital);
    }

    @Test
    void findAll() {
        //given
        Hospital hospital = Hospital.create("hospitalA", Address.create("city", "street", "100"), "100");
        Department dept1 = Department.create("deptA", "100");
        Department dept2 = Department.create("deptB", "100");
        Department dept3 = Department.create("deptC", "100");

        Long hospitalId = hospitalService.join(hospital);
        departmentService.join(dept1, hospitalId);
        departmentService.join(dept2, hospitalId);
        departmentService.join(dept3, hospitalId);

        //when
        List<Department> result = departmentService.findAll();

        //then
        assertThat(result.size()).isEqualTo(3);
        assertThat(hospital.getDepartments().size()).isEqualTo((3));
    }

    @Test
    public void findByName() {
        // given
        Hospital hospital1 = Hospital.create("hospitalA", Address.create("city", "street", "100"), "100");
        Hospital hospital2 = Hospital.create("hospitalB", Address.create("city", "street", "100"), "100");

        Department dept1 = Department.create("deptA", "100");
        Department dept2 = Department.create("deptA", "100");
        Department dept3 = Department.create("deptB", "100");

        Long hospitalId1 = hospitalService.join(hospital1);
        Long hospitalId2 = hospitalService.join(hospital2);
        departmentService.join(dept1, hospitalId1);
        departmentService.join(dept2, hospitalId1);
        departmentService.join(dept3, hospitalId2);

        //when
        List<Department> resultA = departmentService.findByName("deptA");
        List<Department> resultB = departmentService.findByName("deptB");

        //then
        assertThat(resultA.size()).isEqualTo(2);
        assertThat(resultB.size()).isEqualTo(1);

        for (Department department : resultA) {
            assertThat(department.getHospital()).isSameAs(hospital1);
        }

        for (Department department : resultB) {
            assertThat(department.getHospital()).isSameAs(hospital2);
        }
    }
}