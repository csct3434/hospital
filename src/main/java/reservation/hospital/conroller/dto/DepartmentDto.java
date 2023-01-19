package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DepartmentDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private String hospitalName;

    public DepartmentDto() {
    }

    public DepartmentDto(Long id, String name, String phoneNumber, String hospitalName) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.hospitalName = hospitalName;
    }

}
