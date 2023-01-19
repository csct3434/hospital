package reservation.hospital.conroller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorForm {

    private String name;
    private String licenseId;
    private int experience;
    private Long hospitalId;
    private Long departmentId;
}
