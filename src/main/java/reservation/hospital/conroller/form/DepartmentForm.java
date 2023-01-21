package reservation.hospital.conroller.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DepartmentForm {

    private Long id;
    private String name;
    private String phoneNumber;
    private Long hospitalId;
}
