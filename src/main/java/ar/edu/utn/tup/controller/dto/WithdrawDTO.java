package ar.edu.utn.tup.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class WithdrawDTO {
    @NotBlank(message = "'cbu' is mandatory.")
    private String CBU;
    @NotNull(message = "'amount' is mandatory.")
    @Min(value = 0, message = "'amount' must be greater than or equal to 0.")
    private Double amount;

    public String getCBU() {
        return CBU;
    }

    public void setCBU(String CBU) {
        this.CBU = CBU;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
