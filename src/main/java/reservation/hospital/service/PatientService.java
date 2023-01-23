package reservation.hospital.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reservation.hospital.conroller.dto.PatientDto;
import reservation.hospital.domain.Patient;
import reservation.hospital.repository.PatientRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    public Long join(Patient patient) {
        patientRepository.save(patient);
        return patient.getId();
    }

    public Patient findOne(Long id) {
        return patientRepository.findOne(id);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    public List<Patient> findByName(String name) {
        return patientRepository.findByName(name);
    }

    public PatientDto getPatientDto(Long id) {
        Patient patient = patientRepository.findOne(id);

        PatientDto patientDto = new PatientDto(patient.getId(), patient.getName(), patient.getAge(),
                patient.getSex(), patient.getAddress().getCity(), patient.getAddress().getStreet(),
                patient.getAddress().getZipcode(), patient.getPhoneNumber());

        return patientDto;
    }

    public List<PatientDto> getPatientDtoList() {
        List<Patient> patientList = patientRepository.findAll();
        List<PatientDto> patientDtoList = new ArrayList<>();

        for (Patient patient : patientList) {
            patientDtoList.add(new PatientDto(patient.getId(), patient.getName(), patient.getAge(),
                    patient.getSex(), patient.getAddress().getCity(), patient.getAddress().getStreet(),
                    patient.getAddress().getZipcode(), patient.getPhoneNumber()));
        }

        return patientDtoList;
    }

    @Transactional
    public void remove(Long patientId) {
        patientRepository.remove(patientId);
    }
}
