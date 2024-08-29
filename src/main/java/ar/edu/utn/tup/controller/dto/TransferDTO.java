package ar.edu.utn.tup.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TransferDTO {
    @NotNull(message = "'amount' is mandatory.")
    @Min(value = 0, message = "'amount' must be greater than or equal to 0.")
    private double amount;
    @NotBlank(message = "'coinType' is mandatory.")
    private String coinType;
    @NotBlank(message = "'sourceCBU' is mandatory.")
    private String sourceCBU;
    @NotBlank(message = "'destinationCBU' is mandatory.")
    private String destinationCBU;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getSourceCBU() {
        return sourceCBU;
    }

    public void setSourceCBU(String sourceCBU) {
        this.sourceCBU = sourceCBU;
    }

    public String getDestinationCBU() {
        return destinationCBU;
    }

    public void setDestinationCBU(String destinationCBU) {
        this.destinationCBU = destinationCBU;
    }
}
