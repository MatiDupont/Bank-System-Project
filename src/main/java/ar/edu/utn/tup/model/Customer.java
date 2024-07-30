package ar.edu.utn.tup.model;

import ar.edu.utn.tup.service.BankAccountService;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer extends Person {
    // mappedBy = "customer" --> indica que la relacion es bidireccional y que el campo
    // 'customer' en la clase 'BankAccount' mapea esta relacion (debe ser el nombre exacto
    // del atributo en la clase relacionada que establece la relacion inversa)
    // cascade = CascadeType.ALL --> define que las operaciones de persistencia realizadas
    // en un objeto 'Customer' tambien se aplican a sus cuentas bancarias
    // orphanRemoval = true --> indica que si una cuenta bancaria se elimina de la coleccion
    // 'counts', tambien se eliminara de la base de datos
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BankAccount> bankAccounts = new HashSet<>();

    public Set<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccount> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public Customer() {
    }

    public Customer(String name, String lastName, String adress, String telephoneNumber, String email, String NID, Date birthday, String password, String imagePath) {
        super(name, lastName, adress, telephoneNumber, email, NID, birthday, password, imagePath);
    }

    public void addBankAccount(BankAccount bankAccount) {
        this.bankAccounts.add(bankAccount);
        bankAccount.setCustomer(this);
    }
}
