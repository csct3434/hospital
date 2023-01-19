package reservation.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.conroller.dto.DepartmentDto;
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

        // 부서 등록
        departmentRepository.save(department);
        // 부서를 병원에 등록
        hospital.addDepartment(department);

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
        List<Department> departmentList = findAll();
        List<DepartmentDto> departmentDtoList = new ArrayList<>();

        for (Department department : departmentList) {
            DepartmentDto departmentDto = new DepartmentDto(department.getId(), department.getName(),
                    department.getPhoneNumber(), department.getHospital().getName());
            departmentDtoList.add(departmentDto);
        }

        return departmentDtoList;
    }
}
