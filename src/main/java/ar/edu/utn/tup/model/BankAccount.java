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
@Table(name = "bank_account")
public class BankAccount {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bankAccountSeq")
    //@SequenceGenerator(name = "bankAccountSeq", sequenceName = "BANK_ACCOUNT_SEQ", allocationSize = 1)
    private long id;
    @Column(name = "banking_entity")
    @Enumerated(EnumType.STRING)
    private BankingEntities bankingEntities;
    @Column(name = "cbu")
    private String CBU;
    @Column(name = "alias")
    private String alias;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "security_code")
    private String securityCode;
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "balance")
    private double balance;
    @Column(name = "opening_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date openingDate;
    @Column(name = "coin")
    @Enumerated(EnumType.STRING)
    private CoinType coin;
    @Column(name = "account_state")
    private boolean accountState = false;
    @ManyToOne
    @JoinColumn(name = "customer_nid")
    private Customer customer;
    @OneToMany(mappedBy = "sourceAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movement> outcomingMovements = new ArrayList<>();
    @OneToMany(mappedBy = "destinationAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movement> incomingMovements = new ArrayList<>();

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
        this.outcomingMovements.add(movement);
    }

    public void addIncomingMovement(Movement movement) {
        this.incomingMovements.add(movement);
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public String generateRandomNumber() {
        Random random = new Random();
        StringBuilder accountNumber = new StringBuilder();

        for (int i = 0; i < 13; i++) {
            int digit = random.nextInt(10);
            accountNumber.append(digit);
        }

        return accountNumber.toString();
    }

    public String generateRandomCBU() {
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

    public List<Movement> getOutcomingMovements() {
        return outcomingMovements;
    }

    public void setOutcomingMovements(ArrayList<Movement> outcomingMovements) {
        this.outcomingMovements = outcomingMovements;
    }

    public List<Movement> getIncomingMovements() {
        return incomingMovements;
    }

    public void setIncomingMovements(List<Movement> incomingMovements) {
        this.incomingMovements = incomingMovements;
    }

    public void adjustBonusInit(BankingEntities bankingEntities, CoinType coin) {
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
