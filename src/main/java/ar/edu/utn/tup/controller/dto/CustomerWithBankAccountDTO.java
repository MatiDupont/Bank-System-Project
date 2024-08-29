package ar.edu.utn.tup.controller.dto;

import java.util.Set;

public class CustomerWithBankAccountDTO {
    private Long id;
    private String name;
    private String lastName;
    private String NID;
    private Set<BankAccountDTO> bankAccounts;

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

    public String getNID() {
        return NID;
    }

    public void setNID(String NID) {
        this.NID = NID;
    }

    public Set<BankAccountDTO> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(Set<BankAccountDTO> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }
}
