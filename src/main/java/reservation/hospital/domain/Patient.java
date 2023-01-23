package reservation.hospital.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Patient {

    @Id @GeneratedValue
    @Column(name = "patient_id")
    private Long id;

    private String name;

    private int age;

    @Enumerated(EnumType.STRING)
    private SexType sex;

    @Embedded
    private Address address;

    private String phoneNumber;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.REMOVE)
    private List<Reservation> reservations = new ArrayList<>();

    public Patient() {
    }

    public static Patient create(String name, int age, SexType sex, Address address, String phoneNumber) {
        Patient patient = new Patient();
        patient.name = name;
        patient.age = age;
        patient.sex = sex;
        patient.address = address;
        patient.phoneNumber = phoneNumber;

        return patient;
    }

    public static Patient create(Long id, String name, int age, SexType sex, Address address, String phoneNumber) {
        Patient patient = new Patient();
        patient.id = id;
        patient.name = name;
        patient.age = age;
        patient.sex = sex;
        patient.address = address;
        patient.phoneNumber = phoneNumber;

        return patient;
    }

}
