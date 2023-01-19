package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.DepartmentDto;
import reservation.hospital.conroller.dto.DoctorDto;
import reservation.hospital.conroller.dto.HospitalDto;
import reservation.hospital.conroller.form.DoctorForm;
import reservation.hospital.domain.Doctor;
import reservation.hospital.service.DepartmentService;
import reservation.hospital.service.DoctorService;
import reservation.hospital.service.HospitalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;

    @GetMapping("/doctors/new")
    public String createForm(Model model) {
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();
        List<DepartmentDto> departmentDtoList = departmentService.getDepartmentDtoList();
        DoctorForm doctorForm = new DoctorForm();

        model.addAttribute("hospitalDtoList", hospitalDtoList);
        model.addAttribute("departmentDtoList", departmentDtoList);
        model.addAttribute("doctorForm", doctorForm);
        return "doctors/createDoctorForm";
    }

    @PostMapping("/doctors/new")
    public String create(DoctorForm doctorForm, Model model) {
        Doctor doctor = Doctor.create(doctorForm.getName(), doctorForm.getLicenseId(), doctorForm.getExperience());
        doctorService.join(doctor, doctorForm.getHospitalId(), doctorForm.getDepartmentId());
        return "redirect:/";
    }

    @GetMapping("/doctors")
    public String doctorList(Model model) {
        List<DoctorDto> doctorDtoList = doctorService.getDoctorDtoList();
        model.addAttribute("doctorDtoList", doctorDtoList);
        return "doctors/doctorList";
    }
}
