package reservation.hospital.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Doctor {

    @Id @GeneratedValue
    @Column(name = "doctor_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String licenseId;

    private int experience;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToMany(mappedBy = "doctor")
    private List<Reservation> reservations = new ArrayList<>();


    public static Doctor create(String name, String licenseId, int experience) {
        Doctor doctor = new Doctor();
        doctor.name = name;
        doctor.licenseId = licenseId;
        doctor.experience = experience;

        return doctor;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
