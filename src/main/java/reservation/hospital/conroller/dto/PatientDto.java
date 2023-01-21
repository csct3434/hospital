package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;
import reservation.hospital.domain.SexType;

@Getter @Setter
public class PatientDto {
    
    private Long id;
    private String name;
    private int age;
    private SexType sex;
    private String city;
    private String street;
    private String zipcode;
    private String phoneNumber;

    public PatientDto() {
    }

    public PatientDto(Long id, String name, int age, SexType sex, String city, String street, String zipcode, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
    }
}
