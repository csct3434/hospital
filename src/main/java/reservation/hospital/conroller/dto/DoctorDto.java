package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorDto {

    private Long id;
    private String name;
    private String licenseId;
    private int experience;
    private String hospitalName;
    private String departmentName;

    public DoctorDto() {
    }

    public DoctorDto(Long id, String name, String licenseId, int experience, String hospitalName, String departmentName) {
        this.id = id;
        this.name = name;
        this.licenseId = licenseId;
        this.experience = experience;
        this.hospitalName = hospitalName;
        this.departmentName = departmentName;
    }
}
