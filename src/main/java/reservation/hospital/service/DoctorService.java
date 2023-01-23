package reservation.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.conroller.dto.DepartmentDto;
import reservation.hospital.conroller.dto.DoctorDto;
import reservation.hospital.domain.Department;
import reservation.hospital.domain.Doctor;
import reservation.hospital.domain.Hospital;
import reservation.hospital.repository.DepartmentRepository;
import reservation.hospital.repository.DoctorRepository;
import reservation.hospital.repository.HospitalRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final HospitalRepository hospitalRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public Long join(Doctor doctor, Long hospitalId, Long departmentId) {
        // 의사면허 중복 조회
        if(doctor.getId() == null) {
            validateLicenseId(doctor.getLicenseId());
        }

        // 엔티티 조회
        Hospital hospital = hospitalRepository.findOne(hospitalId);
        Department department = departmentRepository.findOne(departmentId);

        // 병원에 의사 정보 등록
        hospital.addDoctor(doctor);
        // 부서에 의사 정보 등록
        department.addDoctor(doctor);
        // DB에 의사 저장
        doctorRepository.save(doctor);

        return doctor.getId();
    }

    public Doctor findOne(Long id) {
        return doctorRepository.findOne(id);
    }

    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    public List<Doctor> findByName(String name) {
        return doctorRepository.findByName(name);
    }

    private void validateLicenseId(String licenseId) {
        List<Doctor> findDoctors = doctorRepository.findByLicenseId(licenseId);
        if (!findDoctors.isEmpty()) {
            throw new IllegalStateException("이미 등록된 의사면허 입니다.");
        }
    }

    public DoctorDto getDoctorDto(Long doctorId) {
        Doctor doctor = doctorRepository.findOne(doctorId);

        DoctorDto doctorDto = new DoctorDto(doctor.getId(), doctor.getName(), doctor.getLicenseId(), doctor.getExperience(),
                doctor.getHospital().getId(), doctor.getHospital().getName(),
                doctor.getDepartment().getId(), doctor.getDepartment().getName());

        return doctorDto;
    }

    public List<DoctorDto> getDoctorDtoList() {
        List<Doctor> doctorList = findAll();
        List<DoctorDto> doctorDtoList = new ArrayList<>();

        for (Doctor doctor : doctorList) {
            doctorDtoList.add(new DoctorDto(doctor.getId(), doctor.getName(), doctor.getLicenseId(), doctor.getExperience(),
                    doctor.getHospital().getId(), doctor.getHospital().getName(),
                    doctor.getDepartment().getId(), doctor.getDepartment().getName()));
        }

        return doctorDtoList;
    }

    @Transactional
    public void remove(Long doctorId) {
        doctorRepository.remove(doctorId);
    }
}
