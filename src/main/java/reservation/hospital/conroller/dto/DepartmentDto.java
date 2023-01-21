package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DepartmentDto {

    private Long id;
    private String name;
    private String phoneNumber;
    private Long hospitalId;
    private String hospitalName;

    public DepartmentDto() {
    }

    public DepartmentDto(Long id, String name, String phoneNumber, Long hospitalId, String hospitalName) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
    }

}
