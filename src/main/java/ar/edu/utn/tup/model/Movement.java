package ar.edu.utn.tup.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "movement")
public class Movement {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movementsSeq")
    //@SequenceGenerator(name = "movementsSeq", sequenceName = "MOVEMENTS_SEQ", allocationSize = 1)
    private long id;
    @ManyToOne
    @JoinColumn(name = "source_account_id")
    @JsonIgnore
    private BankAccount sourceAccount;
    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    @JsonIgnore
    private BankAccount destinationAccount;
    @Column(name = "amount")
    private double amount;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "interest_rate")
    private double interestRate;
    @Column(name = "matured_amount")
    private double maturedAmount;
    @Column(name = "movement_status")
    private String status;
    @Column(name = "motive")
    private String motive;

    public Movement() {

    }

    public Movement(BankAccount sourceAccount, BankAccount destinationAccount, double amount, double maturedAmount, LocalDate startDate, LocalDate endDate, double interestRate, String motive, String status) {
        this.sourceAccount = sourceAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.maturedAmount = maturedAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.interestRate = interestRate;
        this.motive = motive;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(BankAccount sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public BankAccount getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(BankAccount destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return startDate;
    }

    public void setDate(LocalDate date) {
        this.startDate = date;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getMaturedAmount() {
        return maturedAmount;
    }

    public void setMaturedAmount(double maturedAmount) {
        this.maturedAmount = maturedAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMotive() {
        return motive;
    }

    public void setMotive(String motive) {
        this.motive = motive;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
