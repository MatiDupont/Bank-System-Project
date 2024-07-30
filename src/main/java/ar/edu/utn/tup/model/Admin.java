package ar.edu.utn.tup.model;

import jakarta.persistence.*;

import java.security.SecureRandom;
import java.util.Date;

@Entity
@Table(name = "admin")
public class Admin extends Person {
    @Column(name = "admin_code")
    private String adminCode;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGHT = 8;
    public Admin() {

    }
    public Admin(String name, String lastName, String adress, String telephoneNumber, String email, String NID, Date birthday, String password, String imagePath) {
        super(name, lastName, adress, telephoneNumber, email, NID, birthday, password, imagePath);
        this.adminCode = generateCode();
    }

    private String generateCode() {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGHT);

        for (int i = 0; i < CODE_LENGHT; i++) {
            int index = secureRandom.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(index));
        }

        return code.toString();
    }

    public String getAdminCode() {
        return adminCode;
    }

    public void setAdminCode(String adminCode) {
        this.adminCode = adminCode;
    }
}
