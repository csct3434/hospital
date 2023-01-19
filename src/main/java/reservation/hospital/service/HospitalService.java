package reservation.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.conroller.dto.HospitalDto;
import reservation.hospital.domain.Hospital;
import reservation.hospital.repository.DepartmentRepository;
import reservation.hospital.repository.HospitalRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    @Transactional
    public Long join(Hospital hospital) {
        hospitalRepository.save(hospital);
        return hospital.getId();
    }

    public Hospital findOne(Long id) {
        return hospitalRepository.findOne(id);
    }

    public List<Hospital> findAll() {
        return hospitalRepository.findAll();
    }

    public List<Hospital> findByName(String name) {
        return hospitalRepository.findByName(name);
    }

    public List<HospitalDto> getHospitalDtoList() {
        List<Hospital> hospitalList = hospitalRepository.findAll();
        List<HospitalDto> hospitalDtoList = new ArrayList<>();

        for (Hospital hospital : hospitalList) {
            hospitalDtoList.add(new HospitalDto(hospital.getId(), hospital.getName(), hospital.getAddress(), hospital.getPhoneNumber()));
        }

        return hospitalDtoList;
    }
}
