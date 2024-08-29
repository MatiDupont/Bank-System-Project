package ar.edu.utn.tup.controller.dto;

import ar.edu.utn.tup.model.Movement;

import java.util.List;

public class BankAccountMovementsDTO {
    private Long id;
    private String CBU;
    private List<TransactionDataDTO> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCBU() {
        return CBU;
    }

    public void setCBU(String CBU) {
        this.CBU = CBU;
    }

    public List<TransactionDataDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDataDTO> transactions) {
        this.transactions = transactions;
    }
}
