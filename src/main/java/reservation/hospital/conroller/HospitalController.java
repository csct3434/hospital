package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.HospitalDto;
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
        model.addAttribute("hospitalDto", new HospitalDto());
        return "hospitals/createHospitalForm";
    }

    @PostMapping("hospitals/new")
    public String create(HospitalDto hospitalDto, Model model) {
        Address address = Address.create(hospitalDto.getCity(), hospitalDto.getStreet(), hospitalDto.getZipcode());
        Hospital hospital = Hospital.create(hospitalDto.getName(), address, hospitalDto.getPhoneNumber());

        hospitalService.join(hospital);

        return "redirect:/";
    }

    @GetMapping("hospitals")
    public String hospitalList(Model model) {
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();

        model.addAttribute("hospitalDtoList", hospitalDtoList);
        return "hospitals/hospitalList";
    }

    @GetMapping("hospitals/{hospitalId}/edit")
    public String updateForm(@PathVariable("hospitalId") Long hospitalId, Model model) {
        HospitalDto hospitalDto = hospitalService.getHospitalDto(hospitalId);

        model.addAttribute("hospitalDto", hospitalDto);
        return "hospitals/updateHospitalForm.html";
    }

    @PostMapping("hospitals/{hospitalId}/edit")
    public String update(HospitalDto hospitalDto, Model model) {
        Address address = Address.create(hospitalDto.getCity(), hospitalDto.getStreet(), hospitalDto.getZipcode());
        Hospital hospital = Hospital.create(hospitalDto.getId(), hospitalDto.getName(), address, hospitalDto.getPhoneNumber());

        hospitalService.join(hospital);
        return "redirect:/hospitals";
    }

    @GetMapping("hospitals/{hospitalId}/remove")
    public String remove(@PathVariable("hospitalId") Long hospitalId, Model model) {
        hospitalService.remove(hospitalId);
        return "redirect:/";
    }
}
