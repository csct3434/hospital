package reservation.hospital.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reservation.hospital.conroller.dto.*;
import reservation.hospital.conroller.form.ReservationForm;
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
        ReservationForm reservationForm = new ReservationForm();

        model.addAttribute("patientDtoList", patientDtoList);
        model.addAttribute("hospitalDtoList", hospitalDtoList);
        model.addAttribute("departmentDtoList", departmentDtoList);
        model.addAttribute("doctorDtoList", doctorDtoList);
        model.addAttribute("reservationForm", reservationForm);

        return "reservations/createReservationForm";
    }

    @PostMapping("/reservations/new")
    public String create(ReservationForm reservationForm, Model model) {
        reservationService.join(reservationForm.getPatientId(), reservationForm.getHospitalId(), reservationForm.getDepartmentId(),
                reservationForm.getDoctorId(), reservationForm.getReservationTime());

        return "redirect:/";
    }

    @GetMapping("/reservations")
    public String reservationList(Model model) {
        List<ReservationDto> reservationDtoList = reservationService.getReservationDtoList();
        model.addAttribute("reservationDtoList", reservationDtoList);
        return "reservations/reservationList";
    }
}
