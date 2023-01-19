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

    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    private List<Reservation> reservations = new ArrayList<>();

    public static Patient create(String name, int age, SexType sex, Address address, String phoneNumber) {
        Patient patient = new Patient();
        patient.name = name;
        patient.age = age;
        patient.sex = sex;
        patient.address = address;
        patient.phoneNumber = phoneNumber;

        return patient;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
