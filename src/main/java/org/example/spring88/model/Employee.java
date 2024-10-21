package org.example.spring88.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Length(min = 3, max = 30)
    private String name;
    @Min(18)
    @Max(55)
    private int age;
    @NotEmpty(message = "Image can not be empty")
    private String image;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;


    // Constructors
    public Employee() {}

    public Employee(String name, Integer age, String address, String image, Company company) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.image = image;
        this.company = company;
    }
    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Length(min = 3, max = 30) String getName() {
        return name;
    }

    public void setName(@Length(min = 3, max = 30) String name) {
        this.name = name;
    }

    @Min(18)
    @Max(55)
    public int getAge() {
        return age;
    }

    public void setAge(@Min(18) @Max(55) int age) {
        this.age = age;
    }

    public @NotEmpty(message = "Image can not be empty") String getImage() {
        return image;
    }

    public void setImage(@NotEmpty(message = "Image can not be empty") String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}

