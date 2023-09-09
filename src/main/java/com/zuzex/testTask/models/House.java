package com.zuzex.testTask.models;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="street")
    @JsonProperty("street")
    private String streetName;

    @Column(name="house_number")
    @JsonProperty("house_number")
    private String houseNumber;

    @Column(name="apartment_number")
    @JsonProperty("apartment_number")
    private Integer apartmentNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "householder_id")
    @JsonProperty("householder_id")
    private User householder;

    @OneToMany
    @JoinColumn(name="id")
    private Set<User> residents;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getStreetName() {return streetName;}

    public void setStreetName(String streetName) {this.streetName = streetName;}

    public String getHouseNumber() {return houseNumber;}

    public void setHouseNumber(String houseNumber) {this.houseNumber = houseNumber;}

    public Integer getApartmentNumber() {return apartmentNumber;}

    public void setApartmentNumber(Integer apartmentNumber) {this.apartmentNumber = apartmentNumber;}

    public User getHouseholder() {return householder;}

    public void setHouseholder(User householder) {this.householder = householder;}

    public Set<User> getResidents() {return residents;}

    public void setResidents(Set<User> residents) {this.residents = residents;}
}
