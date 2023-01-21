package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class HospitalDto {

    private Long id;
    private String name;
    private String city;
    private String street;
    private String zipcode;
    private String phoneNumber;

    public HospitalDto() {
    }

    public HospitalDto(Long id, String name, String city, String street, String zipcode, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.phoneNumber = phoneNumber;
    }
}
