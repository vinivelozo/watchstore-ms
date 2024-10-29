package com.watchstore.employees.dataaccesslayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Entity
@Getter
@Table(name = "employees")
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private EmployeeIdentifier employeeIdentifier;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="employee_phonenumbers", joinColumns = @JoinColumn(name = "employee_id"))
    private List<PhoneNumber> phoneNumbers;

    private String firstName;
    private String lastName;
    private String email;
    private String job_title;
    private Double salary;

    public Employee(@NotNull  Address address,@NotNull List<PhoneNumber> phoneNumbers,@NotNull String firstName,@NotNull String lastName,@NotNull String email,@NotNull String job_title,@NotNull Double salary,@NotNull Status status) {
        this.employeeIdentifier = new EmployeeIdentifier();
        this.address = address;
        this.phoneNumbers = phoneNumbers;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.job_title = job_title;
        this.salary = salary;
        this.status = status;
    }

    public void setStatus(com.watchstore.employees.dataaccesslayer.Status fired) {
    }
}
