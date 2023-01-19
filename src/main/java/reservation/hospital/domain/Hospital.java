package reservation.hospital.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Hospital {

    @Id @GeneratedValue
    @Column(name = "hospital_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    private String phoneNumber;

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Department> departments = new ArrayList<>();

    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "hospital")
    private List<Reservation> reservations = new ArrayList<>();

    public static Hospital create(String name, Address address, String phoneNumber) {
        Hospital hospital = new Hospital();
        hospital.name = name;
        hospital.address = address;
        hospital.phoneNumber = phoneNumber;

        return hospital;
    }

    public void addDepartment(Department department) {
        this.departments.add(department);
        department.setHospital(this);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.setHospital(this);
    }
}
