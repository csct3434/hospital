package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.DepartmentDto;
import reservation.hospital.conroller.dto.DoctorDto;
import reservation.hospital.conroller.dto.HospitalDto;
import reservation.hospital.domain.Department;
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
        DoctorDto doctorDto = new DoctorDto();

        model.addAttribute("hospitalDtoList", hospitalDtoList);
        model.addAttribute("departmentDtoList", departmentDtoList);
        model.addAttribute("doctorDto", doctorDto);
        return "doctors/createDoctorForm";
    }

    @PostMapping("/doctors/new")
    public String create(DoctorDto doctorDto, Model model) {
        Doctor doctor = Doctor.create(doctorDto.getName(), doctorDto.getLicenseId(), doctorDto.getExperience());
        doctorService.join(doctor, doctorDto.getHospitalId(), doctorDto.getDepartmentId());
        return "redirect:/";
    }

    @GetMapping("/doctors")
    public String doctorList(Model model) {
        List<DoctorDto> doctorDtoList = doctorService.getDoctorDtoList();
        model.addAttribute("doctorDtoList", doctorDtoList);
        return "doctors/doctorList";
    }

    @GetMapping("doctors/{doctorId}/edit")
    public String updateForm(@PathVariable("doctorId") Long doctorId, Model model) {
        DoctorDto doctorDto = doctorService.getDoctorDto(doctorId);
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();
        List<DepartmentDto> departmentDtoList = departmentService.getDepartmentDtoList();

        model.addAttribute("doctorDto", doctorDto);
        model.addAttribute("hospitalDtoList", hospitalDtoList);
        model.addAttribute("departmentDtoList", departmentDtoList);
        return "doctors/updateDoctorForm.html";
    }

    @PostMapping("doctors/{doctorId}/edit")
    public String update(DoctorDto doctorDto) {
        Doctor doctor = Doctor.create(doctorDto.getId(), doctorDto.getName(), doctorDto.getLicenseId(), doctorDto.getExperience());
        doctorService.join(doctor, doctorDto.getHospitalId(), doctorDto.getDepartmentId());
        return "redirect:/doctors";
    }

    @GetMapping("doctors/{doctorId}/remove")
    public String remove(@PathVariable("doctorId") Long doctorId, Model model) {
        doctorService.remove(doctorId);
        return "redirect:/";
    }

}
