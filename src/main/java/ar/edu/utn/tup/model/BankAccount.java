package ar.edu.utn.tup.model;

import ar.edu.utn.tup.model.enums.AccountType;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.model.enums.CoinType;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Entity
@Table(name = "bankAccount")
public class BankAccount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankAccountSeq")
    @SequenceGenerator(name = "bankAccountSeq", sequenceName = "BANK_ACCOUNT_SEQ", allocationSize = 1)
    private long id;
    @Column(name = "bankingEntity")
    @Enumerated(EnumType.STRING)
    private BankingEntities bankingEntities;
    @Column(name = "cbu")
    private String CBU;
    @Column(name = "alias")
    private String alias;
    @Column(name = "accountNumber")
    private String accountNumber;
    @Column(name = "securityCode")
    private String securityCode;
    @Column(name = "accountType")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "balance")
    private double balance;
    @Column(name = "openingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingDate;
    @Column(name = "coin")
    @Enumerated(EnumType.STRING)
    private CoinType coin;
    @Column(name = "accountState")
    private boolean accountState = false;
    @ManyToOne
    @JoinColumn(name = "customerNID")
    private Customer customer;
    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Movement> outgoingMovements = new ArrayList<>();
    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private ArrayList<Movement> incomingMovements = new ArrayList<>();

    public BankAccount() {
    }

    public BankAccount(BankingEntities bankingEntities, String alias, String securityCode, AccountType accountType, Customer customer, CoinType coin) {
        this.bankingEntities = bankingEntities;
        this.accountNumber = generateRandomNumber();
        this.CBU = generateRandomCBU();
        this.alias = alias;
        this.securityCode = securityCode;
        this.accountType = accountType;
        this.openingDate = convertToDateViaInstant(LocalDate.now());
        this.customer = customer;
        this.coin = coin;
        this.accountState = true;

        adjustBonusInit(bankingEntities, coin);
    }

    public void addOutGoingMovement(Movement movement) {
        this.outgoingMovements.add(movement);
    }

    public void addIncomingMovement(Movement movement) {
        this.incomingMovements.add(movement);
    }

    private Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    private String generateRandomNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }

        return accountNumber.toString();
    }

    private String generateRandomCBU() {
        Random random = new Random();
        StringBuilder CBU = new StringBuilder();

        // Codigo de entidad bancaria (3 digitos)
        CBU.append(String.format("%03d", this.bankingEntities.getEntityCode()));

        // Codigo de sucursal (4 digitos)
        CBU.append(String.format("%04d", this.bankingEntities.getBranchCode()));

        // Numero de cuenta (13 digitos)
        CBU.append(this.accountNumber);

        // Digito verificador del numero de cuenta (1 digito)
        CBU.append(random.nextInt(10));

        return CBU.toString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BankingEntities getBankingEntities() {
        return bankingEntities;
    }

    public void setBankingEntities(BankingEntities bankingEntities) {
        this.bankingEntities = bankingEntities;
    }

    public String getCBU() {
        return CBU;
    }

    public void setCBU(String CBU) {
        this.CBU = CBU;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CoinType getCoin() {
        return coin;
    }

    public void setCoin(CoinType coin) {
        this.coin = coin;
    }

    public boolean isAccountState() {
        return accountState;
    }

    public void setAccountState(boolean accountState) {
        this.accountState = accountState;
    }

    public List<Movement> getOutgoingMovements() {
        return outgoingMovements;
    }

    public void setOutgoingMovements(ArrayList<Movement> outgoingMovements) {
        this.outgoingMovements = outgoingMovements;
    }

    public ArrayList<Movement> getIncomingMovements() {
        return incomingMovements;
    }

    public void setIncomingMovements(ArrayList<Movement> incomingMovements) {
        this.incomingMovements = incomingMovements;
    }

    private void adjustBonusInit(BankingEntities bankingEntities, CoinType coin) {
        double exchangeRate = 1.0;

        try {
            if (coin != CoinType.ARS) {
                exchangeRate = ExchangeRateAPI.getExchangeRate("ARS", coin.name());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        bankingEntities.adjustBonusInit(exchangeRate);
    }
}
