package ar.edu.utn.tup.controller.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public class CustomerDTO {
    private Long id;
    @NotBlank(message = "'name' is mandatory")
    private String name;
    @NotBlank(message = "'lastName' is mandatory")
    private String lastName;
    @NotBlank(message = "'address' is mandatory")
    private String address;
    @NotBlank(message = "'telephoneNumber' is mandatory")
    private String telephoneNumber;
    @NotBlank(message = "'email' is mandatory")
    @Email
    private String email;
    @NotBlank(message = "'nid' is mandatory")
    private String NID;
    @NotBlank(message = "'birthday' is mandatory")
    private String birthday;
    @NotBlank(message = "password' is mandatory")
    private String password;
    @Nullable
    private String profilePicturePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }
}
