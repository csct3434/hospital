package reservation.hospital.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Department {

    @Id @GeneratedValue
    @Column(name = "department_id")
    private Long id;

    private String name;

    private String phoneNumber;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Doctor> doctors = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.REMOVE)
    private List<Reservation> reservations = new ArrayList<>();

    public static Department create(String name, String phoneNumber) {
        Department department = new Department();
        department.name = name;
        department.phoneNumber = phoneNumber;

        return department;
    }

    public static Department create(Long id, String name, String phoneNumber) {
        Department department = new Department();
        department.id = id;
        department.name = name;
        department.phoneNumber = phoneNumber;

        return department;
    }

    public void addDoctor(Doctor doctor) {
        this.doctors.add(doctor);
        doctor.setDepartment(this);
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", hospital=" + hospital +
                '}';
    }
}
