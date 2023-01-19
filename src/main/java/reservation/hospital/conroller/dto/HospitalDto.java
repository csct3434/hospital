package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;
import reservation.hospital.domain.Address;

@Getter
public class HospitalDto {

    private Long id;
    private String name;
    private Address address;
    private String phoneNumber;

    public HospitalDto() {
    }

    public HospitalDto(Long id, String name, Address address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
