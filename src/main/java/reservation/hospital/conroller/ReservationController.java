package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.*;
import reservation.hospital.domain.Department;
import reservation.hospital.domain.Doctor;
import reservation.hospital.domain.Reservation;
import reservation.hospital.service.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final PatientService patientService;
    private final HospitalService hospitalService;
    private final DepartmentService departmentService;
    private final DoctorService doctorService;

    @GetMapping("/reservations/new")
    public String createForm(Model model) {
        List<PatientDto> patientDtoList = patientService.getPatientDtoList();
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();
        List<DepartmentDto> departmentDtoList = departmentService.getDepartmentDtoList();
        List<DoctorDto> doctorDtoList = doctorService.getDoctorDtoList();
        ReservationDto reservationDto = new ReservationDto();

        model.addAttribute("patientDtoList", patientDtoList);
        model.addAttribute("hospitalDtoList", hospitalDtoList);
        model.addAttribute("departmentDtoList", departmentDtoList);
        model.addAttribute("doctorDtoList", doctorDtoList);
        model.addAttribute("reservationDto", reservationDto);

        return "reservations/createReservationForm";
    }

    @PostMapping("/reservations/new")
    public String create(ReservationDto reservationDto, Model model) {
        reservationService.join(reservationDto);

        return "redirect:/";
    }

    @GetMapping("/reservations")
    public String reservationList(Model model) {
        List<ReservationDto> reservationDtoList = reservationService.getReservationDtoList();
        model.addAttribute("reservationDtoList", reservationDtoList);
        return "reservations/reservationList";
    }

    @GetMapping("reservations/{reservationId}/edit")
    public String updateForm(@PathVariable("reservationId") Long reservationId, Model model) {
        ReservationDto reservationDto = reservationService.getReservationDto(reservationId);
        List<HospitalDto> hospitalDtoList = hospitalService.getHospitalDtoList();
        List<DepartmentDto> departmentDtoList = departmentService.getDepartmentDtoList();
        List<DoctorDto> doctorDtoList = doctorService.getDoctorDtoList();

        model.addAttribute("reservationDto", reservationDto);
        model.addAttribute("hospitalDtoList", hospitalDtoList);
        model.addAttribute("departmentDtoList", departmentDtoList);
        model.addAttribute("doctorDtoList", doctorDtoList);

        return "reservations/updateReservationForm";
    }

    @PostMapping("reservations/{reservationId}/edit")
    public String update(ReservationDto reservationDto) {
        reservationService.join(reservationDto);
        return "redirect:/reservations";
    }

    @GetMapping("reservations/{reservationId}/cancel")
    public String cancel(@PathVariable("reservationId") Long reservationId) {
        reservationService.cancel(reservationId);
        return "redirect:/reservations";
    }

    @GetMapping("reservations/{reservationId}/remove")
    public String remove(@PathVariable("reservationId") Long reservationId) {
        reservationService.remove(reservationId);
        return "redirect:/reservations";
    }

    @GetMapping("reservations/{reservationId}/complete")
    public String completeForm(@PathVariable("reservationId") Long reservationId, Model model) {
        ReservationDto reservationDto = new ReservationDto();
        reservationDto.setId(reservationId);

        model.addAttribute("reservationDto", reservationDto);
        return "/reservations/completeReservationForm";
    }

    @PostMapping("reservations/{reservationId}/complete")
    public String completeForm(ReservationDto reservationDto) {
        reservationService.complete(reservationDto.getId(), reservationDto.getFee());
        return "redirect:/reservations";
    }

}
