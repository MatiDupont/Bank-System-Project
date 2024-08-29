package ar.edu.utn.tup.service;

import ar.edu.utn.tup.controller.dto.BankAccountDTO;
import ar.edu.utn.tup.controller.dto.BankAccountMovementsDTO;
import ar.edu.utn.tup.controller.dto.CustomerDTO;
import ar.edu.utn.tup.controller.dto.TransactionDataDTO;
import ar.edu.utn.tup.controller.validator.BankAccountDTOValidator;
import ar.edu.utn.tup.model.BankAccount;
import ar.edu.utn.tup.model.Customer;
import ar.edu.utn.tup.model.Movement;
import ar.edu.utn.tup.model.enums.AccountType;
import ar.edu.utn.tup.model.enums.BankingEntities;
import ar.edu.utn.tup.model.enums.CoinType;
import ar.edu.utn.tup.persistence.BankAccountDAO;
import ar.edu.utn.tup.repository.BankAccountRepository;
import ar.edu.utn.tup.repository.CustomerRepository;
import ar.edu.utn.tup.repository.MovementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BankAccountService {
    private BankAccountDAO bankAccountDAO;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private MovementRepository movementRepository;
    @Autowired
    private CustomerService customerService;
    @Lazy
    private MovementsService movementsService;
    @Autowired
    private BankAccountDTOValidator bankAccountDTOValidator;
    public BankAccountService() {
        this.bankAccountDAO = new BankAccountDAO();
    }

    public BankAccount create(BankingEntities bankEntity, String alias, String securityCode, AccountType accountType, Customer customer, CoinType coin) {
        BankAccount bankAccount = new BankAccount(bankEntity, alias, securityCode, accountType, customer, coin);

        if (customer.getBankAccounts().stream().anyMatch(account -> account.getBankingEntities().equals(bankEntity))) {
            return null;
        }
        else {
            customer.addBankAccount(bankAccount);
            bankAccountDAO.create(bankAccount);
            return bankAccount;
        }
    }

    public List<BankAccount> getBankAccountsByCustomer(long customerId) {
        return bankAccountDAO.findBankAccountsByCustomer(customerId);
    }

    public BankAccount findBank(String numberAccount) {
        return bankAccountDAO.findByAccountNumber(numberAccount);
    }

    public void update(BankAccount bankAccount) {
        try {
            bankAccountDAO.edit(bankAccount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public BankAccount getBankAccountByCBUAlias(String CBUAlias) {
        return bankAccountDAO.findBankAccountByCBUAlias(CBUAlias);
    }

    // Metodos para el Controller REST
    public List<BankAccountDTO> getAllBankAccounts() {
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        return bankAccounts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<BankAccount> findBankAccountById(Long id) {
        return bankAccountRepository.findById(id);
    }

    public List<BankAccountDTO> getBankAccountsByBankEntity(BankingEntities bankingEntity) {
        List<BankAccount> bankAccounts = bankAccountRepository.findByBankingEntities(bankingEntity.name());

        return bankAccounts.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public BankAccountMovementsDTO getMovementsByBankAccount(Long id) {
        BankAccount bankAccount = findBankAccountById(id).orElseThrow(() -> new EntityNotFoundException("Bank account not found with id: " + id));

        List<Movement> incoming = movementRepository.getIncomingMovements(id);
        List<Movement> outcoming = movementRepository.getOutcomingMovements(id);

        return convertToDTO(bankAccount, incoming, outcoming);
    }

    public BankAccount createBankAccount(BankAccountDTO bankAccountDTO) {
        bankAccountDTOValidator.validate(bankAccountDTO);
        bankAccountDTOValidator.validateUniqueFieldsForCreate(bankAccountDTO);

        Customer customer = customerService.findCustomerByNID(bankAccountDTO.getCustomerNID());

        if (customer != null) {
            BankAccount bankAccount = convertToEntity(bankAccountDTO, customer);

            movementsService.createDeposit(bankAccount);
            return bankAccountRepository.save(bankAccount);
        }
        else {
            return null;
        }
    }

    public BankAccount updateBankAccount(Long id, BankAccountDTO bankAccountDTO) {
        bankAccountDTOValidator.validateUniqueFieldsForUpdate(id, bankAccountDTO);

        return bankAccountRepository.findById(id)
                .map(existingBankAccount -> {
                    existingBankAccount.setAlias(bankAccountDTO.getAlias());
                    existingBankAccount.setSecurityCode(bankAccountDTO.getSecurityCode());

                    return bankAccountRepository.save(existingBankAccount);
                })
                .orElse(null);
    }

    public boolean deleteBankAccount(Long id) {
        if (bankAccountRepository.existsById(id)) {
            bankAccountRepository.deleteById(id);

            return true;
        }
        else {
            return false;
        }
    }

    public BankAccountDTO convertToDTO(BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();

        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setBankEntity(bankAccount.getBankingEntities().getDescription());
        bankAccountDTO.setAlias(bankAccount.getAlias());
        bankAccountDTO.setAccountType(bankAccount.getAccountType().getDescription());
        bankAccountDTO.setCoinType(bankAccount.getCoin().getDescription());
        bankAccountDTO.setCustomerNID(bankAccount.getCustomer().getNID());
        bankAccountDTO.setCBU(bankAccount.getCBU());
        bankAccountDTO.setAccountNumber(bankAccount.getAccountNumber());
        bankAccountDTO.setSecurityCode(bankAccount.getSecurityCode());
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setOpeningDate(String.valueOf(bankAccount.getOpeningDate()));
        bankAccountDTO.setAccountState(String.valueOf(bankAccount.isAccountState()));

        return bankAccountDTO;
    }

    public BankAccount convertToEntity(BankAccountDTO bankAccountDTO, Customer customer) {
        BankAccount bankAccount = new BankAccount();

        bankAccount.setBankingEntities(BankingEntities.fromDescription(bankAccountDTO.getBankEntity()));
        bankAccount.setAlias(bankAccountDTO.getAlias());
        bankAccount.setAccountType(AccountType.fromDescription(bankAccountDTO.getAccountType()));
        bankAccount.setCoin(CoinType.fromDescription(bankAccountDTO.getCoinType()));
        bankAccount.setSecurityCode(bankAccountDTO.getSecurityCode());
        bankAccount.setAccountNumber(bankAccount.generateRandomNumber());
        bankAccount.setCBU(bankAccount.generateRandomCBU());
        bankAccount.setOpeningDate(bankAccount.convertToDateViaInstant(LocalDate.now()));
        bankAccount.setAccountState(true);
        bankAccount.setCustomer(customer);
        bankAccount.adjustBonusInit(bankAccount.getBankingEntities(), bankAccount.getCoin());
        bankAccount.setBalance(bankAccount.getBankingEntities().getBonusInit());

        return bankAccount;
    }

    public BankAccountDTO convertToDTO(Map<String, Object> jsonMap) {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.convertValue(jsonMap, BankAccountDTO.class);
    }

    public BankAccountMovementsDTO convertToDTO(BankAccount bankAccount, List<Movement> incoming, List<Movement> outcoming) {
        BankAccountMovementsDTO bankAccountMovementsDTO = new BankAccountMovementsDTO();

        bankAccountMovementsDTO.setId(bankAccount.getId());
        bankAccountMovementsDTO.setCBU(bankAccount.getCBU());

        List<TransactionDataDTO> transactions = new ArrayList<>();

        for (Movement movement : incoming) {
            transactions.add(convertToTransactionDataDTO(movement, true));
        }
        for (Movement movement : outcoming) {
            transactions.add(convertToTransactionDataDTO(movement, false));
        }

        bankAccountMovementsDTO.setTransactions(transactions);

        return bankAccountMovementsDTO;
    }

    public TransactionDataDTO convertToTransactionDataDTO(Movement movement, boolean isIncoming) {
        System.out.println(movement);
        TransactionDataDTO transactionDataDTO = new TransactionDataDTO();

        transactionDataDTO.setDate(String.valueOf(movement.getStartDate()));
        transactionDataDTO.setMotive(movement.getMotive());
        transactionDataDTO.setDescription(generateDescription(movement.getMotive(), isIncoming));
        transactionDataDTO.setAmount(movement.getAmount());

        return transactionDataDTO;
    }

    private String generateDescription(String motive, boolean isIncoming) {
        switch (motive) {
            case "Investment":
                return "Fixed-term investment made.";
            case "Deposit":
                return "Initial welcome bonus.";
            case "Withdrawal":
                return "Money withdrawn from the account";
            case "Transfer":
                return isIncoming ? "Money received from another account." : "Money transferred to another account.";
            default:
                return "General transaction.";
        }
    }

}
