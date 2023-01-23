package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.PatientDto;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.Patient;
import reservation.hospital.service.PatientService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("patients/new")
    public String createForm(Model model) {
        model.addAttribute("patientDto", new PatientDto());
        return "patients/createPatientForm";
    }

    @PostMapping("patients/new")
    public String create(PatientDto patientDto) {
        Address address = Address.create(patientDto.getCity(), patientDto.getStreet(), patientDto.getZipcode());
        Patient patient = Patient.create(patientDto.getName(), patientDto.getAge(), patientDto.getSex(), address, patientDto.getPhoneNumber());

        patientService.join(patient);
        return "redirect:/";
    }

    @GetMapping("patients")
    public String patientList(Model model) {
        List<PatientDto> patientDtoList = patientService.getPatientDtoList();
        model.addAttribute("patientDtoList", patientDtoList);
        return "patients/patientList";
    }

    @GetMapping("patients/{patientId}/edit")
    public String updateForm(@PathVariable("patientId") Long patientId, Model model) {
        PatientDto patientDto = patientService.getPatientDto(patientId);

        model.addAttribute("patientDto", patientDto);
        return "patients/updatePatientForm.html";
    }

    @PostMapping("patients/{patientId}/edit")
    public String update(PatientDto patientDto, Model model) {
        Address address = Address.create(patientDto.getCity(), patientDto.getStreet(), patientDto.getZipcode());
        Patient patient = Patient.create(patientDto.getId(), patientDto.getName(), patientDto.getAge(),
                patientDto.getSex(), address, patientDto.getPhoneNumber());

        patientService.join(patient);
        return "redirect:/patients";
    }

    @GetMapping("patients/{patientId}/remove")
    public String remove(@PathVariable("patientId") Long patientId, Model model) {
        patientService.remove(patientId);
        return "redirect:/";
    }

}
