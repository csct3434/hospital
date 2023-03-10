package reservation.hospital.conroller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DoctorDto {

    private Long id;
    private String name;
    private String licenseId;
    private int experience;
    private Long hospitalId;
    private String hospitalName;
    private Long departmentId;
    private String departmentName;

    public DoctorDto() {
    }

    public DoctorDto(Long id, String name, String licenseId, int experience, Long hospitalId, String hospitalName, Long departmentId, String departmentName) {
        this.id = id;
        this.name = name;
        this.licenseId = licenseId;
        this.experience = experience;
        this.hospitalId = hospitalId;
        this.hospitalName = hospitalName;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }
}
