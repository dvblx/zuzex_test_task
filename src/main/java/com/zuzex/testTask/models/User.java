package com.zuzex.testTask.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="firstname")
    @JsonProperty("firstname")
    private String firstName;

    @Column(name="lastname")
    @JsonProperty("lastname")
    private String lastName;

    @Column(name="patronymic")
    @JsonProperty("patronymic")
    private String patronymic;

    @Column(name="age")
    @JsonProperty("age")
    private Integer age;

    @Column(name="password")
    private String password;

    @Column(name="login")
    private String login;

    @Column(name="is_admin")
    private boolean isAdmin;

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPatronymic() {return patronymic;}

    public void setPatronymic(String patronymic) {this.patronymic = patronymic;}

    public Integer getAge() {return age;}

    public void setAge(Integer age) {this.age = age;}

    @JsonIgnore
    public String getPassword() {return password;}

    @JsonProperty("password")
    public void setPassword(String password) {this.password = password;}

    public String getLogin() {return login;}

    public void setLogin(String login) {this.login = login;}

    @JsonIgnore
    public boolean isAdmin() {return isAdmin;}

    @JsonProperty("is_admin")
    public void setAdmin(boolean admin) {isAdmin = admin;}
}
