package reservation.hospital.conroller.form;

import lombok.Getter;
import lombok.Setter;
import reservation.hospital.domain.SexType;

@Getter
@Setter
public class PatientForm {

    private String name;
    private int age;
    private SexType sex;
    private String city;
    private String street;
    private String zipcode;
    private String phoneNumber;
}
