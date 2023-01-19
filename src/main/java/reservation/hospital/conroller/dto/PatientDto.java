package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;
import reservation.hospital.domain.Address;
import reservation.hospital.domain.SexType;

@Getter
public class PatientDto {
    
    private Long id;
    private String name;
    private int age;
    private SexType sex;
    private Address address;
    private String phoneNumber;

    public PatientDto() {
    }

    public PatientDto(Long id, String name, int age, SexType sex, Address address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
