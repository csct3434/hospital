package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.DepartmentDto;
import reservation.hospital.conroller.dto.HospitalDto;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.Department;
import reservation.hospital.domain.Hospital;
import reservation.hospital.service.DepartmentService;
import reservation.hospital.service.HospitalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;
    private final HospitalService hospitalService;

    @GetMapping("departments/new")
    public String createForm(Model model) {
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();

        model.addAttribute("departmentDto", new DepartmentDto());
        model.addAttribute("hospitalDtoList", hospitalDtoList);
        return "departments/createDepartmentForm";
    }

    @PostMapping("departments/new")
    public String create(DepartmentDto departmentDto, Model model) {
        Department department = Department.create(departmentDto.getName(), departmentDto.getPhoneNumber());
        departmentService.join(department, departmentDto.getHospitalId());
        return "redirect:/";
    }

    @GetMapping("departments")
    public String departmentList(Model model) {
        List<DepartmentDto> departmentDtoList = departmentService.getDepartmentDtoList();

        model.addAttribute("departmentDtoList", departmentDtoList);
        return "departments/departmentList";
    }

    @GetMapping("departments/{departmentId}/edit")
    public String updateForm(@PathVariable("departmentId") Long departmentId, Model model) {
        DepartmentDto departmentDto = departmentService.getDepartmentDto(departmentId);
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();

        model.addAttribute("departmentDto", departmentDto);
        model.addAttribute("hospitalDtoList", hospitalDtoList);

        return "departments/updateDepartmentForm.html";
    }

    @PostMapping("departments/{departmentId}/edit")
    public String update(DepartmentDto departmentDto, Model model) {
        Department department = Department.create(departmentDto.getId(), departmentDto.getName(), departmentDto.getPhoneNumber());
        departmentService.join(department, departmentDto.getHospitalId());
        return "redirect:/";
    }

    @GetMapping("departments/{departmentId}/remove")
    public String remove(@PathVariable("departmentId") Long departmentId, Model model) {
        departmentService.remove(departmentId);
        return "redirect:/";
    }
}
