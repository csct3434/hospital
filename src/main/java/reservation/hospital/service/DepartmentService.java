package reservation.hospital.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.conroller.dto.DepartmentDto;
import reservation.hospital.conroller.dto.PatientDto;
import reservation.hospital.domain.Department;
import reservation.hospital.domain.Hospital;
import reservation.hospital.repository.DepartmentRepository;
import reservation.hospital.repository.HospitalRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public Long join(Department department, Long hospitalId) {
        Hospital hospital = hospitalRepository.findOne(hospitalId);

        hospital.addDepartment(department);
        departmentRepository.save(department);

        /* 호출 순서가 바뀐 경우, hospital.addDepartment()를 호출했을 때
        department 엔티티에 hospital 값은 등록되지만, 데이터베이스 조회 시 hospital_id가 null인 오류 발생 */
        // departmentRepository.save(department);
        // hospital.addDepartment(department);

        return department.getId();
    }

    public Department findOne(Long id) {
        return departmentRepository.findOne(id);
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public List<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    public List<DepartmentDto> getDepartmentDtoList() {
        List<Department> departmentList = departmentRepository.findAll();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        for (Department department : departmentList) {
            DepartmentDto departmentDto = new DepartmentDto(department.getId(), department.getName(),
                    department.getPhoneNumber(), department.getHospital().getId(), department.getHospital().getName());
            departmentDtoList.add(departmentDto);
        }

        return departmentDtoList;
    }

    public DepartmentDto getDepartmentDto(Long departmentId) {
        Department department = departmentRepository.findOne(departmentId);

        return new DepartmentDto(department.getId(), department.getName(), department.getPhoneNumber(),
                department.getHospital().getId(), department.getHospital().getName());
    }

    @Transactional
    public void remove(Long departmentId) {
        departmentRepository.remove(departmentId);
    }
}
