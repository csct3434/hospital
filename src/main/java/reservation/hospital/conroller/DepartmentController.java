package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.DepartmentDto;
import reservation.hospital.conroller.dto.HospitalDto;
import reservation.hospital.conroller.form.DepartmentForm;
import reservation.hospital.domain.Department;
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

        model.addAttribute("departmentForm", new DepartmentForm());
        model.addAttribute("hospitalDtoList", hospitalDtoList);
        return "departments/createDepartmentForm";
    }

    @PostMapping("departments/new")
    public String create(DepartmentForm departmentForm, Model model) {
        Department department = Department.create(departmentForm.getName(), departmentForm.getPhoneNumber());
        departmentService.join(department, departmentForm.getHospitalId());
        return "redirect:/";
    }

    @GetMapping("departments")
    public String departmentList(Model model) {
        List<DepartmentDto> departmentDtoList = departmentService.getDepartmentDtoList();

        model.addAttribute("departmentDtoList", departmentDtoList);
        return "departments/departmentList";
    }

}
