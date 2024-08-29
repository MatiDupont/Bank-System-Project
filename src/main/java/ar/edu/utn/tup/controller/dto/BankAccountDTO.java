package ar.edu.utn.tup.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.Nullable;

public class BankAccountDTO {
    private Long id;
    @NotBlank(message = "'bankEntity' is mandatory.")
    private String bankEntity;
    @NotBlank(message = "'alias' is mandatory.")
    private String alias;
    @NotBlank(message = "'accountType' is mandatory.")
    private String accountType;
    @NotBlank(message = "'coinType' is mandatory.")
    private String coinType;
    @NotBlank(message = "'customerNID' is mandatory.")
    private String customerNID;
    @NotBlank(message = "'securityCode' is mandatory.")
    private String securityCode;
    @Nullable
    private String CBU;
    @Nullable
    private String accountNumber;
    @Nullable
    private double balance;
    @Nullable
    private String openingDate;
    @Nullable
    private String accountState;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankEntity() {
        return bankEntity;
    }

    public void setBankEntity(String bankEntity) {
        this.bankEntity = bankEntity;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getCustomerNID() {
        return customerNID;
    }

    public void setCustomerNID(String customerNID) {
        this.customerNID = customerNID;
    }

    public String getCBU() {
        return CBU;
    }

    public void setCBU(String CBU) {
        this.CBU = CBU;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }
}
