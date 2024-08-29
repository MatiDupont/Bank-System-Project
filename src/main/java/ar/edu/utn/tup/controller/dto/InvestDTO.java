package ar.edu.utn.tup.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class InvestDTO {
    @NotNull(message = "'amount' is mandatory.")
    @Min(value = 0, message = "'amount' must be greater than or equal to 0.")
    private double amount;
    @NotBlank(message = "'cbu' is mandatory.")
    private String CBU;
    @NotBlank(message = "'fixedTerm' is mandatory.")
    private String fixedTerm;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCBU() {
        return CBU;
    }

    public void setCBU(String CBU) {
        this.CBU = CBU;
    }

    public String getFixedTerm() {
        return fixedTerm;
    }

    public void setFixedTerm(String fixedTerm) {
        this.fixedTerm = fixedTerm;
    }
}
