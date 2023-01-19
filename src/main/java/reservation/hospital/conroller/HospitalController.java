package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.HospitalDto;
import reservation.hospital.conroller.form.HospitalForm;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.Hospital;
import reservation.hospital.service.HospitalService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HospitalController {

    private final HospitalService hospitalService;

    @GetMapping("hospitals/new")
    public String createForm(Model model) {
        model.addAttribute("hospitalForm", new HospitalForm());
        return "hospitals/createHospitalForm";
    }

    @PostMapping("hospitals/new")
    public String create(HospitalForm hospitalForm, Model model) {
        Address address = Address.create(hospitalForm.getCity(), hospitalForm.getStreet(), hospitalForm.getZipcode());
        Hospital hospital = Hospital.create(hospitalForm.getName(), address, hospitalForm.getPhoneNumber());

        hospitalService.join(hospital);

        return "redirect:/";
    }

    @GetMapping("hospitals")
    public String hospitalList(Model model) {
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();

        model.addAttribute("hospitalDtoList", hospitalDtoList);
        return "hospitals/hospitalList";
    }
}
