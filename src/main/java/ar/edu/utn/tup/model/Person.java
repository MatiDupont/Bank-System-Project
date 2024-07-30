package ar.edu.utn.tup.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

@MappedSuperclass
public class Person {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "personSeq")
    @SequenceGenerator(name = "personSeq", sequenceName = "PERSON_SEQ", allocationSize = 1)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "address")
    private String address;
    @Column(name = "telephone_number")
    private String telephoneNumber;
    @Column(name = "email")
    private String email;
    @Column(name = "nid")
    private String NID;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(name = "password")
    private String password;
    @Column(name = "profile_picture_path")
    private String profilePicturePath;

    public Person() {
    }

    public Person(String name, String lastName, String adress, String telephoneNumber, String email, String NID, Date birthday, String password, String imagePath) {
        this.name = name;
        this.lastName = lastName;
        this.address = adress;
        this.telephoneNumber = telephoneNumber;
        this.email = email;
        this.NID = NID;
        this.birthday = birthday;
        this.password = password;
        this.profilePicturePath = imagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    public LocalDate getBirthdayAsLocalDate() {
        return birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public int getAge() {
        LocalDate birthdayLocalDate = getBirthdayAsLocalDate();
        LocalDate currentDate = LocalDate.now();
        Period agePeriod = Period.between(birthdayLocalDate, currentDate);

        return agePeriod.getYears();
    }
}
