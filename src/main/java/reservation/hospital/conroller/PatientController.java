package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.PatientDto;
import reservation.hospital.conroller.form.PatientForm;
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
        model.addAttribute("patientForm", new PatientForm());
        return "patients/createPatientForm";
    }

    @PostMapping("patients/new")
    public String create(PatientForm patientForm, Model model) {
        Address address = Address.create(patientForm.getCity(), patientForm.getStreet(), patientForm.getZipcode());
        Patient patient = Patient.create(patientForm.getName(), patientForm.getAge(), patientForm.getSex(), address, patientForm.getPhoneNumber());

        patientService.join(patient);
        return "redirect:/";
    }

    @GetMapping("patients")
    public String patientList(Model model) {
        List<PatientDto> patientDtoList = patientService.getPatientDtoList();
        model.addAttribute("patientDtoList", patientDtoList);
        return "patients/patientList";
    }
}
